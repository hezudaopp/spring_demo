package spittr.domain;

/**
 * Created by 273cn on 16/12/24.
 */
public class OAuth2Client {
    private String id;
    private String secret;
    private String[] grantTypes;
    private String[] roles;
    private String[] scopes;
    private int accessTokenValidSeconds;
    private int refreshTokenValidSeconds;

    public OAuth2Client(String id, String secret, String[] grantTypes, String[] roles, String[] scopes, int accessTokenValidSeconds, int refreshTokenValidSeconds) {
        this.id = id;
        this.secret = secret;
        this.grantTypes = grantTypes;
        this.roles = roles;
        this.scopes = scopes;
        this.accessTokenValidSeconds = accessTokenValidSeconds;
        this.refreshTokenValidSeconds = refreshTokenValidSeconds;
    }

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public String[] getGrantTypes() {
        return grantTypes;
    }

    public String[] getRoles() {
        return roles;
    }

    public String[] getScopes() {
        return scopes;
    }

    public int getAccessTokenValidSeconds() {
        return accessTokenValidSeconds;
    }

    public int getRefreshTokenValidSeconds() {
        return refreshTokenValidSeconds;
    }
}
