package io.aionios.config;

import io.aionios.blockchain.BlockchainService;
import io.aionios.blockchain.MockBlockchainService;
import io.aionios.service.IPFSService;
import io.aionios.service.impl.MockIPFSServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BeanConfig {

    // Removed duplicated IPFSService bean definition to resolve conflict
    // MockIPFSServiceImpl is already defined as a @Service with @Primary

    // Removed duplicated BlockchainService bean definition to resolve conflict
    // MockBlockchainService is already defined as a @Service with @Primary
}
