package com.sequenceiq.cloudbreak.api.model.environment.request;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.sequenceiq.cloudbreak.doc.ModelDescriptions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class RegisterDatalakeRequest {
    @ApiModelProperty(ModelDescriptions.ClusterModelDescription.LDAP_CONFIG_NAME)
    @NotEmpty
    private String ldapName;

    @ApiModelProperty(ModelDescriptions.ClusterModelDescription.RDSCONFIG_NAMES)
    @NotEmpty
    private Set<String> rdsNames;

    @ApiModelProperty(ModelDescriptions.ClusterModelDescription.KERBEROSCONFIG_NAME)
    private String kerberosName;

    @NotEmpty
    private String rangerAdminPassword;

    public String getLdapName() {
        return ldapName;
    }

    public void setLdapName(String ldapName) {
        this.ldapName = ldapName;
    }

    public Set<String> getRdsNames() {
        return rdsNames;
    }

    public void setRdsNames(Set<String> rdsNames) {
        this.rdsNames = rdsNames;
    }

    public String getKerberosName() {
        return kerberosName;
    }

    public void setKerberosName(String kerberosName) {
        this.kerberosName = kerberosName;
    }

    public String getRangerAdminPassword() {
        return rangerAdminPassword;
    }

    public void setRangerAdminPassword(String rangerAdminPassword) {
        this.rangerAdminPassword = rangerAdminPassword;
    }
}
