package com.example.springstudy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalcDate {
    public static void main(String[] args) {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //开始时间
            Date star = dft.parse("2023-11-29");
            Long starTime = star.getTime();
            Long endTime = System.currentTimeMillis();
            //时间戳相差的毫秒数
            Long num = endTime - starTime;
            //除以一天的毫秒数
            System.out.println("相差天数为：" + num / 24 / 60 / 60 / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
