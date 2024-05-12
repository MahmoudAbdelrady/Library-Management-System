package com.maidscc.librarymanagementsystem.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthAspect {
    private long startTime;

    @Pointcut("execution(* com.maidscc.librarymanagementsystem.service.AuthService.*(..))")
    public void forAuthService() {
    }

    @Before("forAuthService()")
    public void authMethodCall() {
        System.out.println("\n======== Auth service method called ========");
        startTime = System.currentTimeMillis();
        System.out.println("\n========> Auth operation started");
    }

    @AfterReturning("forAuthService()")
    public void authOperationEndMethod() {
        System.out.println("\n======== Auth operation performance ========");
        long endTime = System.currentTimeMillis();
        System.out.println("========> Auth operation ended in " + (endTime - startTime) + "ms");
    }
}
