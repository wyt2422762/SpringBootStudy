package com.wyt.study.anno;

import com.wyt.study.util.CusBeanRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(CusBeanRegistrar.class)
public @interface WytScan {
    String value() default "";
}
