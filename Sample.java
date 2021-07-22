package Vaccine;
import java.sql.*;  
import java.util.regex.*;  
import java.util.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   

class Basic{
	public void Func()
	{
		Scanner sc = new Scanner(System.in);
		try
		{
		    User u = new User();
		    Center c = new Center();
			System.out.println("Select an option");
			System.out.println("1. User Registration");
			System.out.println("2. User Login");
			System.out.println("3. Center Login");
			int ch = sc.nextInt();
			switch(ch)
			{
			    case 1: u.Registration();
			    break;
			    case 2: u.Login();
			    break;
			    case 3: c.Login();
			    break;
			    default: System.out.println("Invalid Choice");
			    Func();
			}
		}
		catch(Exception e)
		{
		    System.out.println(e);
		}
	}
}
abstract class Log{
    abstract void Login();
}
class User extends Log{
    String mob;
	void Login()
	{
		boolean exist = false;
		Scanner sc = new Scanner(System.in);
	    System.out.println("Enter mobile number");
	    mob = sc.nextLine();
	    Pattern ptrn = Pattern.compile("[6-9][0-9]{9}");
	    Matcher match = ptrn.matcher(mob);  
	    if(match.find() && match.group().equals(mob))
	    {
	    try{  
	    	Class.forName("com.mysql.cj.jdbc.Driver");  
	    	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine","root","Sniper@31");    
	    	Statement stmt=con.createStatement();  
	    	ResultSet rs=stmt.executeQuery("select mobile from registration where mobile = '" + mob + "';");  
	    	rs.next();
	    	if(rs.getString("mobile").equals(mob));
	    	{
	    		exist = true;
	    	}
	    	con.close();  
	    	}
	    catch(Exception e){System.out.println("No such Registration exists");
	       Basic b = new Basic();
	       b.Func();}  
	    if(exist)
	    {
	        Menu();
	    }
	    }
	    else
	    {
	    	System.out.println("Invalid Mobile Number entered");
	    	 Basic b = new Basic();
		     b.Func();
	    }
	    sc.close();
	}
	void Menu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Select an option");
	    System.out.println("1. Add User");
	    System.out.println("2. View User List");
	    int ch = sc.nextInt();
		switch(ch)
		{
		    case 1: Add();
		    break;
		    case 2: View();
		    break;
		    default: System.out.println("Invalid Choice");
		    Menu();
		    break;
		}
		sc.close();
	}
	void Add()
	{
		boolean exist = false;
		Scanner sc = new Scanner(System.in);
	    System.out.println("Enter Adhar Number");
	    String adhaar = sc.nextLine();
	    Pattern ptrn = Pattern.compile("[0-9]{12}");
	    Matcher match = ptrn.matcher(adhaar);  
	    if(match.find() && match.group().equals(adhaar))
	    {
	    	try{  
		    	Class.forName("com.mysql.cj.jdbc.Driver");  
		    	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine","root","Sniper@31");    
		    	Statement stmt=con.createStatement();  
		    	ResultSet rs=stmt.executeQuery("select adhar from user where adhar = '" + adhaar + "';");  
		    	rs.next();
		    	if(rs.getString("adhar").equals(adhaar));
		    	{
		    		exist = true;
		    	}
		    	con.close();  
		    	}
		    catch(Exception e){
		       System.out.println("Enter User Name");
	           String name = sc.nextLine();
	           System.out.println("Enter User Age");
	           int age = sc.nextInt();
	           int rs =0;
			try{  
		    		Class.forName("com.mysql.cj.jdbc.Driver");  
		    		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine","root","Sniper@31");    
		    		Statement stmt=con.createStatement();  
		    		rs=stmt.executeUpdate("Insert into user (mobile,name,age,adhar) values ('" + mob + "','" + name + "','" + age + "','" + adhaar + "' );");  
		    		ResultSet r=stmt.executeQuery("select id from user where adhar = '" + adhaar + "';");
		    		r.next();
		    		rs=stmt.executeUpdate("Insert into vaccine (userid,dose1,date1,centerid1,dose2,date2,centerid2) values (" + r.getInt("id") + ",'N','00-00-0000',0,'N','00-00-0000',0);");
		    		con.close();  
		    		}catch(Exception ex){ System.out.println("");}
		    	if(rs!=0)
		    	{
		       System.out.println("User Added Successfully");
		    	Menu();
		    }
	           }  
	       if(exist)
	       {
	           System.out.println("This user is already registered");
	           Menu();
	       }
	    }
	    else{
	        System.out.println("Invalid Adhaar number entered");
	        Menu();
	    }
	    sc.close();
	}
	void View()
	{
		Scanner sc = new Scanner(System.in);
		int c=0,age=0,id=0,num=0,uid=0;
		String date = "00-00-0000";
		boolean stat=false;
		try{
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
	    	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine","root","Sniper@31");    
	    	Statement stmt=con.createStatement();  
	    	ResultSet rs=stmt.executeQuery(" select * from user, vaccine where user.id=vaccine.userid and mobile = '" + mob + "';");  
	    	while(rs.next())
	    	{
	    		age=rs.getInt("user.age");
	    		id=rs.getInt("user.id");
	    		System.out.print("User Id:" + rs.getInt("user.id"));
	    		System.out.print("\tName: " + rs.getString("user.name"));
	    		System.out.print("\tAge:" +  rs.getInt("user.age"));
	    		System.out.print("\tAdhaar Number: " + rs.getString("user.adhar"));
	    		System.out.println("\n--Dose Status--");
	    		if(rs.getString("vaccine.dose1").equals("N"))
	    		{
	    			System.out.println("You can schedule for Dose 1");
	    		}
	    		else if(rs.getString("vaccine.dose1").equals("S"))
	    		{
	    		System.out.println("You have scheduled for dose 1 on : " + rs.getString("vaccine.date1"));
	    		}
	    		else
	    		{
	    			System.out.println("You can schedule for Dose 2");
	    		}
	    	}
	    	if(id!=0)
	    	{
	    	System.out.println("\nEnter user id for which you want to schedule vaccination");
	    	uid = sc.nextInt();
	    	rs=stmt.executeQuery(" select dose1 from vaccine where userid = " + uid + ";");  
	    	rs.next();
	    	String dosenum = rs.getString("dose1");
	    	if(rs.getString("dose1").equals("Y") || rs.getString("dose1").equals("N"))
	    	{
	    		stat = true;
	    	}
	    	con.close();
	    	if(stat)
	    	{
	    		System.out.println("Enter your city "); 
    			String city = sc.next();
    			System.out.println("Enter your preferred date in the format dd-mm-yyyy");
    			 date = sc.next();
    			try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}  
	    		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine","root","Sniper@31");    
	    		stmt=con.createStatement();   
	    		System.out.println("No.\tCenter Name\t18-45\t45+\tType of Vaccine\tStatus");
	    		rs=stmt.executeQuery("select * from center where city = '" + city + "' and date = '" + date + "';");
	    		while(rs.next())
	    		{
	    			if(age<=44)
		    		{
		    			num = rs.getInt("sec1");
		    		}
		    		else
		    		{
		    			num = rs.getInt("sec2");
		    		}
	    			System.out.println(rs.getInt("id")+"\t"+rs.getString("name")+"\t"+rs.getInt("sec1")+"\t"+rs.getInt("sec2")+"\t"+rs.getString("Type")+"\t"+rs.getString("status"));
	    		}
	    		con.close();
	    		System.out.println("Enter center id of choice:");
	    		c=sc.nextInt();
	    		if(age<=44)
	    		{
	    			try {
						Class.forName("com.mysql.cj.jdbc.Driver");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}  
		    		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine","root","Sniper@31");    
		    		Statement sta=conn.createStatement();  
		    		int rst= -1;
		    		rst = sta.executeUpdate("Update center set sec1 = " + (num-1) +" where id = " + c +";");
		    		conn.close();
		    		conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine","root","Sniper@31");    
		    		sta=conn.createStatement();  
		    		rst= -1;
		    		if(dosenum.equals("N"))
		    		{
		    		rst = sta.executeUpdate("Update vaccine set dose1 = 'S', date1 = '"+ date +"',centerid1 = " + c + " where userid = " + uid +";");
		    		}
		    		else
		    		{
		    		rst = sta.executeUpdate("Update vaccine set dose2 = 'S', date2 = '"+ date +"',centerid2 = " + c + " where userid = " + uid +";");
		    		}
		    		conn.close();
	    		}
	    		else {
	    			try {
						Class.forName("com.mysql.cj.jdbc.Driver");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}  
		    		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine","root","Sniper@31");    
		    		Statement sta=conn.createStatement();  
		    		int rst=sta.executeUpdate("Update Center set sec2 = " + (num -1) +"where id = " + c +" ;");
		    		conn.close();
		    		Connection conne=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine","root","Sniper@31");    
		    		Statement state=conne.createStatement();  
		    		if(dosenum.equals("N"))
		    		{
		    		rst = sta.executeUpdate("Update vaccine set dose1 = 'S', date1 = '"+ date +"',centerid1 = " + c + " where userid = " + uid +";");
		    		}
		    		else
		    		{
		    		rst = sta.executeUpdate("Update vaccine set dose2 = 'S', date2 = '"+ date +"',centerid2 = " + c + " where userid = " + uid +";");
		    		}
		    		conn.close();
		    		
	    		}
	    	}
	    		if(dosenum.equals("N"))
	    		{
	    			System.out.println("You have scheduled for dose 1 on : " + date);	    			
	    		}
	    		else
	    		{
	    			System.out.println("You have scheduled for dose 2 on : " + date);
	    		}
    		}
    		else {
    			if(id!=0)
    			{
    			System.out.println("You have already scheduled the vaccination");
    			}
    			else
    			{
    				System.out.println("You have added no users yet");
    			}
    		}
	    	Menu();
		}
		catch(SQLException w)
		{
			System.out.println("");
		}
	sc.close();
	}
	void Registration()
	{
		int rs =0;
		boolean exist = false;
		Scanner sc = new Scanner(System.in);
	    System.out.println("Enter mobile number");
	    String str = sc.nextLine();
	    Pattern ptrn = Pattern.compile("[6-9][0-9]{9}");
	    Matcher match = ptrn.matcher(str);  
	    if(match.find() && match.group().equals(str))
	    {
	    	try{  
		    	Class.forName("com.mysql.cj.jdbc.Driver");  
		    	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine","root","Sniper@31");    
		    	Statement stmt=con.createStatement();  
		    	ResultSet r=stmt.executeQuery("select mobile from user where mobile = '" + str + "';");  
		    	r.next();
		    	if(r.getString("mobile").equals(str));
		    	{
		    		exist = true;
		    		System.out.println("User already registered with this mobile number");
		    	}
		    	con.close();  
		    	}
		    catch(Exception e){
	    	try{  
	    		Class.forName("com.mysql.cj.jdbc.Driver");  
	    		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine","root","Sniper@31");    
	    		Statement stmt=con.createStatement();  
	    		rs=stmt.executeUpdate("Insert into registration (mobile) values ('" + str + "');");  
	    		con.close();  
	    		}
	    	catch(Exception ex){ System.out.println(ex);}
		    }
	    	if(rs!=0)
	       System.out.println("User Registration Success");
	    	Basic b = new Basic();
			b.Func();
	    }
	    else{
	        System.out.println("Invalid mobile number entered");
	        Basic b = new Basic();
			b.Func();
	    }
	sc.close();
	}
}

class Center extends Log{
	String mob;
	void Menu() {
			Scanner sc = new Scanner(System.in);
			System.out.println("Select an option");
		    System.out.println("1. Add New Slots");
		    System.out.println("2. View Report for Your Center");
		    int ch = sc.nextInt();
			switch(ch)
			{
			    case 1: Add();
			    break;
			    case 2: View();
			    break;
			    default: System.out.println("Invalid Choice");
			    Menu();
			    break;
			}
			sc.close();
		}
	void Add() {
		String name="",city="";
		Scanner sc = new Scanner(System.in);
		try{  
	    	Class.forName("com.mysql.cj.jdbc.Driver");  
	    	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine","root","Sniper@31");    
	    	Statement stmt=con.createStatement();  
	    	ResultSet rs=stmt.executeQuery("select name,city from center where mobile = '" + mob + "';");  
	    	rs.next();
	    	name=rs.getString("name");
	    	city=rs.getString("city");
	    	con.close();  
	    	}
		catch(Exception e)
		{
			System.out.println("");
		}
		System.out.println("Enter Date of slot updation");
	    String date = sc.next();
	    //date check if it is already present or not
	    System.out.println("Enter number of slots for 18-45");
	    int s1 = sc.nextInt();
	    System.out.println("Enter number of slots for 45+");
	    int s2 = sc.nextInt();
	    System.out.println("Enter Vaccine Type: Covishield or Covaxin");
	    String t = sc.next();
	    System.out.println("Choose whether Paid or Free");
	    String s = sc.next();
	    try{  
	    	Class.forName("com.mysql.cj.jdbc.Driver");  
	    	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine","root","Sniper@31");    
	    	Statement stmt=con.createStatement();   
    		int rs=stmt.executeUpdate("Insert into center (name,sec1,sec2,Type,status,city,date,mobile) values ('" + name + "'," + s1 + "," + s2 + ",'" + t +"','" + s + "','" + city +"','"+ date +"','" + mob + "' );"); 
	    con.close();
	    System.out.println("Values added successfully");
	    Menu();
	    }
    	catch(Exception e)
	    {
    		System.out.println("");
	    }
	    sc.close();
	}
	void View()
	{
		try{
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
	    	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine","root","Sniper@31");    
	    	Statement stmt=con.createStatement();  
	    	ResultSet rs=stmt.executeQuery(" select * from center where mobile = '" + mob + "';");  
	    	System.out.println("\nName\tCity\tDate\t18-45Slots\t45+Slots\tType of Vaccine\tStatus");
	    	while(rs.next())
	    	{
	    		System.out.print("\n" + rs.getString("name"));
	    		System.out.print("\t" + rs.getString("city"));
	    		System.out.print("\t" + rs.getString("date"));
	    		System.out.print("\t" +  rs.getInt("sec1"));
	    		System.out.print("\t" + rs.getInt("sec2"));
	    		System.out.print("\t" + rs.getString("Type"));
	    		System.out.print("\t" + rs.getString("status"));
	    	}
		}catch(Exception e)
		{
			System.out.println("");
		}
		Menu();
	}
	void Login()
	{
		boolean exist = false;
		Scanner sc = new Scanner(System.in);
	    System.out.println("Enter mobile number");
	    mob = sc.nextLine();
	    Pattern ptrn = Pattern.compile("[6-9][0-9]{9}");
	    Matcher match = ptrn.matcher(mob);  
	    if(match.find() && match.group().equals(mob))
	    {
	    try{  
	    	Class.forName("com.mysql.cj.jdbc.Driver");  
	    	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine","root","Sniper@31");    
	    	Statement stmt=con.createStatement();  
	    	ResultSet rs=stmt.executeQuery("select * from center where mobile = '" + mob + "';");  
	    	rs.next();
	    	if(rs.getString("mobile").equals(mob));
	    	{
	    		exist = true;
	    	}
	    	con.close();  
	    	}
	    catch(Exception e){System.out.println("No such Center data exists");
	       Basic b = new Basic();
	       b.Func();}  
	    if(exist)
	    {
	        Menu();
	    }
	    }
	    else
	    {
	    	System.out.println("Invalid Mobile Number entered");
	    	 Basic b = new Basic();
		     b.Func();
	    }
	}
	}
class Sample{
	public static void main(String args[]) throws SQLException
	{  
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-mm-yyyy");  
		LocalDateTime now = LocalDateTime.now();    
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}  
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine","root","Sniper@31");    
		Statement sta= conn.createStatement();
		int rst=sta.executeUpdate("Update vaccine set dose1 = 'Y' where date1 = '" + dtf.format(now) +"';");
		rst=sta.executeUpdate("Update vaccine set dose2 = 'Y' where date2 = '" + dtf.format(now) +"';");
		conn.close();
		Basic b = new Basic();
		b.Func();
	}
}