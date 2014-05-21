package com.sequenceiq.provisioning.converter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.sequenceiq.provisioning.controller.json.InfraJson;
import com.sequenceiq.provisioning.controller.validation.RequiredAwsInfraParam;
import com.sequenceiq.provisioning.domain.AwsInfra;
import com.sequenceiq.provisioning.domain.CloudPlatform;

@Component
public class AwsInfraConverter extends AbstractConverter<InfraJson, AwsInfra> {

    @Override
    public InfraJson convert(AwsInfra entity) {
        InfraJson awsStackJson = new InfraJson();
        awsStackJson.setId(entity.getId());
        awsStackJson.setClusterName(entity.getName());
        Map<String, String> props = new HashMap<>();
        props.put(RequiredAwsInfraParam.KEY_NAME.getName(), entity.getKeyName());
        props.put(RequiredAwsInfraParam.REGION.getName(), entity.getRegion());
        awsStackJson.setParameters(props);
        awsStackJson.setCloudPlatform(CloudPlatform.AWS);
        return awsStackJson;
    }

    @Override
    public AwsInfra convert(InfraJson json) {
        AwsInfra awsInfra = new AwsInfra();
        awsInfra.setName(json.getClusterName());
        awsInfra.setRegion(json.getParameters().get(RequiredAwsInfraParam.REGION.getName()));
        awsInfra.setKeyName(json.getParameters().get(RequiredAwsInfraParam.KEY_NAME.getName()));
        return awsInfra;
    }
}
