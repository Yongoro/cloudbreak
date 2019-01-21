package com.sequenceiq.cloudbreak.converter.spi;

import static com.sequenceiq.cloudbreak.services.filesystem.FileSystemType.WASB;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;

import com.sequenceiq.cloudbreak.api.endpoint.v4.filesystems.requests.CloudStorageParameters;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.cluster.storage.CloudStorageV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.cluster.storage.azure.AdlsCloudStorageParametersV4;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.cluster.storage.azure.WasbCloudStorageParametersV4;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.cluster.storage.gcs.GcsCloudStorageParametersV4;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.cluster.storage.s3.S3CloudStorageParametersV4;
import com.sequenceiq.cloudbreak.cloud.model.SpiFileSystem;
import com.sequenceiq.cloudbreak.cloud.model.filesystem.CloudAdlsView;
import com.sequenceiq.cloudbreak.cloud.model.filesystem.CloudGcsView;
import com.sequenceiq.cloudbreak.cloud.model.filesystem.CloudS3View;
import com.sequenceiq.cloudbreak.cloud.model.filesystem.CloudWasbView;
import com.sequenceiq.cloudbreak.services.filesystem.FileSystemType;

public class FileSystemRequestToSpiFileSystemConverterTest {

    private static final String TEST_NAME = "testname";

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private CloudStorageV4RequestToSpiFileSystemConverter underTest;

    @Mock
    private ConversionService conversionService;

    @Mock
    private CloudStorageV4Request request;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(request.getName()).thenReturn(TEST_NAME);
    }

    @Test
    public void testConvertWhenAdlsNotNullThenCloudAdlsShouldBeInReturningObject() {
        AdlsCloudStorageParametersV4 adls = mock(AdlsCloudStorageParametersV4.class);
        CloudAdlsView expected = mock(CloudAdlsView.class);
        when(request.getAdls()).thenReturn(adls);
        when(request.getType()).thenReturn(FileSystemType.ADLS.name());
        when(conversionService.convert(adls, CloudAdlsView.class)).thenReturn(expected);

        SpiFileSystem result = underTest.convert(request);

        assertEquals(expected, result.getCloudFileSystem());
        assertEquals(FileSystemType.ADLS, result.getType());
        assertEquals(TEST_NAME, result.getName());
        verify(conversionService, times(1)).convert(adls, CloudAdlsView.class);
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudGcsView.class));
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudS3View.class));
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudWasbView.class));
    }

    @Test
    public void testConvertWhenGcsNotNullThenCloudGcsShouldBeInReturningObject() {
        GcsCloudStorageParametersV4 gcs = mock(GcsCloudStorageParametersV4.class);
        CloudGcsView expected = mock(CloudGcsView.class);
        when(request.getGcs()).thenReturn(gcs);
        when(request.getType()).thenReturn(FileSystemType.GCS.name());
        when(conversionService.convert(gcs, CloudGcsView.class)).thenReturn(expected);

        SpiFileSystem result = underTest.convert(request);

        assertEquals(expected, result.getCloudFileSystem());
        assertEquals(FileSystemType.GCS, result.getType());
        assertEquals(TEST_NAME, result.getName());
        verify(conversionService, times(1)).convert(gcs, CloudGcsView.class);
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudAdlsView.class));
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudS3View.class));
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudWasbView.class));
    }

    @Test
    public void testConvertWhenS3NotNullThenCloudS3ShouldBeInReturningObject() {
        S3CloudStorageParametersV4 s3 = mock(S3CloudStorageParametersV4.class);
        CloudS3View expected = mock(CloudS3View.class);
        when(request.getS3()).thenReturn(s3);
        when(request.getType()).thenReturn(FileSystemType.S3.name());
        when(conversionService.convert(s3, CloudS3View.class)).thenReturn(expected);

        SpiFileSystem result = underTest.convert(request);

        assertEquals(expected, result.getCloudFileSystem());
        assertEquals(FileSystemType.S3, result.getType());
        assertEquals(TEST_NAME, result.getName());
        verify(conversionService, times(1)).convert(s3, CloudS3View.class);
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudAdlsView.class));
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudGcsView.class));
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudWasbView.class));
    }

    @Test
    public void testConvertWhenWasbNotNullThenCloudWasbShouldBeInReturningObject() {
        WasbCloudStorageParametersV4 wasb = mock(WasbCloudStorageParametersV4.class);
        CloudWasbView expected = mock(CloudWasbView.class);
        when(request.getWasb()).thenReturn(wasb);
        when(request.getType()).thenReturn(WASB.name());
        when(conversionService.convert(wasb, CloudWasbView.class)).thenReturn(expected);

        SpiFileSystem result = underTest.convert(request);

        assertEquals(expected, result.getCloudFileSystem());
        assertEquals(WASB, result.getType());
        assertEquals(TEST_NAME, result.getName());
        verify(conversionService, times(1)).convert(wasb, CloudWasbView.class);
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudAdlsView.class));
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudGcsView.class));
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudS3View.class));
    }

    @Test
    public void testConvertWhenAllCloudStorageParametersAreNullThenNullCloudFileSystemViewShouldPlacedInResultInstance() {
        when(request.getWasb()).thenReturn(null);
        when(request.getAdls()).thenReturn(null);
        when(request.getS3()).thenReturn(null);
        when(request.getGcs()).thenReturn(null);
        when(request.getType()).thenReturn(WASB.name());

        SpiFileSystem result = underTest.convert(request);

        assertNull(result.getCloudFileSystem());
        assertEquals(TEST_NAME, result.getName());
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudWasbView.class));
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudAdlsView.class));
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudGcsView.class));
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudS3View.class));
    }

    @Test
    public void testConvertWhenNotExistingTypeProvidedThenExceptionWouldCome() {
        when(request.getType()).thenReturn("not existing file system type");

        expectedException.expect(IllegalArgumentException.class);

        underTest.convert(request);
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudWasbView.class));
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudAdlsView.class));
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudGcsView.class));
        verify(conversionService, times(0)).convert(any(CloudStorageParameters.class), eq(CloudS3View.class));
    }

}