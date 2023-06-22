public class UsersArrayList implements UsersList{
    private User[] users;
    private int size;

    public UsersArrayList() {
        users = new User[10];
    }

    @Override
    public void addUser(User user) {
        if (size == users.length) {
            User[] newUsers = new User[users.length + users.length / 2];
            System.arraycopy(users, 0, newUsers, 0, users.length);
            users = newUsers;
        }

        users[size++] = user;
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        for (int i = 0; i < size; i++) {
            if (users[i].getId() == id) {
                return users[i];
            }
        }

        throw new UserNotFoundException("User with ID " + id + " does not exist");
    }

    @Override
    public User getUserByIndex(int index) {
        return users[index];
    }

    @Override
    public int getNumberOfUsers() {
        return size;
    }
}
