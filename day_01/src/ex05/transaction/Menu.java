package transaction;

import java.util.Scanner;
import java.util.UUID;

import exception.IllegalTransactionException;
import exception.TransactionNotFoundException;
import exception.UserNotFoundException;
import transaction.Transaction;
import exception.MenuErrorException;
import transaction.TransactionsService;
import user.User;

public class Menu {
    private final TransactionsService service;
    boolean developMod_;

    public Menu() {
        service = new TransactionsService();
        developMod_ = false;
    }

    public Menu(boolean developMod) {
        service = new TransactionsService();
        developMod_ = developMod;
    }

    /**
     * Основной метод класса Menu. запускает процесс в консоли
     *
     * @throws MenuErrorException вызывается при неправильном вводе данных в меню
     */
    public void start() throws MenuErrorException {
        int res = 0;
        Scanner in = new Scanner(System.in);
        while (res != 7) {
            try {
                System.out.println("\u001B[35m1. Add a user\n" +
                        "2. View user balances\n" +
                        "3. Perform a transfer\n" +
                        "4. View all transactions for a specific user\n" +
                        "5. DEV – remove a transfer by ID\n" +
                        "6. DEV – check transfer validity\n" +
                        "7. Finish execution\u001B[0m");
                res = in.nextInt();
                switch (res) {
                    case 1:
                        addUser(in);
                        break;
                    case 2:
                        viewUserBalances(in);
                        break;
                    case 3:
                        performTransfer(in);
                        break;
                    case 4:
                        viewAllTransactions(in);
                        break;
                    case 5:
                        removeTransfer(in);
                        break;
                    case 6:
                        checkTransferValidity(in);
                        break;
                    case 7:
                        break;
                    default:
                        throw new MenuErrorException("Неверный формат строки");
                }
            } catch (MenuErrorException | IllegalTransactionException | TransactionNotFoundException |
                     UserNotFoundException ex) {
                System.out.println(ex + ", повторите ввод\n");
            } catch (Exception ex) {
                System.out.println(ex + " Ошибка, повторите ввод\n");
                in.nextLine();
            }
        }

    }

    /**
     * Создаем нового пользователя words[0] и его баланс words[1] через service.addUser()
     *
     * @param in строка, введённая пользователем
     */
    private void addUser(Scanner in) {
        System.out.println("Enter a user name and a balance");
        String str = in.nextLine();
        str = in.nextLine();
        String[] words = str.split(" ");
        if (words.length != 2) {
            throw new MenuErrorException("Неверный формат строки");
        }
        User user = new User(words[0], Double.parseDouble(words[1]));
        service.addUser(user);

        System.out.println("User with id = " + user.getId() + " is added\n");
    }

    /**
     * Выводим на экран баланс пользователя по его ID через service.getBalance()
     *
     * @param in строка, введённая пользователем
     */
    private void viewUserBalances(Scanner in) {
        System.out.println("Enter a user ID");
        int res = in.nextInt();
        System.out.println(service.getName(res) + " - " + service.getBalance(res) + "\n");
    }


    /**
     * Создаем транзакцию от пользователя 1 words[0] к пользователю 2 words[1] на сумму words[2]
     * через service.performTransfer
     *
     * @param in строка, введённая пользователем
     */
    private void performTransfer(Scanner in) {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        String str = in.nextLine();
        str = in.nextLine();
        String[] words = str.split(" ");
        if (words.length != 3) {
            throw new MenuErrorException("Неверный формат строки");
        }
        service.performTransfer(Integer.parseInt(words[0]), Integer.parseInt(words[1]), Double.parseDouble(words[2]));

        System.out.println("The transfer is completed\n");
    }

    /**
     * Смотрим все транзакции пользователя по его ID через service.getTransactionsList()
     *
     * @param in строка, введённая пользователем
     */
    private void viewAllTransactions(Scanner in) {
        System.out.println("Enter a user ID");
        int id = in.nextInt();

        Transaction[] tr = service.getTransactionsList(id);
        for (int i = 0; i < service.getTransactionsCount(id); i++) {
            System.out.println(tr[i].toString());
        }
        System.out.println();
    }


    /**
     * Удаляем транзакцию UUID words[1] для пользователя words[0] через  service.removeTransaction()<br>
     * ТОЛЬКО с правами АДМ ("--profile=dev")
     *
     * @param in строка, введённая пользователем
     */
    private void removeTransfer(Scanner in) {
        if (developMod_) {
            System.out.println("Enter a user ID and a transfer ID");
            String str = in.nextLine();
            str = in.nextLine();
            String[] words = str.split(" ");
            if (words.length != 2) {
                throw new MenuErrorException("Неверный формат строки");
            }
            service.removeTransaction(Integer.parseInt(words[0]), UUID.fromString(words[1]));
        } else {
            System.out.println("feature is only available in developer mode\n");
        }
    }

    /**
     * Смотрим все непарные транзакции между пользователем 1 words[0] и пользователем 2 words[1]
     * через service.getUniqueElements()<br>
     * ТОЛЬКО с правами АДМ ("--profile=dev")
     *
     * @param in строка, введённая пользователем
     */
    private void checkTransferValidity(Scanner in) {
        if (developMod_) {
            System.out.println("Enter a user_1 ID and a user_2 ID:");
            String str = in.nextLine();
            str = in.nextLine();
            String[] words = str.split(" ");
            if (words.length != 2) {
                throw new MenuErrorException("Неверный формат строки");
            }
            Transaction[] tr = service.getUniqueElements(Integer.parseInt(words[0]), Integer.parseInt(words[1]));
            for (int i = 0; i < service.getSizeUniqueElements(); i++) {
                System.out.println(tr[i].toString());
            }
            System.out.println();
        } else {
            System.out.println("feature is only available in developer mode\n");
        }
    }

}
