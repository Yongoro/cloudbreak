package com.sequenceiq.cloudbreak.api.endpoint.v4.audits.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.cloudbreak.api.endpoint.v4.common.responses.GeneralListV4Response;
import com.sequenceiq.cloudbreak.api.model.annotations.Immutable;

@Immutable
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuditEventV4Responses extends GeneralListV4Response<AuditEventV4Response> {

    public AuditEventV4Responses(List<AuditEventV4Response> responses) {
        super(responses);
    }
}