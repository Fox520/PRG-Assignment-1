/**
* I hereby acknowledge that the work handed in is my own original work. If I
* have quoted from any other source this information has been correctlyreferenced.
* I also declare that I have read the Namibia University of Science and Technology 
* Policies on Academic Honesty and Integrity as indicated in my course outline and 
*the NUST general information and regulations - Yearbook 2018
*
* @author Lone Wolf
*/
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Assignment {
    static Scanner input = new Scanner(System.in); //to access from anywhere
    static Map<String,double[]> item_stoc_price = new HashMap<String, double[]>();//dictionary {item:[stock,price]}
    static Map<String,String> teller_password = new HashMap<String,String>();//dictionary {teller_name:password}
    static Map<String,ArrayList<String[]>> teller_sales1 = new HashMap<String,ArrayList<String[]>>(); //{teller_name: array item,quan,price}
    static String item_price = "";//store the item and price here then separate to print them both same time. Global so no reset when call method
    static int currentIn =0;//how many inputs so far ,keeping track at funct PrintTwo
    static double total_price = 0;//
    
    public static int LandingPage(){
        System.out.println("*****************************************");
        System.out.println("*\tWelcome to Vinah's Kiosk\t*");
        System.out.println("*\tThe Finest Kiosk in Town\t*");
        System.out.println("*\tWith a Frame of Essence\t\t*");
        System.out.println("*****************************************");
        System.out.println("1. Login as Admin\n2. Login as Teller/Shop assistant\n3. Quit");
        int ret_val=0;
        String is_done = "No";
        do{
            String s_choice_login = input.nextLine();//make string first since newline from int causing problem
            int choice_login = Integer.parseInt(s_choice_login);
            
            switch(choice_login){
                case 3:
                    System.out.println("Thank you, have a nice day!");
                    System.exit(0);
                    break;
                case 1:
                    ret_val = 1;
                    is_done = "Yes";
                    break;
                case 2:
                    ret_val = 2;
                    is_done = "Yes";
                    break;
                default:
                    is_done = "No";
                    System.out.println("Invalid entry, please try again");
                    break;
            }
            
        }while(is_done.equals("No"));
        
        return ret_val;
    }
    
    public static int adminLogin(){
        final String admin_pass = "pass"; //constant password
        String attempt_password; // their attempt at password
        System.out.println("Enter admin password:");
        attempt_password = input.nextLine();
        if(attempt_password.equals(admin_pass)){ // pass is valid
            return 1;
          }else{
              System.out.println("Invalid Password"); // invalid pass
              return 0;
          }
    }
    public static void tellerLogin(){
        
        if(teller_password.isEmpty() == true){//check if any tellers exist
            System.out.println("No tellers have been registered!");
            handleLogins();//try again
            
        }else{
            System.out.println("Enter name:");
            String t_name = input.nextLine();
            if(teller_password.containsKey(t_name)){
                System.out.println("Enter password for "+t_name);//teller exists now test password
                String t_pass = input.nextLine();
                if(t_pass.equals(teller_password.get(t_name))){
                    tellerHomeScreen(t_name);//pass name to method to know current user
                }else{
                    System.out.println("Invalid password!");
                    tellerLogin();
                }
            }else{
                System.out.println(t_name+" not found");
                tellerLogin();
            }
        }
        
        //return 1;//only get here when pass validated because a fail calls function again
    }
    public static String getDisbursement(double change){
        int amt_dollar=0;
        int amt_cent = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        //convert change to string then separate dollars and cents then join and convert to double
        String mix_change = decimalFormat.format(change);
        if(mix_change.contains(".")){
            String[] change_string = mix_change.split("\\.");
            amt_dollar = Integer.parseInt(change_string[0]);//dollar first in array
            amt_cent = (int)Integer.parseInt(change_string[1]);//cent second in array
        }else{
            amt_dollar =Integer.parseInt(mix_change);
            amt_cent = 0;
        }
        
        //int now_dollar = 0;
        int num_200=0,num_100=0,num_50=0,num_20 =0,num_10 = 0,num_5 = 0,num_1 = 0;
        int Legal_Note[] = {200,100,50,20,10,5,1};//valid dollar currency notes and coins available to use
        int Legal_Cent[] = {50,10,5};//valid cents currency available for use
        
        if(Integer.toString(amt_cent).length()<2){
            String nn1 = Integer.toString(amt_cent)+"0";
            amt_cent = Integer.parseInt(nn1);
        }
        for(int i: Legal_Note){
            if (i <= amt_dollar){
                int current = amt_dollar/i;
                //add the number of times it goes in the dollar
                if(i == 200){num_200 = current;}if(i == 100){num_100 = current;}if(i == 50){num_50 = current;}if(i == 20){num_20 = current;}if(i == 10){num_10 = current;}if(i == 5){num_5 = current;}if(i == 1){num_1 = current;}
                int a = current * i;
                //now_dollar += a;
                amt_dollar = amt_dollar %i;
            }
        }
        String string_cent;
        int now_cent = 0;
        int num50c =0,num10c =0,num5c = 0;
        for(int i: Legal_Cent){
            if (i <= amt_cent){
                int current1 = amt_cent/i;
                if(i == 50){num50c = current1;}if(i == 10){num10c = current1;}if(i == 5){num5c = current1;}

                int a = current1 * i;
                now_cent += a;
                amt_cent = amt_cent %i;
                }
        }
        if(Integer.toString(now_cent).length()<2){
            String nn = Integer.toString(now_cent)+"0";
            now_cent = Integer.parseInt(nn);
        }
        //make it 2dp
        if(Integer.toString(now_cent).length() == 1 && now_cent == 0){
            string_cent = Integer.toString(now_cent)+"0";
        }else{
            string_cent = Integer.toString(now_cent);
        }
        //String last = Integer.toString(now_dollar)+"."+string_cent;//the change
        String return_disb = "Disbursed as follows: "+num_200+" x N$200; "+num_100+" x N$100; "+ num_50+" x N$50; "  +num_20+" x N$20; "+num_10+" x N$10; "+num_5+" x N$5; "+num_1+" x N$1; "+num50c+" x 50c; "+num10c+" x 10c; "+num5c+" x 5c";
        return return_disb;
    }
    // item name, quantity, price and total
    //username, rec_items, rec_quan, rec_price, rec_total, amt_tendered, change
    public static void printReceipt(String cashier,String[] product, int[] quantity, double[] price,double[] total, double tendered,double change,double all_tot){
        System.out.println("*****************************************");
        System.out.println("*\tVinah's Kiosk\t*");
        System.out.println("*\tThe Finest Kiosk in Town\t*");
        System.out.println("*\tWith a Frame of Essence\t\t*");
        System.out.println("*****************************************");
        System.out.println();//new line spacing
        System.out.println("Name\t\tQTY\t\tPrice\t\tTotal");
        for(int index=0;index<product.length;index++){
            if(product[index] != null){
                System.out.printf(product[index]+"\t\t%d"+"\t\t"+price[index]+"\t\t%.2f\n",quantity[index], total[index]);
            }
            
        }
        
        //System.out.println("*****************************************");
        System.out.println("-----------------------------------------");
        double vat = (0.15)*all_tot;//calculate vat
        double vat_total = all_tot+vat;//add vat to total
        System.out.println("\t\tVAT@15%\t\t\t\t"+String.format("%.2f", vat)+"\n");
        System.out.printf("Total\t\t\t\t\t\t%.2f\n",vat_total);
        System.out.printf("Tendered\t\t\t\t\t%.2f\n",tendered);
        System.out.printf("Change\t\t\t\t\t\t%.2f\n",change);
        System.out.println("-----------------------------------------");
        System.out.println("Cashier: "+cashier);
        System.out.println("\t\tThank You For Your Support\n\t\tPlease call again");
        System.out.println("*****************************************");
        System.out.println();//new line spacing
        
        
        
    }
    

    public static void tellerHomeScreen(String username){
        boolean stay_in_loop = true;
        while(stay_in_loop){
            System.out.println("Select an option");
            System.out.println("1.Sell item\n2.Overall stock summary\n3.Summary of your sales\n4.Quit");
            int selection = input.nextInt();
            input.nextLine();  // Consume newline left-over
            
            switch(selection){
                case 1://sell item
                    if(!item_stoc_price.isEmpty()){//make sure there are items in stock
                        System.out.println("Enter number of items");
                        int num_items = Integer.parseInt(input.nextLine());
                        String[] rec_items = new String[num_items];
                        double[] rec_price = new double[num_items];
                        double[] rec_total = new double[num_items];
                        int[] rec_quan = new int[num_items];
                        int[] rec_diff_stock = new int[num_items];
                        double totalll =0;
                        double change=0;
                        String disburse = "";
                        double amt_tendered = 0;
                        int num_200=0;int num_100=0;int num_50=0;int num_20=0;int num_10=0;int num_5=0;int num_1=0;int num50c=0;int num10c=0;int num5c=0;

                        for (int index = 0; index < num_items; index++) {
                            System.out.println("Enter item number "+(index+1));//prompt for item name
                            String item_name = input.nextLine();
                            
                            if(item_stoc_price.containsKey(item_name)){//check if item is valid in stock
                                
                                System.out.println("Enter quantity of "+item_name);
                                int quantity = input.nextInt();
                                input.nextLine();  // Consume newline left-over
                                if(quantity>0){
                                    //check if quantity entered is in excess
                                    rec_items[index] = item_name;//add to list of items sold
                                    rec_quan[index] = quantity;//add to list of items sold
                                    
                                    int diff_stock = (int)(item_stoc_price.get(item_name)[0])-quantity;//is double default so type cast to int
                                    double price_per_item = item_stoc_price.get(item_name)[1];//get price from stock dictionary
                                    rec_price[index] = price_per_item;
                                    double total = price_per_item * quantity;
                                    rec_total[index] = total;
                                    totalll += total;
                                    rec_diff_stock[index] = diff_stock;
                                    
                                }else{
                                    System.out.println("Invalid quantity");
                                    tellerHomeScreen(username);
                                }
                            }else{
                                System.out.println(item_name + " not found in stock!");
                                tellerHomeScreen(username);
                            }
                        
                        
                        }
                        boolean is_check = false;
                        System.out.println("Total Cost: "+totalll);
                        System.out.println("Enter amount tendered");
                        amt_tendered = Double.parseDouble(input.nextLine());
                        for(int index=0;index<rec_items.length;index++){
                            if(rec_diff_stock[index] >= 0){
                                if(amt_tendered >= ((totalll*0.15)+totalll)){//check if affordable
                                    change = (amt_tendered) - ((totalll*0.15)+totalll);//change including VAT
                                    double[] new_stock_level = {rec_diff_stock[index], rec_price[index]};
                                    item_stoc_price.put(rec_items[index],new_stock_level);//update complete
                                    is_check = true;
                                    //{teller_name: array item,quan,price}
                                    String[] temp = {rec_items[index],Integer.toString(rec_quan[index]),Double.toString(rec_price[index])};
                                    ArrayList<String[]> current_array = teller_sales1.get(username);//get current array containing array item,quan,price
                                    if(current_array != null){
                                        current_array.add(temp);
                                    }else{
                                        ArrayList temp1 = new ArrayList();
                                        temp1.add(temp);//{rec_items[index],Integer.toString(rec_quan[index]),Double.toString(rec_price[index])};
                                        current_array = temp1;
                                    }
                                    
                                    
                                    teller_sales1.put(username, current_array);
                                    
                                }else{
                                    System.out.println("Unable to complete transaction, you are short of money.");
                                    break;
                                }
                                    
                            }else{
                                System.out.println("Quantity exceeds available stock, either reduce quantity or restock");
                                break;
                            }
                        }
                        if(is_check){
                            System.out.println("Change: "+change);
                            if(change <= 0.9 && change > 0.0){
                                int Legal_Cent[] = {50,10,5};
                                int now_cent = 0;
                                int amt_cent = 0;
                                DecimalFormat decimalFormat = new DecimalFormat("#.00");//round to 2 decimal places
                                String mix_change = decimalFormat.format(change);
                                if(mix_change.contains(".")){ //create change of cents here since dollar is none
                                    String[] change_string = mix_change.split("\\.");
                                    amt_cent = (int)Integer.parseInt(change_string[1]);//cent second in array
                                }
                                for(int i: Legal_Cent){
                                    if (i <= amt_cent){
                                        int current1 = amt_cent/i;
                                        if(i == 50){num50c = current1;}if(i == 10){num10c = current1;}if(i == 5){num5c = current1;}
                                            int a = current1 * i;
                                            now_cent += a;
                                            amt_cent = amt_cent %i;
                                        }
                                    }
                                disburse = "Your change is disbursed as follows: "+num_200+" x N$200; "+num_100+" x N$100; "+ num_50+" x N$50; "  +num_20+" x N$20; "+num_10+" x N$10; "+num_5+" x N$5; "+num_1+" x N$1; "+num50c+" x 50c; "+num10c+" x 10c; "+num5c+" x 5c";
                                
                                }else if(!(change == 0.0)){
                                    disburse = getDisbursement(change);//,amt_tendered); //get disbursement from the method
                                }else{
                                    disburse = "Your change is disbursed as follows: "+num_200+" x N$200; "+num_100+" x N$100; "+ num_50+" x N$50; "  +num_20+" x N$20; "+num_10+" x N$10; "+num_5+" x N$5; "+num_1+" x N$1; "+num50c+" x 50c; "+num10c+" x 10c; "+num5c+" x 5c";
                                }
                            printReceipt(username, rec_items, rec_quan, rec_price, rec_total, amt_tendered, change,totalll);
                            System.out.println(disburse);
                        }
                    }else{
                        System.out.println("There are no products in stock");
                    }
                    break;
                case 2:
                    System.out.println("Product\t\t\tQuantity");
                    for(String stock_item: item_stoc_price.keySet()){// loop through the key
                            System.out.println(stock_item+"\t\t\t"+String.format("%.0f",item_stoc_price.get(stock_item)[0]));
                        
                    }
                    break;
                case 3:
                    if(!teller_sales1.isEmpty()){//item_name:amount_tendered:total
                        System.out.println("Product\t\tQuantity\tPrice");
                        double sum=0;
                        int state_ran = 0;// So not to add product and price initially
                        for(String current_teller: teller_sales1.keySet()){// loop through the key
                                if(current_teller.equals(username)){
                                    for(int i=0;i<teller_sales1.get(username).size();i++){
                                        System.out.println(teller_sales1.get(username).get(i)[0]+"\t"+teller_sales1.get(username).get(i)[1]+"\t\t"+teller_sales1.get(username).get(i)[2]);
                                        total_price += Double.parseDouble(teller_sales1.get(username).get(i)[2]);
                                    }
                                    
                                }else{
                                    System.out.println("No sales made for "+username);
                                }
                                
                        }System.out.printf("\nSub Total:\t\t%.2f\n",total_price);
                    }else{
                        System.out.println("No sales made");
                    }
                    break;
                case 4:
                    //reset the total of prices to get ready for next cashier
                    total_price = 0;
                    handleLogins();
                default:
                        System.out.println("Invalid entry selected");
            }
        }
        //sell item -> receipt
        //items sold by teller
        //print summary of stock
        //print summary of items sold during session
        //exit
    }

    public static void stockControl(){
        //double[] temp = {0, 1}; // stock, price
        //map.put("Coffee",temp);//add elements
        //System.out.println(map.get("Coffee")[1]==1);//print stock of coffee
        System.out.println();
        System.out.println("What would you like to do:\n1.Restock item\n2.Add new item\n3.Change price\n4.Show items that need restock\n5.Back");
        int command = input.nextInt();
        input.nextLine();  // Consume newline left-over
        switch(command){
            case 1://Restock
                System.out.println("Enter item to restock");
                String item_name = input.nextLine();
                
                
                if(item_stoc_price.containsKey(item_name)){ // test if item exists
                    System.out.println("Enter the additional stock amount");
                    double new_stock_amt = input.nextDouble();
                    //get current price
                    if(new_stock_amt > 0){
                        double current_price = item_stoc_price.get(item_name)[1]; //amount first[0], price second[1]
                        double current_stock = item_stoc_price.get(item_name)[0];
                        double[] temp_stock = {(new_stock_amt+current_stock),current_price};//create temp array
                        item_stoc_price.put(item_name, temp_stock);//add to dictionary/hashmap
                        System.out.println("Restock complete");
                        System.out.println(item_name+ " - "+item_stoc_price.get(item_name)[0]+ " - "+item_stoc_price.get(item_name)[1]);
                    }else{
                        System.out.println("Negative stock not allowed!");
                    }
                    
                }else{
                    System.out.println("Item: "+item_name+" not found! Try again");//key not found
                }
                stockControl();
                break;
            case 2://Add item
                String which_one="";
                System.out.println("Enter item name");
                String new_name = input.nextLine();
                System.out.println("Enter quantity of "+ new_name);
                int quantity = input.nextInt();
                input.nextLine();  // Consume newline left-over
                System.out.println("Enter price of "+ new_name);
                double price = input.nextDouble();
                input.nextLine();  // Consume newline left-over
                if(quantity >= 0 || price > 0){
                    double[] temp_array = {quantity,price};//e.g. 48, 3.50
                    item_stoc_price.put(new_name,temp_array); //{item:[48,3.50]}
                    System.out.println(new_name+" has been added to stock");
                }else{
                    if(quantity < 0){
                        which_one = "quantity";
                    }else{
                        if(price<0){
                            which_one = "price";
                        }
                    }
                    System.out.println("Negative "+which_one+" not allowed!");
                }
                
                stockControl();
                break;
            case 3://Change price
                System.out.println("Enter item name to change price");
                item_name = input.nextLine();
                if(item_stoc_price.containsKey(item_name)){
                    System.out.println("Enter new price of "+ item_name);
                    double new_price = input.nextDouble();
                    input.nextLine();  // Consume newline left-over
                    if(new_price > 0){
                        double old_price = item_stoc_price.get(item_name)[1];
                        double[] temp_stock = {item_stoc_price.get(item_name)[0],new_price}; //current stock
                        item_stoc_price.put(item_name,temp_stock);
                        System.out.println(item_name +" price new "+item_stoc_price.get(item_name)[1]+" - old "+old_price );
                    }else{
                        System.out.println("Negative price not allowed!");
                    }
                    
                }else{
                    System.out.println(item_name+" not found!");
                }
                stockControl();
                break;
            case 4://list all items
                if(!item_stoc_price.isEmpty()){
                    
                    for(String stock_item: item_stoc_price.keySet()){// loop through the key
                            //System.out.println(stock_item+"\t"+map.get(stock_item)[0]+"\t"+map.get(stock_item)[1]);
                            if(item_stoc_price.get(stock_item)[0] <= 25){
                                System.out.println(stock_item+" needs restocking");
                            }
                        
                    }
                }else{
                    System.out.println("No items found");
                }
                stockControl();
                break;
            case 5:
                adminHomeScreen();
                break;
            default:
              stockControl();
              break;
        }
    }
    public static void adminHomeScreen(){
      System.out.println();
        System.out.println("*****************************\nWhat would you like to do:***\n1.Stock contol\t\t ****\n2.Add new teller  \t ****\n3.Logout    \t\t ****\n*****************************");
        int to_do = input.nextInt();
        input.nextLine();  // Consume newline left-over
        switch (to_do) {
            case 1:
                stockControl();
                break;
            case 2:
                addTellers();
                break;
            case 3:
                handleLogins();
                break;
            default:
                System.out.println("Invalid option");
                adminHomeScreen();
                break;
        }
    }
    public static void addTellers(){
        System.out.println("Enter teller name:");
        String t_name = input.nextLine();
        System.out.println("Enter password for "+t_name);
        String t_pass = input.nextLine();
        teller_password.put(t_name, t_pass);
        System.out.println();
        System.out.println(t_name+" is now part of the Vinah family");
        System.out.println();
        System.out.println();
        adminHomeScreen();
    }
    public static void main(String[] args) {
        handleLogins();
        
    }
    public static void handleLogins(){
        int p = LandingPage();
        switch(p){
            case 1:
                int q = adminLogin();
                if(q==1){
                    adminHomeScreen();
                }else{
                  handleLogins();
                }
                break;
            case 2:
                tellerLogin();
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
        //String disburse = getDisbursement(0.50);
        //System.out.println(disburse);
    }

}
