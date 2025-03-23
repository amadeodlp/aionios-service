package io.aionios.blockchain;

import io.aionios.model.Capsule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Primary
@Slf4j
public class MockBlockchainService implements BlockchainService {

    private final Map<String, Capsule.CapsuleStatus> capsuleStatuses = new HashMap<>();

    @Override
    public String createCapsule(
            String title, 
            String ipfsHash, 
            String creatorAddress, 
            String recipientAddress, 
            Capsule.ConditionType conditionType, 
            String conditionData) {
        
        String blockchainId = "mock_" + UUID.randomUUID().toString();
        capsuleStatuses.put(blockchainId, Capsule.CapsuleStatus.SEALED);
        
        log.info("Mock blockchain service: Created capsule with ID {}", blockchainId);
        return blockchainId;
    }

    @Override
    public boolean openCapsule(String blockchainId, String requesterAddress) {
        if (!capsuleStatuses.containsKey(blockchainId)) {
            return false;
        }
        
        capsuleStatuses.put(blockchainId, Capsule.CapsuleStatus.OPENED);
        log.info("Mock blockchain service: Opened capsule with ID {}", blockchainId);
        return true;
    }

    @Override
    public boolean isCapsuleReadyToOpen(String blockchainId) {
        return capsuleStatuses.containsKey(blockchainId);
    }

    @Override
    public Capsule.CapsuleStatus getCapsuleStatus(String blockchainId) {
        return capsuleStatuses.getOrDefault(blockchainId, Capsule.CapsuleStatus.SEALED);
    }
}
