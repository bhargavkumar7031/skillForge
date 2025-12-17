package org.skillforge.service;

import org.skillforge.domain.User;
import org.skillforge.dto.signupRequestDTO;
import org.skillforge.exceptions.InvalidInputException;
import org.skillforge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;


    public void add(signupRequestDTO signupRequest) {
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String PASS_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&-])[A-Za-z\\d@$!%*?&-]{8,}$";


        User user = new User();

        if(!(Pattern.matches(EMAIL_REGEX, signupRequest.getEmail()))) {
            System.out.println(signupRequest.getEmail());
            throw new InvalidInputException("Invalid Email");
        }

        if(signupRequest.getPassword() == "") {
            throw new InvalidInputException("Password cannot be empty");
        }

        if(!(Pattern.matches(PASS_REGEX, signupRequest.getPassword()))) {
            throw new InvalidInputException("Password should be atleast 8 characters long, consisting of alphabets, numbers and special characters");
        }

        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        user.setCreatedAt(new Date());

        userRepo.save(user);
    }
}
