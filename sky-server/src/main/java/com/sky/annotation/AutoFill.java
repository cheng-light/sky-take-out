package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 自定义注解，用于标识某个方法需要进行功能字段自动填充
 */
@Target(ElementType.METHOD)    // 注解只能加在方法上
@Retention(RetentionPolicy.RUNTIME)  // 运行时保留，AOP可以反射读取
public @interface AutoFill {
    // 数据库操作类型：UPDATE INSERT
    OperationType value();
}