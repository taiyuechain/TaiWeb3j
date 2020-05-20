package com.taiweb3j.sample.erc20;

import com.taiweb3j.common.Constant;
import com.taiweb3j.sample.TaiWeb3jTestNet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;

public class TransactionClientUsage extends TaiWeb3jTestNet {
    private static Logger logger = LoggerFactory.getLogger(TransactionClientUsage.class);

    /**
     * query balance
     *
     * @param address
     * @return balance
     */
    public BigInteger getBalance(String address) {
        BigInteger balance = BigInteger.ZERO;
        try {
            EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            if (ethGetBalance != null) {
                balance = ethGetBalance.getBalance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("address= " + address + ", balance= " + balance + "wei");
        return balance;
    }

    /**
     * query address balance by privatekey
     *
     * @param privatekey
     * @return
     */
    public BigInteger getBalanceWithPrivateKey(String privatekey) {
        BigInteger balance = null;
        String address = "";
        try {
            Credentials credentials = Credentials.create(privatekey);
            address = credentials.getAddress();
            balance = getBalance(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return balance;
    }

    /**
     * Used to generate the String after the transaction signed by the sender offline,
     * which contains the signature information of the sender.
     * Often used to send to third parties and used to send transactions directly
     *
     * @return
     */
    public String genRawTransaction() {
        Credentials credentials = Credentials.create(fromPrivatekey);
        BigInteger nonce = getTransactionNonce(fromAddress);
        RawTransaction rawTransaction =
                RawTransaction.createEtherTransaction(nonce, Constant.DEFAULT_GASPRICE,
                        Constant.DEFAULT_CONTRACT_GASLIMIT, toAddress, Constant.DEFAULT_VALUE);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        String hexMessage = Numeric.toHexString(signedMessage);
        logger.info("genRawTransaction hexMessage ={}", hexMessage);
        return hexMessage;
    }


    /**
     * send transaction
     * Used in conjunction with genRawTransaction method
     *
     * @param hexValue The string form after the transaction is signed
     */
    public void sendRawTransaction(String hexValue) {
        try {
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            String txHash = ethSendTransaction.getTransactionHash();
            if (ethSendTransaction.getError() != null) {
                logger.error("sendTransaction error", ethSendTransaction.getError().getMessage());
            }
            System.out.println("txHash------------------->" + txHash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a  transaction object
     *
     * @param fromAddress
     * @param toAddress
     * @param nonce
     * @param gasPrice
     * @param gasLimit
     * @param value
     * @return
     */
    public Transaction makeTransaction(String fromAddress, String toAddress,
                                       BigInteger nonce, BigInteger gasPrice,
                                       BigInteger gasLimit, BigInteger value) {
        Transaction transaction;
        transaction = Transaction.createEtherTransaction(fromAddress, nonce, gasPrice, gasLimit, toAddress, value);
        return transaction;
    }

    /**
     * Gets the gas ceiling for a normal transaction
     *
     * @param transaction
     * @return gas
     */
    public BigInteger getTransactionGasLimit(Transaction transaction) {
        BigInteger gasLimit = BigInteger.ZERO;
        try {
            EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(transaction).send();
            gasLimit = ethEstimateGas.getAmountUsed();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gasLimit;
    }

    /**
     * @return nonce
     */
    public BigInteger getTransactionNonce(String address) {
        BigInteger nonce = BigInteger.ZERO;
        try {
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(address, DefaultBlockParameterName.PENDING).send();
            nonce = ethGetTransactionCount.getTransactionCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nonce;
    }

}
