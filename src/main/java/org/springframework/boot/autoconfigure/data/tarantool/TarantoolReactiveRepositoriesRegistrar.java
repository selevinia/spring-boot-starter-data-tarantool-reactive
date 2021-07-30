package org.springframework.boot.autoconfigure.data.tarantool;

import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.data.tarantool.repository.config.EnableReactiveTarantoolRepositories;
import org.springframework.data.tarantool.repository.config.ReactiveTarantoolRepositoryConfigurationExtension;

import java.lang.annotation.Annotation;

/**
 * {@link ImportBeanDefinitionRegistrar} used to auto-configure Spring Data Tarantool Reactive Repositories.
 *
 * @author Tatiana Blinova
 */
public class TarantoolReactiveRepositoriesRegistrar extends AbstractRepositoryConfigurationSourceSupport {

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableReactiveTarantoolRepositories.class;
    }

    @Override
    protected Class<?> getConfiguration() {
        return EnableReactiveTarantoolRepositoriesConfiguration.class;
    }

    @Override
    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return new ReactiveTarantoolRepositoryConfigurationExtension();
    }

    @EnableReactiveTarantoolRepositories
    private static class EnableReactiveTarantoolRepositoriesConfiguration {
    }
}
