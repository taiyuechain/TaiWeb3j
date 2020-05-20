package com.taiweb3j;

import com.taiweb3j.common.AddressConstant;
import com.taiweb3j.common.Constant;
import com.taiweb3j.response.Reward.ChainRewardContent;
import com.taiweb3j.response.Reward.RewardInfo;
import com.taiweb3j.response.Reward.SARewardInfos;
import com.taiweb3j.response.*;
import com.taiweb3j.response.committee.CommitteeInfo;
import com.taiweb3j.response.fast.FastBlock;
import com.taiweb3j.response.snail.BalanceChange;
import com.taiweb3j.response.snail.FastBalanceChange;
import com.taiweb3j.response.snail.SnailBlock;
import com.taiweb3j.response.snail.SnailRewardContenet;
import org.apache.commons.lang3.StringUtils;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            TaiFastBlock taiFastBlock = new Request<>(
                    Constant.BLOCK_BYNUMBER,
                    Arrays.asList(DefaultBlockParameter.valueOf(fastBlockNumber).getValue(), returnFullTransactionObjects),
                    web3jService,
                    TaiFastBlock.class).send();
            fastBlock = taiFastBlock.getFastBlock();
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
            TaiFastBlock taiFastBlock = new Request<>(
                    Constant.BLOCK_BYHASH,
                    Arrays.asList(fastHash, returnFullTransactionObjects),
                    web3jService,
                    TaiFastBlock.class).send();
            fastBlock = taiFastBlock.getFastBlock();
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
            TaiFastBlockNumber taiFastBlockNumber = new Request<>(
                    Constant.CURRENT_BLOCK_NUMBER,
                    Arrays.asList(),
                    web3jService,
                    TaiFastBlockNumber.class).send();
            fastNumber = taiFastBlockNumber.getBlockNumber();
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
            TaiBalanceChange taiBalanceChange = new Request<>(
                    Constant.BALANCE_CHANGE_BY_SNAIL_NUMBER,
                    Arrays.asList(DefaultBlockParameter.valueOf(snailBlockNumber).getValue()),
                    web3jService,
                    TaiBalanceChange.class).send();
            BalanceChange balanceChange = taiBalanceChange.getBalanceChange();
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
            TaiChainRewardContent taiChainRewardContent = new Request<>(
                    Constant.CHAIN_REWARD_CONTENT,
                    Arrays.asList(blockParameter.getValue(), AddressConstant.EMPTY_ADDRESS),
                    web3jService,
                    TaiChainRewardContent.class).send();
            chainRewardContent = taiChainRewardContent.getChainRewardContent();
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
            TaiSnailRewardContent taiSnailRewardContent = new Request<>(
                    Constant.SNAIL_REWARD_CONTENT,
                    Arrays.asList(blockParameter.getValue()),
                    web3jService,
                    TaiSnailRewardContent.class).send();
            snailRewardContenet = taiSnailRewardContent.getSnailRewardContenet();
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
            TaiSnailBlock taiSnailBlock = new Request<>(
                    "tai_getSnailBlockByNumber",
                    Arrays.asList(DefaultBlockParameter.valueOf(snailNumber).getValue(), inclFruit),
                    web3jService,
                    TaiSnailBlock.class).send();
            snailBlock = taiSnailBlock.getSnailBlock();
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
            TaiSnailHash taiSnailHash = new Request<>(
                    Constant.SNAIL_HASH_BY_NUMBER,
                    Arrays.asList(DefaultBlockParameter.valueOf(snailNumber).getValue()),
                    web3jService,
                    TaiSnailHash.class).send();
            snailHash = taiSnailHash.getSnailHash();
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
            TaiSnailBlock taiSnailBlock = new Request<>(
                    Constant.SNAIL_BLOCK_BY_HASH,
                    Arrays.asList(snailHash, inclFruit),
                    web3jService,
                    TaiSnailBlock.class).send();
            snailBlock = taiSnailBlock.getSnailBlock();
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
            TaiSnailBlockNumber taiSnailBlockNumber = new Request<>(
                    Constant.SNAIL_BLOCK_NUMBER,
                    Arrays.asList(),
                    web3jService,
                    TaiSnailBlockNumber.class).send();
            snailNumber = taiSnailBlockNumber.getSnailBlockNumber();
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
            TaiCommittee taiCommittee = new Request<>(
                    Constant.COMMITTEE_BY_NUMBER,
                    Arrays.asList(DefaultBlockParameter.valueOf(committeeNumber).getValue()),
                    web3jService,
                    TaiCommittee.class).send();
            committeeInfo = taiCommittee.getCommittee();
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
            TaiCommitteeNumber taiCommitteeNumber = new Request<>(
                    Constant.CURRENT_COMMITTEE_NUMBER,
                    Arrays.asList(),
                    web3jService,
                    TaiCommitteeNumber.class).send();
            currentCommitteeNumber = taiCommitteeNumber.getCommitteeNumber();
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
    public TaiSendTransaction taiSendRawTransaction(String signedTransactionData) {
        TaiSendTransaction taiSendTrueTransaction = null;
        try {
            taiSendTrueTransaction = new Request<>(
                    Constant.SEND_TRUE_RAW_TRANSACTION,
                    Arrays.asList(signedTransactionData),
                    web3jService,
                    TaiSendTransaction.class).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taiSendTrueTransaction;
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
            TaiFastBalanceChange taiFastBalanceChange = new Request<>(
                    Constant.STATE_CHANGE_BY_FAST_NUMBER,
                    Arrays.asList(DefaultBlockParameter.valueOf(fastNumber).getValue()),
                    web3jService,
                    TaiFastBalanceChange.class).send();
            fastBalanceChange = taiFastBalanceChange.getFastBalanceChange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastBalanceChange;
    }


}
