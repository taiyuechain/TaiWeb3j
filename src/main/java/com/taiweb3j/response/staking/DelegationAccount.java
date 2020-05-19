package com.taiweb3j.response.staking;


public class DelegationAccount {

    public String saAddress;

    public String delegate;

    public String validDelegate;

    public ImpawnUnit unit ;


    public String getDelegate() {
        return delegate;
    }

    public void setDelegate(String delegate) {
        this.delegate = delegate;
    }

    public String getValidDelegate() {
        return validDelegate;
    }

    public void setValidDelegate(String validDelegate) {
        this.validDelegate = validDelegate;
    }

    public String getSaAddress() {
        return saAddress;
    }

    public void setSaAddress(String saAddress) {
        this.saAddress = saAddress;
    }

    public ImpawnUnit getUnit() {
        return unit;
    }

    public void setUnit(ImpawnUnit unit) {
        this.unit = unit;
    }
}
