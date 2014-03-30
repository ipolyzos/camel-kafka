/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.camel.component.kafka.load;

import org.apache.camel.Main;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.dataset.DataSet;
import org.apache.camel.component.dataset.SimpleDataSet;
import org.apache.camel.component.kafka.KafkaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Random;

/**
 * Simple test for approximate load measurements through the throughput logger
 * please note that in this test the whole exchange is send
 *
 * NOTE: need to review for correctness
 */
public class SimpleLoadTest extends Main {

    static Logger LOGGER = LoggerFactory.getLogger(SimpleLoadTest.class);

    final long uid = new Random().nextLong();

    /**
     * Test the Camel-Kafka, set topic by headers and sync producer
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        SimpleLoadTest main = new SimpleLoadTest();

        main.enableHangupSupport();
        main.addRouteBuilder(createRouteBuilder());
        main.bind("sampleGenerator", createDataSet());

        main.run(args);
    }

    /**
     * Create route
     *
     * @return
     */
    static RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {

                from("dataset://sampleGenerator?produceDelay=0")
                        .setHeader(KafkaConstants.PARTITION_KEY.value,constant("LoadTest"))
                        .to("kafka:fooiout?zkConnect=localhost:2181&partitionKey=1&metadataBrokerList=localhost:9092&transferExchange=true&groupId="+KafkaConstants.DEFAULT_GROUP.value);

                from("kafka:fooiout?zkConnect=localhost:2181&partitionKey=1&transferExchange=true&groupId=" + KafkaConstants.DEFAULT_GROUP.value)
                        .to("log://demo?groupSize=1000&level=INFO");
            }
        };
    }

    /**
     * Sample data-set
     */
    static DataSet createDataSet() {

        final SimpleDataSet result = new SimpleDataSet();
        result.setSize(10000);
        result.setDefaultBody(createSamplePayload(2000000));

        return result;
    }

    /**
     * Utility method to create a string of specific size (aprox)
     *
     * @param size  Number of characters
     * @return
     */
    private static String createSamplePayload(final int size) {

        char[] chars = new char[size];
        Arrays.fill(chars, 'A');

       return new String(chars);
    }
}
