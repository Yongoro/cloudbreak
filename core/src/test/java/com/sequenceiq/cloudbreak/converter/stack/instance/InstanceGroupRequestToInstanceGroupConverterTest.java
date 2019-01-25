package com.sequenceiq.cloudbreak.converter.stack.instance;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.AccessDeniedException;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.mappable.ProviderParameterCalculator;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.instancegroup.InstanceGroupV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.instancegroup.securitygroup.SecurityGroupV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.instancegroup.template.InstanceTemplateV4Request;
import com.sequenceiq.cloudbreak.converter.AbstractJsonConverterTest;
import com.sequenceiq.cloudbreak.converter.v4.stacks.instancegroup.InstanceGroupV4RequestToInstanceGroupConverter;
import com.sequenceiq.cloudbreak.domain.SecurityGroup;
import com.sequenceiq.cloudbreak.domain.Template;
import com.sequenceiq.cloudbreak.domain.stack.instance.InstanceGroup;

@RunWith(MockitoJUnitRunner.class)
public class InstanceGroupRequestToInstanceGroupConverterTest extends AbstractJsonConverterTest<InstanceGroupV4Request> {

    @InjectMocks
    private InstanceGroupV4RequestToInstanceGroupConverter underTest;

    @Mock
    private ConversionService conversionService;

    @Mock
    private ProviderParameterCalculator providerParameterCalculator;

    @Test
    public void testConvert() {
        InstanceGroupV4Request request = getRequest("instance-group.json");
        // GIVEN
        given(providerParameterCalculator.get(request)).willReturn(() -> new HashMap<>(Map.of("key", "value")));
        given(conversionService.convert(any(InstanceTemplateV4Request.class), eq(Template.class))).willReturn(new Template());
        given(conversionService.convert(any(SecurityGroupV4Request.class), eq(SecurityGroup.class))).willReturn(new SecurityGroup());
        // WHEN
        InstanceGroup instanceGroup = underTest.convert(request);
        // THEN
        assertAllFieldsNotNull(instanceGroup, Collections.singletonList("stack"));
    }

    @Test(expected = AccessDeniedException.class)
    public void testConvertWhenAccessDenied() {
        // GIVEN
        // WHEN
        underTest.convert(getRequest("instance-group.json"));
    }

    @Override
    public Class<InstanceGroupV4Request> getRequestClass() {
        return InstanceGroupV4Request.class;
    }
}
