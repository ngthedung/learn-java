package com.hkt.client.swingexp.app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * @author 5558
 */
		public class CreateDataExample {
	    private ConnectionToServer server;
	    private LinkedBlockingQueue<Object> messages;
	    private Socket socket;

	    public CreateDataExample(String IPAddress, int port) throws Exception{
	        socket = new Socket(IPAddress, port);
	        messages = new LinkedBlockingQueue<Object>();
	        server = new ConnectionToServer(socket);

	        Thread messageHandling = new Thread() {
	            public void run(){
	                while(true){
	                    try{
	                        Object message = messages.take();
	                        // Do some handling here...
	                        System.out.println("Message Received: " + message);
	                    }
	                    catch(InterruptedException e){ }
	                }
	            }
	        };

	        messageHandling.setDaemon(true);
	        messageHandling.start();
	    }

	    public class ConnectionToServer {
	        ObjectInputStream in;
	        ObjectOutputStream out;
	        Socket socket;

	        public ConnectionToServer(Socket socket) throws Exception {
	            this.socket = socket;
	            in = new ObjectInputStream(socket.getInputStream());
	            out = new ObjectOutputStream(socket.getOutputStream());

	            Thread read = new Thread(){
	                public void run(){
	                    while(true){
	                        try{
	                            Object obj = in.readObject();
	                            messages.put(obj);
	                        }
	                        catch(Exception e){ e.printStackTrace(); }
	                    }
	                }
	            };

	            read.setDaemon(true);
	            read.start();
	        }

	        private void write(Object obj) {
	            try{
	                out.writeObject(obj);
	            }
	            catch(IOException e){ e.printStackTrace(); }
	        }


	    }

	    public void send(Object obj) {
	        server.write(obj);
	    }
	}