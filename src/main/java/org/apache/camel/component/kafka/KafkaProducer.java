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

import static org.apache.camel.component.kafka.KafkaComponentUtil.checkProducerConfiguration;
import static org.apache.camel.component.kafka.KafkaComponentUtil.serializeBody;
import static org.apache.camel.component.kafka.KafkaComponentUtil.serializeExchange;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.camel.AsyncCallback;
import org.apache.camel.AsyncProcessor;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Camel-Kafka {@link DefaultProducer}
 */
public class KafkaProducer extends DefaultProducer implements AsyncProcessor {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    /**
     * Kafka Producer
     */
    private final kafka.javaapi.producer.Producer<String, byte[]> producer;

    /**
     * Camel-Kafka Configuration
     */
    private KafkaConfiguration configuration;

    /**
     * Default constructor to be used.
     *
     * @param endpoint
     */
    public KafkaProducer(final Endpoint endpoint,
                         final KafkaConfiguration configuration) {
        super(endpoint);

        checkProducerConfiguration(configuration);
        this.configuration = configuration;

        /* Create Kafka Producer */
        final ProducerConfig config = new ProducerConfig(configuration.getProperties());
        producer = new Producer<String, byte[]>(config);
    }

    @Override
    public final void process(Exchange exchange) throws Exception {

        checkAndSend(exchange);
    }

    @Override
    public final boolean process(final Exchange exchange,
                           final AsyncCallback callback) {

        checkAndSend(exchange);
        callback.done(true);
        return true;
    }

    /**
     * Utility method to prepare and send data
     *
     * @param exchange
     */
    private void checkAndSend(final Exchange exchange) {

        String partitionKey;
        if (exchange.getIn().getHeaders().containsKey(KafkaConstants.PARTITION_KEY.value)) {

            partitionKey = exchange.getIn().getHeader(KafkaConstants.PARTITION_KEY.value, String.class);
        } else {
            LOGGER.info("No partition key set, using the default");

            partitionKey     = configuration.getPartitionKey();
        }

        String topicName;
        if (exchange.getIn().getHeaders().containsKey(KafkaConstants.TOPIC_NAME.value)) {

            topicName = exchange.getIn().getHeader(KafkaConstants.TOPIC_NAME.value, String.class);
        } else {

            topicName = configuration.getTopicName();
        }

        final KeyedMessage<String, byte[]> message;

        if (configuration.isTransferExchange()){ //should transfer exchange?

            message = new KeyedMessage<String,  byte[]>(topicName, partitionKey, serializeExchange(exchange));
        } else{

            message = new KeyedMessage<String,  byte[]>(topicName, partitionKey, serializeBody(exchange));
        }

        producer.send(message);

        if (LOGGER.isDebugEnabled()) {

            LOGGER.debug("Kafka Producer send : " + exchange);
        }
    }
}
