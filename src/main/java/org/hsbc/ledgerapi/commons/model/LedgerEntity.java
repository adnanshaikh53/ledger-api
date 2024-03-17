package org.hsbc.ledgerapi.commons.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hsbc.ledgerapi.commons.enums.EntityType;

import java.util.List;

@jakarta.persistence.Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ENTITY")
public class LedgerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private EntityType type;

    @OneToMany(mappedBy = "ledgerEntity", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Account> accounts;

}
