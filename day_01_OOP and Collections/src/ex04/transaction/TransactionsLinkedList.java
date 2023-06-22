package transaction;

import exception.TransactionNotFoundException;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    /*
    вложенный класс для хранения ноды
     */
    private static class Node {
        Transaction data_;
        Node next;

        Node(Transaction data) {
            data_ = data;
            next = null;
        }
    }

    private Node tail;
    private int size;

    public int getSize() {
        return size;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        Node newNode = new Node(transaction);
        newNode.next = tail;
        tail = newNode;
        size++;
    }

    @Override
    public void removeTransaction(UUID id) throws TransactionNotFoundException {
        if (tail == null) {
            return;
        }
        if (tail.data_.getId().equals(id)) {
            tail = tail.next;
            size--;
            return;
        }
        Node curr = tail;
        while (curr.next != null && !curr.next.data_.getId().equals(id)) {
            curr = curr.next;
        }
        if (curr.next != null) {
            curr.next = curr.next.next;
            size--;
            return;
        }
        throw new TransactionNotFoundException("Transaction not found: " + id.toString());
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] array = new Transaction[size];
        Node tailToArray = tail;
        for (int i = size; i > 0; i--) {
            array[i-1] = tailToArray.data_;
            tailToArray = tailToArray.next;
        }
        return array;
    }
}
