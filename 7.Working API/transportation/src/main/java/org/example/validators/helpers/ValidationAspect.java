package org.example.validators.helpers;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.data.common.ValidationException;
import org.example.data.dto.validation.FieldError;
import org.example.interfaces.Validator;
import org.springframework.stereotype.Component;
import java.lang.reflect.Parameter;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidationAspect {

    private final List<Validator<?>> validators;

    @Around("execution(* org.example.controllers..*(..))")
    public Object validateDto(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Parameter[] parameters = ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature())
                .getMethod().getParameters();

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            Parameter param = parameters[i];
            if (arg == null) continue;

            if (param.isAnnotationPresent(ValidatedDto.class)) {
                for (Validator<?> validator : validators) {
                    if (validatorSupports(validator, arg.getClass())) {
                        List<FieldError> errors = ((Validator<Object>) validator).validate(arg);
                        if (!errors.isEmpty()) {
                            throw new ValidationException(errors);
                        }
                    }
                }
            }
        }

        return joinPoint.proceed();
    }

    private boolean validatorSupports(Validator<?> validator, Class<?> clazz) {
        Class<?> validatorClass = validator.getClass();
        java.lang.reflect.Type[] interfaces = validatorClass.getGenericInterfaces();
        for (java.lang.reflect.Type type : interfaces) {
            if (type instanceof java.lang.reflect.ParameterizedType pt) {
                if (pt.getActualTypeArguments()[0] instanceof Class<?> argType) {
                    if (argType.isAssignableFrom(clazz)) return true;
                }
            }
        }
        return false;
    }
}
