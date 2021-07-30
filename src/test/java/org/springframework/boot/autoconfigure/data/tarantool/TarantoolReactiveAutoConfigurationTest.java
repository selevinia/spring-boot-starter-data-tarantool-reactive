package org.springframework.boot.autoconfigure.data.tarantool;

import io.tarantool.driver.ClusterTarantoolClient;
import io.tarantool.driver.ProxyTarantoolClient;
import io.tarantool.driver.TarantoolClientConfig;
import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.api.TarantoolResult;
import io.tarantool.driver.api.tuple.TarantoolTuple;
import io.tarantool.driver.auth.SimpleTarantoolCredentials;
import io.tarantool.driver.core.TarantoolConnectionSelectionStrategies;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.tarantool.config.client.DefaultTarantoolClientFactory;
import org.springframework.data.tarantool.config.client.DefaultTarantoolClientOptions;
import org.springframework.data.tarantool.config.client.TarantoolClientFactory;
import org.springframework.data.tarantool.config.client.TarantoolClientOptions;

import static org.assertj.core.api.Assertions.assertThat;

public class TarantoolReactiveAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(TarantoolReactiveAutoConfiguration.class));

    @Test
    void shouldCreateClient() {
        contextRunner.run((context) -> assertThat(context).hasSingleBean(TarantoolClient.class));
    }

    @Test
    void shouldNotCreateClientIfAutoConfigurationOff() {
        ApplicationContextRunner runner = new ApplicationContextRunner();
        runner.run((context) -> assertThat(context).doesNotHaveBean(TarantoolClient.class));
    }

    @Test
    void shouldCreateFallbackClient() {
        contextRunner.withUserConfiguration(FallbackTarantoolClientConfig.class)
                .run((context) -> assertThat(context).hasSingleBean(TarantoolClient.class));
    }

    @Test
    void shouldCreateClientWithDefaultProperties() {
        contextRunner.run((context) -> {
            TarantoolProperties defaultProperties = new TarantoolProperties();

            TarantoolClient<?, ?> client = context.getBean(TarantoolClient.class);
            assertThat(client).isInstanceOf(ProxyTarantoolClient.class);

            TarantoolClientConfig clientConfig = client.getConfig();
            assertThat(clientConfig).isNotNull();
            assertThat(clientConfig.getCredentials().getUsername()).isEqualTo(defaultProperties.getUserName());
            assertThat(((SimpleTarantoolCredentials) clientConfig.getCredentials()).getPassword()).isEqualTo(defaultProperties.getPassword());
            assertThat(clientConfig.getConnections()).isEqualTo(defaultProperties.getConnections());
            assertThat(clientConfig.getConnectTimeout()).isEqualTo(defaultProperties.getConnectTimeout().toMillis());
            assertThat(clientConfig.getReadTimeout()).isEqualTo(defaultProperties.getReadTimeout().toMillis());
            assertThat(clientConfig.getRequestTimeout()).isEqualTo(defaultProperties.getRequestTimeout().toMillis());
            assertThat(clientConfig.getConnectionSelectionStrategyFactory()).isEqualTo(TarantoolConnectionSelectionStrategies.ParallelRoundRobinStrategyFactory.INSTANCE);
        });
    }

    @Test
    void shouldCreateClientWithChangedProperties() {
        contextRunner.withPropertyValues(
                "spring.data.tarantool.user-name=admin",
                "spring.data.tarantool.password=admin",
                "spring.data.tarantool.connections=10",
                "spring.data.tarantool.connect-timeout=20",
                "spring.data.tarantool.read-timeout=30ms",
                "spring.data.tarantool.request-timeout=40s",
                "spring.data.tarantool.cluster=false",
                "spring.data.tarantool.crud=false"
        ).run((context) -> {
            TarantoolClient<?, ?> client = context.getBean(TarantoolClient.class);
            assertThat(client).isInstanceOf(ClusterTarantoolClient.class);

            TarantoolClientConfig clientConfig = client.getConfig();
            assertThat(clientConfig).isNotNull();
            assertThat(clientConfig.getCredentials().getUsername()).isEqualTo("admin");
            assertThat(((SimpleTarantoolCredentials) clientConfig.getCredentials()).getPassword()).isEqualTo("admin");
            assertThat(clientConfig.getConnections()).isEqualTo(10);
            assertThat(clientConfig.getConnectTimeout()).isEqualTo(20);
            assertThat(clientConfig.getReadTimeout()).isEqualTo(30);
            assertThat(clientConfig.getRequestTimeout()).isEqualTo(40000);
            assertThat(clientConfig.getConnectionSelectionStrategyFactory()).isEqualTo(TarantoolConnectionSelectionStrategies.RoundRobinStrategyFactory.INSTANCE);
        });
    }

    @Configuration(proxyBeanMethods = false)
    static class FallbackTarantoolClientConfig {

        @Bean
        TarantoolClient<TarantoolTuple, TarantoolResult<TarantoolTuple>> fallbackTarantoolClient() {
            TarantoolClientOptions tarantoolClientOptions = new DefaultTarantoolClientOptions();
            TarantoolClientFactory tarantoolClientFactory = new DefaultTarantoolClientFactory(tarantoolClientOptions);
            return tarantoolClientFactory.createClient();
        }
    }
}