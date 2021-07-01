package Receipt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Receipt {

	public static void main(String[] args) {
	    int inputNo = 0, count = 0, totalprice = 0, VAT = 0, num = 0;
	    Scanner scan =  new Scanner(System.in); 
	    
	    ArrayList<Receipt_Arraylist> orderList = new ArrayList<Receipt_Arraylist>();
		
		    while(true) {
		    	Receipt_Arraylist item = new Receipt_Arraylist();
		    	try {
		    		Class.forName("com.mysql.cj.jdbc.Driver"); 
		    		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/kopo03", "root" , "635300");
		    
		    		System.out.print("상품 번호를 입력하세요 : ");
		    		inputNo = scan.nextInt();
		    
		    		Statement stmt = conn.createStatement();
		    		ResultSet rset = stmt.executeQuery("select * from goods where no = " + inputNo ); 
		    		Statement st = conn.createStatement();
		    		ResultSet rs = st.executeQuery("select count(*) from goods"); 
		    		
		    		while(rs.next()) {
		    			num = rs.getInt(1);
		    		}
		    			if (rset.next()) { 	    	
		    				System.out.printf("%d %s %d원 %s\n", rset.getInt(1), rset.getString(2), rset.getInt(3), rset.getString(4));
		    				item.name = rset.getString(2);
		    				item.price = rset.getInt(3);	
		    				
		    			}else {
		    				int type2;
		    				System.out.println("해당하는 상품이 없습니다.");
		    				System.out.println("1. 다시 입력 2. 구매종료");
		    				type2 = scan.nextInt();
		    				if (type2 == 1) {
		    				continue;
		    				}
		    				else {
		    					break;
		    				}
		    			} 
		    			rset.close(); 
		    			stmt.close(); 
		    			conn.close(); 
		    	}catch(Exception e) {
		    				e.printStackTrace();
		    	}
		    
		    	System.out.println("구매 갯수 :  ");
		    	count = scan.nextInt();
		    	item.total = item.price * count; 
		    	item.count = count;
	 		
		    	orderList.add(item);
		    	
		    	int type;
		    	System.out.print("1. 추가구매, 2. 구매종료\n");
		    	type = scan.nextInt();
		    	System.out.println("-----------------------------------------------");
		    
		    if(type == 2) {
		    break;
		    	}
		    }	
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
	    	String today = format.format(new Date());
	    	String today2 = format2.format(new Date());
		    	
			System.out.println("       [영수증]");
			System.out.println(" ");
			System.out.println("템퍼러쳐 / 102-04-61747 / 임선정");
			System.out.println("서울 마포구 포은로2나길 46 (합정동) 1층");
			System.out.println("010-4540-4286 / " + today2 + "-01-0008");
			System.out.println(today);
			System.out.println(" ");
			System.out.println("CASHIER_ #HAPPY_BIRTHDAY_MIKHAIL");
			System.out.printf("%s\t\t%s\t%s\t %s\n", "상 품 명", "단 가", "수 량", "금 액");
			System.out.println("----------------------------------------------");
			   
			for(int index = 0; index < orderList.size(); index++) {
				totalprice += orderList.get(index).price * orderList.get(index).count;
				VAT = totalprice/11;
				System.out.printf("%s\t\t\t%d\t %d\t%d원\n", orderList.get(index).name, orderList.get(index).price, 
									orderList.get(index).count, orderList.get(index).price * orderList.get(index).count );
			}						
			System.out.println("----------------------------------------------");
			System.out.printf("%s %34d \n", "합 계 금 액", totalprice);
			System.out.println("----------------------------------------------");
			System.out.printf("%18s %5s %11d \n", "부가세", "과세물품가액", totalprice-VAT);
			System.out.printf("%18s %24d \n", "부가세", VAT);
			System.out.println(" ");	
	}
}
