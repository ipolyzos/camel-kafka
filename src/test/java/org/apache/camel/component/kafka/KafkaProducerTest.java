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

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.junit.Before;
import org.junit.Test;

/**
 * Camel-Kafka Producer Tests
 */
public class KafkaProducerTest extends KafkaTestSupport {

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
     * Mock Camel Message
     */
    private Message mockCamelMessage;

    @Before
    public void setUp()  {

        //setup mocks
        mockCamelExchange = mock(Exchange.class);
        mockKafkaEndpoint = mock(KafkaEndpoint.class);
        mockKafkaConfiguration = spy(new KafkaConfiguration());
        mockCamelMessage = mock(Message.class);

        //setup default conditions
        when(mockCamelExchange.getIn()).thenReturn(mockCamelMessage);
        when(mockCamelExchange.getPattern()).thenReturn(ExchangePattern.InOnly);
    }

    @Test
    public void creatingAnInstanceShouldSuccessedIfConfigurationIsNotMissing() throws Exception {

        mockKafkaConfiguration.setMetadataBrokerList("sampleBroker");

        new KafkaProducer(mockKafkaEndpoint,mockKafkaConfiguration);

        verify(mockKafkaConfiguration, atMost(1)).getMetadataBrokerList();
        verify(mockKafkaConfiguration, atMost(1)).getSerializerClass();
        verify(mockKafkaConfiguration, atMost(1)).getPartitionerClass();
        verify(mockKafkaConfiguration, atMost(1)).getRequestRequiredAcks();
    }
}
