package banking_application;

import java.util.Scanner;

public class Transaction {

    User us_ref = new User();
    public static Long remove_money(int amount) {
        Scanner sc = new Scanner(System.in);
        System.out.println("How much money you want to Withdraw? :");
        Long amount_to_subtract = sc.nextLong();
        return amount - amount_to_subtract;
    }
    public static Long add_money(int amount) {
        Scanner sc = new Scanner(System.in);
        System.out.println("How much money do you want to add? ");
        Long amount_to_add = sc.nextLong();
        return amount + amount_to_add;
    }
}
