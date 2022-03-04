package Classes;

import Databases.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Program {
    DBConnect conn = new DBConnect();
    ResultSet rs;
    boolean is_login_successful = false;
    private Scanner user_input = new Scanner(System.in);

    public Program() throws SQLException {
        start();
    }

    // Start
    public void start() throws SQLException {
        System.out.println("***\tCustomer Contact Saver Application\t***");
        System.out.println("***\tConsole Version\t***");
        System.out.println("***\tLogin below\t***");
        if(login(conn.getConnection())){
            fetchData(conn.getConnection());
            System.out.println("----------------------------------------");
            choices();
        }else{
            System.out.println("Wrong Credentials");
        }
    }

    //choices
    private void choices() throws SQLException {
        System.out.println("Choose what to do below ");
        System.out.println("1. Insert new Customer");
        System.out.println("2. Search Individual Customer");
        System.out.println("3. Remove Customer");
        int choice = user_input.nextInt();
        switch (choice) {
            case 1 -> insertData(conn.getConnection());
            case 2 -> searchCustomer(conn.getConnection());
            case 3 -> removeCustomer(conn.getConnection());
            default -> System.out.println("Unexpected input");
        }
    }

    //short display
    private  void display() throws SQLException {
        System.out.println("\n1. ------ Home");
        System.out.println("2. ------ Search Customer");
        System.out.println("3. ------- Display All Customers");
        System.out.println("0. ------- Quit");

        int response = user_input.nextInt();
        switch (response) {
            case 1 -> new Program();
            case 2 -> searchCustomer(conn.getConnection());
            case 3 -> {
                fetchData(conn.getConnection());
                display();
            }
            case 0 -> System.exit(1);
            default -> System.out.println("UnExpected input!");
        }
    }

    //insert data
    private void insertData(Connection connect) throws SQLException {
        System.out.println("Enter customer id: ");
        int id = user_input.nextInt();
        user_input.nextLine();
        System.out.println("Enter Customer name: ");
        String name = user_input.nextLine();
        System.out.println("Enter Customer phone number: ");
        int phone = user_input.nextInt();
        user_input.nextLine();
        System.out.println("Enter customer Email address: ");
        String email = user_input.next();

        //set cust
        Customer customer = new Customer(id, name, phone, email);

        //insert ke now
        String sql = "INSERT INTO customers(user_id, user_name, user_phone, user_email) VALUES(?, ?, ?, ?)";
        PreparedStatement pst = connect.prepareStatement(sql);
        pst.setInt(1, customer.getCustomerID());
        pst.setString(2, customer.getCustomerName());
        pst.setInt(3, customer.getCustomerPhon());
        pst.setString(4, customer.getCustomerEmail());

        pst.executeUpdate();
        System.out.println("Customer " + customer.getCustomerName() + " Inserted!");
        display();
    }

    //search
    private void searchCustomer(Connection connect) throws SQLException {
        String sql = "SELECT * FROM customers WHERE user_id=?";
        System.out.println("Enter customer id to search: ");
        int custid = user_input.nextInt();

        PreparedStatement pst = connect.prepareStatement(sql);
        pst.setInt(1, custid);
        rs = pst.executeQuery();
        while(rs.next()){
            System.out.println("** \tCustomer Details \t**");
            System.out.println(" ID \t Name \t Phone \t Email");
            System.out.println("---------------------------------------");
            int u_id = rs.getInt("user_id");
            String u_name = rs.getString("user_name");
            int u_phone = rs.getInt("user_phone");
            String u_mail = rs.getString("user_email");

            System.out.println(u_id + "\t " + u_name + "\t " + u_phone + "\t " + u_mail);
            System.out.println("---------------------------------------");
        }
        //
        display();
    }

    //remove
    private void removeCustomer(Connection conn) throws SQLException {
        System.out.println("Enter customer id to remove: ");
        String sql = "DELETE FROM customers WHERE user_id=?";
        int customer_id = user_input.nextInt();

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1,customer_id);
        pst.executeUpdate();

        System.out.println("Customer Removed!");
        //
        display();
    }

    //Retrieve data
    private void fetchData(Connection connect) throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement pst = connect.prepareStatement(sql);
        rs = pst.executeQuery();

        //While
        System.out.println("** \tCustomers Details \t**");
        System.out.println(" ID \t Name \t Phone \t Email");
        System.out.println("---------------------------------------");
        while(rs.next()){
            int userid = rs.getInt("user_id");
            String username = rs.getString("user_name");
            int userphone = rs.getInt("user_phone");
            String useremail = rs.getString("user_email");
            //could just create my customer with these details (Since I Have the customer class);
            System.out.println(userid + "\t " + username + "\t " + userphone + "\t " + useremail);
        }
    }

    //login
    private boolean login(Connection connect) throws SQLException {
        String user_name;
        String user_pass;

        //
        System.out.println("Enter your user id: ");
        int user_id = user_input.nextInt();
        System.out.println("Enter your password: ");
        user_pass = user_input.next();

        //
        String sql = "SELECT * FROM users where user_id=?";
        PreparedStatement pst = connect.prepareStatement(sql);
        pst.setInt(1, user_id);

        rs = pst.executeQuery();

        while(rs.next()){
            int userid = rs.getInt("user_id");
            String userpass = rs.getString("user_pass");
            is_login_successful = user_id == userid && user_pass.equals(userpass);
        }
        return  is_login_successful;
    }
}
