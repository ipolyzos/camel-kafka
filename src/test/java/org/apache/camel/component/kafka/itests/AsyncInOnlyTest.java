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

import java.util.Random;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.component.kafka.KafkaTestSupport;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Camel-Kafka Basic InOnly Integration test
 */
@Ignore("to run manually!")
public class AsyncInOnlyTest extends KafkaTestSupport {

    @EndpointInject(uri = "mock:result")
    private MockEndpoint mock;

    final long uid = new Random().nextLong();

    @Test
    public void asyncInOnlyTest() throws Exception {

        final String TEST_PAYLOAD       = "Test Payload InOnly!";

        mock.expectedMessageCount(1);
        mock.expectedBodiesReceived(TEST_PAYLOAD);

        template.send("direct:kaiotep", ExchangePattern.InOnly, new Processor() {
            public void process(Exchange exchange) throws Exception {

                exchange.getIn().setBody(TEST_PAYLOAD);
            }
        });

        assertMockEndpointsSatisfied();
        mock.reset();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {

        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                from("direct:kaiotep").to("kafka:kaiot?zkConnect=localhost:2181&metadataBrokerList=localhost:9092&producerType=async&groupId="+ uid + KafkaConstants.DEFAULT_GROUP.value);
                from("kafka:kaiot?zkConnect=localhost:2181&groupId="+ uid +KafkaConstants.DEFAULT_GROUP.value).to("mock:result");
            }
        };
    }
}
