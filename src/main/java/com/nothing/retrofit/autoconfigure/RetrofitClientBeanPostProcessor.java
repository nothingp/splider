package com.nothing.retrofit.autoconfigure;

import com.nothing.retrofit.annotation.RetrofitClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import retrofit2.Retrofit;

import java.util.Set;

/**
 * Concrete implementation of {@link InstantiationAwareBeanPostProcessor} to use
 * the before-instantiation callback. The callback is required to enable creation
 * and usage of implementations of Retrofit clients for instantiation.
 * <p>
 * The post-processor uses a {@link Retrofit} bean and hence the choice of
 * instantiation-aware post processor than a regular {@link BeanPostProcessor}
 * <p>
 * Extends from the base class {@link InstantiationAwareBeanPostProcessorAdapter}
 * which has no-ops defaults.
 *
 * @author sriv-priyank
 */
class RetrofitClientBeanPostProcessor
        extends InstantiationAwareBeanPostProcessorAdapter
        implements BeanFactoryAware {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private BeanFactory beanFactory;
    public static final String BEAN_NAME = "retrofitClientBeanPostProcessor";

    private final Set<String> packageNames;

    public RetrofitClientBeanPostProcessor(Set<String> packageNames) {
        this.packageNames = packageNames;
    }

    /**
     * Registers {@link RetrofitClientBeanPostProcessor} with the bean registry
     * @param registry bean definition registry
     */
    public static void register(BeanDefinitionRegistry registry,
                                Set<String> packageNames) {

        if (!registry.containsBeanDefinition(BEAN_NAME))  {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(RetrofitClientBeanPostProcessor.class);

            beanDefinition.getConstructorArgumentValues()
                    .addIndexedArgumentValue(0, packageNames);

            beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            beanDefinition.setSynthetic(true);
            registry.registerBeanDefinition(BEAN_NAME, beanDefinition);
        }
    }


    /**
     * @param beanFactory owning beanFactory
     * @throws BeansException
     * @see BeanFactoryAware#setBeanFactory(BeanFactory)
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    /**
     * Before-instantiation callback that handles the creation of Retrofit client
     * implementations; returns the instance of the new bean created for classes
     * annotated with {@link RetrofitClient}.
     *
     * @param beanClass the class of the bean to be instantiated
     * @param beanName the name of the bean
     * @return the bean object to expose instead of a default instance of the target bean,
     * or {@code null} to proceed with default instantiation
     * @throws BeansException
     * @see InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation(Class, String)
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName)
            throws BeansException {

        logger.trace("Bean class {}, Bean name: {}", beanClass, beanName);
        if (packageNames.contains(beanClass.getPackage().getName()) &&
                beanClass.isAnnotationPresent(RetrofitClient.class))
        {
            logger.debug("Found RetrofitClient {}", beanClass);

            Retrofit retrofit = beanFactory.getBean(Retrofit.class);
            return createClientInstance(beanClass, retrofit);
        }
        return null;
    }

    /**
     * Generic implementation for creating implementation of Retrofit clients.
     * @see Retrofit#create(Class)
     */
    private <T> T createClientInstance(Class<T> beanClass, Retrofit retrofit) {
        logger.trace("Invoking retrofit#create on bean {}", beanClass);
        return retrofit.create(beanClass);
    }
}
