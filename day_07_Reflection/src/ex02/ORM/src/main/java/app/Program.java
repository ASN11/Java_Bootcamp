package app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import model.Car;
import model.OrmManager;
import model.User;

public class Program {

    private static final String URL = "jdbc:postgresql://localhost:5555/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);

        HikariDataSource dataSource = new HikariDataSource(config);

        OrmManager ormManager = new OrmManager(dataSource);

        Car car = new Car();
        User user = new User();
        Class<?>[] classes = {car.getClass(), user.getClass()};

        ormManager.initializeTables(classes);
        ormManager.save(car);
        ormManager.save(new Car("Ferrari", "F60", 350));

        ormManager.update(new Car(1, "Ferrari", "F60", 380));

        System.out.println(ormManager.findById(2, Car.class));
    }
}
