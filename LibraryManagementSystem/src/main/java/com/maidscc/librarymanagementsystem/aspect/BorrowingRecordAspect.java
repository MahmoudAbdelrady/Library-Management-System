package com.maidscc.librarymanagementsystem.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BorrowingRecordAspect {
    private long startTime;

    @Pointcut("execution(* com.maidscc.librarymanagementsystem.service.BorrowingRecordService.*(..))")
    public void forBorrowingRecordService() {
    }

    @Before("forBorrowingRecordService()")
    public void borrowingRecordOperationStartMethod() {
        System.out.println("\n======== Borrowing Record service method called ========");
        startTime = System.currentTimeMillis();
        System.out.println("\n========> Borrowing Record operation started");
    }

    @AfterReturning("forBorrowingRecordService()")
    public void borrowingRecordOperationEndMethod() {
        System.out.println("\n======== Borrowing Record operation performance ========");
        long endTime = System.currentTimeMillis();
        System.out.println("========> Borrowing Record operation ended in " + (endTime - startTime) + "ms");
    }
}
