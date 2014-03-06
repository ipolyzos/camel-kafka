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

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.util.ObjectHelper;

/**
 * Camel-Kafka {@link DefaultComponent}.
 */
public class KafkaComponent extends DefaultComponent {

    /**
     * Camel-Kafka configuration
     */
    private KafkaConfiguration configuration;

    /**
     * {@inheritDoc}
     */
    @Override
    protected Endpoint createEndpoint(final String uri,
                                      final String remaining,
                                      final Map<String, Object> parameters) throws Exception {

        configuration= new KafkaConfiguration();
        setProperties(configuration, parameters);

        if(ObjectHelper.isEmpty(remaining)){

            throw new IllegalArgumentException("The topic name is missing.");
        }

        configuration.setTopicName(remaining);

        return new KafkaEndpoint(uri, this, configuration);
    }

    /**
     * Get Configuration
     *
     * @return Configuration
     */
    public KafkaConfiguration getConfiguration() {

        return configuration;
    }
}
