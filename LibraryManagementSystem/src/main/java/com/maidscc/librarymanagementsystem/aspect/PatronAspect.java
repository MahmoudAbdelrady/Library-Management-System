package com.maidscc.librarymanagementsystem.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PatronAspect {
    private long startTime;

    @Pointcut("execution(* com.maidscc.librarymanagementsystem.service.PatronService.*(..))")
    public void forPatronService() {
    }

    @Pointcut("execution(* com.maidscc.librarymanagementsystem.service.PatronService.registerPatron(..)) || " +
            "execution(* com.maidscc.librarymanagementsystem.service.PatronService.updatePatron(..)) || " +
            "execution(* com.maidscc.librarymanagementsystem.service.PatronService.deletePatron(..))")
    public void forPatronOperations() {
    }

    @Before("forPatronService()")
    public void patronMethodCall() {
        System.out.println("\n======== Patron service method called ========\n");
    }

    @Before("forPatronOperations()")
    public void patronOperationStartMethod() {
        startTime = System.currentTimeMillis();
        System.out.println("\n========> Patron operation started");
    }

    @AfterReturning("forPatronOperations()")
    public void patronOperationEndMethod() {
        System.out.println("\n======== Patron operation performance ========");
        long endTime = System.currentTimeMillis();
        System.out.println("========> Patron operation ended in " + (endTime - startTime) + "ms");
    }
}
