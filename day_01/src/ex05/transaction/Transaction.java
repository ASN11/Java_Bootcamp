package transaction;

import user.User;

import java.util.UUID;

public class Transaction {

    private UUID id_;
    private User recipient_;
    private User sender_;
    public TransferCategory transferCategory_;
    private double transferAmount_;

    public Transaction() {}
    public Transaction(UUID id, User recipient, User sender, TransferCategory transferCategory, double transferAmount) {
        executeTransaction(id, recipient, sender, transferCategory, transferAmount);
    }

    public void executeTransaction(UUID id, User recipient, User sender, TransferCategory transferCategory, double transferAmount) {
        if (((transferCategory.equals(TransferCategory.CREDITS) && transferAmount > 0) || recipient.getBalance() < -transferAmount) ||
            ((transferCategory.equals(TransferCategory.DEBITS) && transferAmount < 0) || sender.getBalance() < transferAmount)) {
            System.err.println("Ошибка транзакции");
            System.exit(-1);
        } else {
            id_ = id;
            recipient_ = recipient;
            sender_ = sender;
            transferCategory_ = transferCategory;
            transferAmount_ = transferAmount;

            if (transferCategory.equals(TransferCategory.CREDITS)) {
                recipient_.setBalance(recipient_.getBalance() + transferAmount);
                recipient_.addTransaction(this);
            } else {
                recipient_.setBalance(recipient_.getBalance() + transferAmount);
                recipient_.addTransaction(this);
            }
        }
    }

    public UUID getId() {
        return id_;
    }

    public void setId(UUID id) {
        id_ = id;
    }

    public User getRecipient() {
        return recipient_;
    }

    public User getSender() {
        return sender_;
    }

    public TransferCategory getTransferCategory() {
        return transferCategory_;
    }

    public double getTransferAmount() {
        return transferAmount_;
    }

    @Override
    public String toString() {
        String res;
        if (transferCategory_.equals(TransferCategory.CREDITS)) {
            res = "\u001B[0mTransaction " +
                    "id_= \u001B[34m\u001B[1m" + id_ +
                    ", \u001B[31m" + recipient_.getName() + "\u001B[0m" +
                    " \u001B[1m>> " +
                    "\u001B[32m" + sender_.getName() + "\u001B[0m" +
                    ", transferCategory_= \u001B[34m" + transferCategory_ +
                    "\u001B[0m, transferAmount_= \u001B[1m\u001B[34m" + transferAmount_ + "\u001B[0m";
        } else {
            res = "\u001B[0mTransaction " +
                    "id_= \u001B[34m\u001B[1m" + id_ +
                    ", \u001B[32m" + recipient_.getName() + "\u001B[0m" +
                    " \u001B[1m<< " +
                    "\u001B[31m" + sender_.getName() + "\u001B[0m" +
                    ", transferCategory_= \u001B[34m" + transferCategory_ +
                    "\u001B[0m, transferAmount_= \u001B[1m\u001B[34m" + transferAmount_ + "\u001B[0m";
        }
        return res;
    }
}
