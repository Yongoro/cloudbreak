package com.sequenceiq.cloudbreak.api.endpoint.v3;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sequenceiq.cloudbreak.api.model.environment.request.EnvironmentAttachRequest;
import com.sequenceiq.cloudbreak.api.model.environment.request.EnvironmentChangeCredentialRequest;
import com.sequenceiq.cloudbreak.api.model.environment.request.EnvironmentDetachRequest;
import com.sequenceiq.cloudbreak.api.model.environment.request.EnvironmentEditRequest;
import com.sequenceiq.cloudbreak.api.model.environment.request.EnvironmentRequest;
import com.sequenceiq.cloudbreak.api.model.environment.request.RegisterDatalakeRequest;
import com.sequenceiq.cloudbreak.api.model.environment.response.DetailedEnvironmentResponse;
import com.sequenceiq.cloudbreak.api.model.environment.response.SimpleEnvironmentResponse;
import com.sequenceiq.cloudbreak.doc.ContentType;
import com.sequenceiq.cloudbreak.doc.ControllerDescription;
import com.sequenceiq.cloudbreak.doc.Notes;
import com.sequenceiq.cloudbreak.doc.OperationDescriptions.EnvironmentOpDescription;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/v3/{workspaceId}/environments")
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/v3/{workspaceId}/environments", description = ControllerDescription.ENVIRONMENT_V3_DESCRIPTION, protocols = "http,https")
public interface EnvironmentV3Endpoint {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = EnvironmentOpDescription.CREATE, produces = ContentType.JSON, notes = Notes.ENVIRONMENT_NOTES,
            nickname = "createEnvironment")
    DetailedEnvironmentResponse create(@PathParam("workspaceId") Long workspaceId, @Valid EnvironmentRequest request);

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = EnvironmentOpDescription.GET, produces = ContentType.JSON, notes = Notes.ENVIRONMENT_NOTES,
            nickname = "getEnvironment")
    DetailedEnvironmentResponse get(@PathParam("workspaceId") Long workspaceId, @PathParam("name") String environmentName);

    @PUT
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = EnvironmentOpDescription.EDIT, produces = ContentType.JSON, notes = Notes.ENVIRONMENT_NOTES,
            nickname = "editEnvironment")
    DetailedEnvironmentResponse edit(@PathParam("workspaceId") Long workspaceId, @PathParam("name") String environmentName,
            @NotNull EnvironmentEditRequest request);

    @DELETE
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = EnvironmentOpDescription.DELETE, produces = ContentType.JSON, notes = Notes.ENVIRONMENT_NOTES,
            nickname = "deleteEnvironment")
    SimpleEnvironmentResponse delete(@PathParam("workspaceId") Long workspaceId, @PathParam("name") String environmentName);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = EnvironmentOpDescription.LIST, produces = ContentType.JSON, notes = Notes.ENVIRONMENT_NOTES,
            nickname = "listEnvironment")
    Set<SimpleEnvironmentResponse> list(@PathParam("workspaceId") Long workspaceId);

    @PUT
    @Path("/{name}/attach")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = EnvironmentOpDescription.ATTACH_RESOURCES, produces = ContentType.JSON, notes = Notes.ENVIRONMENT_NOTES,
            nickname = "attachResourcesToEnvironment")
    DetailedEnvironmentResponse attachResources(@PathParam("workspaceId") Long workspaceId, @PathParam("name") String environmentName,
            @Valid EnvironmentAttachRequest request);

    @PUT
    @Path("/{name}/detach")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = EnvironmentOpDescription.DETACH_RESOURCES, produces = ContentType.JSON, notes = Notes.ENVIRONMENT_NOTES,
            nickname = "detachResourcesFromEnvironment")
    DetailedEnvironmentResponse detachResources(@PathParam("workspaceId") Long workspaceId, @PathParam("name") String environmentName,
            @Valid EnvironmentDetachRequest request);

    @PUT
    @Path("/{name}/changeCredential")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = EnvironmentOpDescription.CHANGE_CREDENTIAL, produces = ContentType.JSON, notes = Notes.ENVIRONMENT_NOTES,
            nickname = "changeCredentialInEnvironment")
    DetailedEnvironmentResponse changeCredential(@PathParam("workspaceId") Long workspaceId, @PathParam("name") String environmentName,
            EnvironmentChangeCredentialRequest request);

    @PUT
    @Path("/{name}/registerDatalake")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = EnvironmentOpDescription.REGISTER_EXTERNAL_DATALAKE, produces = ContentType.JSON, notes = Notes.ENVIRONMENT_NOTES,
            nickname = "registerExternalDatalake")
    DetailedEnvironmentResponse registerExternalDatalake(@PathParam("workspaceId") Long workspaceId, @PathParam("name") String environmentName,
            @Valid RegisterDatalakeRequest request);
}
