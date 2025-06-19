package biz.baijing.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import biz.baijing.validation.StateValidation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


import static biz.baijing.common.NotionMessage.STATE_VALIDATION_ERROR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(
        validatedBy = {StateValidation.class} // 校验规则的类
)
@Target({FIELD})
@Retention(RUNTIME)
public @interface State {

    // 错误提示
    String message() default STATE_VALIDATION_ERROR;

    Class<?>[] groups() default {};

    // 负载，获得 state 附加信息 —— 大部分情况下不需要处理
    Class<? extends Payload>[] payload() default {};


}
