package transaction;

import exception.IllegalTransactionException;
import user.User;
import user.UsersArrayList;

import java.util.UUID;

public class TransactionsService {
    private final UsersArrayList userList;

    private int sizeOut;

    public TransactionsService() {
        userList = new UsersArrayList();
    }

    public void addUser(User user) {
        userList.addUser(user);
    }

    public double getBalance(int id) {
        return userList.getUserById(id).getBalance();
    }

    public UUID performTransfer(int senderId, int recipientId, double transferAmount) {
        User sender = userList.getUserById(senderId);
        User recipient = userList.getUserById(recipientId);

        if (sender.getBalance() < transferAmount) {
            throw new IllegalTransactionException("Insufficient balance");
        }

        UUID id = UUID.randomUUID();
        new Transaction(id, sender, recipient, TransferCategory.CREDITS, -(transferAmount));
        new Transaction(id, recipient, sender, TransferCategory.DEBITS, transferAmount);
        return id;
    }

    public Transaction[] getTransactionsList(int id) {
        return userList.getUserById(id).getTransactionsList().toArray();
    }

    public void removeTransaction(int userId, UUID transactionId) {
        userList.getUserById(userId).getTransactionsList().removeTransaction(transactionId);
    }

    public Transaction[] getUniqueElements(int idUser_1, int idUser_2) {
        Transaction[] transaction_1 = this.getTransactionsList(idUser_1);
        int size_1 = userList.getUserById(idUser_1).getTransactionsList().getSize();
        Transaction[] transaction_2 = this.getTransactionsList(idUser_2);
        int size_2 = userList.getUserById(idUser_2).getTransactionsList().getSize();
        Transaction[] transactionOut = new Transaction[size_1 + size_2];
        sizeOut = 0;

        addTransactionOut(transaction_1, transaction_2, transactionOut, size_1, size_2, idUser_2);
        addTransactionOut(transaction_2, transaction_1, transactionOut, size_2, size_1, idUser_1);

        return transactionOut;
    }

    private void addTransactionOut(Transaction[] transaction_1, Transaction[] transaction_2,
                                   Transaction[] transactionOut, int size_1, int size_2, int idUser_2) {
        for (int i = 0; i < size_1; i++) {
            boolean flag = true;
            for (int j = 0; j < size_2; j++) {
                if (transaction_1[i].getId().equals(transaction_2[j].getId())) {
                    flag = false;
                    break;
                }
            }
            if (flag && transaction_1[i].getSender().getId() == idUser_2) {
                transactionOut[sizeOut] = transaction_1[i];
                sizeOut++;
            }
        }
    }

    public int getSizeUniqueElements() {
        return sizeOut;
    }
}

