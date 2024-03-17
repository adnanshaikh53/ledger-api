package org.hsbc.ledgerapi.repository;

import org.hsbc.ledgerapi.commons.model.LedgerTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<LedgerTransaction,Long> {

    List<LedgerTransaction> findAllBySourceWalletIdOrDestinationWalletIdAndTimestampBefore(Long walletId, Long walletId1, Date timestamp);
}
