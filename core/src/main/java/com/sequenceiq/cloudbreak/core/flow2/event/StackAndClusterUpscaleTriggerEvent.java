package com.sequenceiq.cloudbreak.core.flow2.event;

import java.util.Collections;
import java.util.Set;

import com.sequenceiq.cloudbreak.common.type.ScalingType;

import reactor.rx.Promise;

public class StackAndClusterUpscaleTriggerEvent extends StackScaleTriggerEvent {

    private final ScalingType scalingType;

    private final boolean singleMasterGateway;

    private final boolean kerberosSecured;

    public StackAndClusterUpscaleTriggerEvent(String selector, Long stackId, String instanceGroup, Integer adjustment, ScalingType scalingType) {
        super(selector, stackId, instanceGroup, adjustment, Collections.emptySet());
        this.scalingType = scalingType;
        this.singleMasterGateway = false;
        this.kerberosSecured = false;
    }

    public StackAndClusterUpscaleTriggerEvent(String selector, Long stackId, String instanceGroup, Integer adjustment, ScalingType scalingType,
            Set<String> hostNames, boolean singlePrimaryGateway, boolean kerberosSecured, Promise<Boolean> accepted) {
        super(selector, stackId, instanceGroup, adjustment, hostNames, accepted);
        this.scalingType = scalingType;
        this.singleMasterGateway = singlePrimaryGateway;
        this.kerberosSecured = kerberosSecured;
    }

    public ScalingType getScalingType() {
        return scalingType;
    }

    public boolean isSingleMasterGateway() {
        return singleMasterGateway;
    }

    public boolean isKerberosSecured() {
        return kerberosSecured;
    }
}
