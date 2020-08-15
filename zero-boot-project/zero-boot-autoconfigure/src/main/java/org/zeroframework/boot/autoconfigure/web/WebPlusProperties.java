package org.zeroframework.boot.autoconfigure.web;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = WebPlusProperties.WEB_PLUS_PREFIX)
public class WebPlusProperties {
    public static final String WEB_PLUS_PREFIX = "zero.boot.web";

    private Boolean enabled = false;

    private Boolean webLog = false;

    private Boolean controllerAdvice = false;

}
