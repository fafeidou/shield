package com.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@TableName("users") // 表名映射
@ToString
public class User {
    @TableId(type = IdType.AUTO) // 自增主键
    private Long id;

    private String username;

    private String email;

    @TableField(fill = FieldFill.INSERT) // 自动填充
    private LocalDateTime createdAt;
}