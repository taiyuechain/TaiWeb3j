package com.taiweb3j.response.staking;

import java.util.List;

public class StakingAccountInfo {

    public int id;

    public ImpawnUnit unit;

    public String votePubKey;

    public String fee;

    public boolean committee;

    public List<DelegationAccount> delegation;

    public AlterableInfo modify;

    public String staking;

    public String validStaking;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImpawnUnit getUnit() {
        return unit;
    }

    public void setUnit(ImpawnUnit unit) {
        this.unit = unit;
    }

    public String getVotePubKey() {
        return votePubKey;
    }

    public void setVotePubKey(String votePubKey) {
        this.votePubKey = votePubKey;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public boolean isCommittee() {
        return committee;
    }

    public void setCommittee(boolean committee) {
        this.committee = committee;
    }

    public List<DelegationAccount> getDelegation() {
        return delegation;
    }

    public void setDelegation(List<DelegationAccount> delegation) {
        this.delegation = delegation;
    }

    public AlterableInfo getModify() {
        return modify;
    }

    public void setModify(AlterableInfo modify) {
        this.modify = modify;
    }

    public String getStaking() {
        return staking;
    }

    public void setStaking(String staking) {
        this.staking = staking;
    }

    public String getValidStaking() {
        return validStaking;
    }

    public void setValidStaking(String validStaking) {
        this.validStaking = validStaking;
    }


    @Override
    public String toString() {
        return "StakingAccountInfo{" +
                "id=" + id +
                ", unit=" + unit +
                ", votePubKey='" + votePubKey + '\'' +
                ", fee='" + fee + '\'' +
                ", committee=" + committee +
                ", delegation=" + delegation +
                ", modify=" + modify +
                ", staking='" + staking + '\'' +
                ", validStaking='" + validStaking + '\'' +
                '}';
    }
}
