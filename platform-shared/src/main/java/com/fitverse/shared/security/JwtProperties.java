package com.fitverse.shared.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fitverse.security.jwt")
public class JwtProperties {

    /**
     * Secret key used for signing JWT tokens. It should be a base64-encoded string.
     */
    private String secret = "ZmFsbGJhY2stZGVmYXVsdC1zZWNyZXQ=";

    /**
     * Issuer claim that is embedded in every generated token.
     */
    private String issuer = "fitverse-platform";

    /**
     * Token validity in seconds (default 2 hours) when not overridden via configuration.
     */
    private long accessTokenValiditySeconds = 7200L;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public long getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(long accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }
}
