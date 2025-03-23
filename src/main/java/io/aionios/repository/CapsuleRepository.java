package io.aionios.repository;

import io.aionios.model.Capsule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CapsuleRepository extends JpaRepository<Capsule, Long> {

    List<Capsule> findByCreatorAddress(String creatorAddress);
    
    List<Capsule> findByRecipientAddress(String recipientAddress);
    
    List<Capsule> findByStatus(Capsule.CapsuleStatus status);
    
    Optional<Capsule> findByBlockchainId(String blockchainId);
    
    @Query("SELECT c FROM Capsule c WHERE c.status = 'SEALED' AND c.conditionType = 'TIME' AND c.openDate <= ?1")
    List<Capsule> findCapsulesReadyToOpen(LocalDateTime currentTime);
    
    @Query("SELECT c FROM Capsule c WHERE c.creatorAddress = ?1 OR c.recipientAddress = ?1")
    List<Capsule> findByCreatorOrRecipient(String address);
    
    // Popular capsules - based on view count
    @Query("SELECT c FROM Capsule c WHERE c.status = 'SEALED' OR c.status = 'OPENED' ORDER BY c.viewCount DESC")
    List<Capsule> findPopularCapsules(org.springframework.data.domain.Pageable pageable);
    
    // Featured capsules - manually curated
    List<Capsule> findByFeaturedTrue();
    
    // Recently opened capsules
    @Query("SELECT c FROM Capsule c WHERE c.status = 'OPENED' ORDER BY c.openedAt DESC")
    List<Capsule> findRecentlyOpenedCapsules(org.springframework.data.domain.Pageable pageable);
    
    // Most subscribed capsules
    @Query("SELECT c FROM Capsule c WHERE c.status = 'SEALED' ORDER BY c.subscriptionCount DESC")
    List<Capsule> findMostSubscribedCapsules(org.springframework.data.domain.Pageable pageable);
}
