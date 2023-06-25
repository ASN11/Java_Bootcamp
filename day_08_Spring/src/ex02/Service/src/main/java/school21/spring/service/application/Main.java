package school21.spring.service.application;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.services.UsersService;


public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbcTemplateImpl", UsersRepository.class);
        UsersService usersService = context.getBean("usersServiceImpl", UsersService.class);

        System.out.println(usersService.signUp("111@mail.ru"));
        System.out.println(usersService.signUp("222@mail.ru"));
        System.out.println(usersService.signUp("333@mail.ru"));
        System.out.println(usersRepository.findAll());
    }
}
