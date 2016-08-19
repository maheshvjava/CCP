package com.ccp.pushnotification;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.json.simple.JSONObject;

public class FCMServer {
	
	//Method to send Notifications from server to client end.
	public final static String AUTH_KEY_FCM = "AIzaSyByCTvA4oGLNUU-kwAa2pl-ijF7gQlyvHc";
	public final static String AUTH_KEY_ANDROID_KEY = "AIzaSyCuZmovApFvq3fnvZh2f2ZtkGYxrSJEvFk";
	public final static String AUTH_KEY_IOS_KEY = "c5K6YLF-MU8:APA91bHyP5Wrmu1TOhA4_tiU7tM_nOQMRJULlVLfbm7bkbwjEANWlXrfrJ4IWDLB9phMFG4k-bPPit1RIF7NP_sFp0Hqau_5BWnhU1BCY8K2zaYTfafvaPXBLgOJf9kqowezqoacFesr";
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
	
	private static FCMServer fcmServer = new FCMServer( );
	
	/* A private Constructor prevents any other 
	* class from instantiating.
	*/
	private FCMServer(){ }

	/* Static 'instance' method */
	public static FCMServer getInstance( ) {
	  return fcmServer;
	}
	
	private String title;
	
	private String message;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	//userDeviceIdKey is the device id you will query from your database
	@SuppressWarnings("unchecked")
	public void pushFCMNotification(String userDeviceIdKey) throws Exception {
		
		String authKey = AUTH_KEY_FCM;   // You FCM AUTH key
		String FMCurl = API_URL_FCM;     
		
		URL url = new URL(FMCurl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization","key="+authKey);
		conn.setRequestProperty("Content-Type","application/json");
		
		JSONObject json = new JSONObject();
		json.put("to",userDeviceIdKey.trim());
		JSONObject info = new JSONObject();
		info.put("title", this.title);   // Notification title
		info.put("body", this.message); // Notification body
		json.put("notification", info);

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(json.toString());
		wr.flush();
		conn.getInputStream();
		
		System.out.println(json.toString());
	}
	
	public void send() throws Exception {
		this.pushFCMNotification(AUTH_KEY_ANDROID_KEY);
		this.pushFCMNotification(AUTH_KEY_IOS_KEY);
	}
	
	public void buildMessage(String username, Date datetime, String source) {
		
		this.message = username +" sent car-pool request for trip scheduled on " + 
					  datetime +" starting from "+ source; 
		this.title = "Car pool request sent by "+ username;
		
	}
	
	public static void main(String[] args) throws Exception {
		FCMServer fcmServer = getInstance();
		//fcmServer.pushFCMNotification(AUTH_KEY_ANDROID_KEY);
		fcmServer.pushFCMNotification(AUTH_KEY_IOS_KEY);
	}
}
