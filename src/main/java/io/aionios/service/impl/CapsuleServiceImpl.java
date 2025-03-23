package io.aionios.service.impl;

import io.aionios.blockchain.BlockchainService;
import io.aionios.model.Capsule;
import io.aionios.repository.CapsuleRepository;
import io.aionios.service.CapsuleService;
import io.aionios.service.IPFSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CapsuleServiceImpl implements CapsuleService {

    private final CapsuleRepository capsuleRepository;
    private final BlockchainService blockchainService;
    private final IPFSService ipfsService;

    @Override
    @Transactional
    public Capsule createCapsule(Capsule capsule, MultipartFile content) {
        log.info("Creating new time capsule: {}", capsule.getTitle());
        
        // If content is provided, store it on IPFS
        if (content != null && !content.isEmpty()) {
            try {
                String ipfsHash = ipfsService.uploadContent(content);
                capsule.setIpfsHash(ipfsHash);
                log.info("Content uploaded to IPFS with hash: {}", ipfsHash);
            } catch (Exception e) {
                log.error("Failed to upload content to IPFS", e);
                throw new RuntimeException("Failed to upload content to IPFS", e);
            }
        }
        
        // Create the capsule on the blockchain
        try {
            String blockchainId = blockchainService.createCapsule(
                capsule.getTitle(),
                capsule.getIpfsHash(),
                capsule.getCreatorAddress(),
                capsule.getRecipientAddress(),
                capsule.getConditionType(),
                capsule.getConditionData()
            );
            
            capsule.setBlockchainId(blockchainId);
            log.info("Capsule created on blockchain with ID: {}", blockchainId);
        } catch (Exception e) {
            log.error("Failed to create capsule on blockchain", e);
            throw new RuntimeException("Failed to create capsule on blockchain", e);
        }
        
        // Set initial status
        capsule.setStatus(Capsule.CapsuleStatus.SEALED);
        
        // Save to database
        return capsuleRepository.save(capsule);
    }

    @Override
    public Optional<Capsule> getCapsuleById(Long id) {
        return capsuleRepository.findById(id);
    }

    @Override
    public Optional<Capsule> getCapsuleByBlockchainId(String blockchainId) {
        return capsuleRepository.findByBlockchainId(blockchainId);
    }

    @Override
    public List<Capsule> getCapsulesByCreator(String creatorAddress) {
        return capsuleRepository.findByCreatorAddress(creatorAddress);
    }

    @Override
    public List<Capsule> getCapsulesByRecipient(String recipientAddress) {
        return capsuleRepository.findByRecipientAddress(recipientAddress);
    }

    @Override
    public List<Capsule> getCapsulesByAddress(String address) {
        return capsuleRepository.findByCreatorOrRecipient(address);
    }

    @Override
    @Transactional
    public Capsule updateCapsuleStatus(Long id, Capsule.CapsuleStatus status) {
        Capsule capsule = capsuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Capsule not found"));
                
        capsule.setStatus(status);
        
        if (status == Capsule.CapsuleStatus.OPENED) {
            capsule.setOpenedAt(LocalDateTime.now());
        }
        
        return capsuleRepository.save(capsule);
    }

    @Override
    @Transactional
    public Optional<Capsule> openCapsule(Long id, String requesterAddress) {
        Optional<Capsule> optionalCapsule = capsuleRepository.findById(id);
        
        if (optionalCapsule.isEmpty()) {
            return Optional.empty();
        }
        
        Capsule capsule = optionalCapsule.get();
        
        // Check if the capsule is ready to be opened
        if (capsule.getStatus() != Capsule.CapsuleStatus.SEALED && 
            capsule.getStatus() != Capsule.CapsuleStatus.READY_TO_OPEN) {
            log.warn("Attempt to open capsule in invalid state: {}", capsule.getStatus());
            return Optional.empty();
        }
        
        // Check if the requester is authorized to open the capsule
        if (!requesterAddress.equalsIgnoreCase(capsule.getRecipientAddress())) {
            log.warn("Unauthorized attempt to open capsule by: {}", requesterAddress);
            return Optional.empty();
        }
        
        // For time-based capsules, check if it's time to open
        if (capsule.getConditionType() == Capsule.ConditionType.TIME) {
            if (capsule.getOpenDate().isAfter(LocalDateTime.now())) {
                log.warn("Attempt to open time-based capsule before open date");
                return Optional.empty();
            }
        }
        
        // TODO: Implement checks for other condition types
        
        try {
            // Open the capsule on the blockchain
            boolean success = blockchainService.openCapsule(
                capsule.getBlockchainId(),
                requesterAddress
            );
            
            if (success) {
                capsule.setStatus(Capsule.CapsuleStatus.OPENED);
                capsule.setOpenedAt(LocalDateTime.now());
                capsuleRepository.save(capsule);
                log.info("Capsule {} successfully opened by {}", id, requesterAddress);
                return Optional.of(capsule);
            } else {
                log.error("Failed to open capsule on blockchain");
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Error opening capsule on blockchain", e);
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    @Scheduled(cron = "${capsule.scheduler.cron}")
    public int processCapsulesDueForOpening() {
        log.info("Checking for capsules due for opening");
        List<Capsule> readyCapsules = capsuleRepository.findCapsulesReadyToOpen(LocalDateTime.now());
        
        int count = 0;
        for (Capsule capsule : readyCapsules) {
            capsule.setStatus(Capsule.CapsuleStatus.READY_TO_OPEN);
            capsuleRepository.save(capsule);
            count++;
        }
        
        log.info("Found {} capsules ready to be opened", count);
        return count;
    }
}