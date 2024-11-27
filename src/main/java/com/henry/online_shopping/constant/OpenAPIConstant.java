package com.henry.online_shopping.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants belong to <b>OpenAPI</b> configuration.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OpenAPIConstant {

    /**
     * The <b>OpenAPI Specification</b> version you want to use.
     * @see <a href="https://github.com/OAI/OpenAPI-Specification/tree/main/versions">OpenAPI Specification (OAS) Version</a>
     */
    public static final String VERSION = "3.1.1";

    /**
     * The name about your current API.
     */
    public static final String TITLE = "Online Shopping";

    /**
     * The version of the current API.
     */
    public static final String API_VERSION = "1.0";
}
