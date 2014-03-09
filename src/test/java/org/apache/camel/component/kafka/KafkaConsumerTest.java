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

package org.apache.camel.component.kafka;

import static org.mockito.Mockito.*;

import org.I0Itec.zkclient.exception.ZkException;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Camel-Kafka Producer Tests
 */
public class KafkaConsumerTest extends KafkaTestSupport {

    /**
     * Mock CamelExchange
     */
    private Exchange mockCamelExchange;

    /**
     * Mock Camel-Kafka Endpoint
     */
    private KafkaEndpoint mockKafkaEndpoint;

    /**
     * Mock Camel-Kafka Configuration
     */
    private KafkaConfiguration mockKafkaConfiguration;

    /**
     * Mock Camel  Message
     */
    private Message mockCamelMessage;

    /**
     * Mock Processor
     */
    private Processor mockKafkaProcessor;

    @Before
    public void setUp()  {

        //setup mocks
        mockCamelExchange = mock(Exchange.class);
        mockKafkaEndpoint = mock(KafkaEndpoint.class);
        mockKafkaConfiguration = spy(new KafkaConfiguration());
        mockCamelMessage = mock(Message.class);
        mockKafkaProcessor =  mock(Processor.class);

        //setup default conditions
        when(mockCamelExchange.getIn()).thenReturn(mockCamelMessage);
        when(mockCamelExchange.getPattern()).thenReturn(ExchangePattern.InOnly);
    }

    public void creatingAnInstanceShouldNotThrowExceptionIfConfigurationIsMissingGroupId() throws Exception {

        mockKafkaConfiguration.setZookeeperConnect("samplehost");

        new KafkaConsumer(mockKafkaEndpoint,mockKafkaProcessor,mockKafkaConfiguration);

        verify(mockKafkaConfiguration, atMost(1)).getGroupId();
        verify(mockKafkaConfiguration, atMost(1)).getZkConnect();
        verify(mockKafkaConfiguration, atMost(1)).getZookeeperConnect();
    }

    public void creatingAnInstanceShouldNotThrowExceptionIfConfigurationIsMissingZKConnect() throws Exception {

        mockKafkaConfiguration.setGroupId(KafkaConstants.DEFAULT_GROUP.value);

        new KafkaConsumer(mockKafkaEndpoint,mockKafkaProcessor,mockKafkaConfiguration);

        verify(mockKafkaConfiguration, atMost(1)).getGroupId();
        verify(mockKafkaConfiguration, atMost(1)).getZkConnect();
        verify(mockKafkaConfiguration, atMost(1)).getZookeeperConnect();
    }

    public void creatingAnInstanceShouldNotFailIfFakeHostIsProvidedInMandatoryConfiguration() throws Exception {

        mockKafkaConfiguration.setZookeeperConnect("sampleHost");
        mockKafkaConfiguration.setGroupId(KafkaConstants.DEFAULT_GROUP.value);

        new KafkaConsumer(mockKafkaEndpoint,mockKafkaProcessor,mockKafkaConfiguration);

        verify(mockKafkaConfiguration, atMost(1)).getGroupId();
        verify(mockKafkaConfiguration, atMost(1)).getZkConnect();
        verify(mockKafkaConfiguration, atMost(1)).getZookeeperConnect();
    }

    @Test
    @Ignore("This test requires an instance of zookeeper running localy")
    public void creatingAnInstanceShouldSuccessedIfMandatoryConfigurationIsProvided() throws Exception {

        mockKafkaConfiguration.setZookeeperConnect("localhost:2181");
        mockKafkaConfiguration.setGroupId(KafkaConstants.DEFAULT_GROUP.value);

        new KafkaConsumer(mockKafkaEndpoint,mockKafkaProcessor,mockKafkaConfiguration);

        verify(mockKafkaConfiguration, atMost(1)).getGroupId();
        verify(mockKafkaConfiguration, atMost(1)).getZkConnect();
        verify(mockKafkaConfiguration, atMost(1)).getZookeeperConnect();
    }
}
