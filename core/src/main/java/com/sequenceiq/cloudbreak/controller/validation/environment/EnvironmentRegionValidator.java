package com.sequenceiq.cloudbreak.controller.validation.environment;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.model.environment.request.LocationRequest;
import com.sequenceiq.cloudbreak.cloud.model.CloudRegions;
import com.sequenceiq.cloudbreak.common.type.CloudConstants;
import com.sequenceiq.cloudbreak.controller.validation.ValidationResult.ValidationResultBuilder;
import com.sequenceiq.cloudbreak.domain.environment.Environment;

@Component
public class EnvironmentRegionValidator {

    public ValidationResultBuilder validateRegions(Set<String> requestedRegions, CloudRegions cloudRegions,
            String cloudPlatform, ValidationResultBuilder resultBuilder) {
        if (cloudRegions.areRegionsSupported()) {
            validateRegionsWhereSupported(requestedRegions, cloudRegions.getRegionNames(), resultBuilder, cloudPlatform);
        } else if (!requestedRegions.isEmpty()) {
            resultBuilder.error(String.format("Regions are not supporeted on cloudprovider: [%s].", cloudPlatform));
        }
        return resultBuilder;
    }

    public ValidationResultBuilder validateLocation(LocationRequest location, Set<String> requestedRegions,
            Environment environment, ValidationResultBuilder resultBuilder) {
        String cloudPlatform = environment.getCloudPlatform();
        if (!requestedRegions.contains(location.getLocationName())
                && !requestedRegions.isEmpty()) {
            if (!cloudPlatform.equalsIgnoreCase(CloudConstants.OPENSTACK) && !cloudPlatform.equalsIgnoreCase(CloudConstants.MOCK)) {
                resultBuilder.error(String.format("Location [%s] is not one of the regions: [%s].", location.getLocationName(),
                        requestedRegions.stream().collect(Collectors.joining(", "))));
            }
        }
        return resultBuilder;
    }

    private void validateRegionsWhereSupported(Set<String> requestedRegions, Set<String> supportedRegions, ValidationResultBuilder resultBuilder,
            String cloudPlatform) {
        if (requestedRegions.isEmpty()) {
            resultBuilder.error(String.format("Regions are mandatory on cloudprovider: [%s]", cloudPlatform));
        } else {
            Set<String> existingRegionNames = new HashSet<>(supportedRegions);
            requestedRegions = new HashSet<>(requestedRegions);
            requestedRegions.removeAll(existingRegionNames);
            if (!requestedRegions.isEmpty()) {
                resultBuilder.error(String.format("The following regions does not exist in your cloud provider: [%s]. "
                                + "Existing regions are: [%s]",
                        requestedRegions.stream().collect(Collectors.joining(", ")),
                        existingRegionNames.stream().collect(Collectors.joining(", "))
                ));
            }
        }
    }
}
