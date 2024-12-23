package com.henry.online_shopping.annotation;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * Faster declaration for {@link RestController @RestController} class.
 * <p>
 * For example, assume you want declaring a class that handle all requests for route {@code /api/v1/example}, you can do:
 * <pre><code>
 * {@code @FasterRestController(url = "/api/v1/example")}
 * public class Example {}
 * </code></pre>
 * It will be the same with this block of code:
 * <pre><code>
 * {@code @RestController}
 * {@code @RequestMapping("/api/v1/example")}
 * {@code @Tag(name = "", description = "")}
 * public class Example {}
 * </code></pre>
 * <br>
 * <b>Note:</b> I'm using custom annotations for faster annotation applying, (sometimes) not for optimizing. <br>
 * So if you don't want to use this custom annotation, feel free creating one for your own, or simpler, skipping it :v.
 */
@RestController
@RequestMapping
@Tag(name = "")
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FasterRestController {

    /**
     * The requesting URL for this controller. <br>
     * Can't be empty.
     * @return The requesting URL.
     */
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String url() default "";

    /**
     * Specify the name for specified controller to be displayed in Swagger. <br>
     * Shouldn't be empty.
     *
     * @return The controller's custom name displaying in Swagger.
     */
    @AliasFor(annotation = Tag.class, attribute = "name")
    String name() default "";

    /**
     * Specify the description for specified controller to be displayed in Swagger.
     * @return Custom controller description.
     * @apiNote You must specify your controller name by providing value for {@link FasterRestController#name()} first to be working.
     */
    @AliasFor(annotation = Tag.class, attribute = "description")
    String description() default "";
}
