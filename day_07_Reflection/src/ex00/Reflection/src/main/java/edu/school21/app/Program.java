package edu.school21.app;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Classes:\n  - User\n  - Car\n---------------------\nEnter class name:");
        try {
            Class<?> c = Class.forName("edu.school21.classes." + scanner.nextLine());
            Object obj = c.newInstance();
            classInfo(c);
            createNewObject(obj, scanner, c);
            changeObject(obj, scanner, c);
            callMethod(obj, scanner, c);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found " + e);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            System.out.println("Method not found " + e);
        }
    }

    /**
     * Печатает информацию о классе:<br>
     * fields<br>
     * methods
     */
    private static void classInfo(Class<?> c) {
        System.out.println("---------------------\nfields:");
        for (Field field : c.getDeclaredFields()) {
            System.out.println("\t\t" + field.getType().getSimpleName() + " " + field.getName());
        }

        System.out.println("methods:");
        for (Method method : c.getDeclaredMethods()) {
            System.out.println("\t\t" + method.getReturnType().getSimpleName() + " " + method.getName() + Arrays.toString(method.getParameterTypes()));
        }
    }

    /**
     * Создает новый объект класса "c" и заполняет данными все его поля
     */
    private static void createNewObject(Object obj, Scanner scanner, Class<?> c) {
        System.out.println("---------------------\nLet's create an object.");

        for (Field field : c.getDeclaredFields()) {
            System.out.println(field.getName() + ":");
            setValue(scanner, field, obj);
        }
        System.out.println("Object created: " + obj);
    }

    /**
     * Устанавливает новое значение для поля field объекта obj
     */
    private static void setValue(Scanner scanner, Field field, Object obj) {
        field.setAccessible(true);
        try {
            String value = scanner.nextLine();
            field.set(obj, parseArgumentValue(value, field.getType()));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            System.out.println("Incorrect value");
        }
    }

    /**
     * Ищет поле с названием name и изменяет его значение
     */
    private static void changeObject(Object obj,  Scanner scanner, Class<?> c) {
        System.out.println("---------------------\nEnter name of the field for changing:");
        String name = scanner.nextLine();
        Field field;
        try {
            field = c.getDeclaredField(name);
            field.setAccessible(true);
            System.out.println("Enter " + field.getType().getSimpleName() + " value:");
            setValue(scanner, field, obj);
            System.out.println("Object updated: " + obj);
        } catch (NoSuchFieldException e) {
            System.out.println("Field not found: " + name);
        }
    }

    /**
     * grow(int, String)
     */
    private static void callMethod(Object obj, Scanner scanner, Class<?> c) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("---------------------\nEnter name of the method for call:");
        String[] value = scanner.nextLine().split("\\W+");

        Class<?>[] classes = new Class[value.length-1];
        for (int i = 0; i < classes.length; i++) {
            classes[i] = parseMethodParameterType(value[i+1]);
        }

        Method method = c.getMethod(value[0], classes);

        Object[] methodArguments = new Object[classes.length];
        for (int i = 0; i < methodArguments.length; i++) {
            System.out.println("Enter " + value[i+1] + " value:");
            methodArguments[i] = parseArgumentValue(scanner.next(), classes[i]);
        }
        if (methodArguments[0] != null) {
            System.out.println("Method returned:\n" + method.invoke(obj, methodArguments));
        }
    }

    /**
     * Преобразует строковое обозначение типа данных (введенное пользователем) в Класс
     */
    private static Class<?> parseMethodParameterType(String argumentValue) {
        switch (argumentValue) {
            case "String":
                return String.class;
            case "int":
                return int.class;
            case "Integer":
                return Integer.class;
            case "double":
                return double.class;
            case "Double":
                return Double.class;
            case "boolean":
                return boolean.class;
            case "Boolean":
                return Boolean.class;
            case "long":
                return long.class;
            case "Long":
                return Long.class;
            default:
                return null;
        }
    }

    /**
     * Преобразует строку в тот тип данных, который соответствует классу fieldType
     */
    private static Object parseArgumentValue(String next, Class<?> fieldType) {
        try {
            if (fieldType == String.class) {
                return next;
            } else if (fieldType == int.class || fieldType == Integer.class) {
                return Integer.parseInt(next);
            } else if (fieldType == double.class || fieldType == Double.class) {
                return Double.parseDouble(next);
            } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                return Boolean.parseBoolean(next);
            } else if (fieldType == long.class || fieldType == Long.class) {
                return Long.parseLong(next);
            } else {
                return null;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect value " + e);
        }
        return null;
    }
}
