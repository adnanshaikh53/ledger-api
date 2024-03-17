package org.hsbc.ledgerapi.repository;

import org.hsbc.ledgerapi.commons.enums.AccountState;
import org.hsbc.ledgerapi.commons.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    @Modifying
    @Query(value = "UPDATE ACCOUNT SET status = :state WHERE ID = :id", nativeQuery = true)
    void updateAccountState(@Param("state")AccountState state,@Param("id") Long id);
}
