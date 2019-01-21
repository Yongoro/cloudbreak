package com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable;

import io.swagger.annotations.ApiModelProperty;

public abstract class ProviderParametersBase {

    @ApiModelProperty(hidden = true)
    private CloudPlatform cloudPlatform;

    public CloudPlatform getCloudPlatform() {
        return cloudPlatform;
    }

    public void setCloudPlatform(CloudPlatform cloudPlatform) {
        this.cloudPlatform = cloudPlatform;
    }

    public Mappable getAws() {
        return Mappable.EMPTY;
    }

    public Mappable getGcp() {
        return Mappable.EMPTY;
    }

    public Mappable getAzure() {
        return Mappable.EMPTY;
    }

    public Mappable getOpenstack() {
        return Mappable.EMPTY;
    }

    public Mappable getYarn() {
        return Mappable.EMPTY;
    }

    public Mappable getMock() {
        return Mappable.EMPTY;
    }
}
