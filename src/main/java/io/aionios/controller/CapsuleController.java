package io.aionios.controller;

import io.aionios.model.Capsule;
import io.aionios.service.CapsuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/capsules")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class CapsuleController {

    private final CapsuleService capsuleService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Capsule> createCapsule(@RequestPart("capsule") Capsule capsule,
                                                @RequestPart(value = "content", required = false) MultipartFile content) {
        log.info("Creating new capsule: {}", capsule.getTitle());
        Capsule createdCapsule = capsuleService.createCapsule(capsule, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCapsule);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Capsule> getCapsuleById(@PathVariable Long id) {
        log.info("Fetching capsule by ID: {}", id);
        return capsuleService.getCapsuleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/blockchain/{blockchainId}")
    public ResponseEntity<Capsule> getCapsuleByBlockchainId(@PathVariable String blockchainId) {
        log.info("Fetching capsule by blockchain ID: {}", blockchainId);
        return capsuleService.getCapsuleByBlockchainId(blockchainId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/creator/{address}")
    public ResponseEntity<List<Capsule>> getCapsulesByCreator(@PathVariable String address) {
        log.info("Fetching capsules by creator: {}", address);
        List<Capsule> capsules = capsuleService.getCapsulesByCreator(address);
        return ResponseEntity.ok(capsules);
    }

    @GetMapping("/recipient/{address}")
    public ResponseEntity<List<Capsule>> getCapsulesByRecipient(@PathVariable String address) {
        log.info("Fetching capsules by recipient: {}", address);
        List<Capsule> capsules = capsuleService.getCapsulesByRecipient(address);
        return ResponseEntity.ok(capsules);
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<List<Capsule>> getCapsulesByAddress(@PathVariable String address) {
        log.info("Fetching capsules by address (creator or recipient): {}", address);
        List<Capsule> capsules = capsuleService.getCapsulesByAddress(address);
        return ResponseEntity.ok(capsules);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Capsule> updateCapsuleStatus(@PathVariable Long id,
                                                     @RequestParam Capsule.CapsuleStatus status) {
        log.info("Updating capsule status: {} to {}", id, status);
        Capsule updatedCapsule = capsuleService.updateCapsuleStatus(id, status);
        return ResponseEntity.ok(updatedCapsule);
    }

    @PostMapping("/{id}/open")
    public ResponseEntity<Capsule> openCapsule(@PathVariable Long id,
                                             @RequestParam String requesterAddress) {
        log.info("Opening capsule: {} by {}", id, requesterAddress);
        return capsuleService.openCapsule(id, requesterAddress)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
    
    @GetMapping("/explore/popular")
    public ResponseEntity<List<Capsule>> getPopularCapsules(@RequestParam(defaultValue = "10") int limit) {
        log.info("Fetching popular capsules, limit: {}", limit);
        List<Capsule> capsules = capsuleService.getPopularCapsules(limit);
        return ResponseEntity.ok(capsules);
    }
    
    @GetMapping("/explore/featured")
    public ResponseEntity<List<Capsule>> getFeaturedCapsules() {
        log.info("Fetching featured capsules");
        List<Capsule> capsules = capsuleService.getFeaturedCapsules();
        return ResponseEntity.ok(capsules);
    }
    
    @GetMapping("/explore/recent")
    public ResponseEntity<List<Capsule>> getRecentlyOpenedCapsules(@RequestParam(defaultValue = "10") int limit) {
        log.info("Fetching recently opened capsules, limit: {}", limit);
        List<Capsule> capsules = capsuleService.getRecentlyOpenedCapsules(limit);
        return ResponseEntity.ok(capsules);
    }
    
    @GetMapping("/explore/subscribed")
    public ResponseEntity<List<Capsule>> getMostSubscribedCapsules(@RequestParam(defaultValue = "10") int limit) {
        log.info("Fetching most subscribed capsules, limit: {}", limit);
        List<Capsule> capsules = capsuleService.getMostSubscribedCapsules(limit);
        return ResponseEntity.ok(capsules);
    }
    
    @PostMapping("/{id}/view")
    public ResponseEntity<Capsule> incrementViewCount(@PathVariable Long id) {
        log.info("Incrementing view count for capsule: {}", id);
        return capsuleService.incrementViewCount(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/{id}/share")
    public ResponseEntity<Capsule> incrementShareCount(@PathVariable Long id) {
        log.info("Incrementing share count for capsule: {}", id);
        return capsuleService.incrementShareCount(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/{id}/subscribe")
    public ResponseEntity<Capsule> subscribeToCapsule(@PathVariable Long id, 
                                                   @RequestParam String userAddress) {
        log.info("User {} subscribing to capsule: {}", userAddress, id);
        return capsuleService.subscribeToCapsule(id, userAddress)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
