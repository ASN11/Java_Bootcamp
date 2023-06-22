package app;

import exception.UserNotFoundException;
import transaction.Transaction;
import transaction.TransactionsService;
import user.User;

import java.util.UUID;

public class Program {
    public static void main(String[] args) throws UserNotFoundException {
        User Sergey = new User("Sergey", 1500);
        User Ilya = new User();
        Ilya.setName("Ilya");
        User Tanya = new User("Tanya", 4000);



        TransactionsService service = new TransactionsService();
        service.addUser(Sergey);
        service.addUser(Ilya);
        service.addUser(Tanya);

        UUID transferId_2 = service.performTransfer(Sergey.getId(), Ilya.getId(), 100);
        UUID transferId_1 = service.performTransfer(Sergey.getId(), Ilya.getId(), 300);
        service.performTransfer(Tanya.getId(), Sergey.getId(), 250);

        System.out.println("Sergey balance = " + service.getBalance(Sergey.getId()));
        System.out.println("Ilya balance = " + service.getBalance(Ilya.getId()));

//        service.removeTransaction(2, transferId_1);
        service.removeTransaction(1, transferId_2);

        Transaction[] tr = service.getTransactionsList(Sergey.getId());
        for (int i = 0; i < Sergey.getTransactionsList().getSize(); i++) {
            System.out.println(tr[i].toString());
        }

        Transaction[] unique = service.getUniqueElements(Sergey.getId(), Ilya.getId());
        for (int i = 0; i < service.getSizeUniqueElements(); i++) {
            System.out.println("unique = " + unique[i].toString());
        }

    }
}
