package com.example.es.controller;

import com.alibaba.excel.EasyExcel;
import com.example.es.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

@RestController
public class ExcelExportController {

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportExcel() {
        // 生成数据
        List<User> userList = Arrays.asList(
                new User(1L, "张三", 30),
                new User(2L, "李四", 25),
                new User(3L, "王五", 28)
        );

        // 设置Excel文件的输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // 导出Excel
        EasyExcel.write(outputStream, User.class).sheet("用户列表").doWrite(userList);

        // 设置HTTP响应头
        byte[] content = outputStream.toByteArray();

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=users.xlsx")
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(content);
    }
}

