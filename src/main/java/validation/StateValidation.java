package validation;

import biz.baijing.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {

    /**
     *
     * @param s 被校验的数据
     * @param constraintValidatorContext
     * @return  false 校验失败 ； true 校验通过
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // 校验规则
        if (s == null) {
            return false;
        }

        if (s.equals("已发布") || s.equals("草稿")) {
            return true;
        }

        return false;
    }
}
