package com.hhplusconcert.common.annotation;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

@Target({METHOD, TYPE})
public @interface QueueCheckAnnotation {
}
