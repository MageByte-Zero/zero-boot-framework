package org.zero.web;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zeroframework.boot.exception.BaseException;
import org.zeroframework.boot.message.ResultDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public ResultDTO<Greeting> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
        return ResultDTO.success(greeting);
    }

    @GetMapping("/base-exception")
    public ResultDTO<Greeting> baseException() {
        throw new BaseException("出错啦");
    }

    @GetMapping("/exception-test")
    public ResultDTO<Greeting> exception() throws Exception {
        throw new Exception("出错啦");
    }

    @GetMapping("/exception-null")
    public ResultDTO<Long> nullException() throws Exception {
        Greeting greeting = null;
        return ResultDTO.success(greeting.getId());
    }

    @GetMapping("/sql-exception")
    public ResultDTO<String> sqlException() throws Exception {
        if (true) {
            throw new SQLException("数据库执行错误");
        }
        return ResultDTO.success();
    }

    @GetMapping("/methodArgumentNotValidException")
    public ResultDTO<String> methodArgumentNotValidException(@Validated GreetRequest greetRequest) throws Exception {
        return ResultDTO.success();
    }

    @GetMapping("/date")
    public ResultDTO<DateDTO> dateTimeResultDTO(@RequestParam LocalDate localDate, @RequestParam LocalDateTime localDateTime
            , @RequestParam LocalTime localTime, @RequestParam Date date) {
        DateDTO dateDTO = new DateDTO();
        dateDTO.setDate(date);
        dateDTO.setLocalDate(localDate);
        dateDTO.setLocalDateTime(localDateTime);
        dateDTO.setLocalTime(localTime);

        return ResultDTO.success(dateDTO);
    }

    @PostMapping("/date")
    public ResultDTO<DateDTO> postDate(@RequestBody DateDTO dateDTO) {
        return ResultDTO.success(dateDTO);
    }

}