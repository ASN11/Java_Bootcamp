package user;

import transaction.Transaction;
import transaction.TransactionsLinkedList;

public class User {
    private int id_;
    private String name_;
    private double balance_;
    private TransactionsLinkedList TransactionsList;


    public User() {
        id_ = UserIdsGenerator.getInstance().generateId();
        balance_ = 0;
        TransactionsList = new TransactionsLinkedList();
    }

    public User(String name, double balance) {
        if (balance >= 0) {
            id_ = UserIdsGenerator.getInstance().generateId();
            name_ = name;
            balance_ = balance;
            TransactionsList = new TransactionsLinkedList();
        } else {
            System.err.println("Ошибка, отрицательный баланс");
            System.exit(-1);
        }
    }

    public int getId() {
        return id_;
    }

    public TransactionsLinkedList getTransactionsList() {
        return TransactionsList;
    }

    public void addTransaction(Transaction transaction) {
        TransactionsList.addTransaction(transaction);
    }

    public String getName() {
        return name_;
    }

    public void setName(String name) {
        name_ = name;
    }

    public double getBalance() {
        return balance_;
    }

    public void setBalance(double balance) {
        if (balance >= 0) {
            balance_ = balance;
        } else {
            System.err.println("Ошибка, отрицательный баланс");
            System.exit(-1);
        }
    }

    @Override
    public String toString() {
        return "User " +
                "id_= " + id_ +
                ", name_= '" + name_ + '\'' +
                ", balance_= " + balance_;
    }
}
