public class User {
    private final int id_;

    public User() {
        id_ = UserIdsGenerator.getInstance().generateId();
    }

    public int getId() {
        return id_;
    }
}
