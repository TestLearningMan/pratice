package inface;
import org.jsoup.select.*;
import org.jsoup.parser.*;
import org.jsoup.*;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.*;
import org.jsoup.helper.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map.Entry;
import java.util.*;
import inface.JdyTest;



public class JdyApi {
          public int aint=1;
		  public Object data;
		  public String requestid="";
		  public int result=0;
		  public int getAint(){
			  return aint;
		  };
		 
				
			
			public Field[] fieldlist(JSONObject jsn) {
				final Field[] fields;
				Class cls=jsn.getClass();
				fields=cls.getDeclaredFields();
				return fields;
			};
		
			
			public static void main(String phoneurl,String[] phoneurlkey,String[] phoneurlvalue,String compareText) throws Exception{
				 
				
				
				
				String title="";
				 String a="{\"data\":[{\"cid\":1646183,\"hasSearchRight\":true,\"id\":283494354298830,\"index\":1,\"reportid\":300000,\"reportname\":\"\u8001\u677F\u5173\u952E\u6570\u636E\",\"roleid\":148023923592498,\"rolename\":\"\u9500\u552E1\",\"status\":1},{\"cid\":1646183,\"hasSearchRight\":true,\"id\":283494354434979,\"index\":2,\"reportid\":300001,\"reportname\":\"\u5BA2\u6237\u8FD1\u51B5\",\"roleid\":148023923592498,\"rolename\":\"\u9500\u552E1\",\"status\":1},{\"cid\":1646183,\"hasSearchRight\":true,\"id\":283494354381375,\"index\":3,\"reportid\":300002,\"reportname\":\"\u5BA2\u6237\u4FE1\u606F\",\"roleid\":148023923592498,\"rolename\":\"\u9500\u552E1\",\"status\":1},{\"cid\":1646183,\"hasSearchRight\":true,\"id\":283494354544348,\"index\":4,\"reportid\":300003,\"reportname\":\"\u4F9B\u5E94\u5546\u4FE1\u606F\",\"roleid\":148023923592498,\"rolename\":\"\u9500\u552E1\",\"status\":1},{\"cid\":1646183,\"hasSearchRight\":true,\"id\":283494354538968,\"index\":5,\"reportid\":300004,\"reportname\":\"\u5E93\u5B58\u4FE1\u606F\",\"roleid\":148023923592498,\"rolename\":\"\u9500\u552E1\",\"status\":1},{\"cid\":1646183,\"hasSearchRight\":true,\"id\":283494354497148,\"index\":6,\"reportid\":300005,\"reportname\":\"\u5E93\u5B58\u91D1\u989D\u5360\u6BD4\",\"roleid\":148023923592498,\"rolename\":\"\u9500\u552E1\",\"status\":1},{\"cid\":1646183,\"hasSearchRight\":true,\"id\":283494354333562,\"index\":7,\"reportid\":300010,\"reportname\":\"\u91C7\u8D2D\u5173\u952E\u6570\u636E\",\"roleid\":148023923592498,\"rolename\":\"\u9500\u552E1\",\"status\":1},{\"cid\":1646183,\"hasSearchRight\":true,\"id\":283494354560837,\"index\":8,\"reportid\":300011,\"reportname\":\"\u91C7\u8D2D\u8D8B\u52BF\",\"roleid\":148023923592498,\"rolename\":\"\u9500\u552E1\",\"status\":1},{\"cid\":1646183,\"hasSearchRight\":true,\"id\":283494354801980,\"index\":9,\"reportid\":300012,\"reportname\":\"\u70ED\u9500\u5546\u54C1\",\"roleid\":148023923592498,\"rolename\":\"\u9500\u552E1\",\"status\":1},{\"cid\":1646183,\"hasSearchRight\":true,\"id\":283494354420597,\"index\":10,\"reportid\":300013,\"reportname\":\"\u6EDE\u9500\u5546\u54C1\",\"roleid\":148023923592498,\"rolename\":\"\u9500\u552E1\",\"status\":1},{\"cid\":1646183,\"hasSearchRight\":true,\"id\":283494354456730,\"index\":11,\"reportid\":300020,\"reportname\":\"\u9500\u552E\u5173\u952E\u6570\u636E\",\"roleid\":148023923592498,\"rolename\":\"\u9500\u552E1\",\"status\":1},{\"cid\":1646183,\"hasSearchRight\":true,\"id\":283494354607466,\"index\":12,\"reportid\":300021,\"reportname\":\"\u9500\u552E\u8D8B\u52BF\",\"roleid\":148023923592498,\"rolename\":\"\u9500\u552E1\",\"status\":1},{\"cid\":1646183,\"hasSearchRight\":true,\"id\":283494354505362,\"index\":13,\"reportid\":300022,\"reportname\":\"\u6D3B\u8DC3\u5BA2\u6237\u6392\u884C\",\"roleid\":148023923592498,\"rolename\":\"\u9500\u552E1\",\"status\":1},{\"cid\":1646183,\"hasSearchRight\":true,\"id\":283494354791348,\"index\":14,\"reportid\":300030,\"reportname\":\"\u4ED3\u7BA1\u5173\u952E\u6570\u636E\",\"roleid\":148023923592498,\"rolename\":\"\u9500\u552E1\",\"status\":1},{\"cid\":1646183,\"hasSearchRight\":true,\"id\":283494354661648,\"index\":15,\"reportid\":300031,\"reportname\":\"\u6D3B\u8DC3\u5546\u54C1\u6392\u884C\",\"roleid\":148023923592498,\"rolename\":\"\u9500\u552E1\",\"status\":1}],\"requestid\":\"3O6uk1510283670309\",\"result\":200}";
				 Connection doc;
				 Connection doc2;
				 
				doc=Jsoup.connect("http://api.jdy.com/openapi/rest");
					 doc.data("method","jdy.app.role.findRoleReport");
					 doc.data("cid","1646183");
					 doc.data("jxcdbid","795097229118");
					 doc.data("kjdbid","0");
					 doc.data("userName","kingdeetestaa009");
					 doc.data("version","3.0");
					 doc.ignoreContentType(true);
					 Response  doc1=doc.execute();
					 Document dom=doc.post();
					 String body=doc1.body();
					 JSONObject jsn=JSON.parseObject(body);
					/* System.out.println(jsn1.get("data").toString().equals(JSON.parseObject(a).get("data").toString()));
					 System.out.println(a);
					 System.out.println(body1); */
			      String test="[{a:5},{b:6}]";
			     System.out.println(JSON.parse(test).getClass());
			  
					/* Class cls=jsn.getClass();
					 Field[] fields=cls.getDeclaredFields();
					 for(int i=0;i<fields.length;i++)
					 {
						
						 JdyApi Resp= new JdyApi();
						 Field f = fields[i];
						 f.setAccessible(true);
						 System.out.println("type:"+f.getType()+" "+"name:"+f.getName()+" "+"value:"+f.get(jsn));
						 if(f.getName().equals("map")){
					     Map jsnO=(Map)f.get(jsn);
					  
					     for (Iterator iterator = jsn.entrySet().iterator(); iterator  
					                .hasNext();)  
					     {   String entryStr = String.valueOf(iterator.next());  
					    	 System.out.println(entryStr);}
					        
					           
					           
					       }  
							
							 
						 }*/
					  
					 }
					
			}
