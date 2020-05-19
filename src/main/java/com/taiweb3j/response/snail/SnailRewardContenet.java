package com.taiweb3j.response.snail;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class SnailRewardContenet {

    private Map<String, BigInteger> blockminer;
    private List<Map<String, BigInteger>> fruitminer;
    private Map<String, BigInteger> committeeReward;
    private Map<String, BigInteger> developerReward;


    public Map<String, BigInteger> getBlockminer() {
        return blockminer;
    }

    public void setBlockminer(Map<String, BigInteger> blockminer) {
        this.blockminer = blockminer;
    }

    public List<Map<String, BigInteger>> getFruitminer() {
        return fruitminer;
    }

    public void setFruitminer(List<Map<String, BigInteger>> fruitminer) {
        this.fruitminer = fruitminer;
    }

    public Map<String, BigInteger> getCommitteeReward() {
        return committeeReward;
    }

    public void setCommitteeReward(Map<String, BigInteger> committeeReward) {
        this.committeeReward = committeeReward;
    }

    public Map<String, BigInteger> getDeveloperReward() {
        return developerReward;
    }

    public void setDeveloperReward(Map<String, BigInteger> developerReward) {
        this.developerReward = developerReward;
    }
}
