package com.vienna.jaray.controller;

import com.vienna.jaray.groups.Create;
import com.vienna.jaray.model.PaySalary;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@RestController
public class PaySalaryController {

    @PostMapping("/paySalary")
    public Object paySalary(@Validated(Create.class) @RequestBody PaySalary paySalary){
        return "12345";
    }

    @GetMapping("/testValidated")
    public void testValidated(String[] args) {

        PaySalary paySalary = new PaySalary();
//        paySalary.setCardNo("1234");
        paySalary.setPhone("  ");

        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        Set<ConstraintViolation<Object>> set = validator.validate(paySalary);

        for (ConstraintViolation<?> constraintViolation : set) {
            System.out.println(constraintViolation.getMessage());
        }
    }
}
