package io.aionios.service;

import io.aionios.model.Capsule;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CapsuleService {

    /**
     * Creates a new time capsule
     * 
     * @param capsule The capsule to create
     * @param content The content file to store (optional)
     * @return The created capsule
     */
    Capsule createCapsule(Capsule capsule, MultipartFile content);
    
    /**
     * Gets a capsule by its ID
     * 
     * @param id The capsule ID
     * @return The capsule if found
     */
    Optional<Capsule> getCapsuleById(Long id);
    
    /**
     * Gets a capsule by its blockchain ID
     * 
     * @param blockchainId The blockchain ID
     * @return The capsule if found
     */
    Optional<Capsule> getCapsuleByBlockchainId(String blockchainId);
    
    /**
     * Gets all capsules created by a specific address
     * 
     * @param creatorAddress The creator's blockchain address
     * @return List of capsules
     */
    List<Capsule> getCapsulesByCreator(String creatorAddress);
    
    /**
     * Gets all capsules for a specific recipient
     * 
     * @param recipientAddress The recipient's blockchain address
     * @return List of capsules
     */
    List<Capsule> getCapsulesByRecipient(String recipientAddress);
    
    /**
     * Gets all capsules associated with an address (as creator or recipient)
     * 
     * @param address The blockchain address
     * @return List of capsules
     */
    List<Capsule> getCapsulesByAddress(String address);
    
    /**
     * Updates a capsule's status
     * 
     * @param id The capsule ID
     * @param status The new status
     * @return The updated capsule
     */
    Capsule updateCapsuleStatus(Long id, Capsule.CapsuleStatus status);
    
    /**
     * Attempts to open a capsule
     * 
     * @param id The capsule ID
     * @param requesterAddress The address requesting to open the capsule
     * @return The opened capsule if successful
     */
    Optional<Capsule> openCapsule(Long id, String requesterAddress);
    
    /**
     * Checks and updates capsules that are ready to be opened
     * 
     * @return The number of capsules updated
     */
    int processCapsulesDueForOpening();
    
    /**
     * Gets popular capsules based on view count
     *
     * @param limit Maximum number of capsules to return
     * @return List of popular capsules
     */
    List<Capsule> getPopularCapsules(int limit);
    
    /**
     * Gets featured capsules (manually curated)
     *
     * @return List of featured capsules
     */
    List<Capsule> getFeaturedCapsules();
    
    /**
     * Gets recently opened capsules
     *
     * @param limit Maximum number of capsules to return
     * @return List of recently opened capsules
     */
    List<Capsule> getRecentlyOpenedCapsules(int limit);
    
    /**
     * Gets most subscribed sealed capsules
     *
     * @param limit Maximum number of capsules to return
     * @return List of most subscribed capsules
     */
    List<Capsule> getMostSubscribedCapsules(int limit);
    
    /**
     * Increments the view count for a capsule
     *
     * @param id The capsule ID
     * @return The updated capsule
     */
    Optional<Capsule> incrementViewCount(Long id);
    
    /**
     * Increments the share count for a capsule
     *
     * @param id The capsule ID
     * @return The updated capsule
     */
    Optional<Capsule> incrementShareCount(Long id);
    
    /**
     * Subscribes a user to a capsule (to be notified when it opens)
     *
     * @param capsuleId The capsule ID
     * @param userAddress The user's blockchain address
     * @return The updated capsule
     */
    Optional<Capsule> subscribeToCapsule(Long capsuleId, String userAddress);
}
