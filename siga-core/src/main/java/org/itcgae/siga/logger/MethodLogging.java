package org.itcgae.siga.logger;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RUNTIME)
public @interface MethodLogging {
    
    boolean showArgs() default true;
    
    boolean showReturnValue() default true;

}