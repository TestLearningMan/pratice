package inface;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.IOException;
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;
import java.util.*;
import inface.JdyTest;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;



public class OutPutReoprt  {
	
	String now=getDate();   
	String filePath="bin\\\\"+now+".html";
	String to ="332791537@qq.com";
	String  from="";
	String host="smtp.yeah.net";
	boolean debug=false;
	
	public OutPutReoprt(String toAddress){
		to=toAddress;
		// TODO Auto-generated constructor stub
	}
	
	public OutPutReoprt(String toAddress,String fromAddress){
		 to=toAddress;
		 from=fromAddress;
		// TODO Auto-generated constructor stub
	}
	public OutPutReoprt( ) {
		
		// TODO Auto-generated constructor stub
	}
	
	
	@SuppressWarnings("deprecation")
	public String getDate() {
		long time=System.currentTimeMillis();
		Date now=new Date();
		now.setTime(time);
		return now.toLocaleString().replace(" ", "").replace(":", "点");

	}
	
	/*public static void main(String args[]) throws IOException{
		
		outPutHtml();
		System.out.println("success");
	}*/

	public  void outPutHtml(AppReport report,Date startTime,Date endTime )throws FileNotFoundException {			
		StringBuilder stringHtml = new StringBuilder(); 
		String table="";
		int success=0;
		int fail=0;
		PrintStream printStream=null;
		
		try{  
		   //打开文件  
			
		 printStream = new PrintStream(new FileOutputStream(filePath));  
		 
		//输入HTML文件内容  
		stringHtml.append("<html><head>");  
		stringHtml.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");  
		stringHtml.append("<title>自动化测试报告</title>");   
		stringHtml.append("</head>");  
		stringHtml.append("<body><p>自动化测试结果</p>");  
		ArrayList<JdyTest> jTest=report.outputReport;	
		for(int i=0;i<jTest.size();i++ ){
			JdyTest unit=jTest.get(i);	
		    table=table+"<tr><th>"+i+"</th>"+"<th>"+unit.caseName+"</th>"+"<th>"+unit.result+"</th>"+"<th>"+unit.wrongKey+"</th>"+"<th>"+unit.errorMessage+"</th>"+"<th>"+unit.parseApimsg+"</th>";
		    if(unit.result){success=success+1;}	//计算成功的用力书	    
			}			
		fail=jTest.size()-success;	
		stringHtml.append("<p>startTime:"+startTime+"</p><p>endTime:"+endTime+"</p><p>totalExcute:"+jTest.size()+"</p><p>successNumber:"+success+"</p><p>failNumber:"+fail+"</p><table>");		
		stringHtml.append("<table><tr><th>ID</th><th>caseName</th><th>result</th><th>wrongKey</th><th>errorMessage</th><th>AutoTestError</th></tr>");
		stringHtml.append(table+"</table></body></html>");  
		 //将HTML文件内容写入文件中  
		printStream.println(stringHtml.toString());  
		
		}catch (Exception e) {  
		      
		    e.printStackTrace();  
		}  	
	
	
	}	
	
	public void send( String emailUserName,String password) throws AddressException, MessagingException{
		final String from=emailUserName;
		final String epassword=password;
		Properties props =  System.getProperties();
		//props.setProperty("mail.user", "q7213536@yeah.net");
		//props.setProperty("mail.password", "pwy101813551");
		props.put("mail.smtp.host",host);
		props.put("mail.smtp.auth", true);
	    props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    props.setProperty("mail.smtp.socketFactory.fallback", "false");
	    props.setProperty("mail.smtp.port", "465");
	    props.setProperty("mail.smtp.socketFactory.port", "465");
	    props.put("mail.store.protocol", "pop3");
	    props.put("mail.transport.protocol", "smtp");	
		Session session = Session.getInstance(props, 
                 new Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication() {
                       return new PasswordAuthentication(from,epassword);
                    }});  //  getDefaultInstance  获取缓存中的session，切换用户登录邮箱时，会产生发送人与登录名不一致的BUG
		session.setDebug(debug);
		try {
		MimeMessage message=new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		InternetAddress[] address= {new InternetAddress(to)};
		message.setRecipients(Message.RecipientType.TO, address);
		message.setSubject("This is the Subject line!");
		MimeBodyPart mbp1=new MimeBodyPart();
		mbp1.setText("this is message body");
		MimeBodyPart mbp2=new MimeBodyPart();
		mbp2.attachFile(filePath);
		Multipart multipart=new MimeMultipart();
		multipart.addBodyPart(mbp1);
		multipart.addBodyPart(mbp2);
		message.setContent(multipart);
		message.setSentDate(new Date());
		Transport.send(message);
		
		/*String username = "你的电子信箱用户名";
	      String password = "你的电子信箱密码";
	      message.saveChanges(); //    implicit with send()
	      Transport transport = session.getTransport("smtp");
	      transport.connect("mail.htf.com.cn", username, password);
	      transport.sendMessage(message, message.getAllRecipients());
	      transport.close(); */
		
		
	} catch (MessagingException mex) {
	    mex.printStackTrace();
	    Exception ex = null;
	    if ((ex = mex.getNextException()) != null) {
		ex.printStackTrace();
	    }
	} catch (IOException ioex) {
	    ioex.printStackTrace();
	}
	
	
	
}
}