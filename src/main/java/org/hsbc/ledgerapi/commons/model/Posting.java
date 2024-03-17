package org.hsbc.ledgerapi.commons.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hsbc.ledgerapi.commons.enums.PostingState;

import java.math.BigDecimal;
@Data
@Entity
@Table(name = "POSTING")
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_wallet_id")
    private Wallet sourceWallet;

    @ManyToOne
    @JoinColumn(name = "destination_wallet_id")
    private Wallet destinationWallet;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PostingState state;

}
