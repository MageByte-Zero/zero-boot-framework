package org.zeroframework.boot.autoconfigure.sequence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = SequenceProperties.SEQUENCE_PREFIX)
public class SequenceProperties {
    public static final String SEQUENCE_PREFIX = "zero.boot.sequence";

    private Boolean enabled = false;

    private Segment segment;

    private Snowflake snowflake;

    @Setter
    @Getter
    public static class Segment {
        private boolean enabled = false;
        private String url;
        private String username;
        private String password;
    }

    @Setter
    @Getter
    public static class Snowflake {
        private boolean enabled = false;
        private String address;
        private int port;
    }
}
