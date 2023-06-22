import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User Ilya = new User(1, "Ilya", 5000);
        User Dmitriy = new User(2, "Dmitriy", 2000);

        UUID uuid = UUID.randomUUID();
        Transaction one_1 = new Transaction(uuid, Dmitriy, Ilya, TransferCategory.DEBITS, 100);
        Transaction one_2 = new Transaction(uuid, Dmitriy, Ilya, TransferCategory.DEBITS, 300);
        Transaction one_3 = new Transaction(uuid, Dmitriy, Ilya, TransferCategory.CREDITS, -20);

        System.out.println(Ilya.toString());
        System.out.println(Dmitriy.toString());
        System.out.println(one_1.toString());
    }
}
