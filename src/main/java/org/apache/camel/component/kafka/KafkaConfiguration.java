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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.apache.camel.component.kafka.KafkaConstants.*;

import java.util.Properties;
/**
 * Camel Kafka Configuration.
 *
 * <b>NOTE:</b> It contains configuration for both consumers and
 * producers. Default configuration apply upon construction and
 * empty or null values ignored after this.
 */
public class KafkaConfiguration {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfiguration.class);

    /**
     * Topic Name
     */
    private String topicName;

    /**
     * Transfer Exchange
     */
    private boolean transferExchange = false;

    /**
     * Concurrent consumers (i.e streams in this context)
     */
    private int concurrentConsumers = 10;

    /**
     * Partition Key
     */
    private String partitionKey = "DEFAULT_PARTITION";

    /**
     * Kafka Configuration
     */
    private Properties props;

    /**
     * Default constructor
     *
     * NOTE: constructor also initialize configuration with
     * default values as given by kafka documentation
     */
    public KafkaConfiguration() {

        // initialize properties
        props = new Properties();

        /* NOTE: set defaults values according to
         * kafka documentation
         */
        setGroupId(DEFAULT_GROUP.value);
        setTopicName(DEFAULT_TOPIC.value);
        setSocketTimeoutMs(DEFAULT_SOCKET_TIMEOUT_MS.value);
        setSocketReceiveBufferBytes(DEFAULT_SOCKET_RECEIVE_BUFFER_BYTES.value);
        setFetchMessageMaxBytes(DEFAULT_FETCH_MESSAGE_MAX_BYTES.value);
        setAutoCommitEnable(DEFAULT_AUTO_COMMIT_ENABLE.value);
        setAutoCommitIntervalMs(DEFAULT_AUTO_COMMIT_INTERVAL_MS.value);
        setQueuedMaxMessageChunks(DEFAULT_QUEUED_MAX_MESSAGE_CHUNKS.value);
        setRebalanceMaxRetries(DEFAULT_REBALANCE_MAX_RETRIES.value);
        setFetchMinBytes(DEFAULT_FETCH_MIN_BYTES.value);
        setFetchWaitMaxMs(DEFAULT_FETCH_WAIT_MAX_MS.value);
        setRebalanceBackoffMs(DEFAULT_REBALANCE_BACKOFF_MS.value);
        setRefreshLeaderBackoffMs(DEFAULT_REFRESH_LEADER_BACKOFF_MS.value);
        setAutoOffsetReset(DEFAULT_AUTO_OFFSET_RESET.value);
        setConsumerTimeoutMs(DEFAULT_CONSUMER_TIMEOUT_MS.value);
        setZookeeperSessionTimeoutMs(DEFAULT_ZOOKEEPER_SESSION_TIMEOUT_MS.value);
        setZookeeperConnectionTimeoutMs(DEFAULT_CONNECTION_TIMEOUT_MS.value);
        setZookeeperSyncTimeMs(DEFAULT_ZOOKEEPER_SYNC_TIME_MS.value);
        setRequestRequiredAcks(DEFAULT_REQUEST_REQUIRED_ACKS.value);
        setRequestTimeoutMs(DEFAULT_REQUEST_TIMEOUT_MS.value);
        setProducerType(DEFAULT_PRODUCER_TYPE.value);
        setSerializerClass(DEFAULT_SERIALIZER_CLASS.value);
        setPartitionerClass(DEFAULT_PARTITIONER_CLASS.value);
        setCompressionCodec(DEFAULT_COMPRESSION_CODEC.value);
        setCompressedTopics(null);
        setMessageSendMaxRetries(DEFAULT_MESSAGE_SEND_MAX_RETRIES.value);
        setRetryBackoffMs(DEFAULT_RETRY_BACKOFF_MS.value);
        setTopicMetadataRefreshIntervalMs(DEFAULT_TOPIC_METADATA_REFRESH_INTERVAL_MS.value);
        setQueueBufferingMaxMs(DEFAULT_QUEUE_BUFFERING_MAX_MS.value);
        setQueueBufferingMaxMessages(DEFAULT_QUEUE_BUFFERING_MAX_MESSAGES.value);
        setQueueEnqueueTimeoutMs(DEFAULT_QUEUE_ENQUEUE_TIMEOUT_MS.value);
        setBatchNumMessages(DEFAULT_BATCH_NUM_MESSAGES.value);
        setSendBufferBytes(DEFAULT_SEND_BUFFER_BYTES.value);
        setClientId(DEFAULT_CLIENT_ID.value);
        setKeySerializerClass(DEFAULT_KEY_SERIALIZER_CLASS.value);
    }

    /**
     * Get Kafka Consumers/Producers Configuration
     *
     * @return kafkaConfiguration
     */
    public Properties getKafkaConfiguration() {

        return props;
    }

    /**
     * Set Kafka Consumers/Producers Configuration.
     *
     * @param kafkaConfiguration
     */
    public void setKafkaConfiguration(Properties kafkaConfiguration) {

        this.props = kafkaConfiguration;
    }

    /**
     * Get topic name
     *
     * @return topicName
     */
    public String getTopicName() {

        return topicName;
    }

    /**
     * Set topic name
     *
     * @param topicName
     */
    public void setTopicName(String topicName) {

        this.topicName = topicName;
    }

    /**
     * Get transfer exchange
     *
     * @return
     */
    public boolean isTransferExchange() {

        return transferExchange;
    }

    /**
     * Set transfer exchange
     *
     * @param transferExchange
     */
    public void setTransferExchange(boolean transferExchange) {

        this.transferExchange = transferExchange;
    }

    /**
     * Get concurrent consumers
     *
     * @return
     */
    public int getConcurrentConsumers() {
        return concurrentConsumers;
    }

    /**
     * Get concurrent consumers
     *
     * @param concurrentConsumers
     */
    public void setConcurrentConsumers(int concurrentConsumers) {
        this.concurrentConsumers = concurrentConsumers;
    }


    /**
     * Get Partition key
     *
     * @return
     */
    public String getPartitionKey() {
        return partitionKey;
    }

    /**
     * Set partition key
     *
     * @param partitionKey
     */
    public void setPartitionKey(final String partitionKey) {
        this.partitionKey = partitionKey;
    }

    /**
     * Get Group ID.
     *
     * @return groupId
     */
    public String getGroupId() {

        return props.getProperty("group.id");
    }

    /**
     * Set Group ID.
     *
     * @param groupId
     */
    public void setGroupId(final String groupId) {

        if (!isNullOrEmpty(groupId))  {

            props.put("group.id", groupId);
        }
    }

    /**
     * Get Zookeeper Connection String.
     *
     * @return zookeeperConnect
     */
    public String getZookeeperConnect() {

        return props.getProperty("zookeeper.connect");
    }

    /**
     * Set Zookeeper Connection String.
     *
     * @param zookeeperConnect
     */
    public void setZookeeperConnect(final String zookeeperConnect) {

        if (!isNullOrEmpty(zookeeperConnect))  {

            props.put("zookeeper.connect", zookeeperConnect);
        }
    }

    /**
     * Get Zookeeper Connection String.
     *
     * <b>NOTE:</b> Alias of getZookeeperConnect().
     *
     * @return zookeeperConnect
     */
    public String getZkConnect() {

        return getZookeeperConnect();
    }

    /**
     * Set Zookeeper Connection String.
     *
     * <b>NOTE:</b> Alias of setZookeeperConnect().
     *
     * @param zkConnect
     */
    public void setZkConnect(final String zkConnect) {

        this.setZookeeperConnect(zkConnect);
    }

    /**
     * Get Consumer Id
     *
     * <b>NOTE:</b> the default value is : <b>null</b>.
     *
     * @return consumerId
     */
    public String getConsumerId() {

        return props.getProperty("consumer.id");
    }

    /**
     * Set Consumer ID
     *
     * <b>NOTE:</b> Generated automatically if not set.
     *
     * @param consumerId
     */
    public void setConsumerId(final String consumerId) {

        if (!isNullOrEmpty(consumerId)){

            props.setProperty("consumer.id", consumerId);
        }
    }

    /**
     * Get Socket Timeout for network requests.
     *
     * <b>NOTE:</b> the default value is : <b>30 * 1000</b>
     *
     * @return socketTimeoutMs
     */
    public String getSocketTimeoutMs() {

        return props.getProperty("socket.timeout.ms");
    }

    /**
     * Set Socket Timeout for network requests
     *
     * <b>NOTE:</b> The actual timeout set will be max.fetch.wait + socket.timeout.ms.
     *
     * @param socketTimeoutMs
     */
    public void setSocketTimeoutMs(final String socketTimeoutMs) {

        if (!isNullOrEmpty(socketTimeoutMs)){

            props.setProperty("socket.timeout.ms", socketTimeoutMs);
        }
    }

    /**
     * Get the socket receive buffer for network requests.
     *
     * <b>NOTE:</b> the default value is : <b>64 * 1024</b>
     *
     * @return socketReceiveBufferBytes
     */
    public String getSocketReceiveBufferBytes() {

        return props.getProperty("socket.timeout.ms");
    }

    /**
     * Set the socket receive buffer for network requests
     *
     * @param socketReceiveBufferBytes
     */
    public void setSocketReceiveBufferBytes(final String socketReceiveBufferBytes) {

        if (!isNullOrEmpty(socketReceiveBufferBytes)){

            props.setProperty("socket.receive.buffer.bytes", socketReceiveBufferBytes);
        }
    }

    /**
     * Get the number of byes of messages to attempt to fetch for each topic-partition in each fetch request.
     *
     * <b>NOTE:</b> the default value is : <b>1024 * 1024</b>
     *
     * @return fetchMessageMaxBytes
     */
    public String getFetchMessageMaxBytes() {

        return props.getProperty("fetch.message.max.bytes");
    }

    /**
     * The number of byes of messages to attempt to fetch for each topic-partition in each fetch request
     *
     * @param fetchMessageMaxBytes
     */
    public void setFetchMessageMaxBytes(final String fetchMessageMaxBytes) {

        if (!isNullOrEmpty(fetchMessageMaxBytes)){

            props.setProperty("fetch.message.max.bytes",fetchMessageMaxBytes);
        }
    }

    /**
     * Get auto commit status.
     *
     * <b>NOTE:</b> the default value is :<b>true</b>
     *
     * @return autoCommitEnabled
     */
    public String getAutoCommitEnable() {

        return props.getProperty("auto.commit.enable");
    }

    /**
     * Enable/disable auto commit.
     *
     * <b>NOTE:</b> If true, periodically commit to zookeeper the offset of messages already fetched by the consumer
     * <i>(the default value is true)</i>.
     *
     * @param autoCommitEnable
     */
    public void setAutoCommitEnable(final String autoCommitEnable) {

        if (!isNullOrEmpty(autoCommitEnable)){

            props.setProperty("auto.commit.enable",autoCommitEnable);
        }
    }

    /**
     * Get the frequency in ms that the consumer offsets are committed to zookeeper.
     *
     * <b>NOTE:</b> the default value is :<b>60 * 1000</b>
     *
     * @return autoCommitEnable
     */
    public String getAutoCommitIntervalMs() {

        return props.getProperty("auto.commit.interval.ms");
    }

    /**
     * Set the frequency in ms that the consumer offsets are committed to zookeeper.
     *
     * @param autoCommitIntervalMs
     */
    public void setAutoCommitIntervalMs(final String autoCommitIntervalMs) {

        if (!isNullOrEmpty(autoCommitIntervalMs)){

            props.setProperty("auto.commit.interval.ms",autoCommitIntervalMs);
        }
    }

    /**
     * Get the max number of message chunks buffered for consumption.
     *
     * <b>NOTE:</b> the default value is :<b>10</b>.
     *
     * @return queuedMaxMessageChunks
     */
    public String getQueuedMaxMessageChunks() {

        return props.getProperty("queued.max.message.chunks");
    }

    /**
     * Set the max number of message chunks buffered for consumption.
     *
     * @param queuedMaxMessageChunks
     */
    public void setQueuedMaxMessageChunks(final String queuedMaxMessageChunks) {

        if (!isNullOrEmpty(queuedMaxMessageChunks)){

            props.setProperty("queued.max.message.chunks",queuedMaxMessageChunks);
        }
    }

    /**
     * Get the maximum number of attempts to re-balance before giving up.
     *
     * <b>NOTE:</b> the default value is :<b>4</b>.
     *
     * @return rebalanceMaxRetries
     */
    public String getRebalanceMaxRetries() {

        return props.getProperty("rebalance.max.retries");
    }

    /**
     * Set the maximum number of attempts to re-balance before giving up.
     *
     * @param rebalanceMaxRetries
     */
    public void setRebalanceMaxRetries(final String rebalanceMaxRetries) {

        if (!isNullOrEmpty(rebalanceMaxRetries)){

            props.setProperty("rebalance.max.retries",rebalanceMaxRetries);
        }
    }

    /**
     * Set the minimum amount of data the server should return for a fetch request.
     *
     *  <b>NOTE:</b> the default value is :<b>1</b>.
     *
     * @return fetchMinBytes
     */
    public String getFetchMinBytes() {

        return props.getProperty("fetch.min.bytes");
    }

    /**
     * Set the minimum amount of data the server should return for a fetch request.
     *
     * @param fetchMinBytes
     */
    public void setFetchMinBytes(final String fetchMinBytes) {

        if (!isNullOrEmpty(fetchMinBytes)){

            props.setProperty("fetch.min.bytes",fetchMinBytes);
        }
    }

    /**
     * Get the maximum amount of time the server will block before answering the fetch request.
     *
     * <b>NOTE:</b>If there isn't sufficient data to immediately satisfy fetch.min.bytes.
     * The default value is :<b>100</b>.
     *
     * @return fetchWaitMaxMs
     */
    public String getFetchWaitMaxMs() {

        return props.getProperty("fetch.wait.max.ms");
    }

    /**
     * Set the maximum amount of time the server will block before answering the fetch request.
     *
     * @param fetchWaitMaxMs
     */
    public void setFetchWaitMaxMs(final String fetchWaitMaxMs) {

        if (!isNullOrEmpty(fetchWaitMaxMs)){

            props.setProperty("fetch.wait.max.ms",fetchWaitMaxMs);
        }
    }

    /**
     * Get backoff time between retries during rebalance.
     *
     * <b>NOTE:</b> the default value is :<b>2000</b>.
     *
     * @return rebalanceBackoffMs
     */
    public String getRebalanceBackoffMs() {

        return props.getProperty("rebalance.backoff.ms");
    }

    /**
     * Set backoff time between retries during rebalance.
     *
     * @param rebalanceBackoffMs
     */
    public void setRebalanceBackoffMs(final String rebalanceBackoffMs) {

        if (!isNullOrEmpty(rebalanceBackoffMs)){

            props.setProperty("rebalance.backoff.ms",rebalanceBackoffMs);
        }
    }

    /**
     * Get backoff time to wait before trying to determine the leader of a partition that has just lost its leader.
     *
     * <b>NOTE:</b> the default value is :<b>200</b>.
     *
     * @return
     */
    public String getRefreshLeaderBackoffMs() {

        return props.getProperty("refresh.leader.backoff.ms");
    }

    /**
     * Set backoff time to wait before trying to determine the leader of a partition that has just lost its leader.
     *
     * @param refreshLeaderBackoffMs
     */
    public void setRefreshLeaderBackoffMs(final String refreshLeaderBackoffMs) {

        if (!isNullOrEmpty(refreshLeaderBackoffMs)){

            props.setProperty("refresh.leader.backoff.ms",refreshLeaderBackoffMs);
        }
    }

    /**
     * Configure the auto offset reset.
     *
     * <b>NOTE:</b> the default value is :<b>largest</b>.
     *
     * @return
     */
    public String getAutoOffsetReset() {

        return props.getProperty("auto.offset.reset");
    }

    /**
     * Get the auto offset reset configuration.
     *
     * @param autoOffsetReset
     */
    public void setAutoOffsetReset(final String autoOffsetReset) {

        if (!isNullOrEmpty(autoOffsetReset)){

            props.setProperty("auto.offset.reset",autoOffsetReset);
        }
    }

    /**
     * Set the consumer timeout in ms.
     *
     * <b>NOTE:</b> Throw a timeout exception to the consumer if no message is available for consumption after the specified interval. The default value is :<b>-1</b>.
     *
     * @return
     */
    public String getConsumerTimeoutMs() {

        return props.getProperty("consumer.timeout.ms");
    }

    /**
     *  Set the consumer timeout in ms.
     *
     * @param consumerTimeoutMs
     */
    public void setConsumerTimeoutMs(final String consumerTimeoutMs) {

        if (!isNullOrEmpty(consumerTimeoutMs)){

            props.setProperty("consumer.timeout.ms",consumerTimeoutMs);
        }
    }

    /**
     * Get client id
     *
     * <b>Note:</b>The client id is a user-specified string sent in each request to help trace calls.  The default value is :<b>group id value</b>.
     *
     * @return clientId
     */
    public String getClientId() {

        return props.getProperty("client.id");
    }

    /**
     * Set client id
     *
     * @param clientId
     */
    public void setClientId(final String clientId) {

        if (!isNullOrEmpty(clientId)){

            props.setProperty("client.id",clientId);
        }
    }

    /**
     * Get Zookeeper session timeout.
     *
     * <b>NOTE:</b>If the consumer fails to heartbeat to zookeeper for this period of time it is considered dead and a rebalance will occur.
     *
     * @return zookeeperSessionTimeoutMs
     */
    public String getZookeeperSessionTimeoutMs() {

       return props.getProperty("zookeeper.session.timeout.ms");
    }

    /**
     * Set Zookeeper session timeout.
     *
     * @param zookeeperSessionTimeoutMs
     */
    public void setZookeeperSessionTimeoutMs(final String zookeeperSessionTimeoutMs) {

        if (!isNullOrEmpty(zookeeperSessionTimeoutMs)){

            props.setProperty("zookeeper.session.timeout.ms",zookeeperSessionTimeoutMs);
        }
    }

    /**
     * Get the max time that the client waits while establishing a connection to zookeeper.
     *
     * <b>NOTE:</b> the default value is :<b>6000</b>.
     *
     * @return zookeeperConnectionTimeoutMs
     */
    public String getZookeeperConnectionTimeoutMs() {

        return props.getProperty("zookeeper.connection.timeout.ms");
    }

    /**
     * Set the max time that the client waits while establishing a connection to zookeeper.
     *
     * @param zookeeperConnectionTimeoutMs
     */
    public void setZookeeperConnectionTimeoutMs(final String zookeeperConnectionTimeoutMs) {

        if (!isNullOrEmpty(zookeeperConnectionTimeoutMs)){

            props.setProperty("zookeeper.connection.timeout.ms",zookeeperConnectionTimeoutMs);
        }
    }

    /**
     * Get how far a ZK follower can be behind a ZK leader
     *
     * <b>NOTE:</b> the default value is :<b>2000</b>.
     *
     * @return zookeeperSyncTimeMs
     */
    public String getZookeeperSyncTimeMs() {

        return props.getProperty("zookeeper.sync.time.ms");
    }

    /**
     * Set how far a ZK follower can be behind a ZK leader.
     *
     * @param zookeeperSyncTimeMs
     */
    public void setZookeeperSyncTimeMs(final String zookeeperSyncTimeMs) {

        if (!isNullOrEmpty(zookeeperSyncTimeMs)){

            props.setProperty("zookeeper.sync.time.ms",zookeeperSyncTimeMs);
        }
    }

    /**
     * Get metadata broker list.
     *
     * @return
     */
    public String getMetadataBrokerList() {

        return  props.getProperty("metadata.broker.list");
    }

    /**
     * Set metadata broker list.
     *
     * @param metadataBrokerList
     */
    public void setMetadataBrokerList(final String metadataBrokerList) {

        if (!isNullOrEmpty(metadataBrokerList)){

            props.setProperty("metadata.broker.list",metadataBrokerList);
        }
    }

    /**
     * Get Request Required Acks
     *
     * <b>NOTE:</b> the default value is :<b>2000</b>.
     *
     * @return  requestRequiredAcks
     */
    public String getRequestRequiredAcks() {

        return props.getProperty("request.required.acks");
    }

    /**
     * Set Request Required Acks
     *
     * @param requestRequiredAcks
     */
    public void setRequestRequiredAcks(final String requestRequiredAcks) {

        if (!isNullOrEmpty(requestRequiredAcks)){

            props.setProperty("request.required.acks",requestRequiredAcks);
        }
    }

    /**
     * Get the amount of time the broker will wait trying to meet the request.
     *
     * <b>NOTE:</b> the default value is :<b>10000</b>.
     *
     * @return requestTimeoutMs
     */
    public String getRequestTimeoutMs() {

        return props.getProperty("request.timeout.ms");
    }

    /**
     * Set the amount of time the broker will wait trying to meet the request.
     *
     * @param requestTimeoutMs
     */
    public void setRequestTimeoutMs(final String requestTimeoutMs) {

        if (!isNullOrEmpty(requestTimeoutMs)){

            props.setProperty("request.timeout.ms",requestTimeoutMs);
        }
    }

    /**
     * Get the producer type (sync/async)
     *
     * <b>NOTE:</b> the default value is :<b>sync</b>.
     *
     * @return producerType
     */
    public String getProducerType() {

        return props.getProperty("producer.type");
    }

    /**
     * Set the producer type (sync/async)
     *
     * @param producerType
     */
    public void setProducerType(final String producerType) {

        if (!isNullOrEmpty(producerType)){

            props.setProperty("producer.type",producerType);
        }
    }

    /**
     * Get the serializer class.
     *
     * <b>NOTE:</b> the default value is :<b>kafka.serializer.DefaultEncoder</b>. The serializer class for messages.
     * The default encoder takes a byte[] and returns the same byte[].
     *
     * @return serializerClass
     */
    public String getSerializerClass() {

        return props.getProperty("serializer.class");
    }

    /**
     * Set the serializer class.
     *
     * <b>NOTE:</b> currently tested and supports only the default serializer
     * due to it send and receive the whole exchange.
     *
     *
     * @param serializerClass
     */
    public void setSerializerClass(final String serializerClass) {

        /* TODO: need to work around this toward support only body.
         *
         * if (!isNullOrEmpty(serializerClass)){
         *
         *  props.setProperty("serializer.class",serializerClass);
         * }
         */

        LOGGER.debug("Only default serializer support in this version, setting serializer would not work");

        props.setProperty("serializer.class",KafkaConstants.DEFAULT_SERIALIZER_CLASS.value);
    }


    /**
     * Get the key serializer class.
     *
     * <b>NOTE:</b> The serializer class for keys (defaults to the same as for messages if nothing is given).
     *
     * @return
     */
    public String getKeySerializerClass() {

        return props.getProperty("key.serializer.class");
    }

    /**
     * Set the key serializer class.
     *
     * @param keySerializerClass
     */
    public void setKeySerializerClass(final String keySerializerClass) {

        if (!isNullOrEmpty(keySerializerClass)){

            props.setProperty("key.serializer.class",keySerializerClass);
        }
    }

    /**
     * Get the partitioner class.
     *
     * <b>NOTE:</b> the default value is :<b>kafka.producer.DefaultPartitioner</b>
     * @return partitionerClass
     */
    public String getPartitionerClass() {

        return props.getProperty("partitioner.class");
    }

    /**
     * Set the partitioner class.
     *
     * @param partitionerClass
     */
    public void setPartitionerClass(final String partitionerClass) {

        if (!isNullOrEmpty(partitionerClass)){

            props.setProperty("partitioner.class",partitionerClass);
        }
    }

    /**
     * Get the compresion codec.
     *
     * <b>NOTE:</b> the default value is :none<b>
     *
     * @return compressionCodec
     */
    public String getCompressionCodec() {

        return props.getProperty("compression.codec");
    }

    /**
     * Set the compresion codec.
     *
     * @param compressionCodec
     */
    public void setCompressionCodec(final String compressionCodec) {

        if (!isNullOrEmpty(compressionCodec)){

            props.setProperty("compression.codec",compressionCodec);
        }
    }

    /**
     * Get compressed topics
     *
     * <b>NOTE:</b> the default value is : <b>null</b>
     *
     * @return compressedTopics
     */
    public String getCompressedTopics() {

        return props.getProperty("compressed.topics");
    }

    /**
     * Set compressed topics
     *
     * @param compressedTopics
     */
    public void setCompressedTopics(final String compressedTopics) {

        if (!isNullOrEmpty(compressedTopics)){

            props.setProperty("compressed.topics",compressedTopics);
        }
    }

    /**
     * Get the number of retries when failures occur when sending.
     *
     *  <b>NOTE:</b> the default value is : <b>3</b>
     *
     * @return messageSendMaxRetries
     */
    public String getMessageSendMaxRetries() {

        return props.getProperty("message.send.max.retries");
    }

    /**
     * Set the number of retries when failures occur when sending
     *
     * @param messageSendMaxRetries
     */
    public void setMessageSendMaxRetries(final String messageSendMaxRetries) {

        if (!isNullOrEmpty(messageSendMaxRetries)){

            props.setProperty("message.send.max.retries",messageSendMaxRetries);
        }
    }

    /**
     * Get the retry backoff time.
     *
     * <b>NOTE:</b> the default value is : <b>100</b>
     *
     * @return retryBackoffMs
     */
    public String getRetryBackoffMs() {

        return props.getProperty("retry.backoff.ms");
    }

    /**
     * Set the retry backoff time.
     *
     * @param retryBackoffMs
     */
    public void setRetryBackoffMs(final String retryBackoffMs) {

        if (!isNullOrEmpty(retryBackoffMs)){

            props.setProperty("retry.backoff.ms",retryBackoffMs);
        }
    }

    /**
     * Get the Topic metadata refresh interval in ms.
     *
     * <b>NOTE:</b> the default value is : <b>600000</b>
     *
     * @return topicMetadataRefreshIntervalMs
     */
    public String getTopicMetadataRefreshIntervalMs() {

        return props.getProperty("topic.metadata.refresh.interval.ms");
    }

    /**
     * Set the Topic metadata refresh interval in ms.
     *
     * @param topicMetadataRefreshIntervalMs
     */
    public void setTopicMetadataRefreshIntervalMs(String topicMetadataRefreshIntervalMs) {

        if (!isNullOrEmpty(topicMetadataRefreshIntervalMs)){

            props.setProperty("topic.metadata.refresh.interval.ms",topicMetadataRefreshIntervalMs);
        }
    }

    /**
     * Get the queue buffering max time.
     *
     * <b>NOTE:</b> the default value is : <b>5000</b>
     *
     * @return queueBufferingMaxMs
     */
    public String getQueueBufferingMaxMs() {

        return props.getProperty("queue.buffering.max.ms");
    }

    /**
     * Set the queue buffering max time.
     *
     * @param queueBufferingMaxMs
     */
    public void setQueueBufferingMaxMs(String queueBufferingMaxMs) {

        if (!isNullOrEmpty(queueBufferingMaxMs)){

            props.setProperty("queue.buffering.max.ms",queueBufferingMaxMs);
        }
    }

    /**
     * Get the queue buffering max messages.
     *
     * <b>NOTE:</b> the default value is : <b>10000</b>
     *
     * @return queueBufferingMaxMessages
     */
    public String getQueueBufferingMaxMessages() {

        return props.getProperty("queue.buffering.max.messages");
    }

    /**
     * Set the queue buffering max messages.
     *
     * @param queueBufferingMaxMessages
     */
    public void setQueueBufferingMaxMessages(final String queueBufferingMaxMessages) {

        if (!isNullOrEmpty(queueBufferingMaxMessages)){

            props.setProperty("queue.buffering.max.messages",queueBufferingMaxMessages);
        }
    }

    /**
     * Get queue enqueue timeout.
     *
     * <b>NOTE:</b> the default value is : <b>-1</b>
     *
     * @return
     */
    public String getQueueEnqueueTimeoutMs() {

        return props.getProperty("queue.enqueue.timeout.ms");
    }

    /**
     * Set  queue enqueue timeout.
     *
     * @param queueEnqueueTimeoutMs
     */
    public void setQueueEnqueueTimeoutMs(String queueEnqueueTimeoutMs) {

        if (!isNullOrEmpty(queueEnqueueTimeoutMs)){

            props.setProperty("queue.enqueue.timeout.ms",queueEnqueueTimeoutMs);
        }
    }

    /**
     * Get the number of messages to send in one batch when using async mode.
     *
     * <b>NOTE:</b> the default value is : <b>200</b>
     *
     * @return batchNumMessages
     */
    public String getBatchNumMessages() {

        return props.getProperty("batch.num.messages");
    }

    /**
     * Set the number of messages to send in one batch when using async mode.
     *
     * @param batchNumMessages
     */
    public void setBatchNumMessages(String batchNumMessages) {

        if (!isNullOrEmpty(batchNumMessages)){

            props.setProperty("batch.num.messages",batchNumMessages);
        }
    }

    /**
     * Get the Socket write buffer size.
     *
     * <b>NOTE:</b> the default value is : <b>100 * 1024</b>
     *
     * @return
     */
    public String getSendBufferBytes() {

        return props.getProperty("send.buffer.bytes");
    }

    /**
     * Set the Socket write buffer size.
     *
     * @param sendBufferBytes
     */
    public void setSendBufferBytes(String sendBufferBytes) {

        if (!isNullOrEmpty(sendBufferBytes)){
            props.setProperty("send.buffer.bytes",sendBufferBytes);
        }
    }

    /**
     * Get configuration properties
     *
     * @return
     */
    public Properties getProperties() {

        return props;
    }
}
