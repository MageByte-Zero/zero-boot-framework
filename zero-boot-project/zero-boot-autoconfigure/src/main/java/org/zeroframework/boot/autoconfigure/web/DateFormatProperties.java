package org.zeroframework.boot.autoconfigure.web;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author MageByte
 * @date 2020/9/4 17:43
 */
@Setter
@Getter
@ConfigurationProperties(prefix = DateFormatProperties.DATE_FORMAT_PREFIX)
public class DateFormatProperties {

    public static final String DATE_FORMAT_PREFIX = "zero.boot.date";

    private Boolean enabled = false;

    private String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    private String dateFormat = "yyyy-MM-dd";

    private String timeFormat = "HH:mm:ss";
}
