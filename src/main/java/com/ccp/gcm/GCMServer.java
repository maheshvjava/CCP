package com.ccp.gcm;

import java.util.Date;

import com.ccp.controller.ConstantParams;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GCMServer {
	
	
	private static GCMServer gcmServer = new GCMServer( );
	
	/* A private Constructor prevents any other 
	* class from instantiating.
	*/
	private GCMServer(){ }

	/* Static 'instance' method */
	public static GCMServer getInstance( ) {
	  return gcmServer;
	}
	
	private String deviceKey;
	
	public String getDeviceKey() {
		return deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	private String message;
	
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void pushMessage() {
		new Thread(){

            public void run(){
     
                try {
                	
                	GCMServer gcmserver = getInstance();
                	
                    //Please add here your project API key: "Key for browser apps (with referers)".
                    //If you added "API key Key for server apps (with IP locking)" or "Key for Android apps (with certificates)" here
                    //then you may get error responses.
                    Sender sender = new  Sender(ConstantParams.GCMServerKey);

                    // use this to send message with payload data
                    Message message = new Message.Builder()
                    .collapseKey("message")
                    .timeToLive(3)
                    .delayWhileIdle(true)
                    .addData("message", gcmserver.getMessage()) //you can get this message on client side app
                    .build();  
  
                    //Use this code to send notification message to a single device
                    Result result = sender.send(message, gcmserver.getDeviceKey(), 1);
                    System.out.println("Message Result: "+result.toString()); //Print message result on console

                    /*//Use this code to send notification message to multiple devices
                    ArrayList<String> devicesList = new ArrayList<String>();
                    //add your devices RegisterationID, one for each device                
                    devicesList.add("APA91bEbKqwTbvvRuc24vAYljcrhslOw-jXBqozgH8C2OB3H8R7U00NbIf1xp151ptweX9VkZXyHMik022cNrEETm7eM0Z2JnFksWEw1niJ2sQfU3BjQGiGMq8KsaQ7E0jpz8YKJNbzkTYotLfmertE3K7RsJ1_hAA");    
                    devicesList.add("APA91bEVcqKmPnESzgnGpEstHHymcpOwv52THv6u6u2Rl-PaMI4mU3Wkb9bZtuHp4NLs4snBl7aXXVkNn-IPEInGO2jEBnBI_oKEdrEoTo9BpY0i6a0QHeq8LDZd_XRzGRSv_R0rjzzZ1b6jXY60QqAI4P3PL79hMg");    

                    //Use this code for multicast messages    
                    MulticastResult multicastResult = sender.send(message, devicesList, 0);
                    System.out.println("Message Result: "+multicastResult.toString());//Print multicast message result on console
*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();  
	}
	
	public void buildMessage(String token, String username, Date datetime, String source) {
		
		String body = username +" sent car-pool request for trip scheduled on " + 
					  datetime +" starting from "+ source; 
		String title = "Car pool request sent by "+ username;
		
		String response = "{"
				 + "\"token\": \""+token+"\" ,"
				 + "\"notification\": {"
				 				+ "\"body\": \""+body+"\", "
				 				+  "\"title\": \""+title+"\" "
				 			 + "}, ";
		this.setMessage(response);
	}
	/*public void pushToAndroidDevice(String deviceToken, String data) {  
	    OAuthRequest request = new OAuthRequest(Verb.POST, "https://android.googleapis.com/gcm/send");
	    request.addHeader("Authorization", "key=" + apiKey);
	    request.addHeader("Content-Type", "application/json");

	    request.addPayload(data);

	    Response response = request.send();
	}*/
}