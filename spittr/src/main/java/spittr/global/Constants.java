package spittr.global;

import spittr.domain.OAuth2Client;
import spittr.domain.Role;

/**
 * Created by 273cn on 16/12/24.
 */
public class Constants {
    // query constants
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "20";

    // security constants
    public static final String REALM = "spittr";
    public static final String RESOURCE_ID = "spittr_rest_resource";

    // authenticated clients
    public static final OAuth2Client[] O_AUTH2_CLIENTS = {
        new OAuth2Client(
                "ios",
                "ios_secret",
                new String[]{"password", "authorization_code", "refresh_token", "implicit"},
                new String[]{"CLIENT", "USER"},
                new String[]{"read", "write", "trust"},
                120,
                600),
        new OAuth2Client(
                "android",
                "android_secret",
                new String[]{"password", "refresh_token"},
                new String[]{"CLIENT"},
                new String[]{"read", "write"},
                300,
                600)
    };
}
