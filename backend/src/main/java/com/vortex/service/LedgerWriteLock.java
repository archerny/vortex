package com.vortex.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** Short write coordination only; historical restrictions come from processed events. */
@Component
public class LedgerWriteLock {
    private static final long LOCK_KEY = 864827905314L;
    @PersistenceContext private EntityManager entityManager;

    @Transactional(propagation = Propagation.MANDATORY)
    public void acquire() {
        Object isolation = entityManager.createNativeQuery("SELECT current_setting('transaction_isolation')")
                .getSingleResult();
        if (!"read committed".equals(isolation)) {
            throw new IllegalStateException("Ledger writes require READ COMMITTED transaction isolation");
        }
        entityManager.createNativeQuery("SELECT 1 FROM pg_advisory_xact_lock(" + LOCK_KEY + ")")
                .getSingleResult();
    }
}
