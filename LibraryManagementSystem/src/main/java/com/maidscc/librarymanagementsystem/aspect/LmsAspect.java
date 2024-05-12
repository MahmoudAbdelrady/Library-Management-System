package com.maidscc.librarymanagementsystem.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LmsAspect {
    @AfterThrowing(pointcut = "execution(* com.maidscc.librarymanagementsystem.service.*.*(..))", throwing = "ex")
    public void forLoggingException(Exception ex) {
        System.out.println("\n===============> Exception thrown <===============");
        System.out.println("Message: " + ex.getMessage());
    }
}
