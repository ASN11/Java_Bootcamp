public class User {
    private int id;
    private String name;
    private int balance;

    public User() {}
    public User(int id, String name, int balance) {
        if (balance >= 0) {
            this.id = id;
            this.name = name;
            this.balance = balance;
        } else {
            System.err.println("Ошибка, отрицательный баланс");
            System.exit(-1);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            System.err.println("Ошибка, отрицательный баланс");
            System.exit(-1);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
