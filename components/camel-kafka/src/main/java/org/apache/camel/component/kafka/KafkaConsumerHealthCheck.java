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
package org.apache.camel.component.kafka;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.camel.health.HealthCheckResultBuilder;
import org.apache.camel.impl.health.AbstractHealthCheck;
import org.apache.camel.util.TimeUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;

/**
 * Kafka consumer readiness health-check
 */
public class KafkaConsumerHealthCheck extends AbstractHealthCheck {

    private final KafkaConsumer kafkaConsumer;
    private final String routeId;

    public KafkaConsumerHealthCheck(KafkaConsumer kafkaConsumer, String routeId) {
        super("camel", "kafka-consumer-" + routeId);
        this.kafkaConsumer = kafkaConsumer;
        this.routeId = routeId;
    }

    @Override
    public boolean isLiveness() {
        // this health check is only readiness
        return false;
    }

    @Override
    protected void doCall(HealthCheckResultBuilder builder, Map<String, Object> options) {
        List<KafkaFetchRecords> tasks = kafkaConsumer.getTasks();
        for (KafkaFetchRecords task : tasks) {
            if (!task.isReady()) {
                builder.down();

                String msg = "KafkaConsumer is not ready";
                if (task.isTerminated()) {
                    msg += " (gave up recovering and terminated the kafka consumer; restart route or application to recover).";
                } else if (task.isRecoverable()) {
                    String time = TimeUtils.printDuration(task.getCurrentRecoveryInterval());
                    msg += " (recovery in progress using " + time + " intervals).";
                }
                builder.message(msg);

                // was this caused by consumer not able to connect then this is stored in last error
                builder.error(task.getLastError());

                KafkaConfiguration cfg = kafkaConsumer.getEndpoint().getConfiguration();
                Properties props = task.getKafkaProps();

                builder.detail("bootstrap.servers", props.getProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
                builder.detail("client.id", task.getClientId());
                String gid = props.getProperty(ConsumerConfig.GROUP_ID_CONFIG);
                if (gid != null) {
                    builder.detail("group.id", gid);
                }
                if (routeId != null) {
                    // camel route id
                    builder.detail("route.id", routeId);
                }
                builder.detail("topic", cfg.getTopic());

                return; // break on first DOWN
            }
        }
        builder.up();
    }
}
