package com.starter.autoconfiguration;

import com.starter.FormatTemplate;
import com.starter.format.FormatProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(FormatAutoConfiguration.class)
@EnableConfigurationProperties(DemoProperties.class)
@Configuration
public class DemoAutoConfiguration {

    @Bean
    public FormatTemplate formatTemplate(DemoProperties demoProperties, FormatProcessor formatProcessor){
        return new FormatTemplate(demoProperties,formatProcessor);
    }

}
