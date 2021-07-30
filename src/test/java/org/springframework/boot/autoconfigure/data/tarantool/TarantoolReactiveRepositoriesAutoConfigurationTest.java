package org.springframework.boot.autoconfigure.data.tarantool;

import io.tarantool.driver.api.TarantoolClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.data.tarantool.emtpy.EmptyUser;
import org.springframework.boot.autoconfigure.data.tarantool.support.TestAutoConfigurationPackage;
import org.springframework.boot.autoconfigure.data.tarantool.user.ReactiveUserRepository;
import org.springframework.boot.autoconfigure.data.tarantool.user.User;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.tarantool.core.mapping.TarantoolMappingContext;
import org.springframework.data.tarantool.repository.ReactiveTarantoolRepository;
import org.springframework.data.tarantool.repository.config.EnableReactiveTarantoolRepositories;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TarantoolReactiveRepositoriesAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(
                    TarantoolReactiveAutoConfiguration.class,
                    TarantoolReactiveDataAutoConfiguration.class,
                    TarantoolReactiveRepositoriesAutoConfiguration.class));

    @Test
    @SuppressWarnings("unchecked")
    void shouldUseDefaultRepositoryConfiguration() {
        contextRunner.withUserConfiguration(TestConfiguration.class).run((context) -> {
            assertThat(context).hasSingleBean(TarantoolClient.class);
            assertThat(context).hasSingleBean(ReactiveUserRepository.class);

            TarantoolMappingContext mappingContext = context.getBean(TarantoolMappingContext.class);
            Set<? extends Class<?>> entities = (Set<? extends Class<?>>) ReflectionTestUtils.getField(mappingContext, "initialEntitySet");
            assertThat(entities).hasSize(1);
        });
    }

    @Test
    void shouldNotUseDefaultReactiveRepositoryConfiguration() {
        contextRunner.withUserConfiguration(TestConfiguration.class)
                .withPropertyValues("spring.data.tarantool.repositories.type=imperative")
                .run((context) -> {
                    assertThat(context).hasSingleBean(TarantoolClient.class);
                    assertThat(context).doesNotHaveBean(ReactiveUserRepository.class);
                });
    }

    @Test
    void shouldNotUseDefaultRepositoryConfiguration() {
        contextRunner.withUserConfiguration(TestConfiguration.class)
                .withPropertyValues("spring.data.tarantool.repositories.type=none")
                .run((context) -> {
                    assertThat(context).hasSingleBean(TarantoolClient.class);
                    assertThat(context).doesNotHaveBean(ReactiveUserRepository.class);
                });
    }

    @Test
    void shouldNotUseDefaultRepositoryConfigurationWithoutDeps() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(TarantoolReactiveRepositoriesAutoConfiguration.class));

        contextRunner.withUserConfiguration(TestConfiguration.class)
                .run((context) -> {
                    assertThat(context).doesNotHaveBean(TarantoolClient.class);
                    assertThat(context).doesNotHaveBean(ReactiveUserRepository.class);
                });
    }

    @Test
    void shouldUseEmptyConfiguration() {
        contextRunner.withUserConfiguration(EmptyConfiguration.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(TarantoolClient.class);
                    assertThat(context).doesNotHaveBean(ReactiveTarantoolRepository.class);
                });
    }

    @Test
    void shouldUseCustomizedConfiguration() {
        contextRunner.withUserConfiguration(CustomizedConfiguration.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(TarantoolClient.class);
                    assertThat(context).hasSingleBean(ReactiveUserRepository.class);
                });
    }

    @Test
    void shouldUseInvalidCustomConfiguration() {
        contextRunner.withUserConfiguration(InvalidCustomConfiguration.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(TarantoolClient.class);
                    assertThat(context).doesNotHaveBean(ReactiveUserRepository.class);
                });
    }

    @Configuration(proxyBeanMethods = false)
    @TestAutoConfigurationPackage(User.class)
    static class TestConfiguration {
    }

    @Configuration(proxyBeanMethods = false)
    @TestAutoConfigurationPackage(EmptyUser.class)
    static class EmptyConfiguration {
    }

    @Configuration(proxyBeanMethods = false)
    @EnableReactiveTarantoolRepositories(basePackageClasses = ReactiveUserRepository.class)
    static class CustomizedConfiguration {
    }

    @Configuration(proxyBeanMethods = false)
    @EnableReactiveTarantoolRepositories("foo.bar")
    static class InvalidCustomConfiguration {
    }
}