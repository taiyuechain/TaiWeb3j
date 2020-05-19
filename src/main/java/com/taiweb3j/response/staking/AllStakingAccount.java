package com.taiweb3j.response.staking;

import java.util.List;

public class AllStakingAccount {

    public List<StakingAccountInfo> stakers;

    public int stakerCount;

    public int delegateCount;

    public List<StakingAccountInfo> getStakers() {
        return stakers;
    }

    public void setStakers(List<StakingAccountInfo> stakers) {
        this.stakers = stakers;
    }

    public int getStakerCount() {
        return stakerCount;
    }

    public void setStakerCount(int stakerCount) {
        this.stakerCount = stakerCount;
    }

    public int getDelegateCount() {
        return delegateCount;
    }

    public void setDelegateCount(int delegateCount) {
        this.delegateCount = delegateCount;
    }

    @Override
    public String toString() {
        return "AllStakingAccount{" +
                "stakers=" + stakers +
                ", stakerCount=" + stakerCount +
                ", delegateCount=" + delegateCount +
                '}';
    }
}
