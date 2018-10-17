package inface;

import org.jsoup.select.*;
import org.jsoup.parser.*;
import org.jsoup.*;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.*;
import org.jsoup.helper.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import inface.JdyApi;
import java.util.*;



public class JdyTest   {
	         
	       
           boolean result=false;
           String errorMessage="";
           String caseName="";
           String parseApimsg="";
           String body="";
           JSONObject jsn=new JSONObject();
           String responseText="";
           JdyTest(String name){caseName=name;}
           JdyTest(){}                    
           ArrayList<String> wrongKey=new ArrayList<String>();
	       public void apiCompareToString(String text ,String url){   
	    	  
	    	   	    
	    	   if(url.indexOf("api.jdy.com")!=-1) {
	    		   try {
	    			   if(jsn.get("result").toString().equals("200")){
	    		    		  result=jsn.get("data").toString().equals(JSON.parseObject(text).get("data").toString());
	    		    		  if(!result){ checkData(body,text); } /* 接口未报错且字符串不一致时需要详细检查数据*/
	    		    		  
	    		    	  } 
	    		    	  else {errorMessage=jsn.toString();}	
	    			   
	    		   }
	    		   catch(NullPointerException e) {
	    			   e.getStackTrace();
	    		    errorMessage="接口报错:"+e.getStackTrace();	    			   
	    			   
	    		   }
	    	   }
	    	   else if(url.indexOf("vip2.youshang.com")!=-1||url.indexOf("vip2-gd.youshang.com")!=-1) {
	    		   try{	    	  
	    			   if(jsn.get("status").toString().equals("200")){
	    				   result=jsn.get("data").toString().equals(JSON.parseObject(text).get("data").toString());
	    				   if(!result){ checkData(body,text); } /* 接口未报错且字符串不一致时需要详细检查数据*/	    		  
	    			   } 
	    			   else {errorMessage=jsn.toString();}	    	  
	    		   		}
	    		   catch(NullPointerException e){
	    			   	e.getStackTrace();
	    			   	errorMessage="接口报错，或者无result"+"/n"+e.getStackTrace();
	    		   	}
	    	   }
	    	   else {
	    		   	errorMessage="暂不支持此接口";
	    	   }
	    
	       }     /* 直接将api返回的数据与标准字符串进行对比 */

	  
           public void checkData(String bodyText,String standard){
        	   JSONObject response=JSON.parseObject(bodyText);
        	   JSONObject jsnStandard=JSON.parseObject(standard);  
        	   Set<?> responseEntry=response.entrySet();
        	   Set<?> standardEntry=jsnStandard.entrySet();
        	   for(Iterator<?> ita =responseEntry.iterator(),itasta=standardEntry.iterator();ita.hasNext();) {
        		  String responseEntryString=String.valueOf(ita.next());
        		  String standardEntryString=String.valueOf(itasta.next());
        		  if(!responseEntryString.equals(standardEntryString)){
        			 String[] splitResponseEntry = responseEntryString.split("=");
        			 if(splitResponseEntry.length==1) {
        				 wrongKey.add(splitResponseEntry[0]);
        			 }
        			 else {
        			 String responseValue=splitResponseEntry[1];
        			 String responseKey=splitResponseEntry[0];
        			 	if(response.get(responseKey).getClass().equals(JSONObject.class)){
        			 		String[] splitStandardEntry = standardEntryString.split("=");	 
        			 		String standardValue=splitStandardEntry[1];
        			 		checkData(responseValue,standardValue);	 
        			 }
        			 	else if(response.get(responseKey).getClass().equals(JSONArray.class)){
        				 populateArray((JSONArray)response.get(responseKey),(JSONArray)jsnStandard.get(standardEntryString.split("=")[0]));
        				 
        			 }
        			 	else if(responseKey.equals("requestid")){}
        			 	else{wrongKey.add(responseKey);}
        			 }
        			        			        			  
        		  } 
        		  
        		   
        	  }
        	   
        	   
           }   /* 对data中的数据进行检查 ,参数为String*/
           
           
           public void checkData(JSONObject response,JSONObject jsnStandard){       
        	   Set<?> responseEntry=response.entrySet();
        	   Set<?> standardEntry=jsnStandard.entrySet();
        	   for(Iterator<?> ita =responseEntry.iterator(),itasta=standardEntry.iterator();ita.hasNext();) {
        		  String responseEntryString=String.valueOf(ita.next());
        		  String standardEntryString=String.valueOf(itasta.next());
        		  if(!responseEntryString.equals(standardEntryString)){
        			 String[] splitResponseEntry = responseEntryString.split("=");
        			 if(splitResponseEntry.length==1) {
        				 wrongKey.add(splitResponseEntry[0]);
        			 }
        			 else {
        				 String responseValue=splitResponseEntry[1];
            			 String responseKey=splitResponseEntry[0];
            			 if(response.get(responseKey).getClass().equals(JSONObject.class)){
            				 String[] splitStandardEntry = standardEntryString.split("=");	 
            				 String standardValue=splitStandardEntry[1];
            				 checkData(responseValue,standardValue);	 
            			 }
            			 else if(response.get(responseKey).getClass().equals(JSONArray.class)){
            				 
            				 populateArray((JSONArray)response.get(responseKey),(JSONArray)jsnStandard.get(standardEntryString.split("=")[0]));
            	
            			 }
            			 else{wrongKey.add(responseKey);}
        				 
        			 }
        			
        			  
        		  } 
        		  
        		   
        	  }
        	   
        	   
           }   /* 对data中的数据进行检查 ,参数为JSONObject*/
           
           public void populateArray(JSONArray responseJsonArray,JSONArray standardJsonArray){
        	         for (int i=0;i<responseJsonArray.size();i++){
        	        	 if(responseJsonArray.get(i).getClass().equals(JSONObject.class)){
        	        		 JSONObject responseJsonObject =(JSONObject)responseJsonArray.get(i);
        	        		 JSONObject standardJsonObject =(JSONObject)standardJsonArray.get(i);
        	        		 checkData(responseJsonObject,standardJsonObject);
        	        	 }
        	        	 else if(responseJsonArray.get(i).getClass().equals(JSONArray.class)){
        	        		 JSONArray  responseArray=(JSONArray)responseJsonArray.get(i);
        	        		 JSONArray  standardArray=(JSONArray)standardJsonArray.get(i);
        	        		 populateArray(responseArray,standardArray); 
        	        	 }
        	        	 else{wrongKey.add(responseJsonArray.get(i).toString().split("=")[0]);}
        	        	 
        	        	 
        	         }
        	   
        	   
        	   
        	   
           }      /* JSONArray 遍历    */
                                 
           
           
           
           public JSONObject parseApi(String address,Map<String,String> params,Map<String,String> cookies ){
        	       Connection doc;
        	       Document dom=null;        	       
        	       JSONObject jsn=null;
        	       doc=Jsoup.connect(address);
        	       doc.data(params);
        	       if(cookies.size()!=0){
        	    	  doc.cookies(cookies);
        	       }
        	       doc.ignoreContentType(true);
        	      try{dom=doc.post();}
        	      catch(IOException e){parseApimsg=e.getStackTrace().toString();};
        	      responseText=dom.body().text();
        	      jsn=JSON.parseObject(dom.body().text());
                  
        	   return jsn;
           }   /* 获取接口数据转jsonObject  */
           
           
           
           public  Map<String, String> urlMap(String[] key,String[] value) throws  IOException {
        	   Map<String, String> map=new HashMap<String,String>();
        	   if(key.length != value.length){
        		   throw new IOException("键值对数量不一致") ;
        	   }
        	   for(int i=0;i<key.length;i++){
        		   map.put(key[i], value[i]);        		   
        	   } 		   
        	   return map;
           }
           /* 生成url参数map */
           public  void catchurl(String url,Map<String,String> uriMap) throws IOException {
        	        Connection con;
        	        con=Jsoup.connect(url);
        	        con.data(uriMap);
        	        con.ignoreContentType(true);
        	   Document  doc=con.post();
        	   body=doc.body().text();
        	   jsn=JSON.parseObject(body);
        	   
           }
           /* 获取接口返回的 文本字符串  */
           public String getToken(String address,Map<String,String> params,String dbid ) throws noServiceException {
        	   String token="";
    	       Connection doc;
    	       Document dom=null;        	       
    	       JSONObject jsn=null;
    	       JSONArray data=null;
    	       doc=Jsoup.connect(address);
    	       doc.data(params);
    	       doc.ignoreContentType(true);
    	      try{dom=doc.timeout(30000).post();}
    	      catch(IOException e){parseApimsg=e.getStackTrace().toString();};
    	      responseText=dom.body().text();
    	       jsn=JSON.parseObject(dom.body().text());
               data=jsn.getJSONObject("data").getJSONArray("data");
               if(data==null) {
            	  throw new noServiceException("此账号无账套");
            	    }
               else {
            	   int size =data.size();
            	  for(int i=0;i<size;i++) {
            		  JSONObject service=(JSONObject )data.get(i);
            		 if(service.getString("dbid").equals(dbid)) {
            			 token=service.getString("accessToken");	 
            		 }
            		 if(token.equals("")) {
            			 throw new noServiceException("此账号无自动化账套");
            		 }         		 
            	  }           	              	          	   
               }
    	   return token;
       }   /* 获取账套TOken */
           
           public String getToken(String address,Map<String,String> params,String dbid,Map<String,String> cookies ) throws noServiceException {
        	   String token="";
    	       Connection doc;
    	       Document dom=null;        	       
    	       JSONObject jsn=null;
    	       JSONArray data=null;
    	       doc=Jsoup.connect(address);
    	       doc.data(params);
    	       if(cookies.size()!=0){
     	    	  doc.cookies(cookies);
     	       }
    	       doc.ignoreContentType(true);
    	      try{dom=doc.post();}
    	      catch(IOException e){parseApimsg=e.getStackTrace().toString();};
    	      responseText=dom.body().text();
    	       jsn=JSON.parseObject(dom.body().text());
               data=jsn.getJSONObject("data").getJSONArray("data");
               if(data==null) {
            	  throw new noServiceException("此账号无账套");
            	    }
               else {
            	   int size =data.size();
            	  for(int i=0;i<size;i++) {
            		  JSONObject service=(JSONObject )data.get(i);
            		 if(service.getString("dbid").equals(dbid)) {
            			 token=service.getString("accessToken");	 
            		 }
            		 if(token.equals("")) {
            			 throw new noServiceException("此账号无自动化账套");
            		 }         		 
            	  }           	              	          	   
               }
    	   return token;
       }   /* 获取账套TOken */                                                                                            
           
                          




                 class noServiceException extends Exception{
                	
                	 public noServiceException(String info) {
                		 super(info);
                	 }
                	 public noServiceException() {
                		 super(); 
                 }
                	 }
}
