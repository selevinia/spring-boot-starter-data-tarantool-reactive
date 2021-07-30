package org.springframework.boot.autoconfigure.data.tarantool;

import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.api.TarantoolResult;
import io.tarantool.driver.api.tuple.TarantoolTuple;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.tarantool.config.client.DefaultTarantoolClientFactory;
import org.springframework.data.tarantool.config.client.OperationMappingOptions;
import org.springframework.data.tarantool.config.client.TarantoolClientFactory;
import org.springframework.data.tarantool.config.client.TarantoolClientOptions;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Reactive Tarantool.
 * <p>
 * Registers a {@link TarantoolClient} bean if no other bean of the same type is configured.
 *
 * @author Tatiana Blinova
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({TarantoolClient.class, Flux.class})
@EnableConfigurationProperties(TarantoolProperties.class)
public class TarantoolReactiveAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TarantoolClient<TarantoolTuple, TarantoolResult<TarantoolTuple>> tarantoolClient(TarantoolProperties tarantoolProperties) {
        TarantoolClientOptions tarantoolClientOptions = tarantoolClientOptions(tarantoolProperties);
        TarantoolClientFactory tarantoolClientFactory = new DefaultTarantoolClientFactory(tarantoolClientOptions);
        return tarantoolClientFactory.createClient();
    }

    private TarantoolClientOptions tarantoolClientOptions(TarantoolProperties tarantoolProperties) {
        return new CompositeTarantoolClientOptions() {
            @Override
            public List<String> getNodes() {
                return tarantoolProperties.getNodes();
            }

            @Override
            public String getUserName() {
                return tarantoolProperties.getUserName();
            }

            @Override
            public String getPassword() {
                return tarantoolProperties.getPassword();
            }

            @Override
            public int getConnections() {
                return tarantoolProperties.getConnections();
            }

            @Override
            public int getConnectTimeout() {
                return (int) tarantoolProperties.getConnectTimeout().toMillis();
            }

            @Override
            public int getReadTimeout() {
                return (int) tarantoolProperties.getReadTimeout().toMillis();
            }

            @Override
            public int getRequestTimeout() {
                return (int) tarantoolProperties.getRequestTimeout().toMillis();
            }

            @Override
            public boolean isCluster() {
                return tarantoolProperties.isCluster();
            }

            @Override
            public boolean isCrudAvailable() {
                return tarantoolProperties.isCrud();
            }

            @Override
            public String getGetSchemaFunctionName() {
                return tarantoolProperties.getProxyOperations() != null ? tarantoolProperties.getProxyOperations().getGetSchemaFunctionName() : null;
            }

            @Override
            public String getDeleteFunctionName() {
                return tarantoolProperties.getProxyOperations() != null ? tarantoolProperties.getProxyOperations().getDeleteFunctionName() : null;
            }

            @Override
            public String getInsertFunctionName() {
                return tarantoolProperties.getProxyOperations() != null ? tarantoolProperties.getProxyOperations().getInsertFunctionName() : null;
            }

            @Override
            public String getReplaceFunctionName() {
                return tarantoolProperties.getProxyOperations() != null ? tarantoolProperties.getProxyOperations().getReplaceFunctionName() : null;
            }

            @Override
            public String getUpdateFunctionName() {
                return tarantoolProperties.getProxyOperations() != null ? tarantoolProperties.getProxyOperations().getUpdateFunctionName() : null;
            }

            @Override
            public String getUpsertFunctionName() {
                return tarantoolProperties.getProxyOperations() != null ? tarantoolProperties.getProxyOperations().getUpsertFunctionName() : null;
            }

            @Override
            public String getSelectFunctionName() {
                return tarantoolProperties.getProxyOperations() != null ? tarantoolProperties.getProxyOperations().getSelectFunctionName() : null;
            }
        };
    }

    private interface CompositeTarantoolClientOptions extends TarantoolClientOptions, OperationMappingOptions {
    }
}
