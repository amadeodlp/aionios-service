package io.aionios.repository;

import io.aionios.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByWalletAddress(String walletAddress);
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByUsername(String username);
    
    boolean existsByWalletAddress(String walletAddress);
    
    boolean existsByEmail(String email);
    
    boolean existsByUsername(String username);
}
