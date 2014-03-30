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

import static com.google.common.base.Strings.isNullOrEmpty;

import kafka.consumer.ConsumerConfig;

import kafka.message.MessageAndMetadata;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultExchangeHolder;
import org.apache.camel.impl.DefaultMessage;
import org.apache.commons.lang.SerializationUtils;

import java.util.concurrent.ExecutorService;

/**
 * Camel-Kafka Component Util
 */
public final class KafkaComponentUtil {

    /**
     * Private Constructor
     */
    private KafkaComponentUtil() {

        //prevent instantiation
    }

    /**
     * Util method to validate configuration.
     *
     * @param configuration
     *          Camel Kafka Consumer Configuration
     */
    protected static void checkConsumerConfiguration(final KafkaConfiguration configuration) {

        if (isNullOrEmpty(configuration.getGroupId())) {

            throw new IllegalArgumentException("\"group.id\" is null or empty!");
        } else if (isNullOrEmpty(configuration.getZookeeperConnect())) {

            throw new IllegalArgumentException("\"zookeeper.connect\" is null or empty!");
        }
    }

    /**
     * Utility method to validate configuration.
     *
     * @param configuration
     *             Camel Kafka Configuration
     */
    protected static void checkProducerConfiguration(final KafkaConfiguration configuration) {

        if (isNullOrEmpty(configuration.getMetadataBrokerList())) {

            throw new IllegalArgumentException("\"metadata.broker.list\" is null or empty!");
        } else if (isNullOrEmpty(configuration.getSerializerClass())) {

            throw new IllegalArgumentException("\"serializer.class\" is null or empty!");
        }
    }

    /**
     * Util method to create a Consumer Config
     *
     * @param configuration Kafka Configuration
     * @return Consumer Config
     */
    protected static ConsumerConfig createConsumerConfig(final KafkaConfiguration configuration) {

        return new ConsumerConfig(configuration.getProperties());
    }

    /**
     * Utility method to serialize the whole exchange.
     *
     * @param exchange Exchange
     * @return  byte array
     */
    public static byte[] serializeExchange(final Exchange exchange) {

        return SerializationUtils.serialize(DefaultExchangeHolder.marshal(exchange));
    }

    /**
     * Utility method to serialize the whole exchange.
     *
     * @param exchange
     * @return
     */
    public static byte[] serializeBody(final Exchange exchange) {

        return SerializationUtils.serialize(exchange.getIn().getBody(byte[].class));
    }

    /**
     * Utility method to serialize the whole exchange.
     *
     * @param body
     * @return
     */
    public static Object deserializeBody(final byte[] body) {

        return SerializationUtils.deserialize(body);
    }

    /**
     * Get get executor service from camel context
     *
     * @param endpoint
     * @param configuration
     * @return
     */
    protected static ExecutorService getExecutorService(final KafkaEndpoint endpoint,
                                                        final KafkaConfiguration configuration) {

        return endpoint.getCamelContext()
                .getExecutorServiceManager()
                .newFixedThreadPool(endpoint, "KafkaTopic[" + configuration.getTopicName() + "]", configuration.getConcurrentConsumers());
    }

    /**
     * Utility method to create exchange from incoming data
     *
     * @param incomingData
     * @param configuration
     */
    protected static Exchange constructExchange(final KafkaEndpoint endpoint,
                                                final MessageAndMetadata<byte[], byte[]> incomingData,
                                                final KafkaConfiguration configuration) {

        Exchange exchange = new DefaultExchange(endpoint.getCamelContext(), endpoint.getExchangePattern());

        if (configuration.isTransferExchange()) { // transfer exchange?

            DefaultExchangeHolder exchangeHolder = (DefaultExchangeHolder) SerializationUtils.deserialize(incomingData.message());
            DefaultExchangeHolder.unmarshal(exchange, exchangeHolder);
        }else{

            final Message message = new DefaultMessage();
            message.setBody(deserializeBody(incomingData.message()));
            exchange.setIn(message);
        }

        fillExchangeInMessageWithMetadata(exchange, incomingData);

        return exchange;

    }

    /**
     * Utility method to fill in message with metadata
     *
     * @param exchange
     * @param incomingData
     */
    private static void fillExchangeInMessageWithMetadata(final Exchange exchange,
                                                          final MessageAndMetadata<byte[], byte[]> incomingData) {
        final Message message = exchange.getIn();

        message.setHeader(KafkaConstants.PARTITION.value, incomingData.partition());
        message.setHeader(KafkaConstants.TOPIC.value, incomingData.topic());
        message.setHeader(KafkaConstants.OFFSET.value, incomingData.offset());
        message.setHeader(KafkaConstants.KEY.value, incomingData.key());
    }
}
