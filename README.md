## 部署

###  Maven 引用

```java
<dependency>
    <groupId>com.taiyuechain</groupId>
    <artifactId>taiWeb3j</artifactId>
    <version>4.5.5.8</version>
</dependency>
```



### Jar包本地部署

[下载最新的Jar包](https://github.com/taiyuechain/TaiWeb3j/releases)， 在工程主目录建立`src`同级 **`lib`** 目录并将`taiWeb3jxxx.jar`拷贝进去，并在 `pom.xml` 中增加引用

```java
<dependency>
    <groupId>taiWeb3j</groupId>
    <artifactId>com.taiweb3j</artifactId>
    <version>4.5.5.8</version>
    <scope>system</scope>
    <systemPath>${basedir}/lib/taiWeb3j-4.5.5.8.jar</systemPath>
 </dependency>
 <dependency>
 	<groupId>org.web3j</groupId>
    <artifactId>core</artifactId>
    <version>4.5.0</version>
 </dependency>
 <dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.3.2</version>
</dependency>
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
```



## 使用实例


### 链相关

#### 获取余额

```java
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
    return balance;
}
```

#### 获取交易序号


``` java
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
```

#### 获取交易燃料费

```java
public static BigInteger getGasPrice() {
        BigInteger gasPrice = null;
        try {
            EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
            gasPrice = ethGasPrice.getGasPrice();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gasPrice;
}

```
#### 获取快链最新高度

```java
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
```


#### 根据高度获取块信息(full 扩展信息)

```java

public FastBlock getFastBlockByNumber(BigInteger fastBlockNumber, boolean full) {
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
```


#### 根据HASH获取块信息(full 扩展信息)

```java

public FastBlock getFastBlockByHash(String fastHash, boolean full) {
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
```
### 交易相关

#### 普通交易

```java
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
 
 public void sendRawTransaction(String hexValue) {
    try {
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
        String txHash = ethSendTransaction.getTransactionHash();
        if (ethSendTransaction.getError() != null) {
            logger.error("sendTransaction error", ethSendTransaction.getError().getMessage());
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
   
String hexMessage = transactionClientUsage.genRawTransaction();
sendRawTransaction(hexMessage);
```



#### 代付交易

```java
public String sendPaymentTx() {
        String txHash = null;
        try {
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
            
            TaiRawTransaction taiRawTransaction = TaiRawTransaction.creattaiPaymentTransaction(nonce, Constant.DEFAULT_GASPRICE,Constant.DEFAULT_GASLIMIT, toAddress, Constant.DEFAULT_VALUE, null, paymentAddress);
 			TaiTransactionManager taiTransactionManager = new TaiTransactionManager(taiWeb3JRequest, chainId);

            TaiSendTransaction taiSendTransaction = taiTransactionManager.signWithFromPaymentAndSend(
                    taiRawTransaction, fromPrivatekey, paymentPrivateKey);
            if (taiSendTransaction != null && taiSendTransaction.hasError()) {
                logger.error("sendPaymentTransactionWithSigned error=[{}] ", taiSendTransaction.getError().getMessage());
            }
            txHash = taiSendTransaction.getTrueTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txHash;
    }
```



### ERC20 代币相关

#### 交易

```java
 public void sendTokenTransaction(String contractAddress, String toAddress, String from_privateKey) {
        try {
            Credentials from_credentials = Credentials.create(from_privateKey);
            String from_address = from_credentials.getAddress();
            BigInteger amount = Constant.DEFAULT_VALUE;
            String methodName = "transfer";
            List<Type> inputParameters = new ArrayList<>();
            List<TypeReference<?>> outputParameters = new ArrayList<>();

            Address tAddress = new Address(toAddress);
    
            Uint256 value = new Uint256(amount);
            inputParameters.add(tAddress);
            inputParameters.add(value);
    
            TypeReference<Bool> typeReference = new TypeReference<Bool>() {
            };
            outputParameters.add(typeReference);
            Function function = new Function(methodName, inputParameters, outputParameters);
            String data = FunctionEncoder.encode(function);
    
            EthGetTransactionCount ethGetTransactionCount = web3j
                    .ethGetTransactionCount(from_address, DefaultBlockParameterName.PENDING).sendAsync().get();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
    
            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    nonce, Constant.DEFAULT_GASPRICE,
                    Constant.DEFAULT_CONTRACT_GASLIMIT, contractAddress, data);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, from_credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
            String txHash = ethSendTransaction.getTransactionHash();
            if (ethSendTransaction.getError() != null) {
                logger.error("sendTokenTransaction error" + ethSendTransaction.getError());
            }
            logger.info("txHash={}", txHash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```



#### 获取余额

```java
 public BigInteger getTokenBalance(String fromAddress, String contractAddress) {
        String methodName = "balanceOf";
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Address address = new Address(fromAddress);
        inputParameters.add(address);

        TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(fromAddress, contractAddress, data);
    
        BigInteger balanceValue = BigInteger.ZERO;
        try {
            EthCall ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            if (ethCall.getError() != null) {
                logger.error("getTokenBalance error={}", ethCall.getError().getMessage());
            }
            if (results.size() == 0) {
                logger.error("contractAddress =[{}] is not exist", contractAddress);
                return balanceValue;
            }
            String resultVal = results.get(0).getValue().toString();
            balanceValue = new BigInteger(resultVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return balanceValue;
 }
```



#### 获取代币名称

```java
 public String getTokenName(String contractAddress) {
        String methodName = "name";
        String name = null;
        String fromAddr = AddressConstant.EMPTY_ADDRESS;
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();

        TypeReference<Utf8String> typeReference = new TypeReference<Utf8String>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
    
        Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);
        try {
            EthCall ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            name = results.get(0).getValue().toString();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return name;
    }

```



#### 获取代币位数（小数位)

```java
public int getTokenDecimals(String contractAddress) {
    String methodName = "decimals";
    String fromAddr = AddressConstant.EMPTY_ADDRESS;
    int decimal = 0;
    List<Type> inputParameters = new ArrayList<>();
    List<TypeReference<?>> outputParameters = new ArrayList<>();

    TypeReference<Uint8> typeReference = new TypeReference<Uint8>() {
    };
    outputParameters.add(typeReference);
    Function function = new Function(methodName, inputParameters, outputParameters);
    String data = FunctionEncoder.encode(function);
    
    Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);
    try {
        EthCall ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
        List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
        decimal = Integer.parseInt(results.get(0).getValue().toString());
    } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
    }
    return decimal;
}
```



#### 获取代币总量

```java
public BigInteger getTokenTotalSupply(String contractAddress) {
    String methodName = "totalSupply";
    String fromAddr = AddressConstant.EMPTY_ADDRESS;
    BigInteger totalSupply = BigInteger.ZERO;
    List<Type> inputParameters = new ArrayList<>();
    List<TypeReference<?>> outputParameters = new ArrayList<>();

    TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
    };
    outputParameters.add(typeReference);
    Function function = new Function(methodName, inputParameters, outputParameters);
    String data = FunctionEncoder.encode(function);
    
    Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);
    try {
        EthCall ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
        List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
        totalSupply = (BigInteger) results.get(0).getValue();
    } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
    }
    return totalSupply;
}
```



### 委员会相关

#### 获取当前委员会届数

```java
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
```



#### 查询委员会

```java
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
```



### 使用方法和常量

#### Web3j 声明

```java
public TaiWeb3jRequest taiWeb3JRequest = new TaiWeb3jRequest(节点地址);
public Web3j web3j = Web3j.build(new HttpService(节点地址));
```

#### 建议测试交易执行参数

```java
GasPrice:1200000000
GasLimit:30000(普通交易)/60000(ERC20)
```



## 更多支持

### 测试用例

#### 		https://github.com/taiyuechain/TaiWeb3j/tree/master/src/test/java

### Git官网

#### 		https://github.com/taiyuechain

### 官网

#### 		https://www.taiyuechain.com/