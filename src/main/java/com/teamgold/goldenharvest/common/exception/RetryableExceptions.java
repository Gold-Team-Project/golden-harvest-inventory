package com.teamgold.goldenharvest.common.exception;


import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.dao.QueryTimeoutException;

public class RetryableExceptions {
    public static boolean isRetryable(Exception e) {
        return e instanceof OptimisticLockingFailureException ||
                e instanceof PessimisticLockingFailureException ||
                e instanceof QueryTimeoutException ||
                e instanceof DataAccessResourceFailureException;
    }
}
