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

import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

/**
 * Camel-Kafka {@link DefaultEndpoint}.
 */
public class KafkaEndpoint extends DefaultEndpoint {

    /**
     * Camel Kafka Configuration
     */
    private KafkaConfiguration configuration;

    /**
     * Kafka endpoint Constructor
     *
     * @param endpointUri
     * @param component
     * @param configuration
     */
    public KafkaEndpoint(final String endpointUri,
                         final Component component,
                         final KafkaConfiguration configuration) {

        super(endpointUri, component);
        this.configuration = configuration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Producer createProducer() throws Exception {

        return new KafkaProducer(this, this.configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Consumer createConsumer(Processor processor) throws Exception {

        return new KafkaConsumer(this, processor, this.configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSingleton() {

        return true;
    }
}
