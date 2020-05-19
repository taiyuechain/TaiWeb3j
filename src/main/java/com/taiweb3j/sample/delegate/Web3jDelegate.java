package com.taiweb3j.sample.delegate;


import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Web3jDelegate {

    private static String stakingContentAddress = "0x000000000000000000747275657374616b696E67";
    private static int stakingChainId = 19330;
    private static Web3j stakingWeb3j = Web3j.build(new HttpService("https://rpc.truescan.net"));
    private static String hoder = "0xDaa07f97034916517afFF28b672A61B0027346a2";
    private static BigInteger delegateNumber = new BigInteger("1000000000000000000");
    private static BigInteger fee = new BigInteger("100");
    private static BigInteger stakingGasPrice = new BigInteger("1000000000");
    private static BigInteger stakingGasLimit = new BigInteger("8000000");

    private static String holder = "0xDaa07f97034916517afFF28b672A61B0027346a2";
    private static String pubkey = "0x3ed17e0f3d0a3673cf967e238cf81ae151c5548fc0a1ed5c6a3d050b5faccf12bf8e8ce2e8ed462cf34101293d02d58306fd5174f0d8d4bb3ba5b388d7b01040";

    public static void main(String[] args) {
        String cancelData = StakingFunctionEncoder.makeCancel(delegateNumber);
        System.out.println("cancelData=" + cancelData);

        String withdrawData =  StakingFunctionEncoder.makeWithdraw(delegateNumber);
        System.out.println("withdrawData=" + withdrawData);

        String appendData =  StakingFunctionEncoder.makeAppend(delegateNumber);
        System.out.println("appendData=" + appendData);

        String feeData =  StakingFunctionEncoder.makeSetFee(fee);
        System.out.println("feeData=" + feeData);
    }

    public static String testDeposit() {
        Credentials credentials = Credentials.create("0x18e14a7b6a307f426a94f8114701e7c8e774e7f9a47e2c2035db29a206321725");
        BigInteger a = credentials.getEcKeyPair().getPublicKey();
        String pubkeyStr = new BigInteger(a.toString(), 10).toString(16);
        System.out.println("pubkeyStr=" + pubkeyStr);
        String data = StakingFunctionEncoder.makeDeposit(a.toByteArray(),
                10, BigInteger.ONE.multiply(new BigInteger("1000")));
        return data;
    }

    public static void testCancel(BigInteger valueWei) {
        String encoded = getFunctionCancel(valueWei);
        System.out.println("encoded=" + encoded);
    }



    public static String getFunctionCancel(BigInteger valueWei) {
        List<Type> inputParameters = new ArrayList<>();
        Uint256 _value = new Uint256(valueWei);
        inputParameters.add(_value);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Function function = new Function("setFee", inputParameters, outputParameters);
        String encoded = FunctionEncoder.encode(function);
        return encoded;

        //append
        //0xe33b8707000000000000000000000000000000000000000000000e85accf60e2b9ac0000
        //0xe33b87070000000000000000000000000000000000000000000000000de0b6b3a7640000
    }

    public static String getFunctionDelegate(String hoder, BigInteger valueWei) {
        List<Type> inputParameters = new ArrayList<>();
        Address _hoder = new Address(hoder);
        Uint256 _value = new Uint256(valueWei);
        inputParameters.add(_hoder);
        inputParameters.add(_value);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Function function = new Function("delegate", inputParameters, outputParameters);
        return FunctionEncoder.encode(function);
    }

    public static String getFunctionUnDelegate(String hoder, BigInteger valueWei) {
        List<Type> inputParameters = new ArrayList<>();
        Address _hoder = new Address(hoder);
        Uint256 _value = new Uint256(valueWei);
        inputParameters.add(_hoder);
        inputParameters.add(_value);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Function function = new Function("undelegate", inputParameters, outputParameters);
        return FunctionEncoder.encode(function);
    }

    public static String getFunctionWithdrawDelegate(String hoder, BigInteger valueWei) {
        List<Type> inputParameters = new ArrayList<>();
        Address _hoder = new Address(hoder);
        Uint256 _value = new Uint256(valueWei);
        inputParameters.add(_hoder);
        inputParameters.add(_value);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Function function = new Function("withdrawDelegate", inputParameters, outputParameters);
        return FunctionEncoder.encode(function);
    }

    public static String getFunctionGetDelegate(String hoder, String from) {
        String methodName = "getDelegate";
        List<Type> inputParameters = new ArrayList<>();
        Address owner = new Address(from);
        Address holder = new Address(hoder);
        inputParameters.add(owner);
        inputParameters.add(holder);
        List<TypeReference<?>> outputParameters = new ArrayList<>();
        TypeReference<Uint256> staked = new TypeReference<Uint256>() {
        };
        TypeReference<Uint256> locked = new TypeReference<Uint256>() {
        };
        TypeReference<Uint256> unlocked = new TypeReference<Uint256>() {
        };
        outputParameters.add(staked);
        outputParameters.add(locked);
        outputParameters.add(unlocked);

        Function function = new Function(methodName, inputParameters, outputParameters);
        return FunctionEncoder.encode(function);
    }


    public static MyDelegateResult getDelegate(String address, String holderAddress) {
        String methodName = "getDelegate";
        List<TypeReference<?>> outputParameters = new ArrayList<>();
        List<Type> inputParameters = new ArrayList<>();
        Address holder = new Address(holderAddress);
        Address owner = new Address(address);
        inputParameters.add(owner);
        inputParameters.add(holder);

        TypeReference<Uint256> staked = new TypeReference<Uint256>() {
        };
        TypeReference<Uint256> locked = new TypeReference<Uint256>() {
        };
        TypeReference<Uint256> unlocked = new TypeReference<Uint256>() {
        };
        outputParameters.add(staked);
        outputParameters.add(locked);
        outputParameters.add(unlocked);

        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.request.Transaction transaction =
                org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(address, stakingContentAddress, data);

        MyDelegateResult myDelegateResult = new MyDelegateResult();
        myDelegateResult.setUnlock(BigDecimal.ZERO);
        myDelegateResult.setLock(BigDecimal.ZERO);
        myDelegateResult.setDelegate(BigDecimal.ZERO);
        BigInteger balanceValue = BigInteger.ZERO;
        try {
            EthCall ethCall = stakingWeb3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            if (null == results || results.size() != 3) return myDelegateResult;
            BigInteger d = (BigInteger) results.get(0).getValue();
            if (d.compareTo(BigInteger.ZERO) == 0)
                myDelegateResult.setDelegate(BigDecimal.ZERO);
            else
                myDelegateResult.setDelegate(new BigDecimal(d).divide(new BigDecimal("1000000000000000000")));

            BigInteger l = (BigInteger) results.get(1).getValue();
            if (l.compareTo(BigInteger.ZERO) == 0)
                myDelegateResult.setLock(BigDecimal.ZERO);
            else
                myDelegateResult.setLock(new BigDecimal(l).divide(new BigDecimal("1000000000000000000")));


            BigInteger u = (BigInteger) results.get(2).getValue();
            if (u.compareTo(BigInteger.ZERO) == 0)
                myDelegateResult.setUnlock(BigDecimal.ZERO);
            else
                myDelegateResult.setUnlock(new BigDecimal(u).divide(new BigDecimal("1000000000000000000")));

            System.out.println(u.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("myDelegateResult" + myDelegateResult.getDelegate().toString() + ":" +
                myDelegateResult.getLock().toString() + ":" + myDelegateResult.getUnlock().toString());

        return myDelegateResult;
    }


    public static String sendFunctionTransfer(String hexValue) {
        String txHash = null;
        try {
            EthSendTransaction ethSendTransaction = stakingWeb3j.ethSendRawTransaction(hexValue).sendAsync().get();
            if (ethSendTransaction.getError() != null) {
                System.out.println("sendFunctionTransfer : error" + ethSendTransaction.getError().getMessage());
            }
            txHash = ethSendTransaction.getTransactionHash();
            System.out.println("sendFunctionTransfer: tx " + ethSendTransaction.getTransactionHash());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txHash;
    }

    public static String sendFunctionTransferAndCHeckValue(String hexValue) {
        String txHash = null;
        try {
            EthSendTransaction ethSendTransaction = stakingWeb3j.ethSendRawTransaction(hexValue).sendAsync().get();
            if (ethSendTransaction.getError() != null) {
                System.out.println("sendFunctionTransfer : error" + ethSendTransaction.getError().getMessage());
            }

            txHash = ethSendTransaction.getTransactionHash();

            if (txHash != null) {
                Transaction tr = stakingWeb3j.ethGetTransactionByHash(txHash).sendAsync().get().getResult();
                if (null != tr) {
                    if (tr.getValue().compareTo(new BigInteger(String.valueOf(999))
                            .multiply(new BigInteger("1000000000000000000"))) <= 0) {
                        System.out.println("check hash error value low:" + txHash);
                        txHash = null;
                    }
                }
            }

            System.out.println("sendFunctionTransfer: tx " + ethSendTransaction.getTransactionHash());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txHash;
    }


    public static BigDecimal getTrueBalance(String address) {
        try {
            EthGetBalance ethGetBalance1 = stakingWeb3j.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();
            return new BigDecimal(ethGetBalance1.getBalance()).divide(new BigDecimal("1000000000000000000"));
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }


    //
    public static BigInteger queryTxExecutionReceipt(String txHash) {
        //确认交易已执行
        EthGetTransactionReceipt receipt = null;
        try {
            receipt = stakingWeb3j.ethGetTransactionReceipt(txHash).send();
            if (null != receipt.getResult() && null != receipt.getResult().getStatus()) {
                System.out.println("status=" + receipt.getResult().getStatus());
                if ("0x1".equals(receipt.getResult().getStatus())) {
                    return receipt.getResult().getGasUsed().multiply(stakingGasPrice);
                } else if ("0x0".equals(receipt.getResult().getStatus())) {
                    if (receipt.getError() != null) {
                        System.out.println("txHash=" + txHash + "  receipt error = " + receipt.getError().getMessage());
                    }
                    return BigInteger.ONE;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("queryTxExecutionState Transaction_Pending");
        return BigInteger.ZERO;
    }


    public static BigInteger queryTxExecutionReceipt(String txHash, int value) {
        //确认交易已执行
        EthGetTransactionReceipt receipt = null;
        try {
            Transaction tr = stakingWeb3j.ethGetTransactionByHash(txHash).sendAsync().get().getResult();
            if (null != tr) {
                if (tr.getValue().compareTo(new BigInteger(String.valueOf(value))
                        .multiply(new BigInteger("1000000000000000000"))) != 0) {
                    return BigInteger.ONE;
                }
            }

            receipt = stakingWeb3j.ethGetTransactionReceipt(txHash).send();
            if (null != receipt.getResult() && null != receipt.getResult().getStatus()) {
                System.out.println("status=" + receipt.getResult().getStatus());
                if ("0x1".equals(receipt.getResult().getStatus())) {
                    return receipt.getResult().getGasUsed().multiply(stakingGasPrice);
                } else if ("0x0".equals(receipt.getResult().getStatus())) {
                    if (receipt.getError() != null) {
                        System.out.println("txHash=" + txHash + "  receipt error = " + receipt.getError().getMessage());
                    }
                    return BigInteger.ONE;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("queryTxExecutionState Transaction_Pending");
        return BigInteger.ZERO;
    }

    public static BigInteger queryTxExecutionReceiptTimes(String txHash) {
        int times = 3;
        while (times > 0) {
            BigInteger gas = queryTxExecutionReceipt(txHash);
            if (gas.compareTo(BigInteger.ONE) > 0) {
                return gas;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            times--;
        }
        return BigInteger.ONE;
    }


    public static String staking_delegate(BigInteger value) {
        Credentials credentials = Credentials.create("0xa355208ca298ae484707d7c6a5ada8562df36ddc6bdb886f33d42deda4be3f5f");
        System.out.println(credentials.getAddress());

        List<Type> inputParameters = new ArrayList<>();
        Address holder = new Address(hoder);
        inputParameters.add(holder);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Function function = new Function("delegate", inputParameters, outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);

        BigInteger nonce = NonceManager.getInstance().getNonce(stakingWeb3j, credentials.getAddress());

        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce,
                stakingGasPrice, stakingGasLimit, stakingContentAddress, value, encodedFunction);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, stakingChainId, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        EthSendTransaction ethSendTransaction = null;
        try {
            ethSendTransaction = stakingWeb3j.ethSendRawTransaction(hexValue).sendAsync().get();
            System.out.println("createOrder: tx " + ethSendTransaction.getTransactionHash());
            NonceManager.getInstance().exhaustNonce(credentials.getAddress(), ethSendTransaction.getTransactionHash());
            return ethSendTransaction.getTransactionHash();
        } catch (InterruptedException | ExecutionException e) {
            NonceManager.getInstance().exhaustNonce(credentials.getAddress(), null);
            e.printStackTrace();
        }
        return null;
    }


    public static String staking_undelegate(BigInteger value) {
        Credentials credentials = Credentials.create("0xd15ad4aa95ee81f7d8055b3ad3619d0c76a925df00a85ab129ad2eb624ea990a");
        System.out.println(credentials.getAddress());
//        String b = "";
//        try {
//            b = getTrueTestBalance(credentials.getAddress()).toPlainString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(b);

        List<Type> inputParameters = new ArrayList<>();
        Address holder = new Address("0xC02f50f4F41f46b6a2f08036ae65039b2F9aCd69");
        Uint256 _value = new Uint256(value);
        inputParameters.add(holder);
        inputParameters.add(_value);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Function function = new Function("undelegate", inputParameters, outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);

        BigInteger nonce = NonceManager.getInstance().getNonce(stakingWeb3j, credentials.getAddress());

        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce,
                stakingGasPrice, stakingGasLimit, stakingContentAddress, encodedFunction);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, stakingChainId, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        EthSendTransaction ethSendTransaction = null;

        try {
            ethSendTransaction = stakingWeb3j.ethSendRawTransaction(hexValue).sendAsync().get();
            System.out.println("undelegate: tx " + ethSendTransaction.getTransactionHash());
            NonceManager.getInstance().exhaustNonce(credentials.getAddress(), ethSendTransaction.getTransactionHash());
            return ethSendTransaction.getTransactionHash();
        } catch (InterruptedException | ExecutionException e) {
            NonceManager.getInstance().exhaustNonce(credentials.getAddress(), null);
            e.printStackTrace();
        }
        return null;
    }


    public static String staking_withdrawDelegate(BigInteger value) {
        Credentials credentials = Credentials.create("0xd15ad4aa95ee81f7d8055b3ad3619d0c76a925df00a85ab129ad2eb624ea990a");
        System.out.println(credentials.getAddress());
//        String b = "";
//        try {
//            b = getTrueTestBalance(credentials.getAddress()).toPlainString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(b);

        List<Type> inputParameters = new ArrayList<>();
        Address holder = new Address("0xC02f50f4F41f46b6a2f08036ae65039b2F9aCd69");
        Uint256 _value = new Uint256(value);
        inputParameters.add(holder);
        inputParameters.add(_value);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Function function = new Function("withdrawDelegate", inputParameters, outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);

        BigInteger nonce = NonceManager.getInstance().getNonce(stakingWeb3j, credentials.getAddress());

        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce,
                stakingGasPrice, stakingGasLimit, stakingContentAddress, encodedFunction);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, stakingChainId, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        EthSendTransaction ethSendTransaction = null;

        try {
            ethSendTransaction = stakingWeb3j.ethSendRawTransaction(hexValue).sendAsync().get();
            System.out.println("withdrawDelegate: tx " + ethSendTransaction.getTransactionHash());
            NonceManager.getInstance().exhaustNonce(credentials.getAddress(), ethSendTransaction.getTransactionHash());
            return ethSendTransaction.getTransactionHash();
        } catch (InterruptedException | ExecutionException e) {
            NonceManager.getInstance().exhaustNonce(credentials.getAddress(), null);
            e.printStackTrace();
        }
        return null;
    }


    public static void staking_depo() {
        Credentials credentials = Credentials.create("0x18e14a7b6a307f426a94f8114701e7c8e774e7f9a47e2c2035db29a206321725");
        String data = StakingFunctionEncoder.makeDeposit(credentials.getEcKeyPair().getPublicKey().toByteArray(),
                10, BigInteger.ONE.multiply(new BigInteger("1000")));


        System.out.println(credentials.getAddress() + "pk:" + credentials.getEcKeyPair().getPublicKey().toString(16) + "fee:" + 0 + "value:" + 1);

        BigInteger nonce = NonceManager.getInstance().getNonce(stakingWeb3j, credentials.getAddress());

        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce,
                stakingGasPrice, stakingGasLimit, stakingContentAddress, data);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, stakingChainId, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        EthSendTransaction ethSendTransaction = null;

        try {
            ethSendTransaction = stakingWeb3j.ethSendRawTransaction(hexValue).sendAsync().get();
            System.out.println("withdrawDelegate: tx " + ethSendTransaction.getTransactionHash());
            NonceManager.getInstance().exhaustNonce(credentials.getAddress(), ethSendTransaction.getTransactionHash());
            System.out.println(ethSendTransaction.getTransactionHash());
        } catch (InterruptedException | ExecutionException e) {
            NonceManager.getInstance().exhaustNonce(credentials.getAddress(), null);
            e.printStackTrace();
        }
    }


    public static BigInteger staking_getDelegate() {
        String methodName = "getDelegate";
        Credentials credentials = Credentials.create("0xd15ad4aa95ee81f7d8055b3ad3619d0c76a925df00a85ab129ad2eb624ea990a");
        List<TypeReference<?>> outputParameters = new ArrayList<>();
        List<Type> inputParameters = new ArrayList<>();
        Address holder = new Address("0xC02f50f4F41f46b6a2f08036ae65039b2F9aCd69");
        Address owner = new Address(credentials.getAddress());
        inputParameters.add(owner);
        inputParameters.add(holder);

        TypeReference<Uint256> staked = new TypeReference<Uint256>() {
        };
        TypeReference<Uint256> locked = new TypeReference<Uint256>() {
        };
        TypeReference<Uint256> unlocked = new TypeReference<Uint256>() {
        };
        outputParameters.add(staked);
        outputParameters.add(locked);
        outputParameters.add(unlocked);

        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.request.Transaction transaction =
                org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(credentials.getAddress(), stakingContentAddress, data);

        BigInteger balanceValue = BigInteger.ZERO;
        try {
            EthCall ethCall = stakingWeb3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            balanceValue = (BigInteger) results.get(0).getValue();
            System.out.println(((BigInteger) results.get(0).getValue()).toString());
            System.out.println(((BigInteger) results.get(1).getValue()).toString());
            System.out.println(((BigInteger) results.get(2).getValue()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return balanceValue;
    }


}
