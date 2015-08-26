package com.sequenceiq.cloudbreak.controller.validation;

import com.google.common.base.Optional;

public enum RequiredAzureRmCredentialParam implements TemplateParam {

    SUBSCRIPTION_ID("subscriptionId", true, String.class, Optional.<String>absent()),
    SECRET_KEY("secretKey", true, String.class, Optional.<String>absent()),
    TENANT_ID("tenantId", true, String.class, Optional.<String>absent()),
    ACCES_KEY("accesKey", true, String.class, Optional.<String>absent());


    private final String paramName;
    private final Class clazz;
    private final boolean required;
    private final Optional<String> regex;

    private RequiredAzureRmCredentialParam(String paramName, Boolean required, Class clazz, Optional<String> regex) {
        this.paramName = paramName;
        this.clazz = clazz;
        this.required = required;
        this.regex = regex;
    }

    @Override
    public String getName() {
        return paramName;
    }

    @Override
    public Class getClazz() {
        return clazz;
    }

    @Override
    public Boolean getRequired() {
        return required;
    }

    @Override
    public Optional<String> getRegex() {
        return regex;
    }
}
