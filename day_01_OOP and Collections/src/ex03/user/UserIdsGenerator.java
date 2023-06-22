package user;

public class UserIdsGenerator {
    private static UserIdsGenerator instance = null;
    private int userId;

    private UserIdsGenerator() {
        userId = 1;
    }

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    public int generateId() {
        return userId++;
    }
}
