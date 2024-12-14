package com.example.petmanagement.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.petmanagement.service.PetService.getAllPets())")
    public void beforeGetAllPets(JoinPoint joinPoint) {
        log.info("Before executing getAllPets method in PetService");
    }

    @After("execution(* com.example.petmanagement.repository.PetRepository.findAll())")
    public void afterFindAllPets(JoinPoint joinPoint) {
        log.info("After executing findAll method in PetRepository");
    }

    @Before("execution(* com.example.petmanagement.controller.HouseholdController.createHousehold(..))")
    public void beforeCreateHousehold(JoinPoint joinPoint) {
        log.info("Before {} executing createHousehold with arguments {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @After("execution(* com.example.petmanagement.service.UserService.resetPassword(..))")
    public void afterResetPassword(JoinPoint joinPoint) {
        log.info("After executing resetPassword in UserService for user ID: {}",
                joinPoint.getArgs()[0]);
    }
}

