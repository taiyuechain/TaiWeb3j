package com.taiweb3j.sample;

import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BatchTransansferParser {

    public static void main(String[] args) {
        String input ="0xffc3a769000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000002e00000000000000000000000000000000000000000000000000000000000000014000000000000000000000000c414eeb5077ff57fc029753f49d1e23fe50b7dd90000000000000000000000005e12901bc0934a03d404860c10af687ebea753e70000000000000000000000004f6c0b84f90375bdbaa4d604c49747ae5390f277000000000000000000000000b206e7c4b2e0dd6e9c58ffbd1ce9550fe1e782810000000000000000000000006690534c1e2bf2dfd3f1928cad82486b1b7f636300000000000000000000000072fedf26efd3ff187c6faa8d454d31e08d3101ab0000000000000000000000002c42e413c0121c674be607b8ea888c4ac4375d100000000000000000000000006c99716c0ec88cd673a4b42a3d74f8a246f0ebe50000000000000000000000009fee8eb4fa644e9e06c8eb8828b412363d5014d40000000000000000000000006b56936435d38bd8abc5f755a87454a5c1da9a130000000000000000000000009f70910a70238beb499d5c8da9499c24f0139eeb000000000000000000000000924c61295e5452937ddaafda3f99d0ee39df6cc5000000000000000000000000f1898b6a765b691bd14e992e71880a029433c87e0000000000000000000000001a56f24c987a451a7fa51bd6429c154c9d2d52f500000000000000000000000000843a8ec0a690b8d4c872dfc57234e5d390e6fb00000000000000000000000028120d740a17eafaa30aec969ccb991989168710000000000000000000000000016d56490a312bd728b7d20f5ca0386320d004cf000000000000000000000000df09cb7590c70551d00b3c009b4ab8a8968c290f0000000000000000000000006a14cd4efe221e02384593e8e4a9549e81aa40af000000000000000000000000251db8e4e370605180c2210482b8733f6f86e9d4000000000000000000000000000000000000000000000000000000000000001400000000000000000000000000000000000000000000000007293344201a00000000000000000000000000000000000000000000000000000f143c902b26000000000000000000000000000000000000000000000000000005a3a52c0c7ae00000000000000000000000000000000000000000000000000001717b72f0a4000000000000000000000000000000000000000000000000000000c6f3b40b6c000000000000000000000000000000000000000000000000000059ac1bae1d52200000000000000000000000000000000000000000000000000069bfada0a47a0000000000000000000000000000000000000000000000000000040d6473f26a000000000000000000000000000000000000000000000000000037d81eae102c000000000000000000000000000000000000000000000000000000c6f3b40b6c0000000000000000000000000000000000000000000000000000058d15e17628000000000000000000000000000000000000000000000000000000d529ae9e86000000000000000000000000000000000000000000000000000240148e2f7d7300000000000000000000000000000000000000000000000000000b2861bd7f6a000000000000000000000000000000000000000000000000000070cc74ef9e600000000000000000000000000000000000000000000000000000017fb16d83be000000000000000000000000000000000000000000000000000ac6e77ab663a80000000000000000000000000000000000000000000000000001198d748273200000000000000000000000000000000000000000000000000008d1b9c5b60dbc80000000000000000000000000000000000000000000000000379a398180dcdcc000";
        parseInput(input);

    }

    public static final BigInteger MAX_BYTE_LENGTH = new BigInteger("32");
    public static final int fixedByteNumber = 64;
    public static final String hexPrefix = "0x";

    public static BigInteger convertHex(String hexValue) {
        if (!hexValue.startsWith("0x")) {
            hexValue = hexPrefix.concat(hexValue);
        }
        return Numeric.decodeQuantity(hexValue);
    }

    public static void parseInput(String input) {
        int prePosition = 0;
        int currentPosition = 10;
        String methodId = input.substring(prePosition, currentPosition);
        System.out.println("methodId=" + methodId);

        /**
         * 获取动态数组的长度
         */
        prePosition = currentPosition;
        currentPosition += fixedByteNumber;
        String hexDynamicArrayNumber = input.substring(prePosition, currentPosition);
        int dynamicArrayNumber = convertHex(hexPrefix.concat(hexDynamicArrayNumber)).intValue();
        int dynamicArrayLen = convertHex(hexDynamicArrayNumber).divide(MAX_BYTE_LENGTH).intValue();

        prePosition = currentPosition;
        currentPosition += fixedByteNumber;
        String hexNextArrayLocation = input.substring(prePosition, currentPosition);
        int nextArrayLocation = (convertHex(hexNextArrayLocation).intValue() - dynamicArrayNumber) * 2;

        /**
         * 获取地址信息
         */
        prePosition = currentPosition;
        currentPosition += nextArrayLocation;
        String addressData = input.substring(prePosition, currentPosition);
        List<String> addressList = parseAddressData(addressData);
        addressList = handleAddressList(addressList);
        for (String address : addressList) {
            System.out.println("address=" + address);
        }

        /**
         * 获取转账金额
         */
        prePosition = currentPosition;
        currentPosition += nextArrayLocation;
        String transferBalanceData = input.substring(prePosition, currentPosition);
        List<String> amountList = parseTransferAmountData(transferBalanceData);
        List<BigInteger> transferBalanceList = handleAmountList(amountList);
        for (BigInteger amount : transferBalanceList) {
            System.out.println("amount=" + amount.toString());
        }
    }


    public static List<String> parseAddressData(String addressData) {
        int hexAddressLenth = convertHex(addressData.substring(0, fixedByteNumber)).intValue();
        String realAddressData = addressData.substring(fixedByteNumber);
        if (realAddressData.length() != hexAddressLenth * fixedByteNumber) {
            System.out.println("parseAddressList length error");
            return null;
        }

        List<String> addressList = new ArrayList<String>();
        String address = null;
        for (int i = 0; i < hexAddressLenth; i++) {
            address = realAddressData.substring(i * fixedByteNumber, (i + 1) * fixedByteNumber);
            addressList.add(address);
        }
        return addressList;
    }

    public static List<String> handleAddressList(List<String> addressList) {
        if (addressList == null || addressList.size() == 0) {
            return null;
        }
        for (int i = 0; i < addressList.size(); i++) {
            addressList.set(i, HandleAddress(addressList.get(i)));
        }
        return addressList;
    }

    private static String HandleAddress(String address) {
        if (address.length() != 64) {
            System.out.println("HandleAddress error,address=" + address);
            return null;
        }
        return address.substring(24);
    }


    public static List<String> parseTransferAmountData(String transferAmountData) {
        int amountLenth = convertHex(transferAmountData.substring(0, fixedByteNumber)).intValue();
        String realAmountData = transferAmountData.substring(fixedByteNumber);
        if (realAmountData.length() != amountLenth * fixedByteNumber) {
            System.out.println("parseTransferAmountData length error");
            return null;
        }

        List<String> amountList = new ArrayList<String>();
        String amount = null;
        for (int i = 0; i < amountLenth; i++) {
            amount = realAmountData.substring(i * fixedByteNumber, (i + 1) * fixedByteNumber);
            amountList.add(amount);
        }
        return amountList;
    }

    public static List<BigInteger> handleAmountList(List<String> amountList) {
        if (amountList == null || amountList.size() == 0) {
            System.out.println("handleAmountList error");
            return null;
        }
        List<BigInteger> newAmountList = new ArrayList<>();
        for (int i = 0; i < amountList.size(); i++) {
            newAmountList.add(HandleAmount(amountList.get(i)));
        }
        return newAmountList;
    }

    private static BigInteger HandleAmount(String amount) {
        if (amount.length() != 64) {
            System.out.println("HandleAmount error,amount=" + amount);
            return BigInteger.ZERO;
        }
        return convertHex(amount);
    }

}
