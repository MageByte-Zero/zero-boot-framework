package org.zero.web;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author lijianqing
 * @date 2020/9/9 15:53
 */
@Data
public class DateDTO implements Serializable {

    private LocalDate localDate;

    private LocalDateTime localDateTime;
    private LocalTime localTime;
    private Date date;
}
