/*
 * Copyright 2019 Web3 Labs LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.taiweb3j.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.taiweb3j.response.fast.FastBlock;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.core.Response;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class EtrueFastBlock extends Response<FastBlock> {

    @Override
    @JsonDeserialize(using = EtrueFastBlock.ResponseDeserialiser.class)
    public void setResult(FastBlock result) {
        super.setResult(result);
    }

    public FastBlock getFastBlock() {
        return getResult();
    }

    public static class ResponseDeserialiser extends JsonDeserializer<FastBlock> {

        private ObjectReader objectReader = ObjectMapperFactory.getObjectReader();

        @Override
        public FastBlock deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (jsonParser.getCurrentToken() != JsonToken.VALUE_NULL) {
                return objectReader.readValue(jsonParser, FastBlock.class);
            } else {
                return null; // null is wrapped by Optional in above getter
            }
        }
    }


}
