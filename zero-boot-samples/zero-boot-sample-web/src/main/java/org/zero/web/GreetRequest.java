package org.zero.web;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class GreetRequest implements Serializable {
    private long id;
    @NotNull
    private String content;
}
