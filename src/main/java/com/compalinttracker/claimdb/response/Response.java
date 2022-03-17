package com.compalinttracker.claimdb.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@SuperBuilder       // Builder pattern
@JsonInclude(JsonInclude.Include.NON_NULL)                    // only use values that not null
public class Response {

    protected LocalDateTime timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String reason;            // reason for the error
    protected String message;           // success message, return this message and the user could use it on the front end
    protected String developerMessage;  // more
    protected Map<?, ?> data;
}
