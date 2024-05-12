package com.maidscc.librarymanagementsystem.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BookAspect {
    private long startTime;

    @Pointcut("execution(* com.maidscc.librarymanagementsystem.service.BookService.*(..))")
    public void forBookService() {
    }

    @Pointcut("execution(* com.maidscc.librarymanagementsystem.service.BookService.createBook(..)) || " +
            "execution(* com.maidscc.librarymanagementsystem.service.BookService.updateBook(..)) || " +
            "execution(* com.maidscc.librarymanagementsystem.service.BookService.deleteBook(..))")
    public void forBookOperations() {
    }

    @Before("forBookService()")
    public void bookMethodCall() {
        System.out.println("\n======== Book service method called ========\n");
    }

    @Before("forBookOperations()")
    public void bookOperationStartMethod() {
        startTime = System.currentTimeMillis();
        System.out.println("\n========> Book operation started");
    }

    @AfterReturning("forBookOperations()")
    public void bookOperationEndMethod() {
        System.out.println("\n======== Book operation performance ========");
        long endTime = System.currentTimeMillis();
        System.out.println("========> Book operation ended in " + (endTime - startTime) + "ms");
    }
}
