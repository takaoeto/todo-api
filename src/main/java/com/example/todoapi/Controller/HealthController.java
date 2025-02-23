package com.example.todoapi.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.todoapi.controller.HealthApi;

@RestController
public class HealthController implements HealthApi{
    
    @Override
    public ResponseEntity<Void> healthGet() {
        return ResponseEntity.ok().build();
    }

}
