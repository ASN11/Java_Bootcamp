public class Program {
    public static void main(String[] args) throws UserNotFoundException {
        User Sergey = new User();
        User Ilya = new User();
        User Tanya = new User();

        UsersArrayList userList = new UsersArrayList();
        userList.addUser(Sergey);
        userList.addUser(Ilya);
        userList.addUser(Tanya);

        System.out.println("Количество пользователей = " + userList.getNumberOfUsers());
        System.out.println("ID = " + userList.getUserById(2).getId());
        System.out.println("ID = " + userList.getUserByIndex(0).getId());
    }
}
