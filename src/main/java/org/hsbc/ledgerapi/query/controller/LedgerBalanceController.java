package org.hsbc.ledgerapi.query.controller;

import org.hsbc.ledgerapi.query.service.LedgerBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/ledger/api/v1/balance")
public class LedgerBalanceController {

    private final LedgerBalanceService ledgerService;

    @Autowired
    public LedgerBalanceController(LedgerBalanceService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @GetMapping("/wallets/{walletId}/latest-balance")
    public BigDecimal getLatestBalance(@PathVariable Long walletId) {
        return ledgerService.getLatestBalance(walletId);
    }

    @GetMapping("/wallets/{walletId}/historical-balance")
    public BigDecimal getHistoricalBalance(@PathVariable Long walletId, @RequestParam String timestamp) {
        Date val = new Date(timestamp);
        return ledgerService.getHistoricalBalance(walletId, val);
    }
}
