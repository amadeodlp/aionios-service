package io.aionios.service.impl;

import io.aionios.service.IPFSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Primary
@Slf4j
public class MockIPFSServiceImpl implements IPFSService {

    private final Map<String, byte[]> contentStore = new HashMap<>();

    @Override
    public String uploadContent(MultipartFile file) throws IOException {
        try {
            // Generate a mock IPFS hash
            String hash = "Qm" + UUID.randomUUID().toString().replace("-", "");
            
            // Store the content in memory
            contentStore.put(hash, file.getBytes());
            
            log.info("Mock IPFS: Uploaded content with hash {}", hash);
            return hash;
        } catch (IOException e) {
            log.error("Error in mock IPFS upload", e);
            throw e;
        }
    }

    @Override
    public byte[] getContent(String ipfsHash) throws IOException {
        if (!contentStore.containsKey(ipfsHash)) {
            log.error("Content not found in mock IPFS: {}", ipfsHash);
            throw new IOException("Content not found");
        }
        
        log.info("Mock IPFS: Retrieved content with hash {}", ipfsHash);
        return contentStore.get(ipfsHash);
    }

    @Override
    public boolean contentExists(String ipfsHash) {
        boolean exists = contentStore.containsKey(ipfsHash);
        log.info("Mock IPFS: Checking if content exists: {} - {}", ipfsHash, exists);
        return exists;
    }
}
