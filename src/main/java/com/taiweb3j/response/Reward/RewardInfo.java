package com.taiweb3j.response.Reward;

import java.math.BigInteger;

public class RewardInfo {
    public String Address;

    public String Amount;

    public String Staking;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public BigInteger getAmount() {
        return new BigInteger(Amount);
    }

    public String getAmountRaw() {
        return Amount;
    }


    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getStaking() {
        return Staking;
    }

    public void setStaking(String staking) {
        Staking = staking;
    }

    @Override
    public String toString() {
        return "RewardInfo{" +
                "Address='" + Address + '\'' +
                ", Amount='" + Amount + '\'' +
                ", Staking='" + Staking + '\'' +
                '}';
    }
}
