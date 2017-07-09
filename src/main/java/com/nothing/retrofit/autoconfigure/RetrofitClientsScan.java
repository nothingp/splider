package com.nothing.retrofit.autoconfigure;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Annotation to enable scanning of Retrofit clients in the classpath. It provides an
 * alternative to manually configuring beans for the clients by creating implementation
 * for them via {@link retrofit2.Retrofit#create(Class)}
 * <p>
 * {@link #basePackages()} may be specified to define specific packages to scan. If
 * specific packages are not defined scanning will occur from the package of the
 * class with this annotation.
 *
 * @author PriyankS
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(RetrofitClientsRegistrar.class)
public @interface RetrofitClientsScan {

    /**
     * Base packages to scan for annotated entities.
     * @return the base packages to scan
     */
    String[] basePackages() default {};
}
