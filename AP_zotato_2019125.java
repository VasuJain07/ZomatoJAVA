package ap;
import java.util.*;


public class AP_zotato_2019125 {
	
	public static wallet zotato=new wallet();//note zotato's wallet.reward will contain the delivery charges.

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		HashMap<Integer,Restaurant> allrest=new HashMap<Integer,Restaurant>();
		Restaurant toadd=new authenticrest("Shah",0);
		allrest.put(1,toadd);
		Restaurant toadd2 =new Restaurant("Ravi's");
		allrest.put(2, toadd2);
		Restaurant toadd3=new authenticrest("The Chinese",0);
		allrest.put(3, toadd3);
		Restaurant toadd4=new fastfoodrest("Wang's",0);
		allrest.put(4, toadd4);
		Restaurant toadd5=new Restaurant("Paradise");
		allrest.put(5, toadd5);
		
		HashMap<Integer,customer> allcust=new HashMap<Integer,customer>();
		customer custy1=new elitecust("Ram",zotato);
		allcust.put(1,custy1);
		customer custy2=new elitecust("Sam",zotato);
		allcust.put(2,custy2);
		customer custy3=new specialcust("Tim",zotato);
		allcust.put(3, custy3);
		customer custy4=new customer("Kim",zotato);
		allcust.put(4, custy4);
		customer custy5=new customer("Jim",zotato);
		allcust.put(5, custy5);
		
		
		while(true) {
			parentmenu();
			int input1=in.nextInt();
			
			if(input1==1) {
				User myuser=new restowner();
				myuser.displaymenu(allrest,new customer("nouse",zotato));
			}
			
			else if(input1==2) {
//				User myuser=new customer();
//				myuser.displaymenu(allrest);
				System.out.println("1. Ram (Elite)\r\n" + 
						"2. Sam (Elite)\r\n" + 
						"3. Tim (Special)\r\n" + 
						"4. Kim\r\n" + 
						"5. Jim");
				User myuser=allcust.get(in.nextInt());
				myuser.displaymenu(allrest,(customer)myuser);
			}
			else if(input1==3) {
				System.out.println("1) Customer List\r\n" + 
						"2) Restaurant List");
				int stats=in.nextInt();
				if(stats==1) {
					System.out.println("1. Ram (Elite)\r\n" + 
							"2. Sam (Elite)\r\n" + 
							"3. Tim (Special)\r\n" + 
							"4. Kim\r\n" + 
							"5. Jim");
					int custid=in.nextInt();
					System.out.println(allcust.get(custid).name+", "+allcust.get(custid).address+", "+allcust.get(custid).getbalance()+" /-");
				}
				else if(stats==2) {
					System.out.println("Choose Restaurant\r\n" + 
							"1) Shah (Authentic)\r\n" + 
							"2) Ravi’s\r\n" + 
							"3) The Chinese (Authentic)\r\n" + 
							"4) Wang’s (Fast Food)\r\n" + 
							"5) Paradise");
					int restid=in.nextInt();
					System.out.println(allrest.get(restid).name);
					System.out.println("amount:"+allrest.get(restid).getwally().getValue());
					System.out.println("rewards:"+allrest.get(restid).getwally().getReward());
				}
			}
			else if(input1==4) {
				System.out.println("Total Company balance - INR "+zotato.getValue()+" /-");
				System.out.println("Total Delivery Charges Collected - INR "+zotato.getReward()+" /-");
			}
			else if(input1==5) {
				System.out.println("program ended");
				break;
			}
		}
		
	}
	
	public static void parentmenu() {
		System.out.println("Welcome to Zotato:");
		System.out.println("1) Enter as Restaurant Owner");
		System.out.println("2) Enter as Customer");
		System.out.println("3) Check User Details");
		System.out.println("4) Company Account details");
		System.out.println("5) Exit");
	}
	
}

class carter{//this function will manage all the transactions and wallets so as to follow encapsulation and abstraction 
	private double balance=1000;
	private double reward=0;
	private ArrayList<String> cart=new ArrayList<String>();
	
	public ArrayList<String> getcart(){
		return this.cart;
	}
	
	public double getbalance() {
		return this.balance;
	}
	public double getreward() {
		return this.reward;
	}
	private void manageuser(double cost,double reward) {
		if(this.reward<cost) {
			cost-=this.reward;
			this.reward=reward;
			this.balance-=cost;
		}
		else {
			this.reward-=cost;
			this.reward+=reward;
		}
	}
	
	public double[] processaddorder(customer oforder,Restaurant chosen,int id,int quant,double[] arr) {
		double[] arry=chosen.processorder(oforder, id, quant,arr);
		food foo=chosen.getFooditems().get(id);
		String ordersum=(id+" "+chosen.name+" - "+foo.getName()+" "+foo.getPrice()+" "+quant+
				" "+foo.getOffer()+"% off ");
		cart.add(ordersum);
		System.out.println("Items added to cart");
		return arry;
	}
	public double[] checkout(double[] details,Restaurant which,double del) {
		manageuser(details[0]+del,details[1]);
		which.getwally().setValue(which.getwally().getValue()+details[0]);
		which.getwally().setReward(which.getwally().getReward()+details[1]);
		this.cart=new ArrayList<String>();
		double []freshdetails=new double[3];
		return freshdetails;
		////after checkout the cart contents are flushed out 
		//so is the contents of detail double[] array as it contains its required details.
		
	}
	
	
}

class wallet{//this wallet is to be used by the company ZOTATO and restaurants
	private double value=0;
	private double reward=0;
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public double getReward() {
		return reward;
	}
	public void setReward(double reward) {
		this.reward = reward;
	}
}

interface User{
	void displaymenu(HashMap<Integer,Restaurant> allrest,customer theuser);
}

class restowner implements User{
	Scanner in=new Scanner(System.in);
	@Override
	public void displaymenu(HashMap<Integer,Restaurant> allrest,customer theuser) {
		System.out.println("Choose Restaurant\r\n" + 
				"1) Shah (Authentic)\r\n" + 
				"2) Ravi’s\r\n" + 
				"3) The Chinese (Authentic)\r\n" + 
				"4) Wang’s (Fast Food)\r\n" + 
				"5) Paradise");
		int restkey=in.nextInt();
		allrest.get(restkey).ownermenu();
		
	}
}

class customer implements User {
	Scanner in=new Scanner(System.in);
	private carter userwally= new carter();
	private ArrayList<String> recent=new ArrayList<String>();
	final String name;
	private wallet zotato;
	final String address="Pune";
	
	public customer(String name,wallet zotato){
		this.name=name;
		this.zotato=zotato;
	}
	public double getbalance() {
		return userwally.getbalance();
		
	}
	public double[] finalbilling=new double[3];
	@Override
	public void displaymenu(HashMap<Integer,Restaurant> allrest,customer theuser) {
		while(true) {
			System.out.println("Welcome "+name);
			System.out.println("Customer Menu\r\n" + 
					"1) Select Restaurant\r\n" + 
					"2) checkout cart\r\n" + 
					"3) Reward won\r\n" + 
					"\r\n" + 
					"4) print the recent orders\r\n" + 
					"5) Exit");
			int inputc=in.nextInt();
			
			Restaurant bought=allrest.get(1);
			
			if(inputc==1) {
				System.out.println("Choose Restaurant\r\n" + 
						"1) Shah (Authentic)\r\n" + 
						"2) Ravi’s \r\n" + 
						"3) The Chinese (Authentic)\r\n" + 
						"4) Wang’s (Fast Food)\r\n" + 
						"5) Paradise");
				int restkey=in.nextInt();
				allrest.get(restkey).listitems();
				int itemid=in.nextInt();
				System.out.println("Enter item quantity -");
				int itemquant=in.nextInt();	
				bought=allrest.get(restkey);
				finalbilling=this.userwally.processaddorder(this, allrest.get(restkey), itemid, itemquant,finalbilling);
			}
			
			else if(inputc==2) {
				ArrayList<String> cart=this.userwally.getcart();
				System.out.println("Items in Cart:");
				for(int i=0;i<cart.size();i++) {
					System.out.println(cart.get(i));
					this.recent.add(cart.get(i));
				}
				double deliverycharge=40;
				double todeduct=0;
				if(this.getClass().getName().equalsIgnoreCase("ap.elitecust")) {
					deliverycharge=0;
					todeduct=50;
				}
				else if(this.getClass().getName().equalsIgnoreCase("ap.specialcust")) {
					deliverycharge=20;
					todeduct=25;
				}
				System.out.println("Delivery charge -"+deliverycharge+"/-");
				double withoutdel=finalbilling[0];
				if(withoutdel>=200) {
					withoutdel-=todeduct;
				}
				System.out.println("Total order value -"+(withoutdel+deliverycharge)+"/-");
				System.out.println("1) Proceed to checkout");
				int ini=in.nextInt();
				if(ini==1) {
					zotato.setValue(zotato.getValue()+(withoutdel*0.01));
					zotato.setReward(zotato.getReward()+deliverycharge);
					System.out.println((int)finalbilling[2]+" items successfully bought for INR "+(withoutdel+deliverycharge)+"/-");
					finalbilling=this.userwally.checkout(finalbilling,bought,deliverycharge);//
				}
				
			}
			else if(inputc==3) {
				System.out.println("Reward Point:"+this.userwally.getreward());
			}
			else if(inputc==4) {
				for(int i=this.recent.size()-1;i>=0;i--) {
					System.out.println(recent.get(i));
				}
			}
			else if(inputc==5){
				break;
			}	
		}
			
	}
}

class elitecust extends customer{
//	double a=this.wally.getValue();
	private carter userwally= new carter();
	private wallet zotato;
	private ArrayList<String> recent=new ArrayList<String>();
	
	elitecust(String name,wallet zotato){
		super(name,zotato);
	}
	
}
class specialcust extends customer{
	private wallet zotato;
	private carter userwally= new carter();
	private ArrayList<String> recent=new ArrayList<String>();
	
	specialcust(String name,wallet zotato){
		super(name,zotato);
	}
}

class Restaurant{
	Scanner in=new Scanner(System.in);
	private wallet wally=new wallet();
	String name;
	double billoffer;
	private HashMap<Integer,food> fooditems=new HashMap<Integer,food>();
	
	public HashMap<Integer, food> getFooditems() {
		return fooditems;
	}
	
	public wallet getwally() {
		return this.wally;
	}

	static int id=1;
	
	Restaurant(String name){
		this.name=name;
		billoffer=0;
	}
	

	public double[] processorder(customer oforder,int idorder,int quantorder,double[] totarray) {

		double price=0;
		price+=fooditems.get(idorder).getPrice()*quantorder;
		price-=price*fooditems.get(idorder).getOffer()/100;
		fooditems.get(idorder).setQuantity(fooditems.get(idorder).getQuantity()-quantorder);
		price-=price*this.billoffer/100;
//		this.wally.setValue(this.wally.getValue()+price);
		double reward=0;
		if(this.getClass().getName().equalsIgnoreCase("ap.fastfoodrest")) {
			reward+=10*(int)(price/150);
		}
		else if(this.getClass().getName().equalsIgnoreCase("ap.authenticrest")) {
			reward+=25*(int)(price/200);
			if(price>=100) {
				price-=50;
			}
		}
		else {
			reward+=5*(int)(price/100);
		}
//		this.wally.setReward(this.wally.getReward()+reward);
//		double[] totarray=new double[3];
		totarray[0]+=price;
		totarray[1]+=reward;
		totarray[2]+=quantorder;

		return totarray;
	}
	private void additem() {

		System.out.println("Food name:");
		String foodname=in.next();
		System.out.println("item price:");
		double itemprice=in.nextDouble();
		System.out.println("item quantity:");
		int quantity=in.nextInt();
		String category="";
		if(this.getClass().getName().equalsIgnoreCase("ap.authenticrest")) {
			System.out.println("item category:");
			category=in.next();
		}
		System.out.println("Offer:");
		double offer=in.nextDouble();
		

		if(!this.getClass().getName().equalsIgnoreCase("ap.authenticrest")) {
			food foodadd=new food(foodname,itemprice,quantity,offer,id++);
			fooditems.put(id-1,foodadd);
			food foo=foodadd;
			System.out.println(foo.getID()+" "+this.name+" - "+foo.getName()+" "+foo.getPrice()+" "+foo.getQuantity()+
					" "+foo.getOffer()+"% off ");
		}
		else {
			food foodadd=new food(foodname,itemprice,quantity,offer,id++,category);
			fooditems.put(id-1,foodadd);
			food foo=foodadd;
			System.out.println(foo.getID()+" "+this.name+" - "+foo.getName()+" "+foo.getPrice()+" "+foo.getQuantity()+
					" "+foo.getOffer()+"% off "+foo.getCategory());
		}

	}
	
	public void listitems() {
		System.out.println("Choose item by code");
		if(this.getClass().getName().equalsIgnoreCase("ap.authenticrest")) {
			for(int i=1;i<=fooditems.size();i++) {
				food foo=fooditems.get(i);
				System.out.println(foo.getID()+" "+this.name+" - "+foo.getName()+" "+foo.getPrice()+" "+foo.getQuantity()+
						" "+foo.getOffer()+"% off "+foo.getCategory());
			}
		}
		else {
			for(int i=1;i<=fooditems.size();i++) {
				food foo=fooditems.get(i);
				System.out.println(foo.getID()+" "+this.name+" - "+foo.getName()+" "+foo.getPrice()+" "+foo.getQuantity()+
						" "+foo.getOffer()+"% off ");
			}
		}
	}
	
	private void edititem() {
//		System.out.println("Choose item by code");
		listitems();
		int input=in.nextInt();
		
		System.out.println("Choose an attribute to edit:\r\n" + 
				"1) Name\r\n" + 
				"2) Price\r\n" + 
				"3) Quantity\r\n" + 
				"4) Category\r\n" + 
				"5) Offer");
		int at=in.nextInt();
		System.out.print("Enter the new ");
		switch(at) {
		case 1:
			System.out.println("Name:");
			fooditems.get(input).setName(in.next());
			break;
		case 2:
			System.out.println("Price:");
			fooditems.get(input).setPrice(in.nextDouble());
			break;
		case 3:
			System.out.println("Quantity:");
			fooditems.get(input).setQuantity(in.nextInt());
			break;
		case 4:
			System.out.println("Category:");
			fooditems.get(input).setCategory(in.next());
			break;
		case 5:
			System.out.println("Offer:");
			fooditems.get(input).setOffer(in.nextDouble());
			break;
		}

		if(this.getClass().getName().equalsIgnoreCase("ap.authenticrest")) {
				food foo=fooditems.get(input);
				System.out.println(foo.getID()+" "+this.name+" - "+foo.getName()+" "+foo.getPrice()+" "+foo.getQuantity()+
						" "+foo.getOffer()+"% off "+foo.getCategory());
			
		}
		else {
				food foo=fooditems.get(input);
				System.out.println(foo.getID()+" "+this.name+" - "+foo.getName()+" "+foo.getPrice()+" "+foo.getQuantity()+
						" "+foo.getOffer()+"% off ");
			
		}
	}

	
	public void ownermenu() {
		boolean exiter=false;
		while(!exiter) {
			System.out.println("Welcome "+name);
			System.out.println("1) Add item\r\n" + 
					"2) Edit item\r\n" + 
					"3) Print Rewards\r\n" + 
					"4) Discount on bill value\r\n" + 
					"5) Exit");
			int input=in.nextInt();
			if(input==1) {
				this.additem();
			}
			else if(input==2) {
				this.edititem();
			}
			else if(input==3) {
				System.out.println("Reward Points :"+wally.getReward());
			}
			else if(input==4) {
				System.out.println("Enter offer on total bill value -");
				this.billoffer=in.nextDouble();
			}
			else if(input==5) {
				exiter=true;
			}
		}
	}
	
}

class fastfoodrest extends Restaurant{
	private double discount=0;
	
	fastfoodrest(String name,float discount){
		super(name);
		this.discount=discount;
	}
	public void setdiscount(float discount) {
		this.discount=discount;
	}
	
}

class authenticrest extends Restaurant{
	private double discount;
	private double authdiscount=50;
	
	authenticrest(String name,float discount){
		super(name);
		this.discount=discount;
	}
	public void setdiscount(float discount) {
		this.discount=discount;
	}
}

class food{
	private String name;
	private double price;
	private int quantity;
	private int ID;
	private String category="";
	private double offer;
	
	food(String name,double price,int quantity,double offer,int ID) {
		this.name=name;
		this.price=price;
		this.quantity=quantity;
		this.ID=ID;
		this.offer=offer;
	}
	food(String name,double price,int quantity,double offer,int ID,String category) {
		this.name=name;
		this.price=price;
		this.quantity=quantity;
		this.ID=ID;
		this.offer=offer;
		this.category=category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getOffer() {
		return offer;
	}
	public void setOffer(double offer) {
		this.offer = offer;
	}	
}
