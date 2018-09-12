/**
 * 文件名：DecimalRangeValidator.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年10月28日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 〈一句话功能简述〉数字精度范围验证器
 * 〈功能详细描述〉 对数字范围以及指定精度进行校验，针对bigDecimal起作用
 * 
 * @author duanhy
 * @version [版本号, 2015年10月28日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DecimalRangeValidator implements
    ConstraintValidator<DecimalRange, BigDecimal> {
    /** 最大值 */
    private double max;
    
    /** 最小值 */
    private double min;
    
    /** 小数部分精度 */
    private int fraction;
    
    /** 整数部分精度 */
    private int integer;
    
    /**
     * 〈一句话功能简述〉为isValid方法初始化参数
     * 〈功能详细描述〉
     * 
     * @param constraintAnnotation annotation instance for a given constraint
     *            declaration
     */
    @Override
    public void initialize(DecimalRange constraintAnnotation) {
        this.max = constraintAnnotation.max();
        this.min = constraintAnnotation.min();
        this.fraction = constraintAnnotation.fraction();
        this.integer = constraintAnnotation.integer();
    }
    
    /**
     * 〈一句话功能简述〉实现验证逻辑
     * 〈功能详细描述〉
     * 
     * @param value object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (value != null) {
            String valueStr = String.valueOf(value.toString());
            String pattern = "";
            if (fraction > 0 && integer > 0) {
                pattern =
                    "^([+]|[-])?(\\d{0," + integer + "})(\\.?)(\\d{1,"
                        + fraction + "}?)$";
            }
            if (fraction > 0 && integer == 0) {
                pattern = "^([+]|[-])?(0\\.\\d{1," + fraction + "})$";
            }
            if (fraction == 0 && integer > 0) {
                pattern = "^([0-9]{0," + integer + "})$";
                pattern = "^([+]|[-])?(\\d{0," + integer + "})$";
            }
            if (!valueStr.matches(pattern)) {
                isValid = false;
            }
            if (value.doubleValue() > max || value.doubleValue() < min) {
                isValid = false;
            }
        }
        return isValid;
    }
    
}
