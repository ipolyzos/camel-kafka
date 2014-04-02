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
package org.apache.camel.component.kafka.itests;

import java.io.IOException;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConfiguration;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.apache.commons.lang.SerializationUtils.serialize;

/**
 * The Producer IT tests require a Kafka broker running on 9092 and a zookeeper instance running on 2181.
 * The broker must have a topic called test created.
 */
@Ignore("to run manually!")
public class KafkaConsumerIT extends CamelTestSupport {

    public static final String TOPIC = "test";

    @EndpointInject(uri = "mock:result")
    private MockEndpoint to;

    private Producer<String, byte[]> producer;

    @Before
    public void before() {

        KafkaConfiguration conf = new KafkaConfiguration();

        conf.setZkConnect("localhost:2181");
        conf.setMetadataBrokerList("localhost:9092");

        ProducerConfig config = new ProducerConfig(conf.getProperties());
        producer = new Producer<String, byte[]>(config);
    }

    @After
    public void after() {
        producer.close();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {

                from("kafka:"+TOPIC+"?zkConnect=localhost:2181&metadataBrokerList=localhost:9092&groupId=1")
                        .to("mock:result");
            }
        };
    }

    /**
     * NOTE:
     *  it can use only Default Encoder for now and therefore needs
     *  the data to be of type byte[]
     */
    @Test
    public void kaftMessageIsConsumedByCamel() throws InterruptedException, IOException {

        to.expectedMessageCount(5);
        to.expectedBodiesReceived("message-0", "message-1", "message-2", "message-3", "message-4");

        for (int k = 0; k < 5; k++) {

            String msg = "message-" + k;
            KeyedMessage<String, byte[]> data = new KeyedMessage<String, byte[]>(TOPIC, "1", serialize(msg));
            producer.send(data);
        }

        to.assertIsSatisfied(3000);
    }
}

