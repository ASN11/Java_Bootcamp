package transaction;

import user.User;

import java.util.UUID;

public class Transaction {

    private UUID id;
    private User recipient;
    private User sender;
    public TransferCategory transferCategory;
    private int transferAmount;

    public Transaction() {
        id = UUID.randomUUID();
    }
    public Transaction(User recipient, User sender, TransferCategory transferCategory, int transferAmount) {
        executeTransaction(recipient, sender, transferCategory, transferAmount);
    }

    public void executeTransaction(User recipient, User sender, TransferCategory transferCategory, int transferAmount) {
        if (((transferCategory.equals(TransferCategory.CREDITS) && transferAmount > 0) || sender.getBalance() < -transferAmount) ||
            ((transferCategory.equals(TransferCategory.DEBITS) && transferAmount < 0) || recipient.getBalance() < transferAmount)) {
            System.err.println("Ошибка транзакции");
            System.exit(-1);
        } else {
            id = UUID.randomUUID();
            this.recipient = recipient;
            this.sender = sender;
            this.transferCategory = transferCategory;
            this.transferAmount = transferAmount;

            recipient.setBalance(recipient.getBalance() - transferAmount);
            sender.setBalance(sender.getBalance() + transferAmount);

            recipient.addTransaction(this);
            sender.addTransaction(this);
        }
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public TransferCategory getTransferCategory() {
        return transferCategory;
    }

    public int getTransferAmount() {
        return transferAmount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", recipient=" + recipient +
                ", sender=" + sender +
                ", transferCategory=" + transferCategory +
                ", transferAmount=" + transferAmount +
                '}';
    }
}
