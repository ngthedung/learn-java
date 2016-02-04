package com.hkt.client.swingexp.app.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GoogleMail {
	
	private static GoogleMail googleMail;
	
	public static GoogleMail getInstance(){
		if(googleMail==null){
			googleMail = new GoogleMail();
		}
		return googleMail;
	}
	

//	public void Send(final String username, final String password, String recipientEmail, String title, String message) throws AddressException, MessagingException {
//        Send(username, password, recipientEmail, "", title, message);
//    }
	
	 public  void sendEmail(final String username, final String password,String to, String title, String message1)
	    {
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	        props.put("mail.smtp.EnableSSL.enable","true");

	        Session session = Session.getInstance(props,
	          new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	          });

	        try {

	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(to));
	            message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(to));
	            message.setSubject(title);
	            message.setText(message1);
	            
	          BodyPart messageBodyPart1 = new MimeBodyPart();  
	          messageBodyPart1.setText("Giờ gửi: "+new SimpleDateFormat("HH:mm").format(new Date()));  
	            
	          //4) create new MimeBodyPart object and set DataHandler object to this object      
	          MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
	          
	          String filename = HKTFile.getFile("Hoa Don", title+".xls").getAbsolutePath();
	          DataSource source = new FileDataSource(filename);  
	             
	          messageBodyPart2.setDataHandler(new DataHandler(source));  
	          messageBodyPart2.setFileName("HD ngay "+new SimpleDateFormat("dd/MM/yyyy").format(new Date())+".xls");  
	           
	           
	          //5) create Multipart object and add MimeBodyPart objects to this object      
	          Multipart multipart = new MimeMultipart();
	          multipart.addBodyPart(messageBodyPart1);  
	          multipart.addBodyPart(messageBodyPart2);  
	        
	          //6) set the multiplart object to the message object  
	          message.setContent(multipart );  

	            Transport.send(message);

	            System.out.println("Done");

	        } 

	        catch (MessagingException e) 
	        {
	        	e.printStackTrace();
	            // throw new RuntimeException(e);
	           // System.out.println("Username or Password are incorrect ... exiting !");
	        }
	    }
	
//	public void Send(final String username, final String password, String recipientEmail, String ccEmail, String title, String message) throws AddressException, MessagingException {
//        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
//        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
//
//        // Get a Properties object
//        Properties props = System.getProperties();
//        props.setProperty("mail.smtps.host", "smtp.gmail.com");
//        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
//        props.setProperty("mail.smtp.socketFactory.fallback", "false");
//        props.setProperty("mail.smtp.starttls.enable","true");
//       // props.setProperty("mail.smtp.port", "465");
//        props.setProperty("mail.smtp.socketFactory.port", "465");
//        props.setProperty("mail.smtps.auth", "false");
//        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        
//
//        /*
//        If set to false, the QUIT command is sent and the connection is immediately closed. If set 
//        to true (the default), causes the transport to wait for the response to the QUIT command.
//
//        ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
//                http://forum.java.sun.com/thread.jspa?threadID=5205249
//                smtpsend.java - demo program from javamail
//        */
//        props.put("mail.smtps.quitwait", "false");
//
//        Session session = Session.getInstance(props, null);
//
//        // -- Create a new message --
//        final MimeMessage msg = new MimeMessage(session);
//
//        // -- Set the FROM and TO fields --
//        msg.setFrom(new InternetAddress(username + "@gmail.com"));
//        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));
//
//        if (ccEmail.length() > 0) {
//            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
//        }
//
//        msg.setSubject(title);
//        msg.setText(message, "utf-8");
//        msg.setSentDate(new Date());
//
//        BodyPart messageBodyPart1 = new MimeBodyPart();  
//        messageBodyPart1.setText("Giờ gửi: "+new SimpleDateFormat("HH:ss").format(new Date()));  
//          
//        //4) create new MimeBodyPart object and set DataHandler object to this object      
//        MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
//        
//        String filename = HKTFile.getFile("Hoa Don", "DSHoaDon.xls").getAbsolutePath();
//        System.out.println("ok1           "+ filename);
//        DataSource source = new FileDataSource(filename);  
//           
//        messageBodyPart2.setDataHandler(new DataHandler(source));  
//        messageBodyPart2.setFileName("DSHD ngay "+new SimpleDateFormat("dd/MM/yyyy").format(new Date())+".xls");  
//         
//         
//        //5) create Multipart object and add MimeBodyPart objects to this object      
//        Multipart multipart = new MimeMultipart();
//        multipart.addBodyPart(messageBodyPart1);  
//        multipart.addBodyPart(messageBodyPart2);  
//      
//        //6) set the multiplart object to the message object  
//        msg.setContent(multipart );  
//        
//        Transport transport = session.getTransport("smtps");
//        transport.connect ("smtp.gmail.com",465, username, password);
//        transport.sendMessage(msg, msg.getAllRecipients());
//        transport.close();    
//
////        SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
////
////        t.connect("smtp.gmail.com", username, password);
////        t.sendMessage(msg, msg.getAllRecipients());
////        t.close();
//    }
}
