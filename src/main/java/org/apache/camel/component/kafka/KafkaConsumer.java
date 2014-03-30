/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package org.apache.camel.component.kafka;

import static org.apache.camel.component.kafka.KafkaComponentUtil.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import kafka.consumer.Consumer;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.apache.camel.AsyncProcessor;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.util.AsyncProcessorConverterHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Camel-Kafka {@link DefaultConsumer}.
 */
public class KafkaConsumer extends DefaultConsumer {

  /**
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

  /**
   * Camel-Kafka consumer processor
   */
  private final AsyncProcessor processor;

  /**
   * Kafka Consumer
   */
  private ConsumerConnector consumer;

  /**
   * Context executor service
   */
  private ExecutorService executor;

    /**
   * Camel-Kafka Configuration
   */
  private KafkaConfiguration configuration;

  /**
   * Camel-Kafka Endpoint reference
   */
  private KafkaEndpoint endpoint;

  /**
   * Default constructor.
   * 
   * @param endpoint
   * @param processor
   * @param configuration
   */
  public KafkaConsumer(final KafkaEndpoint endpoint,
                       final Processor processor,
                       final KafkaConfiguration configuration) {

    super(endpoint, processor);
    this.endpoint = endpoint;
    this.configuration = configuration;
    this.processor = AsyncProcessorConverterHelper.convert(processor);

    // validate configuration
    checkConsumerConfiguration(configuration);

  }

  @Override
  protected void doStart() throws Exception {

    super.doStart();

    if (LOGGER.isDebugEnabled()) {

      LOGGER.info("Starting Kafka Consumer");
    }

    this.executor = getExecutorService(endpoint, configuration);

    // Kafka Consumer initialisation
    consumer = Consumer.createJavaConsumerConnector(createConsumerConfig(configuration));

    final Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
    topicCountMap.put(configuration.getTopicName(), configuration.getConcurrentConsumers());

    final Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);

    // create message streams
    /*
    Kafka Streams
   */
      List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(configuration.getTopicName());

    // create different thread to process streams
    for (final KafkaStream stream : streams) {

      executor.submit(new KafkaConsumerTask(stream, endpoint, this, processor, configuration));
    }
  }

  @Override
  protected void doStop() throws Exception {

    super.doStop();
    if (LOGGER.isDebugEnabled()) {

      LOGGER.info("Stopping Kafka Consumer");
    }

    if (consumer != null) {

      consumer.shutdown();
    }

    if (executor != null) {

      if (endpoint != null && getEndpoint().getCamelContext() != null) {

        endpoint.getCamelContext().getExecutorServiceManager().shutdownNow(executor);
      } else {

        executor.shutdownNow();
      }
    }
  }
}
