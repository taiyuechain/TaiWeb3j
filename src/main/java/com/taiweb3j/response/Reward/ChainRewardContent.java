package com.taiweb3j.response.Reward;

import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 某个慢链块的收益
 */
public class ChainRewardContent {
    public String Number;

    public String time;

    //某个质押节点下所有地址的委托收益
    public List<RewardInfo> stakingReward;

    /**
     * 当传入空地址，获取的是所有的收益
     */
    public RewardInfo developerReward;

    public RewardInfo blockminer;

    public List<RewardInfo> fruitminer;

    public List<SARewardInfos> committeeReward;

    public ChainRewardContent() {
    }

    public RewardInfo getDeveloperReward() {
        return developerReward;
    }

    public void setDeveloperReward(RewardInfo developerReward) {
        this.developerReward = developerReward;
    }

    public List<SARewardInfos> getCommitteeReward() {
        return committeeReward;
    }

    public void setCommitteeReward(List<SARewardInfos> committeeReward) {
        this.committeeReward = committeeReward;
    }

    public  BigInteger getNumber() {
        return Numeric.decodeQuantity(Number);
    }

    public String getNumberRaw(){
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public BigInteger getTime() {
        return Numeric.decodeQuantity(time);
    }

    public String getTimeRaw() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<RewardInfo> getStakingReward() {
        return stakingReward;
    }

    public void setStakingReward(List<RewardInfo> stakingReward) {
        this.stakingReward = stakingReward;
    }

    public RewardInfo getBlockminer() {
        return blockminer;
    }

    public void setBlockminer(RewardInfo blockminer) {
        this.blockminer = blockminer;
    }

    public List<RewardInfo> getFruitminer() {
        return fruitminer;
    }

    public void setFruitminer(List<RewardInfo> fruitminer) {
        this.fruitminer = fruitminer;
    }

    @Override
    public String toString() {
        return "ChainRewardContent{" +
                "Number='" + Number + '\'' +
                ", time='" + time + '\'' +
                ", stakingReward=" + stakingReward +
                ", developerReward=" + developerReward +
                ", blockminer=" + blockminer +
                ", fruitminer=" + fruitminer +
                ", committeeReward=" + committeeReward +
                '}';
    }
}
