package user;

import transaction.Transaction;
import transaction.TransactionsLinkedList;

public class User {
    private int id;
    private String name;
    private int balance;
    private TransactionsLinkedList TransactionsList;


    public User() {
        id = UserIdsGenerator.getInstance().generateId();
        TransactionsList = new TransactionsLinkedList();
    }

    public User(String name, int balance) {
        if (balance >= 0) {
            id = UserIdsGenerator.getInstance().generateId();
            this.name = name;
            this.balance = balance;
            TransactionsList = new TransactionsLinkedList();
        } else {
            System.err.println("Ошибка, отрицательный баланс");
            System.exit(-1);
        }
    }

    public int getId() {
        return id;
    }

    public TransactionsLinkedList getTransactionsList() {
        return TransactionsList;
    }

    public void addTransaction(Transaction transaction) {
        TransactionsList.addTransaction(transaction);
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
