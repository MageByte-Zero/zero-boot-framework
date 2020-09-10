package org.zeroframework.boot.autoconfigure.web;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author MageByte
 * @date 2020/9/4 18:31
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = DateFormatProperties.DATE_FORMAT_PREFIX, name = "enabled", havingValue = "true")
@EnableConfigurationProperties(DateFormatProperties.class)
public class DateFormatAutoConfiguration {

    @Autowired
    private DateFormatProperties dateFormatProperties;

    /**
     * LocalDate转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        log.info("zero-boot-starter-web 加载了 LocalDate 转换器.");
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                if (StringUtils.isNotBlank(source)) {
                    return LocalDate.parse(source, DateTimeFormatter.ofPattern(dateFormatProperties.getDateFormat()));
                }
                return null;
            }
        };
    }

    /**
     * LocalDateTime转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        log.info("zero-boot-starter-web 加载了 LocalDateTime 转换器.");
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                if (StringUtils.isNotBlank(source)) {
                    return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(dateFormatProperties.getDateTimeFormat()));
                }
                return null;
            }
        };
    }

    /**
     * LocalTime转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        log.info("zero-boot-starter-web 加载了 LocalTime 转换器.");
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                if (StringUtils.isNotBlank(source)) {
                    return LocalTime.parse(source, DateTimeFormatter.ofPattern(dateFormatProperties.getTimeFormat()));
                }
                return null;
            }
        };
    }

    /**
     * Date转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, Date> dateConverter() {
        log.info("zero-boot-starter-web 加载了 Date 转换器.");
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                SimpleDateFormat format = new SimpleDateFormat(dateFormatProperties.getDateTimeFormat());
                try {
                    return format.parse(source);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    /**
     * 定义输入 json 与输出 json的序列化与反序列化
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        log.info("zero-boot-starter-web 加载了 Jackson2ObjectMapperBuilderCustomizer, DateFormatProperties = {}", JSON.toJSONString(dateFormatProperties));

        return builder -> builder
                .simpleDateFormat(dateFormatProperties.getDateTimeFormat())
                .serializers(
                        new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormatProperties.getDateFormat())),
                        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateFormatProperties.getDateTimeFormat())),
                        new LocalTimeSerializer(DateTimeFormatter.ofPattern(dateFormatProperties.getTimeFormat()))
                )
                .deserializers(
                        new LocalDateDeserializer(DateTimeFormatter.ofPattern(dateFormatProperties.getDateFormat())),
                        new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateFormatProperties.getDateTimeFormat())),
                        new LocalTimeDeserializer(DateTimeFormatter.ofPattern(dateFormatProperties.getTimeFormat()))
                )
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

}
