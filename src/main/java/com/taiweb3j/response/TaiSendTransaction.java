package com.taiweb3j.response;

import org.web3j.protocol.core.Response;

/**
 * eth_sendTransaction.
 */
public class TaiSendTransaction extends Response<String> {
    public String getTrueTransactionHash() {
        return getResult();
    }
}
