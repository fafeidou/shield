package com.shield;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ModifyFinalFieldExample {
    public static void main(String[] args) {
        // 使用反射将 Constants 类中的 FINAL修饰符移除
        try {
            Field field = Class.forName("com.shield.MyClass").getDeclaredField("CONSTANT");
            field.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(null, "Initial modified value via reflection");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Logging the modified constant's value
        System.out.println("Modified constant value: " + MyClass.CONSTANT);
    }
}
