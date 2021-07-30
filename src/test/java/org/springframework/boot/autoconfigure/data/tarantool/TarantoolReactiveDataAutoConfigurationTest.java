package org.springframework.boot.autoconfigure.data.tarantool;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.data.tarantool.core.ReactiveTarantoolTemplate;
import org.springframework.data.tarantool.core.TarantoolExceptionTranslator;
import org.springframework.data.tarantool.core.convert.TarantoolConverter;
import org.springframework.data.tarantool.core.convert.TarantoolCustomConversions;
import org.springframework.data.tarantool.core.mapping.TarantoolMappingContext;

import static org.assertj.core.api.Assertions.assertThat;

public class TarantoolReactiveDataAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(
                    TarantoolReactiveAutoConfiguration.class,
                    TarantoolReactiveDataAutoConfiguration.class));

    @Test
    void shouldCreateTemplate() {
        contextRunner.run((context) -> {
            assertThat(context).hasSingleBean(ReactiveTarantoolTemplate.class);
            assertThat(context).hasSingleBean(TarantoolMappingContext.class);
            assertThat(context).hasSingleBean(TarantoolCustomConversions.class);
            assertThat(context).hasSingleBean(TarantoolConverter.class);
            assertThat(context).hasSingleBean(TarantoolExceptionTranslator.class);
        });
    }

    @Test
    void shouldNotCreateTemplateWithoutDeps() {
        ApplicationContextRunner runner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(TarantoolReactiveDataAutoConfiguration.class));
        runner.run((context) -> assertThat(context).doesNotHaveBean(ReactiveTarantoolTemplate.class));
    }
}