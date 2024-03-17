package org.hsbc.ledgerapi.commons.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import org.hsbc.ledgerapi.commons.enums.PostingState;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "LEDGER_TRANSACTION")
public class LedgerTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "source_wallet_id")
    private Wallet sourceWallet;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "destination_wallet_id")
    private Wallet destinationWallet;

    private BigDecimal amount;

    private BigDecimal exchangeRate;

    private Date timestamp;

    @Enumerated(EnumType.STRING)
    private PostingState state;
}

