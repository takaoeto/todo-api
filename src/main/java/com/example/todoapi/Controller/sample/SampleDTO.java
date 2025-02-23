package com.example.todoapi.Controller.sample;

import java.time.LocalDateTime;
import lombok.Value;


@Value
public class SampleDTO {

    String content;
    LocalDateTime timestamp;

    
}
