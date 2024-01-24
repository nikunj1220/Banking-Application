package banking_application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;
public class User {
    String name;
    long id;
    long amount;
    String password;
    public static void create_user() {
        User us = new User();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter User Name: ");
        us.name = sc.next();
        System.out.println("Enter User id (1-10): ");
        us.id = sc.nextInt();
        System.out.println("Create a password: ");
        us.password = sc.next();
        System.out.println("Enter the amount available: ");
        us.amount = sc.nextLong();
        try {
            //Connection to the user database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_details", "root", "n2n0n1N0!");
            Statement statement = con.createStatement();
            String sql_query = "INSERT INTO USER_DETAILS values" + "('"+us.id+"', '"+us.name+"', '"+us.password+"')";
            statement.executeUpdate(sql_query);

            String account_sql_query = "INSERT INTO ACCOUNT_DETAILS VALUES" + "('"+us.id+"', '"+us.amount+"')";
            statement.executeUpdate(account_sql_query);

            String transaction_query = "INSERT INTO USER_TRANSACTION VALUES" + "('"+us.id+"', '"+us.amount+"', 'None', '"+us.amount+"' )";
            statement.executeUpdate(transaction_query);

        }
        catch (Exception e) {
        }
        System.out.println("User Added !!");
    }
    public static void remove_user(Long id) {
        try { //Connection to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_details", "root", "n2n0n1N0!");
            Statement statement = con.createStatement();
            String sql_query = "DELETE from USER_DETAILS where USER_ID =" + "'"+id+"'";
            statement.executeUpdate(sql_query);

            String account_sql_query = "DELETE FROM ACCOUNT_DETAILS WHERE ACCOUNT_ID =" + "'"+id+"'";
            statement.executeUpdate(account_sql_query);

            String transaction_query = "Delete from user_transaction where account_ID = '"+id+"'";
            statement.executeUpdate(transaction_query);
        }
        catch (Exception e) {
        }
        System.out.println("Record Deleted!!!");
    }
}