package app;

import exception.UserNotFoundException;
import transaction.Transaction;
import transaction.TransactionsLinkedList;
import transaction.TransferCategory;
import user.User;

public class Program {
    public static void main(String[] args) throws UserNotFoundException {
        User Sergey = new User("Sergey", 1500);
        User Ilya = new User();
        Ilya.setName("Ilya");
        User Tanya = new User("Tanya", 4000);

        Transaction one_1 = new Transaction(Sergey, Ilya, TransferCategory.DEBITS, 100);
        Transaction one_2 = new Transaction(Tanya, Ilya, TransferCategory.DEBITS, 300);
        Transaction one_3 = new Transaction(Sergey, Ilya, TransferCategory.CREDITS, -20);

        TransactionsLinkedList transactionsList = new TransactionsLinkedList();
        transactionsList.addTransaction(one_1);
        transactionsList.addTransaction(one_2);
        transactionsList.addTransaction(one_3);

        transactionsList.removeTransaction(one_1.getId());

        Transaction[] forPrint = transactionsList.toArray();
        for (int i = 0; i < transactionsList.getSize(); i++) {
            System.out.println(forPrint[i].toString());
        }

    }
}
