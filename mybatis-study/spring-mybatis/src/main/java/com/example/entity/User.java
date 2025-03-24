package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}