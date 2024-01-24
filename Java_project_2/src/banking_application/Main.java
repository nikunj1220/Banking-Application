package banking_application;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
public class Main {
    Scanner sc = new Scanner(System.in);
    @SuppressWarnings("rawtypes")
	public void menu() throws ClassNotFoundException {
        System.out.println("Hello, what would you like to do?(Add_user(1)/Add_money(2)/withdraw_money(3),Remove_user(4), Show all Users(5), Exit(6)");
        int action = sc.nextInt();
        switch(action) {
            case 1: {
                User.create_user();// FUNCTION CALL FROM USER CLASS
                Class UserClass = Class.forName("User");
                
                print_allUser();
                menu();
                break;
            }
            case 2: {
                System.out.println("Verify your account ");
                System.out.println("Name: ");
                String name = sc.next();
                System.out.println("Password: ");
                String password = sc.next();
                int amt = verify_user(name, password);
                if(amt == -1) {
                    System.out.println("No person of this name and Id");
                }
                else {
                    System.out.println("Current user amount is " + amt);
                    Long amount = Transaction.add_money(amt); // FUNCTION CALL FROM TRANSACTION CLASS
                    System.out.println("Updated user amount is " + amount);
                    try {
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_details", "root", "n2n0n1N0!");
                        Statement st = con.createStatement();
                        int id = get_id(amt);
                        System.out.println("ID is: " + id);
                        String account_query = "UPDATE ACCOUNT_DETAILS SET ACCOUNT_AMOUNT =" + "'"+amount+"'" + " WHERE ACCOUNT_ID =" + "'"+id+"'";
                        String transaction_query = "UPDATE USER_TRANSACTION SET ACCOUNT_CURRENT_BALANCE =" + "'"+amt+"'" + ", USER_TRANSACTION_DETAIL = 'ADDED' , ACCOUNT_POST_BALANCE =" + "'"+amount+"'" + "WHERE ACCOUNT_ID =" + "'"+id+"'";
                        st.executeUpdate(transaction_query);
                        st.executeUpdate(account_query);
                    } catch (Exception e) {
                    }
                    menu();
                }
                break;
            }
            case 3: {
                System.out.println("Verify your account ");
                System.out.println("Name: ");
                String name = sc.next();
                System.out.println("Password: ");
                String password = sc.next();
                int amt = verify_user(name, password);
                if(amt == -1) {
                    System.out.println("No person of this name and Id");
                }
                else {
                    System.out.println("Current user amount is " + amt);
                    Long amount = Transaction.remove_money(amt); // FUNCTION CALL FROM TRANSACTION CLASS
                    System.out.println("Updated user amount is " + amount);
                    try {
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_details", "root", "n2n0n1N0!");
                        Statement st = con.createStatement();
                        int id = get_id(amt);
                        System.out.println("ID is: " + id);
                        String account_query = "UPDATE ACCOUNT_DETAILS SET ACCOUNT_AMOUNT =" + "'"+amount+"'" + " WHERE ACCOUNT_ID =" + "'"+id+"'";
                        String transaction_query = "UPDATE USER_TRANSACTION SET ACCOUNT_CURRENT_BALANCE =" + "'"+amt+"'" + ", USER_TRANSACTION_DETAIL = 'WITHDRAWN' , ACCOUNT_POST_BALANCE =" + "'"+amount+"'" + "WHERE ACCOUNT_ID =" + "'"+id+"'";
                        st.executeUpdate(transaction_query);
                        st.executeUpdate(account_query);
                    } catch (Exception e) {
                    }
                    menu();
                }
                break;
            }
            case 4: {
                System.out.print("Enter Name: ");
                String name = sc.next();
                System.out.print("Enter Password: ");
                String password = sc.next();
                System.out.print("Enter id: ");
                Long id = sc.nextLong();
                int ver = verify_user(name, password);
                if(ver == -1) {
                    System.out.println("No such user found!!");
                }
                else {
                    User.remove_user(id);// FUNCTION CALL FROM USER CLASS
                }
                menu();
            }
            case 5: {
                print_allUser();
            }
            default: {
                break;
            }
        }
    }

    public int get_id(int amt) {
        try { //Connection to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_details", "root", "n2n0n1N0!");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM USER_TRANSACTION");
            while(rs.next()) {
                if(rs.getInt("ACCOUNT_CURRENT_BALANCE") == amt){
                    return rs.getInt("ACCOUNT_ID");
                }
            }
        }
        catch (Exception e) {
        }
        return 0;
    }

    public void print_allUser() {
        System.out.println("Available users are: ");
        try { //Connection to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_details", "root", "n2n0n1N0!");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM USER_DETAILS");
            while(rs.next()) {
                System.out.println(rs.getString("USER_NAME"));
            }
            Statement account_statement = con.createStatement();
            String account_sql_query = "SELECT * FROM ACCOUNT_DETAILS" ;
            account_statement.executeQuery(account_sql_query);
        }
        catch (Exception e) {
        }
    }
    public int verify_user(String name, String password) {
        try { //Connection to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_details", "root", "n2n0n1N0!");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from user_details u \n" +
                    "INNER JOIN account_details a\n" +
                    "WHERE u.USER_ID = a.ACCOUNT_ID;");
            //ResultSet ts = statement.executeQuery("SELECT * FROM ACCOUNT_DETAILS ");
            while(rs.next()) {
                if(rs.getString("user_name").equals(name) && rs.getString("user_password").equals(password)){
                    System.out.println(rs.getInt("ACCOUNT_AMOUNT"));
                    return rs.getInt("ACCOUNT_AMOUNT");
                }
            }
        }
        catch (Exception e) {
        }
        return -1;
    }
    public static void main(String args[]) {
        Main atm = new Main();
        atm.menu();
    }
}
