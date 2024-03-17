package org.hsbc.ledgerapi.repository;

import org.hsbc.ledgerapi.commons.model.LedgerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends JpaRepository<LedgerEntity,Long> {
}
