package com.sequenceiq.cloudbreak.service.cluster.clouderamanager;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.domain.stack.Stack;
import com.sequenceiq.cloudbreak.domain.stack.cluster.host.HostGroup;
import com.sequenceiq.cloudbreak.domain.stack.cluster.host.HostMetadata;
import com.sequenceiq.cloudbreak.service.CloudbreakException;
import com.sequenceiq.cloudbreak.service.cluster.api.ClusterModificationService;

@Service
public class ClouderaManagerModificationService implements ClusterModificationService {

    @Override
    public void upscaleCluster(Stack stack, HostGroup hostGroup, Collection<HostMetadata> hostMetadata) throws CloudbreakException {

    }

    @Override
    public void stopCluster(Stack stack) throws CloudbreakException {

    }

    @Override
    public int startCluster(Stack stack) throws CloudbreakException {
        return 0;
    }

    @Override
    public Map<String, String> gatherInstalledComponents(Stack stack, String hostname) {
        return Map.of();
    }

    @Override
    public void stopComponents(Stack stack, Map<String, String> components, String hostname) throws CloudbreakException {

    }

    @Override
    public void ensureComponentsAreStopped(Stack stack, Map<String, String> components, String hostname) throws CloudbreakException {

    }

    @Override
    public void initComponents(Stack stack, Map<String, String> components, String hostname) throws CloudbreakException {

    }

    @Override
    public void installComponents(Stack stack, Map<String, String> components, String hostname) throws CloudbreakException {

    }

    @Override
    public void regenerateKerberosKeytabs(Stack stack, String hostname) throws CloudbreakException {

    }

    @Override
    public void startComponents(Stack stack, Map<String, String> components, String hostname) throws CloudbreakException {

    }
}
