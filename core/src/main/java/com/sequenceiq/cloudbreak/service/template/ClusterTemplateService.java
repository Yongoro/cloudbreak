package com.sequenceiq.cloudbreak.service.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.ResourceStatus;
import com.sequenceiq.cloudbreak.authorization.WorkspaceResource;
import com.sequenceiq.cloudbreak.common.model.user.CloudbreakUser;
import com.sequenceiq.cloudbreak.controller.exception.BadRequestException;
import com.sequenceiq.cloudbreak.domain.Network;
import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.domain.stack.cluster.Cluster;
import com.sequenceiq.cloudbreak.domain.stack.cluster.ClusterTemplate;
import com.sequenceiq.cloudbreak.domain.workspace.User;
import com.sequenceiq.cloudbreak.domain.workspace.Workspace;
import com.sequenceiq.cloudbreak.init.clustertemplate.ClusterTemplateLoaderService;
import com.sequenceiq.cloudbreak.repository.OrchestratorRepository;
import com.sequenceiq.cloudbreak.repository.cluster.ClusterTemplateRepository;
import com.sequenceiq.cloudbreak.repository.workspace.WorkspaceResourceRepository;
import com.sequenceiq.cloudbreak.service.AbstractWorkspaceAwareResourceService;
import com.sequenceiq.cloudbreak.service.ComponentConfigProvider;
import com.sequenceiq.cloudbreak.service.RestRequestThreadLocalService;
import com.sequenceiq.cloudbreak.service.cluster.ClusterService;
import com.sequenceiq.cloudbreak.service.network.NetworkService;
import com.sequenceiq.cloudbreak.service.stack.InstanceGroupService;
import com.sequenceiq.cloudbreak.service.stack.StackTemplateService;
import com.sequenceiq.cloudbreak.service.user.UserService;

@Service
public class ClusterTemplateService extends AbstractWorkspaceAwareResourceService<ClusterTemplate> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterTemplateService.class);

    @Inject
    private ClusterTemplateRepository clusterTemplateRepository;

    @Inject
    private UserService userService;

    @Inject
    private RestRequestThreadLocalService restRequestThreadLocalService;

    @Inject
    private ClusterTemplateLoaderService clusterTemplateLoaderService;

    @Inject
    private OrchestratorRepository orchestratorRepository;

    @Inject
    private ClusterService clusterService;

    @Inject
    private NetworkService networkService;

    @Inject
    private InstanceGroupService instanceGroupService;

    @Inject
    private StackTemplateService stackTemplateService;

    @Inject
    private ComponentConfigProvider componentConfigProvider;

    @Override
    protected WorkspaceResourceRepository<ClusterTemplate, Long> repository() {
        return clusterTemplateRepository;
    }

    @Override
    protected void prepareDeletion(ClusterTemplate resource) {
        if (resource.getStatus() == ResourceStatus.DEFAULT || resource.getStatus() == ResourceStatus.DEFAULT_DELETED) {
            throw new AccessDeniedException("Default template deletion is forbidden");
        }
    }

    @Override
    protected void prepareCreation(ClusterTemplate resource) {

        validateBeforeCreate(resource);

        Stack stackTemplate = resource.getStackTemplate();
        stackTemplate.setName(UUID.randomUUID().toString());
        if (stackTemplate.getOrchestrator() != null) {
            orchestratorRepository.save(stackTemplate.getOrchestrator());
        }

        Network network = stackTemplate.getNetwork();
        if (network != null) {
            network.setWorkspace(stackTemplate.getWorkspace());
            networkService.pureSave(network);
        }

        Cluster cluster = stackTemplate.getCluster();
        if (cluster != null) {
            cluster.setWorkspace(stackTemplate.getWorkspace());
            clusterService.saveWithRef(cluster);
        }

        stackTemplate = stackTemplateService.pureSave(stackTemplate);

        componentConfigProvider.store(new ArrayList<>(stackTemplate.getComponents()));

        if (cluster != null) {
            cluster.setStack(stackTemplate);
            clusterService.save(cluster);
        }

        if (stackTemplate.getInstanceGroups() != null && !stackTemplate.getInstanceGroups().isEmpty()) {
            instanceGroupService.saveAll(stackTemplate.getInstanceGroups(), stackTemplate.getWorkspace());
        }
    }

    private void validateBeforeCreate(ClusterTemplate resource) {

        if (resource.getStackTemplate() == null) {
            throw new BadRequestException("The stack tempalte cannot be null.");
        }

        if (resource.getStatus() != ResourceStatus.DEFAULT && resource.getStackTemplate().getEnvironment() == null) {
            throw new BadRequestException("The environment cannot be null.");
        }

        if (clusterTemplateRepository.findByNameAndWorkspace(resource.getName(), resource.getWorkspace()) != null) {
            throw new BadRequestException(
                    String.format("clustertemplate already exists with name '%s' in workspace %s", resource.getName(), resource.getWorkspace().getName()));
        }
    }

    @Override
    public Set<ClusterTemplate> findAllByWorkspace(Workspace workspace) {
        return getAllAvailableInWorkspace(workspace);
    }

    @Override
    public Set<ClusterTemplate> findAllByWorkspaceId(Long workspaceId) {
        CloudbreakUser cloudbreakUser = restRequestThreadLocalService.getCloudbreakUser();
        User user = userService.getOrCreate(cloudbreakUser);
        Workspace workspace = getWorkspaceService().get(workspaceId, user);
        return getAllAvailableInWorkspace(workspace);
    }

    private Set<ClusterTemplate> getAllAvailableInWorkspace(Workspace workspace) {
        Set<ClusterTemplate> clusterTemplates = clusterTemplateRepository.findAllByNotDeletedInWorkspace(workspace.getId());
        if (clusterTemplateLoaderService.isDefaultClusterTemplateUpdateNecessaryForUser(clusterTemplates)) {
            LOGGER.debug("Modifying clusterTemplates based on the defaults for the '{}' workspace.", workspace.getId());
            Collection<ClusterTemplate> outdatedTemplates = clusterTemplateLoaderService.collectOutdatedTemplatesInDb(clusterTemplates);
            outdatedTemplates.forEach(ct -> {
                ct.setStatus(ResourceStatus.OUTDATED);
                delete(ct.getName(), ct.getWorkspace().getId());
            });
            clusterTemplates = clusterTemplateRepository.findAllByNotDeletedInWorkspace(workspace.getId());
            clusterTemplates = clusterTemplateLoaderService.loadClusterTemplatesForWorkspace(clusterTemplates, workspace, this::createAll);
            LOGGER.debug("ClusterTemplate modifications finished based on the defaults for '{}' workspace.", workspace.getId());
        }
        return clusterTemplates;
    }

    private Collection<ClusterTemplate> createAll(Iterable<ClusterTemplate> clusterTemplates) {
        return StreamSupport.stream(clusterTemplates.spliterator(), false)
                .map(ct -> create(ct, ct.getWorkspace(), userService.getOrCreate(restRequestThreadLocalService.getCloudbreakUser())))
                .collect(Collectors.toList());
    }

    @Override
    public WorkspaceResource resource() {
        return WorkspaceResource.CLUSTER_TEMPLATE;
    }

    public ClusterTemplate delete(String name, Long workspaceId) {
        ClusterTemplate clusterTemplate = getByNameForWorkspaceId(name, workspaceId);
        deleteByNameFromWorkspace(name, workspaceId);
        stackTemplateService.delete(clusterTemplate.getStackTemplate());
        return clusterTemplate;
    }
}
