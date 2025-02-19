/*
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
package org.apache.camel.component.mybatis;

import org.apache.camel.ShutdownRunningTask;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyBatisShutdownAllTasksTest extends MyBatisTestSupport {

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        // super will insert 2 accounts already

        Account account = new Account();
        account.setId(881);
        account.setFirstName("A");
        account.setLastName("A");
        account.setEmailAddress("a@gmail.com");

        template.sendBody("mybatis:insertAccount?statementType=Insert", account);

        account = new Account();
        account.setId(882);
        account.setFirstName("B");
        account.setLastName("B");
        account.setEmailAddress("b@gmail.com");

        template.sendBody("mybatis:insertAccount?statementType=Insert", account);

        account = new Account();
        account.setId(883);
        account.setFirstName("C");
        account.setLastName("C");
        account.setEmailAddress("c@gmail.com");

        template.sendBody("mybatis:insertAccount?statementType=Insert", account);

        account = new Account();
        account.setId(884);
        account.setFirstName("D");
        account.setLastName("D");
        account.setEmailAddress("d@gmail.com");

        template.sendBody("mybatis:insertAccount?statementType=Insert", account);

        account = new Account();
        account.setId(885);
        account.setFirstName("E");
        account.setLastName("E");
        account.setEmailAddress("e@gmail.com");

        template.sendBody("mybatis:insertAccount?statementType=Insert", account);

        account = new Account();
        account.setId(886);
        account.setFirstName("F");
        account.setLastName("F");
        account.setEmailAddress("f@gmail.com");

        template.sendBody("mybatis:insertAccount?statementType=Insert", account);
    }

    @Test
    public void testShutdownAllTasks() throws Exception {
        context.getRouteController().startRoute("route1");

        MockEndpoint bar = getMockEndpoint("mock:bar");
        bar.expectedMinimumMessageCount(1);
        bar.setResultWaitTime(3000);

        assertMockEndpointsSatisfied();

        // shutdown during processing
        context.stop();

        // sleep a little
        Thread.sleep(1000);

        // should route all 8
        assertEquals(8, bar.getReceivedCounter(), "Should complete all messages");
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("mybatis:selectAllAccounts").noAutoStartup().routeId("route1")
                        // let it complete all tasks
                        .shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks)
                        .delay(1000).to("seda:foo");

                from("seda:foo").routeId("route2").to("mock:bar");
            }
        };
    }
}
