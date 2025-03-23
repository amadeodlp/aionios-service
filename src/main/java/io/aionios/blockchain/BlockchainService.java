package io.aionios.blockchain;

import io.aionios.model.Capsule;

public interface BlockchainService {

    /**
     * Creates a time capsule on the blockchain
     * 
     * @param title The capsule title
     * @param ipfsHash The IPFS hash of the content
     * @param creatorAddress The address of the creator
     * @param recipientAddress The address of the recipient
     * @param conditionType The type of condition for opening
     * @param conditionData Additional data for the condition
     * @return The blockchain ID of the created capsule
     * @throws Exception If there's an error creating the capsule on the blockchain
     */
    String createCapsule(
            String title,
            String ipfsHash,
            String creatorAddress,
            String recipientAddress,
            Capsule.ConditionType conditionType,
            String conditionData) throws Exception;
    
    /**
     * Opens a capsule on the blockchain
     * 
     * @param blockchainId The blockchain ID of the capsule
     * @param requesterAddress The address requesting to open the capsule
     * @return true if the capsule was successfully opened, false otherwise
     * @throws Exception If there's an error opening the capsule on the blockchain
     */
    boolean openCapsule(String blockchainId, String requesterAddress) throws Exception;
    
    /**
     * Checks if a capsule is ready to be opened on the blockchain
     * 
     * @param blockchainId The blockchain ID of the capsule
     * @return true if the capsule is ready to be opened, false otherwise
     * @throws Exception If there's an error checking the capsule on the blockchain
     */
    boolean isCapsuleReadyToOpen(String blockchainId) throws Exception;
    
    /**
     * Gets the blockchain status of a capsule
     * 
     * @param blockchainId The blockchain ID of the capsule
     * @return The status of the capsule
     * @throws Exception If there's an error getting the capsule status from the blockchain
     */
    Capsule.CapsuleStatus getCapsuleStatus(String blockchainId) throws Exception;
}
