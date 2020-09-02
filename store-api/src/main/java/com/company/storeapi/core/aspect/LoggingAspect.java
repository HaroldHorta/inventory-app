package com.company.storeapi.core.aspect;

import com.company.storeapi.core.aspect.base.BaseAspect;
import com.company.storeapi.core.constants.DeployType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type Logging aspect.
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect extends BaseAspect {

    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * Instantiates a new Logging aspect.
     *
     * @param mapper the mapper
     */
    public LoggingAspect(ObjectMapper mapper) {
        super(mapper);
    }

    /**
     * Bean pointcut.
     */
    @Pointcut("within(@org.springframework.stereotype.Component *) || within(@org.springframework.stereotype.Service *) || within(@org.springframework.stereotype.Repository * ) || within(@org.springframework.web.bind.annotation.RestController *)")
    public void beanPointcut() {
    }

    /**
     * Application package pointcut.
     */
    @Pointcut("within(com.company.storeapi.repositories..*)|| within(com.company.storeapi.model..*)|| within(com.company.storeapi.services..*) || within(com.company.storeapi.web..*)")
    public void applicationPackagePointcut() {
    }

    /**
     * Log around object.
     *
     * @param proceedingJoinPoint the proceeding join point
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("applicationPackagePointcut() && beanPointcut()")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Marker marker = MarkerFactory.getMarker(proceedingJoinPoint.getSignature().getName());
        if (log.isDebugEnabled() || profile.equals(DeployType.DEVELOP) || profile.equals(DeployType.PRODUCTION)) {
            log.info(marker, "Enter: {}.{}() with argument[s] = {}", proceedingJoinPoint.getSignature().getDeclaringType(), proceedingJoinPoint.getSignature().getName(), getPayload(proceedingJoinPoint.getArgs()));
        }
        try {
            Object result = proceedingJoinPoint.proceed();
            if (log.isDebugEnabled() || profile.equals(DeployType.DEVELOP) || profile.equals(DeployType.PRODUCTION)) {
                log.info(marker, "Exit: {}.{}() with result = {}", proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                        proceedingJoinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException ie) {
            log.error(marker, "Error: {} in {}.{}()", getPayload(proceedingJoinPoint.getArgs()), proceedingJoinPoint.getSignature().getDeclaringType(), proceedingJoinPoint.getSignature().getDeclaringTypeName());
            throw ie;
        } catch (Exception e) {
            log.error("Error: {} in {}.{}() - EXCEPTION: {} ", getPayload(proceedingJoinPoint.getArgs()), proceedingJoinPoint.getSignature().getDeclaringType(), proceedingJoinPoint.getSignature().getDeclaringTypeName(), e);
            throw e;
        }

    }


}
