package com.taiweb3j.response.fast;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.taiweb3j.response.committee.BlockMember;
import com.taiweb3j.response.transaction.TaiTransaction;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FastBlock {
    private String number;
    private String hash;
    private String parentHash;
    private String committeeRoot;
    private String maker;
    private String logsBloom;
    private String stateRoot;

    private String snailNumber;
    private String snailHash;
    private String extraData;
    private String size;
    private String gasLimit;
    private String gasUsed;
    private String timestamp;
    private String transactionsRoot;
    private String receiptsRoot;

    private List<BlockMember> switchInfos;

    private List<TransactionResult> transactions;

    public FastBlock() {
    }

    public FastBlock(String number, String hash, String parentHash, String committeeRoot, String maker, String logsBloom, String stateRoot, String snailNumber, String snailHash, String extraData, String size, String gasLimit, String gasUsed, String timestamp, String transactionsRoot, String receiptsRoot, List<BlockMember> switchInfos, List<TransactionResult> transactions) {
        this.number = number;
        this.hash = hash;
        this.parentHash = parentHash;
        this.committeeRoot = committeeRoot;
        this.maker = maker;
        this.logsBloom = logsBloom;
        this.stateRoot = stateRoot;
        this.snailNumber = snailNumber;
        this.snailHash = snailHash;
        this.extraData = extraData;
        this.size = size;
        this.gasLimit = gasLimit;
        this.gasUsed = gasUsed;
        this.timestamp = timestamp;
        this.transactionsRoot = transactionsRoot;
        this.receiptsRoot = receiptsRoot;
        this.switchInfos = switchInfos;
        this.transactions = transactions;
    }

    public List<BlockMember> getSwitchInfos() {
        return switchInfos;
    }

    public void setSwitchInfos(List<BlockMember> switchInfos) {
        this.switchInfos = switchInfos;
    }

    public String getCommitteeRoot() {
        return committeeRoot;
    }

    public void setCommitteeRoot(String committeeRoot) {
        this.committeeRoot = committeeRoot;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getSnailNumberRaw() {
        return snailNumber;
    }

    public BigInteger getSnailNumber() {
        return Numeric.decodeQuantity(snailNumber);
    }

    public void setSnailNumber(String snailNumber) {
        this.snailNumber = snailNumber;
    }

    public String getSnailHash() {
        return snailHash;
    }

    public void setSnailHash(String snailHash) {
        this.snailHash = snailHash;
    }

    public BigInteger getNumber() {
        return Numeric.decodeQuantity(number);
    }

    public String getNumberRaw() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getParentHash() {
        return parentHash;
    }

    public void setParentHash(String parentHash) {
        this.parentHash = parentHash;
    }

    public String getLogsBloom() {
        return logsBloom;
    }

    public void setLogsBloom(String logsBloom) {
        this.logsBloom = logsBloom;
    }

    public String getTransactionsRoot() {
        return transactionsRoot;
    }

    public void setTransactionsRoot(String transactionsRoot) {
        this.transactionsRoot = transactionsRoot;
    }

    public String getStateRoot() {
        return stateRoot;
    }

    public void setStateRoot(String stateRoot) {
        this.stateRoot = stateRoot;
    }

    public String getReceiptsRoot() {
        return receiptsRoot;
    }

    public void setReceiptsRoot(String receiptsRoot) {
        this.receiptsRoot = receiptsRoot;
    }


    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public BigInteger getSize() {
        return size != null ? Numeric.decodeQuantity(size) : BigInteger.ZERO;
    }

    public String getSizeRaw() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigInteger getGasLimit() {
        return Numeric.decodeQuantity(gasLimit);
    }

    public String getGasLimitRaw() {
        return gasLimit;
    }

    public void setGasLimit(String gasLimit) {
        this.gasLimit = gasLimit;
    }

    public BigInteger getGasUsed() {
        return Numeric.decodeQuantity(gasUsed);
    }

    public String getGasUsedRaw() {
        return gasUsed;
    }

    public void setGasUsed(String gasUsed) {
        this.gasUsed = gasUsed;
    }

    public BigInteger getTimestamp() {
        return Numeric.decodeQuantity(timestamp);
    }

    public String getTimestampRaw() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @JsonDeserialize(using = ResultTransactionDeserialiser.class)
    public void setTransactions(List<TransactionResult> transactions) {
        this.transactions = transactions;
    }

    public List<TransactionResult> getTransactions() {
        return transactions;
    }


    public interface TransactionResult<T> {
        T get();
    }

    public static class TransactionHash implements TransactionResult<String> {
        private String value;

        public TransactionHash() {
        }

        public TransactionHash(String value) {
            this.value = value;
        }

        @Override
        public String get() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof TransactionHash)) {
                return false;
            }

            TransactionHash that = (TransactionHash) o;

            return value != null ? value.equals(that.value) : that.value == null;
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "TransactionHash{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }

    public static class TransactionObject extends TaiTransaction
            implements TransactionResult<TaiTransaction> {
        public TransactionObject() {
        }

        public TransactionObject(
                String hash, String nonce, String blockHash,
                String blockNumber, String transactionIndex, String from,
                String to, String value, String gasPrice, String gas,
                String input, String creates, String publicKey,
                String raw, String r, String s, int v,
                String fee, String payer, String pr, String ps, int pv) {
            super(
                    hash, nonce, blockHash,
                    blockNumber, transactionIndex, from,
                    to, value, gas, gasPrice,
                    input, creates, publicKey,
                    raw, r, s, v,
                    fee, payer,
                    pr, ps, pv);

        }

        @Override
        public TaiTransaction get() {
            return this;
        }
    }

    public static class ResultTransactionDeserialiser
            extends JsonDeserializer<List<TransactionResult>> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public List<TransactionResult> deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {

            List<TransactionResult> transactionResults = new ArrayList<>();
            JsonToken nextToken = jsonParser.nextToken();

            if (nextToken == JsonToken.START_OBJECT) {
                Iterator<TransactionObject> transactionObjectIterator =
                        objectReader.readValues(jsonParser, TransactionObject.class);
                while (transactionObjectIterator.hasNext()) {
                    transactionResults.add(transactionObjectIterator.next());
                }
            } else if (nextToken == JsonToken.VALUE_STRING) {
                jsonParser.getValueAsString();

                Iterator<TransactionHash> transactionHashIterator =
                        objectReader.readValues(jsonParser, TransactionHash.class);
                while (transactionHashIterator.hasNext()) {
                    transactionResults.add(transactionHashIterator.next());
                }
            }

            return transactionResults;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FastBlock)) {
            return false;
        }

        FastBlock block = (FastBlock) o;

        if (getNumberRaw() != null
                ? !getNumberRaw().equals(block.getNumberRaw())
                : block.getNumberRaw() != null) {
            return false;
        }
        if (getHash() != null ? !getHash().equals(block.getHash()) : block.getHash() != null) {
            return false;
        }
        if (getParentHash() != null
                ? !getParentHash().equals(block.getParentHash())
                : block.getParentHash() != null) {
            return false;
        }
        if (getLogsBloom() != null
                ? !getLogsBloom().equals(block.getLogsBloom())
                : block.getLogsBloom() != null) {
            return false;
        }
        if (getTransactionsRoot() != null
                ? !getTransactionsRoot().equals(block.getTransactionsRoot())
                : block.getTransactionsRoot() != null) {
            return false;
        }
        if (getStateRoot() != null
                ? !getStateRoot().equals(block.getStateRoot())
                : block.getStateRoot() != null) {
            return false;
        }
        if (getReceiptsRoot() != null
                ? !getReceiptsRoot().equals(block.getReceiptsRoot())
                : block.getReceiptsRoot() != null) {
            return false;
        }
        if (getExtraData() != null
                ? !getExtraData().equals(block.getExtraData())
                : block.getExtraData() != null) {
            return false;
        }
        if (getSizeRaw() != null
                ? !getSizeRaw().equals(block.getSizeRaw())
                : block.getSizeRaw() != null) {
            return false;
        }
        if (getGasLimitRaw() != null
                ? !getGasLimitRaw().equals(block.getGasLimitRaw())
                : block.getGasLimitRaw() != null) {
            return false;
        }
        if (getGasUsedRaw() != null
                ? !getGasUsedRaw().equals(block.getGasUsedRaw())
                : block.getGasUsedRaw() != null) {
            return false;
        }
        if (getTimestampRaw() != null
                ? !getTimestampRaw().equals(block.getTimestampRaw())
                : block.getTimestampRaw() != null) {
            return false;
        }
        if (getTransactions() != null
                ? !getTransactions().equals(block.getTransactions())
                : block.getTransactions() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = getNumberRaw() != null ? getNumberRaw().hashCode() : 0;
        result = 31 * result + (getHash() != null ? getHash().hashCode() : 0);
        result = 31 * result + (getParentHash() != null ? getParentHash().hashCode() : 0);
        result = 31 * result + (getLogsBloom() != null ? getLogsBloom().hashCode() : 0);
        result =
                31 * result
                        + (getTransactionsRoot() != null
                        ? getTransactionsRoot().hashCode()
                        : 0);
        result = 31 * result + (getStateRoot() != null ? getStateRoot().hashCode() : 0);
        result = 31 * result + (getReceiptsRoot() != null ? getReceiptsRoot().hashCode() : 0);
        result = 31 * result + (getExtraData() != null ? getExtraData().hashCode() : 0);
        result = 31 * result + (getSizeRaw() != null ? getSizeRaw().hashCode() : 0);
        result = 31 * result + (getGasLimitRaw() != null ? getGasLimitRaw().hashCode() : 0);
        result = 31 * result + (getGasUsedRaw() != null ? getGasUsedRaw().hashCode() : 0);
        result = 31 * result + (getTimestampRaw() != null ? getTimestampRaw().hashCode() : 0);
        result = 31 * result + (getTransactions() != null ? getTransactions().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FastBlock{" +
                "number='" + number + '\'' +
                ", hash='" + hash + '\'' +
                ", parentHash='" + parentHash + '\'' +
                ", committeeRoot='" + committeeRoot + '\'' +
                ", maker='" + maker + '\'' +
                ", logsBloom='" + logsBloom + '\'' +
                ", stateRoot='" + stateRoot + '\'' +
                ", snailNumber='" + snailNumber + '\'' +
                ", snailHash='" + snailHash + '\'' +
                ", extraData='" + extraData + '\'' +
                ", size='" + size + '\'' +
                ", gasLimit='" + gasLimit + '\'' +
                ", gasUsed='" + gasUsed + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", transactionsRoot='" + transactionsRoot + '\'' +
                ", receiptsRoot='" + receiptsRoot + '\'' +
                ", switchInfos=" + switchInfos +
                ", transactions=" + transactions +
                '}';
    }
}
