package com.alaska.todoapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.alaska.todoapi.Exception.UserDoesNotExistException;
import com.alaska.todoapi.Exception.UserExistValidationException;
import com.alaska.todoapi.entity.Address;
import com.alaska.todoapi.entity.User;
import com.alaska.todoapi.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userservice;

    @MockBean
    private UserRepository userRepository;

    private User testUser;

    private Address testAddress;

    @BeforeEach
    public void setUp() {
        this.testAddress = Address.builder().city("Accra").zipCode("00233").country("Ghana").build();
        this.testUser = User.builder().firstName("Urie").lastName("Doe").emailAddress("urie@mail.com")
                .phonenumber("12345667890").address(this.testAddress).id(1L).build();
        Mockito.when(this.userRepository.save(this.testUser)).thenReturn(testUser);
    }

    @Test
    public void whenUserInfoIsValid_thenSaveNewUserToDb() throws UserExistValidationException {
        Mockito.when(this.userRepository.existsByPhonenumber(this.testUser.getPhonenumber())).thenReturn(false);
        Mockito.when(this.userRepository.existsByEmailAddress(this.testUser.getEmailAddress())).thenReturn(false);
        User savedUser = this.userservice.saveUser(this.testUser);

        assertNotNull(savedUser);
        assertEquals(savedUser.getFirstName(), this.testUser.getFirstName());

        Mockito.verify(this.userRepository, atLeastOnce()).existsByPhonenumber(this.testUser.getPhonenumber());
        Mockito.verify(this.userRepository, atLeastOnce()).existsByEmailAddress(this.testUser.getEmailAddress());
    }

    @Test
    public void whenExistingPhonenumberisProvided_thenThrowPhonenumberExistException()
            throws UserExistValidationException {
        Mockito.when(this.userRepository.existsByPhonenumber(this.testUser.getPhonenumber())).thenReturn(true);

        assertThrows(UserExistValidationException.class, () -> this.userservice.saveUser(this.testUser));

        Mockito.verify(this.userRepository, atLeastOnce()).existsByPhonenumber(this.testUser.getPhonenumber());

        Mockito.verifyNoMoreInteractions(this.userRepository);

    }

    @Test
    public void whenExistingEmailAddressIsProvided_thenThrowEmailExistException() {
        Mockito.when(this.userRepository.existsByEmailAddress(this.testUser.getEmailAddress())).thenReturn(true);

        assertThrows(UserExistValidationException.class, () -> this.userservice.saveUser(this.testUser));

        Mockito.verify(this.userRepository).existsByEmailAddress(this.testUser.getEmailAddress());
    }

    @Test
    public void whenWrongUserIdIsProvided_thenRaiseAnException() {
        Mockito.when(this.userRepository.findById(testUser.getId())).thenReturn(Optional.empty());

        assertThrows(UserDoesNotExistException.class,
                () -> this.userservice.updateUser(this.testUser.getId(), this.testUser));

        Mockito.verify(this.userRepository).findById(testUser.getId());
    }

    @Test
    public void whenValidUserInfoIsProvided_thenUpdateExistingUserInfo()
            throws UserDoesNotExistException, UserExistValidationException {

        Mockito.when(this.userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        User user = User.builder().firstName("Urie updated").lastName("Doe").emailAddress("urie@mail.com")
                .phonenumber("12345667890").address(this.testAddress).id(1L).build();
        User updatedUser = this.userservice.updateUser(this.testUser.getId(), user);

        assertEquals(updatedUser.getFirstName(), user.getFirstName());

        Mockito.verify(this.userRepository).save(user);
    }
}
