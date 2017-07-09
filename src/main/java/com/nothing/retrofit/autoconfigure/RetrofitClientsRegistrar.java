package com.nothing.retrofit.autoconfigure;

import com.nothing.retrofit.annotation.RetrofitClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Concrete implementation of {@link ImportBeanDefinitionRegistrar}
 * used by {@link RetrofitClient} to auto-configure Retrofit client beans.
 *
 * @author PriyankS
 * @see ImportBeanDefinitionRegistrar
 */
public class RetrofitClientsRegistrar implements ImportBeanDefinitionRegistrar {

    private Logger logger = LoggerFactory.getLogger(RetrofitClientsRegistrar.class);

    /**
     * Registers {@link BeanPostProcessor}s and {@link RetrofitClient} annotated
     * interfaces (beans).
     *
     * @param aMetadata annotation metadata of the importing class
     * @param registry current bean definition registry
     * @see ImportBeanDefinitionRegistrar#registerBeanDefinitions(AnnotationMetadata, BeanDefinitionRegistry)
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata aMetadata,
                                        BeanDefinitionRegistry registry) {

        Set<String> packagesToScan = packagesToScan(aMetadata);
        register(clientBeanDefinitions(packagesToScan), registry);

        RetrofitClientBeanPostProcessor.register(registry, packagesToScan);
    }


    /*
     * Registers beans by scanning relevant packages for Retrofit clients.
     * <p>
     * Note: <code>RetrofitClientsComponentProvider</code> provides filters to
     * exclude classes and register only interfaces.
     */
    private Set<BeanDefinition> clientBeanDefinitions(Set<String> packageNames)
    {
        logger.trace("Base packages: {}", packageNames);

        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
        RetrofitClientsComponentScanner scanner = new RetrofitClientsComponentScanner();

        for (String packageName: packageNames) {
            logger.debug("Scanning package {}", packageName);
            beanDefinitions.addAll(scanner.findCandidateComponents(packageName));
        }

        return beanDefinitions;
    }


    private void register(Set<BeanDefinition> beanDefinitions,
                          BeanDefinitionRegistry registry) {

        BeanNameGenerator bng = new DefaultBeanNameGenerator();
        for (BeanDefinition candidate : beanDefinitions) {
            String beanName = bng.generateBeanName(candidate, registry);
            logger.info("Registering client {}, beanName {}", candidate, beanName);

            registry.registerBeanDefinition(beanName, candidate);
        }
    }


    /*
     * Gets a list of base packages to scout for interfaces from the annotation metadata.
     */
    private Set<String> packagesToScan(AnnotationMetadata metadata) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata
                .getAnnotationAttributes(RetrofitClientsScan.class.getName()));

        String basePackagesAttribute = "basePackages";
        String[] basePackages = attributes.getStringArray(basePackagesAttribute);

        logger.debug("Configured in basePackages() - {}", Arrays.toString(basePackages));

        if (basePackages.length < 1) {
            return Collections.singleton(ClassUtils
                    .getPackageName(metadata.getClassName()));
        }
        else {
            return Arrays.asList(basePackages).stream()
                    .filter(StringUtils::hasText)
                    .collect(Collectors.toSet());
        }
    }


    /**
     * {@link ClassPathScanningCandidateComponentProvider} implementation to
     * provide {@link BeanDefinition} candidates.
     *
     * @author PriyankS
     */
    static final class RetrofitClientsComponentScanner
            extends ClassPathScanningCandidateComponentProvider {

        Logger logger = LoggerFactory.getLogger(RetrofitClientsComponentScanner.class);

        /**
         * Instantiation; excludes "default filters" by passing a {@code false} flag to parent class constructor
         * {@link ClassPathScanningCandidateComponentProvider#ClassPathScanningCandidateComponentProvider(boolean)}
         * <p>
         * Adds custom filter {@link AnnotationTypeFilter} to filter classes by annotation
         */
        RetrofitClientsComponentScanner() {
            super(false);
            TypeFilter filter = new AnnotationTypeFilter(RetrofitClient.class, true, true);

            logger.trace("Adding annotation type filter for RetrofitClient");
            addIncludeFilter(filter);
        }

        /**
         * Determines if a class found during classpath scanning is an interface.
         * Classes are skipped as Retrofit clients cannot be concrete classes.
         *
         * @param beanDefinition {@link BeanDefinition} with annotation metadata
         * @return boolean ({@code true} if bean represents an interface)
         *
         * @see super#findCandidateComponents(String)
         */
        @Override
        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition)
        {
            Objects.requireNonNull(beanDefinition);
            logger.trace("isCandidateComponent for bean {}",
                    beanDefinition.getBeanClassName());

            AnnotationMetadata aMetadata = beanDefinition.getMetadata();
            return aMetadata.isIndependent() && aMetadata.isInterface();
        }
    }
}
