package io.aionios.service.impl;

import io.aionios.service.IPFSService;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * Real implementation of IPFSService that connects to an actual IPFS node.
 * Currently disabled in favor of the mock implementation.
 */
@Slf4j
public class IPFSServiceImpl implements IPFSService {

    private final IPFS ipfs;

    public IPFSServiceImpl(
            @Value("${ipfs.node.host}") String ipfsHost,
            @Value("${ipfs.node.port}") int ipfsPort) {
        this.ipfs = new IPFS(ipfsHost, ipfsPort);
    }

    @Override
    public String uploadContent(MultipartFile file) throws IOException {
        try {
            // Convert MultipartFile to NamedStreamable.InputStreamWrapper
            NamedStreamable.InputStreamWrapper is = new NamedStreamable.InputStreamWrapper(
                    file.getOriginalFilename(), file.getInputStream());

            // Add the file to IPFS
            MerkleNode response = ipfs.add(is).get(0);
            log.info("Successfully uploaded content to IPFS: {}", response.hash);
            
            return response.hash.toString();
        } catch (IOException e) {
            log.error("Error uploading to IPFS", e);
            throw e;
        }
    }

    @Override
    public byte[] getContent(String ipfsHash) throws IOException {
        try {
            Multihash filePointer = Multihash.fromBase58(ipfsHash);
            byte[] content = ipfs.cat(filePointer);
            log.info("Successfully retrieved content from IPFS: {}", ipfsHash);
            
            return content;
        } catch (IOException e) {
            log.error("Error retrieving from IPFS", e);
            throw e;
        }
    }

    @Override
    public boolean contentExists(String ipfsHash) {
        try {
            Multihash filePointer = Multihash.fromBase58(ipfsHash);
            ipfs.cat(filePointer);
            return true;
        } catch (Exception e) {
            log.warn("Content does not exist in IPFS: {}", ipfsHash);
            return false;
        }
    }
}