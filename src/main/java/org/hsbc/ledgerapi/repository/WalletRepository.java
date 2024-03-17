package org.hsbc.ledgerapi.repository;

import org.hsbc.ledgerapi.commons.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
}
