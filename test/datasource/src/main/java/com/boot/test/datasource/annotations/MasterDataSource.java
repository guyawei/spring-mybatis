package com.boot.test.datasource.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主数据源注解，如果不加此注解默认数据源为slave
 * 
 * @author zhanghz
 *
 */

/**
 * Annotations are to be recorded in the class file by the compiler and
 * retained by the VM at run time, so they may be read reflectively.
 *
 * @see java.lang.reflect.AnnotatedElement
 */
@Retention(RetentionPolicy.RUNTIME)
/** Class, interface (including annotation type), or enum declaration */
@Target(ElementType.METHOD)
public @interface MasterDataSource {

}
