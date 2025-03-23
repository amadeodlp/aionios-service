package io.aionios.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPFSService {

    /**
     * Uploads content to IPFS
     * 
     * @param file The file to upload
     * @return The IPFS hash of the uploaded content
     * @throws IOException If there's an error uploading the content
     */
    String uploadContent(MultipartFile file) throws IOException;
    
    /**
     * Retrieves content from IPFS
     * 
     * @param ipfsHash The IPFS hash of the content to retrieve
     * @return The content as a byte array
     * @throws IOException If there's an error retrieving the content
     */
    byte[] getContent(String ipfsHash) throws IOException;
    
    /**
     * Checks if content exists on IPFS
     * 
     * @param ipfsHash The IPFS hash to check
     * @return true if the content exists, false otherwise
     */
    boolean contentExists(String ipfsHash);
}
