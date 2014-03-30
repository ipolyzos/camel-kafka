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

/**
 * Camel-Kafka Constants
 */
public enum KafkaConstants {

    TOPIC_NAME("KafkaTopicName"),

    /*
        Default values from are based on kafka documentation.

        TODO: append description for each property
     */

    DEFAULT_CLIENT_ID("kafka.DEFAULT_CLIENT_ID"),
    DEFAULT_GROUP("kafka.DEFAULT_GROUP"),
    DEFAULT_TOPIC("kafka.DEFAULT_TOPIC"),
    DEFAULT_SERIALIZER_CLASS("kafka.serializer.DefaultEncoder"),
    DEFAULT_PARTITIONER_CLASS("kafka.producer.DefaultPartitioner"),
    DEFAULT_PRODUCER_TYPE("sync"),
    DEFAULT_BATCH_NUM_MESSAGES("200"),
    DEFAULT_QUEUE_ENQUEUE_TIMEOUT_MS("-1"),
    DEFAULT_QUEUE_BUFFERING_MAX_MESSAGES("10000"),
    DEFAULT_QUEUE_BUFFERING_MAX_MS("5000"),
    DEFAULT_SOCKET_TIMEOUT_MS("30000"),
    DEFAULT_SOCKET_RECEIVE_BUFFER_BYTES("65536"),
    DEFAULT_FETCH_MESSAGE_MAX_BYTES("1048576"),
    DEFAULT_AUTO_COMMIT_ENABLE("true"),
    DEFAULT_AUTO_COMMIT_INTERVAL_MS("60000"),
    DEFAULT_QUEUED_MAX_MESSAGE_CHUNKS("10"),
    DEFAULT_REBALANCE_MAX_RETRIES("4"),
    DEFAULT_FETCH_MIN_BYTES("1"),
    DEFAULT_FETCH_WAIT_MAX_MS("100"),
    DEFAULT_REBALANCE_BACKOFF_MS("2000"),
    DEFAULT_REFRESH_LEADER_BACKOFF_MS("200"),
    DEFAULT_AUTO_OFFSET_RESET("largest"),
    DEFAULT_CONSUMER_TIMEOUT_MS("-1"),
    DEFAULT_ZOOKEEPER_SESSION_TIMEOUT_MS("6000"),
    DEFAULT_CONNECTION_TIMEOUT_MS("60000"),
    DEFAULT_ZOOKEEPER_SYNC_TIME_MS("2000"),
    DEFAULT_REQUEST_REQUIRED_ACKS("0"),
    DEFAULT_REQUEST_TIMEOUT_MS("10000"),
    DEFAULT_COMPRESSION_CODEC("none"),
    DEFAULT_MESSAGE_SEND_MAX_RETRIES("3"),
    DEFAULT_RETRY_BACKOFF_MS("100"),
    DEFAULT_TOPIC_METADATA_REFRESH_INTERVAL_MS("600000"),
    DEFAULT_SEND_BUFFER_BYTES("102400"),
    // assume that by default key will be of type string
    DEFAULT_KEY_SERIALIZER_CLASS("kafka.serializer.StringEncoder"),

    PARTITION_KEY("kafka.PARTITION_KEY"),
    PARTITION("kafka.EXCHANGE_NAME"),
    OFFSET("kafka.OFFSET"),
    KEY("kafka.CONTENT_TYPE"),
    TOPIC("kafka.TOPIC");

    /** enum value*/
    public final String value;

    /**
     * Enum Constructor
     *
     * @param str
     */
    private KafkaConstants(String str) {
        value = str;
    }
}
