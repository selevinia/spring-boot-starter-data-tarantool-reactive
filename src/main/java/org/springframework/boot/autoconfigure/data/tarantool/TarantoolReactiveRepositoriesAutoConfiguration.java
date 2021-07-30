package org.springframework.boot.autoconfigure.data.tarantool;

import io.tarantool.driver.api.TarantoolClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.tarantool.core.ReactiveTarantoolTemplate;
import org.springframework.data.tarantool.repository.ReactiveTarantoolRepository;
import org.springframework.data.tarantool.repository.config.EnableReactiveTarantoolRepositories;
import org.springframework.data.tarantool.repository.config.ReactiveTarantoolRepositoryConfigurationExtension;
import org.springframework.data.tarantool.repository.support.ReactiveTarantoolRepositoryFactoryBean;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's Tarantool Reactive Repositories.
 * <p>
 * Activates when there is no bean of type {@link ReactiveTarantoolRepositoryFactoryBean}
 * configured in the context, the Spring Data Tarantool {@link ReactiveTarantoolRepository} type
 * is on the classpath, the tarantool driver API is on the classpath,
 * and there is no other configured {@link ReactiveTarantoolRepository}.
 * <p>
 * Once in effect, the auto-configuration is the equivalent of enabling Tarantool repositories
 * using the {@link EnableReactiveTarantoolRepositories @EnableReactiveTarantoolRepositories}
 * annotation.
 *
 * @author Tatiana Blinova
 * @see EnableReactiveTarantoolRepositories
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({TarantoolClient.class, ReactiveTarantoolRepository.class})
@ConditionalOnBean(ReactiveTarantoolTemplate.class)
@ConditionalOnMissingBean({ReactiveTarantoolRepositoryFactoryBean.class, ReactiveTarantoolRepositoryConfigurationExtension.class})
@ConditionalOnRepositoryType(store = "tarantool", type = RepositoryType.REACTIVE)
@Import(TarantoolReactiveRepositoriesRegistrar.class)
@AutoConfigureAfter(TarantoolReactiveDataAutoConfiguration.class)
public class TarantoolReactiveRepositoriesAutoConfiguration {
}
