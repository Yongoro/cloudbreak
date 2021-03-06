package com.sequenceiq.cloudbreak.service.cluster.clouderamanager;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cloudera.api.swagger.client.ApiClient;
import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.service.PollingResult;
import com.sequenceiq.cloudbreak.service.PollingService;

@Service
public class ClouderaManagerPollingServiceProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClouderaManagerPollingServiceProvider.class);

    private static final int POLL_INTERVAL = 5000;

    private static final int MAX_ATTEMPT = 120;

    @Inject
    private PollingService<ClouderaManagerPollerObject> clouderaManagerPollerService;

    @Inject
    private PollingService<ClouderaManagerTemplateInstallPollerObject> clouderaManagerTemplateInstallPollerService;

    @Inject
    private ClouderaManagerStartupListenerTask clouderaManagerStartupListenerTask;

    @Inject
    private ClouderaManagerHostStatusChecker clouderaManagerHostStatusChecker;

    @Inject
    private ClouderaManagerTemplateInstallChecker clouderaManagerTemplateInstallChecker;

    PollingResult clouderaManagerStartupPollerObjectPollingService(Stack stack, ApiClient apiClient) {
        LOGGER.debug("Waiting for Cloudera Manager startup. [Server address: {}]", stack.getAmbariIp());
        ClouderaManagerPollerObject clouderaManagerPollerObject = new ClouderaManagerPollerObject(stack, apiClient);
        return clouderaManagerPollerService.pollWithTimeoutSingleFailure(
                clouderaManagerStartupListenerTask,
                clouderaManagerPollerObject,
                POLL_INTERVAL,
                MAX_ATTEMPT);
    }

    public PollingResult hostsPollingService(Stack stack, ApiClient apiClient) {
        LOGGER.debug("Waiting for Cloudera Manager hosts to connect. [Server address: {}]", stack.getAmbariIp());
        ClouderaManagerPollerObject clouderaManagerPollerObject = new ClouderaManagerPollerObject(stack, apiClient);
        return clouderaManagerPollerService.pollWithTimeoutSingleFailure(
                clouderaManagerHostStatusChecker,
                clouderaManagerPollerObject,
                POLL_INTERVAL,
                MAX_ATTEMPT);
    }

    public PollingResult templateInstallCheckerService(Stack stack, ApiClient apiClient, BigDecimal commandId) {
        LOGGER.debug("Waiting for Cloudera Manager to install template. [Server address: {}]", stack.getAmbariIp());
        ClouderaManagerTemplateInstallPollerObject clouderaManagerTemplateInstallPollerObject =
                new ClouderaManagerTemplateInstallPollerObject(stack, apiClient, commandId);
        return clouderaManagerTemplateInstallPollerService.pollWithTimeoutSingleFailure(
                clouderaManagerTemplateInstallChecker,
                clouderaManagerTemplateInstallPollerObject,
                POLL_INTERVAL,
                MAX_ATTEMPT);
    }

}
