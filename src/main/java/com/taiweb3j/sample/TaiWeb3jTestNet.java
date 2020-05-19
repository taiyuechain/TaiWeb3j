package com.taiweb3j.sample;

import com.taiweb3j.TaiWeb3jRequest;
import com.taiweb3j.common.AddressConstant;
import com.taiweb3j.common.Constant;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class TaiWeb3jTestNet {

    public TaiWeb3jRequest taiWeb3JRequest = new TaiWeb3jRequest(Constant.RPC_TESTNET_URL);

    public Web3j web3j = Web3j.build(new HttpService(Constant.RPC_TESTNET_URL));

    public static int chainId = Constant.CHAINID_TESTNET;

    //发送者账户
    public static String fromPrivatekey = AddressConstant.fromPrivateKey;
    public static String fromAddress = AddressConstant.fromAddress;
    public static String toAddress = AddressConstant.toAddress;

    public static String paymentPrivateKey = AddressConstant.paymentPrivatekey;
    public static String paymentAddress = AddressConstant.paymentAddress;

}

