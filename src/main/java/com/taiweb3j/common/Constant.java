package com.taiweb3j.common;

import org.web3j.utils.Convert;

import java.math.BigInteger;

/**
 * 运行配置项
 */
public class Constant {

    public static String RPC_MAINNET_URL = "https://publicrpc.taiyuechain.com";
    public static int CHAINID_MAINNET = 20515;

    public static String RPC_TESTNET_URL = "https://publicrpc.taiyuechain.com";
    public static int CHAINID_TESTNET = 20515;

    public static BigInteger DEFAULT_GASPRICE = Convert.toWei("2", Convert.Unit.GWEI).toBigInteger();

    public static BigInteger DEFAULT_GASLIMIT = Convert.toWei("21000", Convert.Unit.WEI).toBigInteger();

    public static BigInteger DEFAULT_CONTRACT_GASLIMIT = Convert.toWei("200000", Convert.Unit.WEI).toBigInteger();

    public static BigInteger DEFAULT_VALUE = Convert.toWei("1", Convert.Unit.ETHER).toBigInteger();

    public static BigInteger DEFAULT_FEE = Convert.toWei("1", Convert.Unit.ETHER).toBigInteger();


    public static String BALANCE_CHANGE_BY_SNAIL_NUMBER = "tai_getBalanceChangeBySnailNumber";

    public static String BLOCK_BYNUMBER = "tai_getBlockByNumber";

    public static String BLOCK_BYHASH = "tai_getBlockByHash";

    public static String CURRENT_BLOCK_NUMBER = "tai_blockNumber";

    public static String CHAIN_REWARD_CONTENT = "tai_getChainRewardContent";

    public static String SNAIL_REWARD_CONTENT = "tai_getSnailRewardContent";

    public static String SNAIL_HASH_BY_NUMBER = "tai_getSnailHashByNumber";

    public static String SNAIL_BLOCK_NUMBER = "tai_snailBlockNumber";

    public static String SNAIL_BLOCK_BY_HASH = "tai_getSnailBlockByHash";

    public static String STATE_CHANGE_BY_FAST_NUMBER = "tai_getStateChangeByFastNumber";

    public static String COMMITTEE_BY_NUMBER = "tai_getCommittee";

    public static String CURRENT_COMMITTEE_NUMBER = "tai_committeeNumber";

    public static String SEND_TRUE_RAW_TRANSACTION = "tai_sendTrueRawTransaction";

}
