package com.turfnovo.global;

public interface ApiPathConstants {
    String BASE = "/api";

    // Auth
    String AUTH = ApiPathConstants.BASE + "/auth";
    String SIGN_UP = ApiPathConstants.AUTH + "/signup";
    String SIGN_IN = ApiPathConstants.AUTH + "/signin";

    // User
    String USER = ApiPathConstants.BASE + "/user";
    String UPDATE_USER = ApiPathConstants.USER;

    // Swagger
    String SWAGGER_HTML = "/swagger-ui.html";
    String SWAGGER_WEB_JARS = "/webjars/swagger-ui/**";
    String SWAGGER_UI = "/swagger-ui/**";
    String SWAGGER_API_DOCS_CONFIG = "/v3/api-docs/swagger-config";
    String SWAGGER_API_DOCS = "/v3/api-docs";
}
