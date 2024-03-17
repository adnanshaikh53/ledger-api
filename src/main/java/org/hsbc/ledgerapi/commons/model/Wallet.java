package org.hsbc.ledgerapi.commons.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hsbc.ledgerapi.commons.enums.AssetType;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WALLET")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "account_id")
    private Account account;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    private String identifier;

    @OneToMany(mappedBy = "sourceWallet", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LedgerTransaction> outgoingLedgerTransactions;

    @OneToMany(mappedBy = "destinationWallet", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LedgerTransaction> incomingLedgerTransactions;
}
