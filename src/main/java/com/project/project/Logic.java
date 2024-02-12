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
        System.out.println("4. Выйти из приложения");
        int choice;
        do {
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    dataBase.addItem(product);
                    break;
                case 2:

                    System.out.println(dataBase.getAllProducts());
                    break;
                case 3:
                    dataBase.removeProduct("Ручка");
                    break;
                case 4:
                    System.out.println("Выход из программы");
            }
        }while (choice !=4);
    }

}
