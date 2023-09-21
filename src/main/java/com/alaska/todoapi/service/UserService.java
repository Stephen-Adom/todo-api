package com.alaska.todoapi.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.alaska.todoapi.Exception.UserExistValidationException;
import com.alaska.todoapi.entity.Address;
import com.alaska.todoapi.entity.User;
import com.alaska.todoapi.repository.UserRepository;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll(Sort.by("createdAt").descending());
    }

    @Override
    public User saveUser(User user) throws UserExistValidationException {
        if (Objects.nonNull(user.getPhonenumber()) && !"".equals(user.getPhonenumber())) {
            User userWithPhoneNumber = this.userRepository.findByPhonenumber(user.getPhonenumber());

            if (Objects.nonNull(userWithPhoneNumber)) {
                throw new UserExistValidationException("User with phonenumber already exist");
            }
        }

        if (Objects.nonNull(user.getEmailAddress()) && !"".equals(user.getEmailAddress())) {
            User userWithEmailAddress = this.userRepository.findByEmailAddress(user.getEmailAddress());

            if (Objects.nonNull(userWithEmailAddress)) {
                throw new UserExistValidationException("User with email address already exist");
            }
        }

        User newUser = this.userRepository.save(this.createUserPostBody(user));
        return newUser;
    }

    private User createUserPostBody(User user) throws UserExistValidationException {
        if (Objects.isNull(user.getAddress())) {
            throw new UserExistValidationException("User address is not available");
        }

        new Address();
        Address newAddress = Address.builder().city(user.getAddress().getCity())
                .zipCode(user.getAddress().getZipCode()).country(user.getAddress().getCountry()).build();

        new User();
        User newUser = User.builder().firstName(user.getFirstName()).lastName(user.getLastName())
                .emailAddress(user.getEmailAddress()).phonenumber(user.getPhonenumber()).address(newAddress).build();

        return newUser;
    }
}
