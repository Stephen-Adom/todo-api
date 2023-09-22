package com.alaska.todoapi.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.alaska.todoapi.Exception.UserDoesNotExistException;
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
        return this.userRepository.findAll(Sort.by("createdAt").ascending());
    }

    @Override
    public User saveUser(User user) throws UserExistValidationException {
        if (Objects.nonNull(user.getPhonenumber()) && !"".equals(user.getPhonenumber())) {
            if (this.userRepository.existsByPhonenumber(user.getPhonenumber())) {
                throw new UserExistValidationException("User with phonenumber already exist");
            }
        }

        if (Objects.nonNull(user.getEmailAddress()) && !"".equals(user.getEmailAddress())) {
            if (this.userRepository.existsByEmailAddress(user.getEmailAddress())) {
                throw new UserExistValidationException("User with email address already exist");
            }
        }
        return this.userRepository.save(this.createUserPostBody(user));
    }

    @Override
    public User updateUser(Long id, User user) throws UserDoesNotExistException, UserExistValidationException {
        Optional<User> userExist = this.userRepository.findById(id);

        if (userExist.isEmpty()) {
            throw new UserDoesNotExistException("User with id " + id + " does not exist");
        }

        User existingUser = userExist.get();

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());

        if (Objects.nonNull(user.getPhonenumber()) && !"".equals(user.getPhonenumber())) {

            if (this.userRepository.existsByPhonenumber(user.getPhonenumber())) {
                throw new UserExistValidationException("User with phonenumber already exist");
            } else {
                existingUser.setPhonenumber(user.getPhonenumber());
            }
        }

        new Address();
        Address address = Address.builder().city(user.getAddress().getCity()).country(user.getAddress().getCountry())
                .zipCode(user.getAddress().getZipCode()).build();

        existingUser.setAddress(address);

        return this.userRepository.save(existingUser);
    }

    @Override
    public User getUserById(Long id) throws UserDoesNotExistException {
        Optional<User> user = this.userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserDoesNotExistException("User with id " + id + " does not exist");
        }

        return user.get();
    }

    @Override
    public void deleteUserById(Long id) throws UserDoesNotExistException {
        Optional<User> user = this.userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserDoesNotExistException("User with id " + id + " does not exist");
        }

        this.userRepository.deleteById(id);
    }

    private User createUserPostBody(User user) throws UserExistValidationException {
        if (Objects.isNull(user.getAddress())) {
            throw new UserExistValidationException("User address is not available");
        }

        new Address();
        Address newAddress = Address.builder().city(user.getAddress().getCity())
                .zipCode(user.getAddress().getZipCode()).country(user.getAddress().getCountry()).build();

        new User();
        return User.builder().firstName(user.getFirstName()).lastName(user.getLastName())
                .emailAddress(user.getEmailAddress()).phonenumber(user.getPhonenumber()).address(newAddress).build();
    }
}
