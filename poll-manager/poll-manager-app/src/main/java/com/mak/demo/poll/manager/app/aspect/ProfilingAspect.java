package com.mak.demo.poll.manager.app.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ProfilingAspect {

  @Around("serviceClassMethods() || controllerClassMethods() || @annotation(com.mak.demo.poll.manager.app.aspect.LogExecutionTime)")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    Object returnValue = joinPoint.proceed();
    long finishTime = System.currentTimeMillis();
    log.info("-- {}.{}() took {} ms", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), finishTime - startTime);
    return returnValue;
  }

  @Pointcut("execution(public * com.mak.demo.poll.manager.app.service..*.*(..))")
  public void serviceClassMethods() {}

  @Pointcut("execution(public * com.mak.demo.poll.manager.app.controller..*.*(..))")
  public void controllerClassMethods() {}

}
