package com.taiweb3j.sample.delegate;

import java.math.BigDecimal;

public class MyDelegateResult {
    private BigDecimal delegate;
    private BigDecimal lock;
    private BigDecimal unlock;

    public BigDecimal getDelegate() {
        return delegate;
    }

    public void setDelegate(BigDecimal delegate) {
        this.delegate = delegate;
    }

    public BigDecimal getLock() {
        return lock;
    }

    public void setLock(BigDecimal lock) {
        this.lock = lock;
    }

    public BigDecimal getUnlock() {
        return unlock;
    }

    public void setUnlock(BigDecimal unlock) {
        this.unlock = unlock;
    }
}
