package com.taiweb3j.response.transaction;

import org.web3j.utils.Numeric;

import java.math.BigInteger;

/**
 * Transaction class used for signing transactions locally.<br>
 * For the specification, refer to p4 of the <a href="http://gavwood.com/paper.pdf">
 * yellow paper</a>.
 */
public class TrueRawTransaction {

    private BigInteger nonce;
    private BigInteger gasPrice;
    private BigInteger gasLimit;
    private String to;
    private BigInteger value;
    private String data;

    private String payment;//代付者账户
    private BigInteger fee;//发送者的扣费数量

    protected TrueRawTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
                                 BigInteger value, String data, BigInteger fee, String payment) {
        this.nonce = nonce;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.to = to;
        this.value = value;

        this.fee = fee;
        this.payment = payment;

        if (data == null) {
            data = "";
        }
        this.data = Numeric.cleanHexPrefix(data);
    }

    public TrueRawTransaction(SignedTrueRawTransaction signedTrueRawTransaction) {
        this.nonce = signedTrueRawTransaction.getNonce();
        this.gasPrice = signedTrueRawTransaction.getGasPrice();
        this.gasLimit = signedTrueRawTransaction.getGasLimit();
        this.to = signedTrueRawTransaction.getTo();
        this.value = signedTrueRawTransaction.getValue();

        this.fee = signedTrueRawTransaction.getFee();
        this.payment = signedTrueRawTransaction.getPayment();

        if (signedTrueRawTransaction.getData() != null) {
            this.data = Numeric.cleanHexPrefix(signedTrueRawTransaction.getData());
        }
    }

    /**
     * cantains payment transaction
     *
     * @param nonce
     * @param gasPrice
     * @param gasLimit
     * @param to
     * @param value
     * @param data
     * @param payment
     * @return
     */
    public static TrueRawTransaction createTruePaymentTransaction(
            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
            BigInteger value, String data, String payment) {
        return new TrueRawTransaction(nonce, gasPrice, gasLimit, to, value, data, null, payment);
    }

    /**
     * contains fee  transaction
     *
     * @param nonce
     * @param gasPrice
     * @param gasLimit
     * @param to
     * @param value
     * @param data
     * @param fee
     * @return
     */
    public static TrueRawTransaction createTrueFeeTransaction(
            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
            BigInteger value, String data, BigInteger fee) {
        return new TrueRawTransaction(nonce, gasPrice, gasLimit, to, value, data, fee, null);
    }

    /**
     * contains payment and fee transaction
     *
     * @param nonce
     * @param gasPrice
     * @param gasLimit
     * @param to
     * @param value
     * @param data
     * @param fee
     * @param payment
     * @return
     */
    public static TrueRawTransaction createTruePaymentAndFeeTransaction(
            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
            BigInteger value, String data, BigInteger fee, String payment) {
        return new TrueRawTransaction(nonce, gasPrice, gasLimit, to, value, data, fee, payment);
    }

    /**
     * contains basic info transaction
     *
     * @param nonce
     * @param gasPrice
     * @param gasLimit
     * @param to
     * @param value
     * @param data
     * @return
     */
    public static TrueRawTransaction createTransaction(
            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
            BigInteger value, String data) {
        return new TrueRawTransaction(nonce, gasPrice, gasLimit, to, value, data, null, null);
    }


    public BigInteger getNonce() {
        return nonce;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public String getTo() {
        return to;
    }

    public BigInteger getValue() {
        return value;
    }

    public String getData() {
        return data;
    }

    public BigInteger getFee() {
        return fee;
    }

    public String getPayment() {
        return payment;
    }
}
