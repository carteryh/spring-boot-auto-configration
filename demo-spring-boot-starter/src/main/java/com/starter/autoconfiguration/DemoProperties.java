package com.starter.autoconfiguration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix= DemoProperties.FORMAT_PREFIX)
public class DemoProperties {

    public static final String FORMAT_PREFIX="format";

    private String style;

    private String code;

    private String name;

}
