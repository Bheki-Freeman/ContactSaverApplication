package Classes;

public class Customer {
    private int customer_id, customer_phone;
    private String customer_name, customer_email;

    public Customer(int cust_id, String cust_name, int phone, String email){
        this.customer_id = cust_id;
        this.customer_name = cust_name;
        this.customer_phone = phone;
        this.customer_email  = email;
    }

    //get and set
    public int getCustomerID(){
        return this.customer_id;
    }
    public void setCustomerID(int id){
        this.customer_id = id;
    }
    public  String getCustomerName(){
        return  this.customer_name;
    }
    public void setCustomerName(String name){
        this.customer_name = name;
    }
    public int getCustomerPhon(){
        return this.customer_phone;
    }
    public void setCustomerPhone(int phone){
        this.customer_phone = phone;
    }
    public String getCustomerEmail(){
        return  this.customer_email;
    }
    public void setCustomerEmail(String email){
        this.customer_email = email;
    }
}
