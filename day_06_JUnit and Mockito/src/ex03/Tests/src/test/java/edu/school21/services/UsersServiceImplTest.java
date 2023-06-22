package edu.school21.services;
import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {
    @Mock
    UsersRepository usersRepository;
    UsersServiceImpl service;

    @BeforeEach
    public void init() {
        service = new UsersServiceImpl(usersRepository);
    }
    @Test
    public void correctLogin() {
        Mockito.doReturn(new User(1, "Alex", "12345", false)).when(usersRepository).findByLogin("Alex");

        Assertions.assertFalse(usersRepository.findByLogin("Alex").isAuthenticated());
        Assertions.assertTrue(service.authenticate("Alex", "12345"));
        Assertions.assertTrue(usersRepository.findByLogin("Alex").isAuthenticated());
    }

    /**
     * Кидает исключение EntityNotFoundException для любого логина, кроме "Alex"
     */
    @Test
    public void incorrectLogin() {
        Mockito.doAnswer(invocation -> {
            String parameter = invocation.getArgument(0);
            if (!parameter.equals("Alex")) {
                throw new EntityNotFoundException("Error");
            }
            return null;
        }).when(usersRepository).findByLogin(any());

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.authenticate("Elena", "12345"));
    }

    @Test
    public void alreadyAuthenticated() {
        Mockito.doReturn(new User(1, "Alex", "12345", true)).when(usersRepository).findByLogin("Alex");

        Assertions.assertThrows(AlreadyAuthenticatedException.class, () -> service.authenticate("Alex", "12345"));
    }

    @Test
    public void incorrectPassword() {
        Mockito.doReturn(new User(1, "Alex", "12345", false)).when(usersRepository).findByLogin("Alex");

        Assertions.assertFalse(service.authenticate("Alex", "123456"));
    }
}
