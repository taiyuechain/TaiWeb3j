package com.taiweb3j;

import com.taiweb3j.common.AddressConstant;
import com.taiweb3j.response.*;
import com.taiweb3j.response.EtrueSnailBlockNumber;
import com.taiweb3j.response.Reward.ChainRewardContent;
import com.taiweb3j.response.Reward.RewardInfo;
import com.taiweb3j.response.Reward.SARewardInfos;
import com.taiweb3j.response.committee.CommitteeInfo;
import com.taiweb3j.response.fast.FastBlock;
import com.taiweb3j.response.snail.*;
import com.taiweb3j.response.staking.AllStakingAccount;
import com.taiweb3j.response.staking.StakingAccountInfo;
import org.apache.commons.lang3.StringUtils;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import com.taiweb3j.common.Constant;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class TaiWeb3jRequest {
    public Web3jService web3jService = null;

    private TaiWeb3jRequest() {

    }

    public TaiWeb3jRequest(String rpcUrl) {
        web3jService = new HttpService(rpcUrl);
    }

    public TaiWeb3jRequest(HttpService httpService) {
        this.web3jService = httpService;
    }


    /**
     * query lock balance
     */
    public BigInteger getLockBalance(String address, DefaultBlockParameter defaultBlockParameter) {
        BigInteger balanceValue = BigInteger.ZERO;
        if (StringUtils.isBlank(address)) {
            return balanceValue;
        }
        if (defaultBlockParameter == null) {
            defaultBlockParameter = DefaultBlockParameterName.LATEST;
        }
        try {
            EthGetBalance ethGetBalance = new Request<>("eth_getLockBalance",
                    Arrays.asList(address, defaultBlockParameter.getValue()),
                    web3jService,
                    EthGetBalance.class).send();
            balanceValue = ethGetBalance.getBalance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return balanceValue;
    }


    /**
     * get FastBlock by fastNumber
     *
     * @param fastBlockNumber
     * @param returnFullTransactionObjects
     * @return
     */
    public FastBlock getFastBlockByNumber(BigInteger fastBlockNumber, boolean returnFullTransactionObjects) {
        FastBlock fastBlock = null;
        try {
            EtrueFastBlock etrueFastBlock = new Request<>(
                    Constant.BLOCK_BYNUMBER,
                    Arrays.asList(DefaultBlockParameter.valueOf(fastBlockNumber).getValue(), returnFullTransactionObjects),
                    web3jService,
                    EtrueFastBlock.class).send();
            fastBlock = etrueFastBlock.getFastBlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastBlock;
    }

    /**
     * get FastBlock by hash
     *
     * @param fastHash
     * @param returnFullTransactionObjects
     * @return
     */
    public FastBlock getFastBlockByHash(String fastHash, boolean returnFullTransactionObjects) {
        FastBlock fastBlock = null;
        try {
            EtrueFastBlock etrueFastBlock = new Request<>(
                    Constant.BLOCK_BYHASH,
                    Arrays.asList(fastHash, returnFullTransactionObjects),
                    web3jService,
                    EtrueFastBlock.class).send();
            fastBlock = etrueFastBlock.getFastBlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastBlock;
    }

    /**
     * get current fastNumber on the chain
     *
     * @return
     */
    public BigInteger getCurrentFastNumber() {
        BigInteger fastNumber = null;
        try {
            EtrueFastBlockNumber etrueFastBlockNumber = new Request<>(
                    Constant.CURRENT_BLOCK_NUMBER,
                    Arrays.asList(),
                    web3jService,
                    EtrueFastBlockNumber.class).send();
            fastNumber = etrueFastBlockNumber.getBlockNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastNumber;
    }

    /**
     * get snail reward address and balance of the address
     *
     * @param snailBlockNumber
     * @return
     */
    public Map<String, String> getSnailBalanceChange(BigInteger snailBlockNumber) {
        Map<String, String> addrWithBalance = new HashMap<String, String>();
        try {
            EtrueBalanceChange etrueBalanceChange = new Request<>(
                    Constant.BALANCE_CHANGE_BY_SNAIL_NUMBER,
                    Arrays.asList(DefaultBlockParameter.valueOf(snailBlockNumber).getValue()),
                    web3jService,
                    EtrueBalanceChange.class).send();
            BalanceChange balanceChange = etrueBalanceChange.getBalanceChange();
            if (balanceChange != null) {
                addrWithBalance = balanceChange.getAddrWithBalance();
                /*for (Map.Entry<String, String> entry : addrWithBalance.entrySet()) {
                    System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addrWithBalance;
    }

    /**
     * get snail reward content by snail number
     * inclued blockminer、fruitminer、committeReward、foundationReward
     * <p>
     * <p>
     * attention:getSnailRewardContent get by rpc of "tai_getChainRewardContent"
     *
     * @param snailNumber
     * @return
     */
    public ChainRewardContent getSnailRewardContent(BigInteger snailNumber) {
        ChainRewardContent chainRewardContent = null;
        if (snailNumber == null) {
            return null;
        }
        DefaultBlockParameter blockParameter = DefaultBlockParameter.valueOf(snailNumber);
        try {
            EtrueChainRewardContent etrueChainRewardContent = new Request<>(
                    Constant.CHAIN_REWARD_CONTENT,
                    Arrays.asList(blockParameter.getValue(), AddressConstant.EMPTY_ADDRESS),
                    web3jService,
                    EtrueChainRewardContent.class).send();
            chainRewardContent = etrueChainRewardContent.getChainRewardContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainRewardContent;
    }

    public SnailRewardContenet getSnailRewardContent_Old(BigInteger snailNumber) {
        SnailRewardContenet snailRewardContenet = null;
        if (snailNumber == null) {
            return null;
        }
        DefaultBlockParameter blockParameter = DefaultBlockParameter.valueOf(snailNumber);
        try {
            EtrueSnailRewardContent etrueSnailRewardContent = new Request<>(
                    Constant.SNAIL_REWARD_CONTENT,
                    Arrays.asList(blockParameter.getValue()),
                    web3jService,
                    EtrueSnailRewardContent.class).send();
            snailRewardContenet = etrueSnailRewardContent.getSnailRewardContenet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return snailRewardContenet;
    }


    /**
     * get gather addresses snail reward by snailNumber
     *
     * @param snailNumber
     * @return
     */
    public Map<String, BigInteger> getAddressesSnailReward(BigInteger snailNumber) {
        //慢链奖励中涉及所有的地址
        Map<String, BigInteger> snailRewardWithAddr = new HashMap<String, BigInteger>();
        ChainRewardContent chainRewardContent = getSnailRewardContent(snailNumber);
        if (chainRewardContent == null) {
            return snailRewardWithAddr;
        }

        RewardInfo minerRewardInfo = chainRewardContent.getBlockminer();
        gatherAddressBalance(snailRewardWithAddr, minerRewardInfo);

        RewardInfo developerReward = chainRewardContent.getDeveloperReward();
        gatherAddressBalance(snailRewardWithAddr, developerReward);

        List<RewardInfo> fruitRewardInfos = chainRewardContent.getFruitminer();
        if (fruitRewardInfos != null && fruitRewardInfos.size() != 0) {
            for (RewardInfo fruitRewardInfo : fruitRewardInfos) {
                gatherAddressBalance(snailRewardWithAddr, fruitRewardInfo);
            }
        }

        List<SARewardInfos> saRewardInfosList = chainRewardContent.getCommitteeReward();
        if (saRewardInfosList != null && saRewardInfosList.size() > 0) {
            for (SARewardInfos saRewardInfo : saRewardInfosList) {
                if (saRewardInfo != null && saRewardInfo.getItems() != null
                        && saRewardInfo.getItems().size() != 0) {
                    for (RewardInfo committeeRewardInfo : saRewardInfo.getItems()) {
                        gatherAddressBalance(snailRewardWithAddr, committeeRewardInfo);
                    }
                }
            }
        }
        return snailRewardWithAddr;
    }

    private Map<String, BigInteger> gatherAddressBalance(
            Map<String, BigInteger> snailRewardWithAddr, RewardInfo rewardInfo) {
        if (rewardInfo == null) {
            return snailRewardWithAddr;
        }
        String address = rewardInfo.getAddress();
        if (snailRewardWithAddr.get(address) != null) {
            BigInteger balance = snailRewardWithAddr.get(address);
            balance = balance.add(rewardInfo.getAmount());
            snailRewardWithAddr.put(address, balance);
        } else {
            snailRewardWithAddr.put(address, rewardInfo.getAmount());
        }
        return snailRewardWithAddr;
    }


    /**
     * get snail Block by snailNumber
     *
     * @param snailNumber
     * @param inclFruit   whether include fruits info
     * @return
     */
    public SnailBlock getSnailBlockByNumber(BigInteger snailNumber, boolean inclFruit) {
        SnailBlock snailBlock = null;
        try {
            EtrueSnailBlock etrueSnailBlock = new Request<>(
                    "etrue_getSnailBlockByNumber",
                    Arrays.asList(DefaultBlockParameter.valueOf(snailNumber).getValue(), inclFruit),
                    web3jService,
                    EtrueSnailBlock.class).send();
            snailBlock = etrueSnailBlock.getSnailBlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return snailBlock;
    }

    /**
     * get snailHash by snailNumber
     *
     * @param snailNumber
     * @return if return null, donnot have generate the snailNumber
     */
    public String getSnailHashByNumber(BigInteger snailNumber) {
        String snailHash = null;
        try {
            EtrueSnailHash etrueSnailHash = new Request<>(
                    Constant.SNAIL_HASH_BY_NUMBER,
                    Arrays.asList(DefaultBlockParameter.valueOf(snailNumber).getValue()),
                    web3jService,
                    EtrueSnailHash.class).send();
            snailHash = etrueSnailHash.getSnailHash();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return snailHash;
    }

    /**
     * get snailBlock by snailHash
     *
     * @param snailHash
     * @param inclFruit whether include fruits info
     * @return
     */
    public SnailBlock getSnailBlockByHash(String snailHash, boolean inclFruit) {
        SnailBlock snailBlock = null;
        try {
            EtrueSnailBlock etrueSnailBlock = new Request<>(
                    Constant.SNAIL_BLOCK_BY_HASH,
                    Arrays.asList(snailHash, inclFruit),
                    web3jService,
                    EtrueSnailBlock.class).send();
            snailBlock = etrueSnailBlock.getSnailBlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return snailBlock;
    }

    /**
     * get current snail block number
     *
     * @return
     */
    public BigInteger getCurrentSnailNumber() {
        BigInteger snailNumber = null;
        try {
            EtrueSnailBlockNumber etrueSnailBlockNumber = new Request<>(
                    Constant.SNAIL_BLOCK_NUMBER,
                    Arrays.asList(),
                    web3jService,
                    EtrueSnailBlockNumber.class).send();
            snailNumber = etrueSnailBlockNumber.getSnailBlockNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return snailNumber;
    }


    /**
     * get committeeInfo by committeeNumber
     *
     * @param committeeNumber
     * @return
     */
    public CommitteeInfo getCommitteeByNumber(BigInteger committeeNumber) {
        CommitteeInfo committeeInfo = null;
        try {
            EtrueCommittee etrueCommittee = new Request<>(
                    Constant.COMMITTEE_BY_NUMBER,
                    Arrays.asList(DefaultBlockParameter.valueOf(committeeNumber).getValue()),
                    web3jService,
                    EtrueCommittee.class).send();
            committeeInfo = etrueCommittee.getCommittee();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return committeeInfo;
    }

    /**
     * get current committee number
     *
     * @return
     */
    public Integer getCurrentCommitteeNumber() {
        Integer currentCommitteeNumber = null;
        try {
            EtrueCommitteeNumber etrueCommitteeNumber = new Request<>(
                    Constant.CURRENT_COMMITTEE_NUMBER,
                    Arrays.asList(),
                    web3jService,
                    EtrueCommitteeNumber.class).send();
            currentCommitteeNumber = etrueCommitteeNumber.getCommitteeNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentCommitteeNumber;
    }

    /**
     * send true raw transaction
     *
     * @param signedTransactionData
     * @return
     */
    public EtrueSendTransaction etrueSendRawTransaction(String signedTransactionData) {
        EtrueSendTransaction etrueSendTrueTransaction = null;
        try {
            etrueSendTrueTransaction = new Request<>(
                    Constant.SEND_TRUE_RAW_TRANSACTION,
                    Arrays.asList(signedTransactionData),
                    web3jService,
                    EtrueSendTransaction.class).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return etrueSendTrueTransaction;
    }

    /**
     * get balance with address which has changed by fast number
     *
     * @param fastNumber
     * @return
     */
    public FastBalanceChange getStateChangeByFastNumber(BigInteger fastNumber) {
        FastBalanceChange fastBalanceChange = null;
        try {
            EtrueFastBalanceChange etrueFastBalanceChange = new Request<>(
                    Constant.STATE_CHANGE_BY_FAST_NUMBER,
                    Arrays.asList(DefaultBlockParameter.valueOf(fastNumber).getValue()),
                    web3jService,
                    EtrueFastBalanceChange.class).send();
            fastBalanceChange = etrueFastBalanceChange.getFastBalanceChange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastBalanceChange;
    }


    /**
     * get staking info by account
     *
     * @param account
     * @return
     */
    public StakingAccountInfo getStakingAccountInfo(String account) {
        StakingAccountInfo stakingAccountInfo = null;
        try {
            EtrueStakingAccountInfo etrueStakingAccountInfo = new Request<>(
                    Constant.STAKING_ACCOUNT,
                    Arrays.asList(
                            DefaultBlockParameterName.LATEST,
                            account),
                    web3jService,
                    EtrueStakingAccountInfo.class).send();
            stakingAccountInfo = etrueStakingAccountInfo.getStakingAccountInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stakingAccountInfo;
    }

    /**
     * get all staking account infos
     *
     * @return
     */
    public AllStakingAccount getAllStakingAccount() {
        AllStakingAccount allStakingAccount = null;
        try {
            EtrueAllStakingAccountInfo etrueAllStakingAccountInfo = new Request<>(
                    Constant.ALL_STAKING_ACCOUNT,
                    Arrays.asList(DefaultBlockParameterName.LATEST),
                    web3jService,
                    EtrueAllStakingAccountInfo.class).send();
            allStakingAccount = etrueAllStakingAccountInfo.getAllStakingAccount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allStakingAccount;
    }

    /**
     * get the proceeds of all the delegate addresses under a pledge node(stakingAddress) in snailNumber block
     *
     * @param snailNumber
     * @param stakingAddress
     * @return
     */
    public ChainRewardContent getChainRewardContent(BigInteger snailNumber, String stakingAddress) {
        ChainRewardContent chainRewardContent = null;

        if (snailNumber == null || StringUtils.isBlank(stakingAddress)) {
            return null;
        }
        DefaultBlockParameter blockParameter = DefaultBlockParameter.valueOf(snailNumber);
        try {
            EtrueChainRewardContent etrueChainRewardContent = new Request<>(
                    Constant.CHAIN_REWARD_CONTENT,
                    Arrays.asList(blockParameter.getValue(), stakingAddress),
                    web3jService,
                    EtrueChainRewardContent.class).send();
            chainRewardContent = etrueChainRewardContent.getChainRewardContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chainRewardContent;
    }


    public FastBlock getFastBockOfReward(BigInteger snailRewardNumber) {
        DefaultBlockParameter blockParameter = DefaultBlockParameter.valueOf(snailRewardNumber);
        FastBlock fastBlock = null;
        try {
            EtrueFastBlock etrueFastBlock = new Request<>(
                    Constant.FAST_BLOCK_OF_REWARD,
                    Arrays.asList(blockParameter.getValue()),
                    web3jService,
                    EtrueFastBlock.class).send();
            fastBlock = etrueFastBlock.getFastBlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastBlock;
    }


}
