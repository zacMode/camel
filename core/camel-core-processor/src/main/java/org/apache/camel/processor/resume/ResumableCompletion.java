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

package org.apache.camel.processor.resume;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Resumable;
import org.apache.camel.ResumeStrategy;
import org.apache.camel.UpdatableConsumerResumeStrategy;
import org.apache.camel.spi.CamelLogger;
import org.apache.camel.spi.Synchronization;
import org.apache.camel.support.ExchangeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResumableCompletion implements Synchronization {
    private static final Logger LOG = LoggerFactory.getLogger(ResumableCompletion.class);

    private final ResumeStrategy resumeStrategy;
    private final LoggingLevel loggingLevel;

    public ResumableCompletion(ResumeStrategy resumeStrategy, LoggingLevel loggingLevel) {
        this.resumeStrategy = resumeStrategy;
        this.loggingLevel = loggingLevel;
    }

    @Override
    public void onComplete(Exchange exchange) {
        if (ExchangeHelper.isFailureHandled(exchange)) {
            return;
        }

        Object offset = ExchangeHelper.getResultMessage(exchange).getHeader(Exchange.OFFSET);

        if (offset instanceof Resumable) {
            Resumable<?, ?> resumable = (Resumable<?, ?>) offset;

            if (LOG.isTraceEnabled()) {
                LOG.trace("Processing the resumable: {}", resumable.getAddressable());
                LOG.trace("Processing the resumable of type: {}", resumable.getLastOffset().offset());
            }

            if (resumeStrategy instanceof UpdatableConsumerResumeStrategy) {
                UpdatableConsumerResumeStrategy updatableConsumerResumeStrategy
                        = (UpdatableConsumerResumeStrategy) resumeStrategy;
                try {
                    updatableConsumerResumeStrategy.updateLastOffset(resumable);
                } catch (Exception e) {
                    LOG.error("Unable to update the offset: {}", e.getMessage(), e);
                }
            } else {
                LOG.debug("Cannot perform an offset update because the strategy is not updatable");
            }
        } else {
            exchange.setException(new NoOffsetException(exchange));
            LOG.warn("Cannot update the last offset because it's not available");
        }
    }

    @Override
    public void onFailure(Exchange exchange) {
        Exception e = exchange.getException();
        Object offset = exchange.getMessage().getHeader(Exchange.OFFSET);

        if (offset instanceof Resumable) {
            Resumable<?, ?> resumable = (Resumable<?, ?>) offset;

            String logMessage = String.format(
                    "Skipping offset update with address '%s' and offset value '%s' due to failure in processing: %s",
                    resumable.getAddressable(), resumable.getLastOffset().offset(), e.getMessage());

            if (LOG.isDebugEnabled() || CamelLogger.shouldLog(LOG, loggingLevel)) {
                CamelLogger.log(LOG, LoggingLevel.DEBUG, logMessage, e);
            } else {
                logMessage += " (stacktrace available in DEBUG logging level)";

                CamelLogger.log(LOG, loggingLevel, logMessage);
            }
        } else {
            String logMessage = String.format("Skipping offset update of '%s' due to failure in processing: %s",
                    offset == null ? "type null" : "unspecified type", e.getMessage());

            if (LOG.isDebugEnabled() || CamelLogger.shouldLog(LOG, loggingLevel)) {
                CamelLogger.log(LOG, LoggingLevel.DEBUG, logMessage, e);
            } else {
                logMessage += " (stacktrace available in DEBUG logging level)";

                CamelLogger.log(LOG, loggingLevel, logMessage);
            }
        }
    }
}
