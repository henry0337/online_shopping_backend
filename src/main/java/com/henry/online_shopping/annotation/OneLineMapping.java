package com.henry.online_shopping.annotation;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.annotation.*;

/**
 * Faster declaration for {@link RequestMapping @RequestMapping} method.
 * <p>
 * For example, assume you want declaring a method {@code GET} for route {@code /example}, you can do:
 * <pre><code>
 * {@code @OneLineMapping(method = RequestMethod.GET, url = "/example")}
 * public Example foo() {
 *     // TODO: Do something
 * }
 * </code></pre>
 * It will be the same with this block of code:
 * <pre><code>
 * {@code @GetMapping("/example")}
 * {@code @ResponseStatus(HttpStatus.OK)}
 * {@code @Tag(name = "", description = "")}
 * public Example foo() {
 *     // TODO: Do something
 * }
 * </code></pre>
 *
 * Read the source for more information about how to use it.<br><br>
 * <b>Note:</b> I'm using custom annotations for faster annotation applying, (sometimes) not for optimizing. <br>
 * So if you don't want to use this custom annotation, feel free to create one for your own, or simpler, skipping it :v.
 */
@RequestMapping
@ResponseStatus
@Tag(name = "")
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneLineMapping {

    /**
     * Specify the name for specified route to be displayed in Swagger.
     * @return Custom route name.
     */
    @AliasFor(annotation = Tag.class, attribute = "name")
    String name() default "";

    /**
     * Specify the description for specified route to be displayed in Swagger.
     * @return Custom route description.
     * @apiNote You must specify your route name by providing value for {@link OneLineMapping#name()} first to be working.
     */
    @AliasFor(annotation = Tag.class, attribute = "description")
    String description() default "";

    /**
     * HTTP requesting method for any marked class method.
     * @return HTTP requesting method.
     */
    @AliasFor(annotation = RequestMapping.class, attribute = "method")
    RequestMethod method();

    /**
     * The child URL to be added into controller's main URL. <br>
     * Can be empty.
     * @return The child URL.
     */
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String url() default "";

    /**
     * The HTTP response status for class's marked-method. <br>
     * Default to {@link HttpStatus#OK}.
     * @return HTTP response status.
     */
    @AliasFor(annotation = ResponseStatus.class, attribute = "code")
    HttpStatus status() default HttpStatus.OK;
}
