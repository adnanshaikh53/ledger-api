package org.hsbc.ledgerapi.commons.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hsbc.ledgerapi.commons.enums.AccountState;

import java.util.List;

@jakarta.persistence.Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ACCOUNT")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private AccountState status;

    @ManyToOne(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "entity_id")
    private LedgerEntity ledgerEntity;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Wallet> wallets;
}

