package com.sequenceiq.cloudbreak.reactor.api.event.cluster;

import java.util.Map;

public class AmbariStartComponentsRequest extends AmbariComponentsRequest {

    private final Boolean restartAll;

    public AmbariStartComponentsRequest(Long stackId, String hostGroupName, String hostName, Map<String, String> components, boolean restartAll) {
        super(stackId, hostGroupName, hostName, components);
        this.restartAll = restartAll;
    }

    public Boolean isRestartAll() {
        return restartAll;
    }
}
