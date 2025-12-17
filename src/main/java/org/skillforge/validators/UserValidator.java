package org.skillforge.validators;

import org.skillforge.dto.authRequestDTO;
import org.skillforge.exceptions.InvalidInputException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidator {
    public void userValidation(authRequestDTO request) {
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String PASS_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&-])[A-Za-z\\d@$!%*?&-]{8,}$";

        if (!(Pattern.matches(EMAIL_REGEX, request.getEmail()))) {
            System.out.println(request.getEmail());
            throw new InvalidInputException("Invalid Email");
        }

        if (request.getPassword() == "") {
            throw new InvalidInputException("Password cannot be empty");
        }

        if (!(Pattern.matches(PASS_REGEX, request.getPassword()))) {
            throw new InvalidInputException("Password should be atleast 8 characters long, consisting of alphabets, numbers and special characters");
        }
    }
}
