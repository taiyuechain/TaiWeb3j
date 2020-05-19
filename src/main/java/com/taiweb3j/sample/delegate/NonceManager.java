package com.taiweb3j.sample.delegate;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NonceManager {
    private String tag = this.getClass().getName();
    private static NonceManager nonceManager;
    private ConcurrentHashMap<String, NonceLocker> nonceMap = new ConcurrentHashMap<>();

    public static NonceManager getInstance() {
        if (null == nonceManager) nonceManager = new NonceManager();
        return nonceManager;
    }

    private BigInteger getNonceForWeb3j(Web3j web3j, String address) {
        try {
            EthGetTransactionCount ethGetTransactionCount =
                    web3j.ethGetTransactionCount(address, DefaultBlockParameterName.PENDING).sendAsync().get();
            return ethGetTransactionCount.getTransactionCount();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigInteger getNonce(Web3j web3j, String address) {
        NonceLocker nonceLocker = nonceMap.get(address);
        if (null == nonceLocker) nonceLocker = new NonceLocker();
        nonceLocker.lock.lock();
        if (null == nonceLocker.nonce)
            nonceLocker.nonce = getNonceForWeb3j(web3j, address);
        else
            nonceLocker.nonce = nonceLocker.nonce.add(BigInteger.ONE);
        nonceMap.put(address, nonceLocker);
        return nonceLocker.nonce;
    }

    public void exhaustNonce(String address, String tx) {
        NonceLocker nonceLocker = nonceMap.get(address);
        if (null == tx || tx.length() == 0) {
            nonceLocker.nonce = null;
            nonceMap.put(address, nonceLocker);
        }
        nonceLocker.lock.unlock();
    }

    public static class NonceLocker {
        private BigInteger nonce = null;
        private Lock lock = new ReentrantLock();
    }
}
