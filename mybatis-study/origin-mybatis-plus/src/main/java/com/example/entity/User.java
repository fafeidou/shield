package com.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@TableName("users") // 指定表名
@Data
public class User {
    @TableId(type = IdType.AUTO) // 自增主键
    private Long id;

    private String username;
    private String email;

    // Getter/Setter 省略（可用Lombok）
}
