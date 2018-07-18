package com.example.unique.presencetracking;

import com.example.unique.presencetracking.General_Exception;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import java.net.URI;

public class network {

	public static String callGet(String url) throws General_Exception{
		HttpClient client;
		HttpGet requestGet;
		HttpResponse response;
		String op="";
		client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 5000);

		try {
			URI uri = new URI(url);
			requestGet=new HttpGet(uri);
			response = client.execute(requestGet);
			if(response!=null){
				op=text.convertStreamToString(response.getEntity().getContent());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new General_Exception(e);
		}
		return op;
	}

	public 	static String postCall(String url,String data) throws General_Exception{
		HttpClient client;
		HttpPost requestPost;
		HttpResponse response;
		String op="";
		client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 5000);
		requestPost = new HttpPost(url);
		StringEntity se;
		try {
			se = new StringEntity(data);
			requestPost.setEntity(se);
			response = client.execute(requestPost);
			if(response!=null)
				op=text.convertStreamToString(response.getEntity().getContent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new General_Exception(e);
		}
		return op;	
	}

	//	public String call(int type,String url,String data) throws General_Exception{
	//		HttpClient client;
	//		HttpPost requestPost = null;
	//		HttpGet requestGet = null;
	//		HttpResponse response;
	//		String http_response;
	//
	//		http_response="";
	//		client = new DefaultHttpClient();
	//		//timeout for connection establishment
	//		//		org.apache.http.config
	//		HttpConnectionParams.setConnectionTimeout(client.getParams(), 15000);
	//		if(type==0){
	//			URI uri = null;
	//			try {
	//				uri = new URI(url);
	//			} catch (URISyntaxException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//			requestGet=new HttpGet(uri);	
	//		}else{
	//			requestPost = new HttpPost(url);	
	//		}
	//
	//		//		request.setHeader("Accept", "application/json");
	//		//		request.setHeader("Content-Type", "application/json");
	//		try {
	//
	//			if(type==1)
	//			{
	//				StringEntity se=new StringEntity(data);
	//				requestPost.setEntity(se);
	//				response = client.execute(requestPost);
	//			}else{
	//				response = client.execute(requestGet);
	//			}
	//
	//			if(response!=null)
	//				return text.convertStreamToString(response.getEntity().getContent());
	//		} catch (IOException|IllegalStateException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//			throw new General_Exception(e);
	//		}
	//		return http_response;
	//	}
}