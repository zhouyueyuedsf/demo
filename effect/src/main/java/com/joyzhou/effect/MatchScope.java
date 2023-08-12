package com.joyzhou.effect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
public @interface MatchScope {

    /**
     * 指定的匹配值
     *
     * @return
     */
    String[] value() default "";
}