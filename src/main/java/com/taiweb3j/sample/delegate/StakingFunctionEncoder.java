package com.taiweb3j.sample.delegate;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class StakingFunctionEncoder {

    /**
     * makeDelegate is make delegate function
     * @param holder address of a particular validator account
     * @param valueWei amount of coin deposit to the delegator account
     * @return function string
     */
    public static String makeDelegate(String holder, BigInteger valueWei) {
        List<Type> inputParameters = new ArrayList<>();
        Address _holder = new Address(holder);
        Uint256 _value = new Uint256(valueWei);
        inputParameters.add(_holder);
        inputParameters.add(_value);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Function function = new Function("delegate", inputParameters, outputParameters);
        return FunctionEncoder.encode(function);
    }

    /**
     * makeUnDelegate is make unDelegate function
     * @param holder  address of the delegator
     * @param valueWei unlock a portion of delegated coin
     * @return function string
     */
    public static String makeUnDelegate(String holder, BigInteger valueWei) {
        List<Type> inputParameters = new ArrayList<>();
        Address _holder = new Address(holder);
        Uint256 _value = new Uint256(valueWei);
        inputParameters.add(_holder);
        inputParameters.add(_value);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Function function = new Function("undelegate", inputParameters, outputParameters);
        return FunctionEncoder.encode(function);
    }

    /**
     * makeUnDelegate is make withdraw delegate  function
     * @param holder  address of the delegator
     * @param valueWei amount of coin withdraw to the owner
     * @return function string
     */
    public static String makeWithdrawDelegate(String holder, BigInteger valueWei) {
        List<Type> inputParameters = new ArrayList<>();
        Address _holder = new Address(holder);
        Uint256 _value = new Uint256(valueWei);
        inputParameters.add(_holder);
        inputParameters.add(_value);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Function function = new Function("withdrawDelegate", inputParameters, outputParameters);
        return FunctionEncoder.encode(function);
    }

    /**
     * makeGetDelegate is make get delegate function
     * @param holder address of the delegator
     * @param from owner address of the delegated coin
     * @return function string
     */
    public static String makeGetDelegate(String holder, String from) {
        Function function = makeGetDelegateFunction(holder,from);
        return FunctionEncoder.encode(function);
    }

    private static Function makeGetDelegateFunction(String holder, String from){
        List<Type> inputParameters = new ArrayList<>();
        Address _holder = new Address(holder);
        Address owner = new Address(from);
        inputParameters.add(owner);
        inputParameters.add(_holder);
        return new Function("getDelegate", inputParameters, getStakingOutPutParameters());
    }


    private static byte[] byteMerger(byte[] bt1, byte[] bt2){
        byte[] bt3 = new byte[bt1.length+bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }

    /**
     * makeDeposit is make deposit function
     * @param publicKey delegator's publicKey
     * @param fee  percent of reward charged for delegate, the rate = fee / 10000
     * @param valueWei true token to deposit
     * @return function string
     */
    public static String makeDeposit(byte[] publicKey, int fee, BigInteger valueWei) {
        List<Type> inputParameters = new ArrayList<>();
        if (publicKey.length == 64){
            byte[] top = new byte[]{4};
            publicKey = byteMerger(top,publicKey);
        }
        DynamicBytes _publicKey = new DynamicBytes(publicKey);
        Uint256 _value = new Uint256(valueWei);
        Uint256 _fee = new Uint256(fee);
        inputParameters.add(_publicKey);
        inputParameters.add(_fee);
        inputParameters.add(_value);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Function function = new Function("deposit", inputParameters, outputParameters);
        return FunctionEncoder.encode(function);
    }


    /**
     *  makeCancel is make a cancel function
     * @param valueWei A portion of deposit, the unit is wei
     * @return function string
     */
    public static String makeCancel(BigInteger valueWei) {
        List<Type> inputParameters = new ArrayList<>();
        Uint256 _value = new Uint256(valueWei);
        inputParameters.add(_value);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Function function = new Function("cancel", inputParameters, outputParameters);
        return FunctionEncoder.encode(function);
    }


    /**
     *  makeAppend is make a append function
     * @param valueWei A portion of deposit, the unit is wei
     * @return function string
     */
    public static String makeAppend(BigInteger valueWei) {
        List<Type> inputParameters = new ArrayList<>();
        Uint256 _value = new Uint256(valueWei);
        inputParameters.add(_value);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Function function = new Function("append", inputParameters, outputParameters);
        return FunctionEncoder.encode(function);
    }

    public static String makeSetFee(BigInteger valueWei) {
        List<Type> inputParameters = new ArrayList<>();
        Uint256 _value = new Uint256(valueWei);
        inputParameters.add(_value);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Function function = new Function("setFee", inputParameters, outputParameters);
        return FunctionEncoder.encode(function);
    }


    /**
     *  makeWithdraw is make a withDraw function
     * @param valueWei A portion of deposit, the unit is wei
     * @return function string
     */
    public static String makeWithdraw(BigInteger valueWei) {
        List<Type> inputParameters = new ArrayList<>();
        Uint256 _value = new Uint256(valueWei);
        inputParameters.add(_value);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Function function = new Function("withdraw", inputParameters, outputParameters);
        return FunctionEncoder.encode(function);
    }

    /**
     * makeGetDeposit is make get Deposit function
     * @param owner address of the delegator
     * @return function string
     */
    public static String makeGetDeposit(String owner) {
        List<Type> inputParameters = new ArrayList<>();
        Address _owner = new Address(owner);
        inputParameters.add(_owner);

        Function function = new Function("getDeposit", inputParameters, getStakingOutPutParameters());
        return FunctionEncoder.encode(function);
    }

    private static List<TypeReference<?>> getStakingOutPutParameters() {
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
        return outputParameters;
    }

    private static class DelegateResult {
        private BigInteger delegate;
        private BigInteger lock;
        private BigInteger unlock;

        public BigInteger getDelegate() {
            return delegate;
        }

        public void setDelegate(BigInteger delegate) {
            this.delegate = delegate;
        }

        public BigInteger getLock() {
            return lock;
        }

        public void setLock(BigInteger lock) {
            this.lock = lock;
        }

        public BigInteger getUnlock() {
            return unlock;
        }

        public void setUnlock(BigInteger unlock) {
            this.unlock = unlock;
        }
    }

    /**
     * parsingResults is parsing eth result callValue
     * @param callValue eth callValue
     * @return delegate lock unlock amount
     */
    public static DelegateResult parsingResults(String callValue) {
        DelegateResult delegateResult = new DelegateResult();
        String tempAddress = "0x00000000000000000000000000000000000000";
        List<Type> results = FunctionReturnDecoder.decode(callValue,
                makeGetDelegateFunction(tempAddress,tempAddress).getOutputParameters());
        delegateResult.setDelegate((BigInteger) results.get(0).getValue());
        delegateResult.setLock((BigInteger) results.get(1).getValue());
        delegateResult.setUnlock((BigInteger) results.get(2).getValue());
        return delegateResult;
    }



    public static byte[] hexStrToByteArray(String str)
    {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++){
            String subStr = str.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte)Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }

}
