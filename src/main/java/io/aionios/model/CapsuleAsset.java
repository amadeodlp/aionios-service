package io.aionios.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "capsule_assets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CapsuleAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capsule_id", nullable = false)
    private Capsule capsule;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetType type;

    @Column(nullable = false)
    private String value;

    @Column(name = "token_address")
    private String tokenAddress;

    @Column(name = "token_id")
    private String tokenId;

    @Column(name = "token_amount")
    private String tokenAmount;

    @Column(name = "is_transferred")
    private boolean transferred;

    public enum AssetType {
        ETH,
        ERC20,
        ERC721,
        ERC1155,
        DATA
    }
}
