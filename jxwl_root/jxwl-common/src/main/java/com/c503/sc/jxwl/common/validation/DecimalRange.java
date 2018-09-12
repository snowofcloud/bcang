/**
 * 文件名：DecimalRange.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年10月28日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


/**
 * 〈一句话功能简述〉数值大小范围以及精度自定义注解
 * 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2015年10月30日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = {DecimalRangeValidator.class})
public @interface DecimalRange {
    /** 最小值 */
    double min() default Double.MIN_VALUE;
    
    /** 最大值 */
    double max() default Double.MAX_VALUE;
    
    /** 小数部分精度 */
    int fraction();
    
    /** 整数部分精度 */
    int integer();
    
    /** 默认消息 */
    String message() default "{javax.validation.constraints.NotNull.message}";
    
    /** 分组函数 */
    Class<?>[] groups() default {};
    
    /** 负载 */
    Class<? extends Payload>[] payload() default {};
    
}
