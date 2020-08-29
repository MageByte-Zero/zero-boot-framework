package org.zeroframework.boot.autoconfigure.sequence;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zeroframework.boot.sequence.leaf.exception.InitException;
import org.zeroframework.boot.sequence.leaf.facade.SegmentSequenceGen;
import org.zeroframework.boot.sequence.leaf.facade.SnowflakeSequenceGen;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = SequenceProperties.SEQUENCE_PREFIX, name = "enabled", havingValue = "true")
@EnableConfigurationProperties(value = {SequenceProperties.class})
public class SequenceAutoConfiguration {

    @Autowired
    private SequenceProperties properties;

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = SequenceProperties.SEQUENCE_PREFIX + ".segment", name = "enabled", havingValue = "true")
    public SegmentSequenceGen segmentSequenceGen() throws Exception {
        SegmentSequenceGen segmentSequenceGen = new SegmentSequenceGen(properties.getSegment().getUrl()
                , properties.getSegment().getUsername(), properties.getSegment().getPassword());
        log.info("zero-boot-starter-sequence create segmentSequenceGen success, properties = {}", JSON.toJSONString(properties));
        return segmentSequenceGen;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = SequenceProperties.SEQUENCE_PREFIX + ".snowflake", name = "enabled", havingValue = "true")
    public SnowflakeSequenceGen snowflakeSequenceGen() throws InitException {
        SnowflakeSequenceGen snowflakeSequenceGen = new SnowflakeSequenceGen(properties.getSnowflake().getAddress()
                , properties.getSnowflake().getPort());
        log.info("zero-boot-starter-sequence create snowflakeSequenceGen success, properties = {}", JSON.toJSONString(properties));
        return snowflakeSequenceGen;
    }
}
