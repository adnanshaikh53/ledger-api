package org.hsbc.ledgerapi.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hsbc.ledgerapi.configuration.database.DatabaseContextHolder;
import org.hsbc.ledgerapi.configuration.database.DatabaseEnvironment;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class TransactionReadOnlyAspect {

    @Around("@annotation(transactional)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, org.springframework.transaction.annotation.Transactional transactional) throws Throwable {
        System.out.println("Aspect executed");
        try {
            if (transactional.readOnly()) {
                DatabaseContextHolder.set(DatabaseEnvironment.READONLY);
            }
            return proceedingJoinPoint.proceed();
        } finally {
            DatabaseContextHolder.reset();
        }
    }
}