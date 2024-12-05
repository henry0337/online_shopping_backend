package com.henry.online_shopping.constant;

import io.swagger.v3.oas.models.OpenAPI;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants belong to {@link OpenAPI} configuration.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OpenAPIConstant {

    /**
     * The <b>OpenAPI Specification</b> version you want to use.
     * @see <a href="https://github.com/OAI/OpenAPI-Specification/tree/main/versions">OpenAPI Specification (OAS) Version</a>
     */
    public static final String OPENAPI_VERSION = "3.1.1";

    /**
     * The name about your current API.
     */
    public static final String TITLE = "Online Shopping";

    /**
     * The version of the current API.
     */
    public static final String API_VERSION = "1.0";
}
