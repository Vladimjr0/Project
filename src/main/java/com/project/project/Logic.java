package com.project.project;

import java.util.Scanner;

public class Logic {

    private DataBase dataBase;
    private Scanner scanner;

    {
        dataBase = new DataBase();
        scanner = new Scanner(System.in);
    }


    public void mainLogic() {
        Product product = new Product("Ручка", 20, 40);
        System.out.println("Магазин. Доступные действия");
        System.out.println("1. Добавить товар.");
        System.out.println("2. Посмотреть товары.");
        System.out.println("3. Удалить товар");
        System.out.println("4. Купить товар");
        System.out.println("5. Выйти из приложения");
        int choice;
        do {
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    dataBase.addItem(new Product(inputString("название"), inputDouble("цену"), inputInt("количество")));
                    break;
                case 2:
                    System.out.println(dataBase.getAllProducts());
                    break;
                case 3:
                    dataBase.removeProduct(inputString("название"));
                    break;
                case 4:
                    dataBase.buyProduct(inputString("название"), inputInt("количество"));
                    scanner.nextLine();
                    break;
                case 5:
                    System.out.println("Выход из программы");
            }
        } while (choice != 5);
    }

    public String inputString(String name){
        System.out.println("Введите " + name + " товара.");
        return scanner.nextLine();
    }

    public int inputInt(String name){
        System.out.println("Введите " + name + " товара.");
        return scanner.nextInt();
    }

    public double inputDouble(String name){
        System.out.println("Введите " + name + " товара.");
        return scanner.nextDouble();
    }

}
