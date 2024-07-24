package com.turfnovo;

public interface ApiPathConstants {
    String BASE = "/api";

    // Auth
    String AUTH = ApiPathConstants.BASE + "/auth";
    String SIGN_UP = ApiPathConstants.AUTH + "/signup";
    String SIGN_IN = ApiPathConstants.AUTH + "/signin";

    // User
    String USER = ApiPathConstants.BASE + "/user";
    String UPDATE_USER = ApiPathConstants.USER;
    String USER_GET_DETAILS = ApiPathConstants.USER;

    // Turf
    String TURF_GET_ALL = ApiPathConstants.BASE + "/turfs";
    String TURF_GET = ApiPathConstants.TURF_GET_ALL + "/{id}";

    // Turf Owners
    String TURF_OWNER = ApiPathConstants.BASE + "/owner/turfs";
    String TURF_CREATE = ApiPathConstants.TURF_OWNER;
    String TURF_UPDATE = ApiPathConstants.TURF_OWNER + "/{id}";;
    String TURF_DELETE = ApiPathConstants.TURF_UPDATE;

    // Swagger
    String SWAGGER_HTML = "/swagger-ui.html";
    String SWAGGER_WEB_JARS = "/webjars/swagger-ui/**";
    String SWAGGER_UI = "/swagger-ui/**";
    String SWAGGER_API_DOCS_CONFIG = "/v3/api-docs/swagger-config";
    String SWAGGER_API_DOCS = "/v3/api-docs";
}
