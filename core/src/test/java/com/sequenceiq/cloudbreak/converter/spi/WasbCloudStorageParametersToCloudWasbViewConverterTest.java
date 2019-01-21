package com.sequenceiq.cloudbreak.converter.spi;

import com.sequenceiq.cloudbreak.api.endpoint.v4.filesystems.requests.wasb.WasbCloudStorageParameters;
import com.sequenceiq.cloudbreak.cloud.model.filesystem.CloudWasbView;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WasbCloudStorageParametersToCloudWasbViewConverterTest {

    private static final String ACCOUNT_KEY = "a-224sv-55dfdsf-3-3444dfsf";

    private static final String ACCOUNT_NAME = "testName";

    private WasbCloudStorageParametersV4ToCloudWasbViewConverter underTest;

    @Before
    public void setUp() {
        underTest = new WasbCloudStorageParametersV4ToCloudWasbViewConverter();
    }

    @Test
    public void testConvertWhenPassingWasbCloudStorageParametersThenEveryNecessaryParametersShouldBePassed() {
        CloudWasbView expected = new CloudWasbView();
        expected.setSecure(false);
        expected.setAccountKey(ACCOUNT_KEY);
        expected.setAccountName(ACCOUNT_NAME);
        expected.setResourceGroupName(null);

        CloudWasbView result = underTest.convert(createSource());

        assertEquals(expected, result);
    }

    private WasbCloudStorageParameters createSource() {
        WasbCloudStorageParameters parameters = new WasbCloudStorageParameters();
        parameters.setSecure(true);
        parameters.setAccountKey(ACCOUNT_KEY);
        parameters.setAccountName(ACCOUNT_NAME);
        return parameters;
    }

}