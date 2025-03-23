package io.aionios.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "capsules")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Capsule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(name = "blockchain_id")
    private String blockchainId;

    @Column(name = "creator_address", nullable = false)
    private String creatorAddress;

    @Column(name = "recipient_address")
    private String recipientAddress;

    @Column(name = "content_hash")
    private String contentHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CapsuleStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "open_date")
    private LocalDateTime openDate;

    @Column(name = "opened_at")
    private LocalDateTime openedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_type", nullable = false)
    private ConditionType conditionType;

    @Column(name = "condition_data", columnDefinition = "TEXT")
    private String conditionData;

    @Column(name = "transaction_hash")
    private String transactionHash;

    @Column(name = "ipfs_hash")
    private String ipfsHash;
    
    @Column(name = "view_count")
    private Integer viewCount = 0;
    
    @Column(name = "share_count")
    private Integer shareCount = 0;
    
    @Column(name = "subscription_count")
    private Integer subscriptionCount = 0;
    
    @Column(name = "featured")
    private Boolean featured = false;

    @OneToMany(mappedBy = "capsule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CapsuleAsset> assets = new HashSet<>();

    public enum CapsuleStatus {
        DRAFT,
        PENDING,
        SEALED,
        READY_TO_OPEN,
        OPENED,
        FAILED
    }

    public enum ConditionType {
        TIME,
        MULTISIG,
        ORACLE,
        COMPOUND
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = CapsuleStatus.DRAFT;
        }
    }
}
