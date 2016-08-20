package com.ccp.pushnotification;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.json.simple.JSONObject;

public class FCMServer {
	
	//Method to send Notifications from server to client end.
	public final static String AUTH_KEY_FCM = "AIzaSyByCTvA4oGLNUU-kwAa2pl-ijF7gQlyvHc";
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
	
	//sample token
	//cEhiDTXmQwk:APA91bFcTTUy0cSQbLOV1kZdrD7u32oIe0fzGkMk3lxNLBUhnzRumX0SeIPJ1cAAap3InwCa098EaAl0N1f6NAizyOaaiAVmeMItg6KcxfJcz_L_VebiMSAGUg2vhYzXjj4mfJie6pPm
	
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
	public void pushNotification(String userDeviceIdKey) throws Exception {
		
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
	
	public void buildMessage(String username, Date datetime, String source) {
		
		this.message = username +" sent car-pool request for trip scheduled on " + 
					  datetime +" starting from "+ source; 
		this.title = "Car pool request sent by "+ username;
		
	}
	
}
