package com.hkt.client.rest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Inet4Address;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.Timer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hkt.client.rest.service.HKTFile;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;
import com.hkt.util.json.JSONSerializer;
import com.mysql.jdbc.Statement;

public class RESTClient {
	private RestTemplate restTemplate;
	private String restUrl;
	private Connection conn = new DBConnection().connection();

	public RESTClient() {
		loadIP();

	}
	
	public RESTClient(String a) {

	}

	private void loadIP() {
		try {
			String str = "";
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse("C:\\HKTSoft4.0\\DataBase\\ip.xml");
			NodeList list = doc.getElementsByTagName("Account");
			Node accountNode = list.item(0);
			NodeList child = accountNode.getChildNodes();
			Node linkNode = child.item(1);
			boolean local = false;
			try {
				if (readDataOnline().equals("false")) {
					local = true;
				}
			} catch (Exception e) {
			}
			if (linkNode.getTextContent().equals("localhost") || local || conn == null) {
				str = readData();
			} else {
				str = "http://" + linkNode.getTextContent().toString() + ":7080/rest";
				System.out.println(str);
				Timer timer = new Timer(5000, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
							DocumentBuilder builder = builderFactory.newDocumentBuilder();
							Document doc = builder.parse("C:\\HKTSoft4.0\\DataBase\\ip.xml");
							NodeList list = doc.getElementsByTagName("Account");
							Node accountNode = list.item(0);
							NodeList child = accountNode.getChildNodes();
							Node linkNode = child.item(1);
							Document doc1 = builder.parse("C:\\HKTSoft4.0\\DataBase\\code.xml");
							NodeList list1 = doc1.getElementsByTagName("Account");
							Node accountNode1 = list1.item(0);
							NodeList child1 = accountNode1.getChildNodes();
							Node codeNode = child1.item(1);
							
							String link = "";
							String select = "select link from Account where code=?";
							PreparedStatement ps = conn.prepareStatement(select);
							ps.setString(1, codeNode.getTextContent());
							ResultSet rs = ps.executeQuery();
							if (rs.next()) {
								link = rs.getString("link");
							}
							System.out.println(link+"   "+linkNode.getTextContent());
							if(!link.toString().equals(linkNode.getTextContent().toString())){
								Document doc3 = builder.newDocument();
								Element account = doc3.createElement("Account");
								Element ip = doc3.createElement("Link");
								ip.setTextContent(link);
								account.appendChild(ip);
								doc3.appendChild(account);
								TransformerFactory factory = TransformerFactory.newInstance();
								Transformer trans = factory.newTransformer();
								trans.setOutputProperty("indent", "yes");
								trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
								trans.transform(new DOMSource(doc3), new StreamResult("C:\\HKTSoft4.0\\DataBase\\ip.xml"));
								System.out.println("thanh cong my man");
								restUrl = "http://" + link + ":7080/rest";
								getRestUrl();
							}
							
            } catch (Exception e2) {
            }
						
					}
				});
				timer.start();
			}
			System.out.println("???????????");
			restUrl = str;
		} catch (Exception e) {
			//e.printStackTrace();
			try {
				String str = readData();
				restUrl = str;
			} catch (Exception e1) {
				restUrl = "http://localhost:7080/rest";
			}
		}
	}

	private String readDataOnline() {
		try {
			FileInputStream fi = new FileInputStream(HKTFile.getFile("Database", "online"));
			ObjectInputStream of = new ObjectInputStream(fi);
			String str = of.readObject().toString();
			of.close();
			return str;
		} catch (Exception e) {
			return "";
		}
	}

	private String ipConfig() {
//		try {
//	    return Inet4Address.getLocalHost().getHostAddress();
//    } catch (Exception e) {
//	    // TODO: handle exception
//    }
		String ip = "";
		try {
			URL whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			ip = in.readLine(); // you get the IP as a String
		} catch (Exception e) {

		}
		return ip;
	}

	public void startTimer() {
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			// Ä�á»�c file code.xml
			Document doc1 = builder.parse("C:\\HKTSoft4.0\\DataBase\\Link.xml");
			NodeList list1 = doc1.getElementsByTagName("Account");
			Node accountNode1 = list1.item(0);
			NodeList child1 = accountNode1.getChildNodes();
			child1.item(1);
			Document doc3 = builder.newDocument();
			Element account3 = doc3.createElement("Account");
			Element link = doc3.createElement("Link");
			Timer timer = new Timer(30000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Cap nhat1");
					DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
					try {
						DocumentBuilder builder = builderFactory.newDocumentBuilder();
						// Ä�á»�c file code.xml
						Document doc1 = builder.parse("C:\\HKTSoft4.0\\DataBase\\code.xml");
						NodeList list1 = doc1.getElementsByTagName("Account");
						Node accountNode1 = list1.item(0);
						NodeList child1 = accountNode1.getChildNodes();
						Node codeNode = child1.item(1);
						// Ä�á»�c file link.xml
						Document doc2 = builder.parse("C:\\HKTSoft4.0\\DataBase\\Link.xml");
						NodeList list2 = doc2.getElementsByTagName("Account");
						Node accountNode2 = list2.item(0);
						NodeList child = accountNode2.getChildNodes();
						Node linkNode = child.item(1);
						System.out.println(ipConfig());
						System.out.println(linkNode.getTextContent());
						String linkhost = "";
						String select = "select link from Account where code=?";
						PreparedStatement ps = conn.prepareStatement(select);
						ps.setString(1, codeNode.getTextContent());
						ResultSet rs = ps.executeQuery();
						if (rs.next()) {
							linkhost = rs.getString("link");
						}
						if (!ipConfig().equals(linkNode.getTextContent())||linkhost.isEmpty()) {
							String updateip = "UPDATE  Account SET link='" + ipConfig() + "' where code ='"
							    + codeNode.getTextContent() + "'";
							Statement stmt = (Statement) conn.createStatement();
							stmt.executeUpdate(updateip);

							Document doc3 = builder.newDocument();
							Element account3 = doc3.createElement("Account");
							Element link = doc3.createElement("Link");
//							
//							String link1 = "";
//							String select = "select link from Account where code=?";
//							PreparedStatement ps = conn.prepareStatement(select);
//							ps.setString(1, codeNode.getTextContent());
//							ResultSet rs = ps.executeQuery();
//							if (rs.next()) {
//								link1 = rs.getString("link");
//							}
//							link.setTextContent(link1);
//							
							account3.appendChild(link);
							doc3.appendChild(account3);
							TransformerFactory factory = TransformerFactory.newInstance();
							Transformer trans = factory.newTransformer();
							trans.setOutputProperty("indent", "yes");
							trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
							System.out.println("cn ok");
							trans.transform(new DOMSource(doc3), new StreamResult("C:\\HKTSoft4.0\\DataBase\\Link.xml"));
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						// Logger.getLogger(UpdateIP.class.getName()).log(Level.SEVERE,
						// null, ex);
					}

				}
			});
			timer.start();
		} catch (Exception e) {
		System.out.println("ko dc dau soi a");
		}

	}

	private String readData() {
		try {
			FileInputStream fi = new FileInputStream(HKTFile.getFile("Database", "rest"));
			ObjectInputStream of = new ObjectInputStream(fi);
			String str = of.readObject().toString();
			of.close();
			return str;
		} catch (Exception e) {
			return "http://localhost:7080/rest";
		}
	}

	public RestTemplate getRestTemplate() {
		return this.restTemplate;
	}

	public void setRestTemplate(RestTemplate tmpl) {
		this.restTemplate = tmpl;
	}

	public String getRestUrl() {
		return this.restUrl;
	}

	public void setRestUrl(String url) {
	}

	public Response POST(Request request) throws Exception {
		Response response = (Response) restTemplate.postForObject(restUrl + "/post", request, Response.class);
		return response;
	}

	public String POST(String requestJson) throws Exception {
		Request request = JSONSerializer.INSTANCE.fromString(requestJson, Request.class);
		Response response = (Response) restTemplate.postForObject(restUrl + "/post", request, Response.class);
		return JSONSerializer.INSTANCE.toString(response);
	}

	public Response GET(Request request) throws Exception {
		String json = JSONSerializer.INSTANCE.toString(request);
		String url = restUrl + "/get?req=" + URLEncoder.encode(json, "UTF-8");
		Response response = (Response) restTemplate.getForObject(url, Response.class);
		return response;
	}

	public String GET(String requestJson) throws Exception {
		String url = restUrl + "/get?req=" + URLEncoder.encode(requestJson, "UTF-8");
		Response response = (Response) restTemplate.getForObject(url, Response.class);
		return JSONSerializer.INSTANCE.toString(response);
	}
}