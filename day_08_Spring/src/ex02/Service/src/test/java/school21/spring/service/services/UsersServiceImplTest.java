package school21.spring.service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.TestApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

public class UsersServiceImplTest {
    UsersService usersService;
    UsersRepository usersRepository;

    @BeforeEach
    public void context() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        usersService = context.getBean("usersService", UsersService.class);
        usersRepository = context.getBean("usersRepository", UsersRepository.class);
    }

    @Test
    void testPassword() {
        String password = usersService.signUp("111@mail.ru");
        User one = usersRepository.findByEmail("111@mail.ru").get();

        Assertions.assertEquals(password, one.getPassword());
    }

    @Test
    void testPassword2() {
        String password = usersService.signUp("222@mail.ru");
        User one = usersRepository.findByEmail("222@mail.ru").get();

        Assertions.assertEquals(password, one.getPassword());
    }
}
