package com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.sequenceiq.cloudbreak.api.endpoint.v4.JsonEntity;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.ProviderParametersBase;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.parameter.instancegroup.AwsInstanceGroupV4Parameters;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.parameter.instancegroup.AzureInstanceGroupV4Parameters;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.parameter.instancegroup.GcpInstanceGroupV4Parameters;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.base.parameter.instancegroup.OpenStackInstanceGroupV4Parameters;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.HostGroupModelDescription;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.InstanceGroupModelDescription;

import io.swagger.annotations.ApiModelProperty;

public class InstanceGroupV4Base extends ProviderParametersBase implements JsonEntity {

    @Min(value = 0, message = "The node count has to be greater or equals than 0")
    @Max(value = 100000, message = "The node count has to be less than 100000")
    @Digits(fraction = 0, integer = 10, message = "The node count has to be a number")
    @ApiModelProperty(value = InstanceGroupModelDescription.NODE_COUNT, required = true)
    private int nodeCount;

    @NotNull
    @ApiModelProperty(value = InstanceGroupModelDescription.INSTANCE_GROUP_NAME, required = true)
    private String name;

    @ApiModelProperty(value = InstanceGroupModelDescription.INSTANCE_GROUP_TYPE, allowableValues = "CORE,GATEWAY")
    private InstanceGroupType type = InstanceGroupType.CORE;

    @ApiModelProperty(InstanceGroupModelDescription.AZURE_PARAMETERS)
    private AzureInstanceGroupV4Parameters azure;

    @ApiModelProperty(InstanceGroupModelDescription.GCP_PARAMETERS)
    private GcpInstanceGroupV4Parameters gcp;

    @ApiModelProperty(InstanceGroupModelDescription.AWS_PARAMETERS)
    private AwsInstanceGroupV4Parameters aws;

    @ApiModelProperty(InstanceGroupModelDescription.OPENSTACK_PARAMETERS)
    private OpenStackInstanceGroupV4Parameters openstack;

    @ApiModelProperty(value = HostGroupModelDescription.RECOVERY_MODE, allowableValues = "MANUAL,AUTO")
    private RecoveryMode recoveryMode = RecoveryMode.MANUAL;

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InstanceGroupType getType() {
        return type;
    }

    public void setType(InstanceGroupType type) {
        this.type = type;
    }

    public AzureInstanceGroupV4Parameters getAzure() {
        return azure;
    }

    public void setAzure(AzureInstanceGroupV4Parameters azure) {
        this.azure = azure;
    }

    public GcpInstanceGroupV4Parameters getGcp() {
        return gcp;
    }

    public void setGcp(GcpInstanceGroupV4Parameters gcp) {
        this.gcp = gcp;
    }

    public AwsInstanceGroupV4Parameters getAws() {
        return aws;
    }

    public void setAws(AwsInstanceGroupV4Parameters aws) {
        this.aws = aws;
    }

    public OpenStackInstanceGroupV4Parameters getOpenstack() {
        return openstack;
    }

    public void setOpenstack(OpenStackInstanceGroupV4Parameters openstack) {
        this.openstack = openstack;
    }

    public RecoveryMode getRecoveryMode() {
        return recoveryMode;
    }

    public void setRecoveryMode(RecoveryMode recoveryMode) {
        this.recoveryMode = recoveryMode;
    }
}
