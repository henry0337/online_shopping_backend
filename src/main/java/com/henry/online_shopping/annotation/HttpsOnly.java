package com.henry.online_shopping.annotation;

import com.henry.online_shopping.utility.constant.Profiles;
import org.springframework.context.annotation.Profile;

import java.lang.annotation.*;

/**
 * Chú thích rằng lớp hoặc phương thức nào đó được đánh dấu chỉ có tác dụng trên môi trường <b>HTTPS</b>.
 */
@Profile({Profiles.HTTPS})
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpsOnly {}
