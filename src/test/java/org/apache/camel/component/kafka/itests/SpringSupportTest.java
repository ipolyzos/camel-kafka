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

import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring support tests
 */
@Ignore
public class SpringSupportTest extends CamelSpringTestSupport {

    /**
     * Mock Endpoint used for verification
     * of the results.
     */
    @EndpointInject(uri = "mock:result")
    private MockEndpoint mock;


    /**
     * Test the Camel-Kafka, set topic by headers and sync producer
     *
     * @throws Exception
     */
    @Test
    public void setHeaderByXmlAndSyncProducerTest() throws Exception {

        final String PAYLOAD = "SpringBasicTest";

        mock.reset();  // reset the mock
        template.sendBody("direct:basicTest", PAYLOAD);

        //setup the actual expectation
        mock.expectedMessageCount(1);
        mock.expectedBodiesReceived(PAYLOAD);

        assertMockEndpointsSatisfied();
        mock.reset();
    }


    /**
     * load spring context
     *
     * @return
     */
    @Override
    protected ClassPathXmlApplicationContext createApplicationContext() {

        return new ClassPathXmlApplicationContext("META-INF/spring/basic-spring-tests.xml");
    }
}
