package be.dynatrace.api.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiClient { 
	private String url;
	private String port="";
	private String environment="";
	private String token;
	private CloseableHttpClient httpclient;
	private boolean headerauth=true;
	private String customcookie=null;
	
	public void setParamAuth() {
		headerauth=false;
	}
	public void setHeaderAuth() {
		headerauth=true;
	}
	
	public void setCustomCookie(String scustomcookie) {
		customcookie=scustomcookie;
	}
	
	private CloseableHttpClient createAcceptingClient() {
		try {
			TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, 
					NoopHostnameVerifier.INSTANCE);
	    Registry<ConnectionSocketFactory> socketFactoryRegistry = 
	      RegistryBuilder.<ConnectionSocketFactory> create()
	      .register("https", sslsf)
	      .register("http", new PlainConnectionSocketFactory())
	      .build();
	 
	    BasicHttpClientConnectionManager connectionManager = 
	      new BasicHttpClientConnectionManager(socketFactoryRegistry);
	    /* CloseableHttpClient httpClient = */ return HttpClients.custom().setSSLSocketFactory(sslsf)
	      .setConnectionManager(connectionManager).build();		
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(-1);
		}
		return null;
	}
	
	public ApiClient(String url,String token) {
		this.url=url;
		this.token=token;	
		this.httpclient=createAcceptingClient() /*HttpClients.createDefault()*/;
	}

	public ApiClient(String url,String port,String env, String token) {
		this.url=url;
		this.token=token;	
		this.port=port;	
		if (env!=null) {
			this.environment="/e/"+env;
		}
		this.httpclient=createAcceptingClient() /*HttpClients.createDefault()*/;
	}

	public ApiClient(Properties p) {
		this.url=p.getProperty("url");
		this.token=p.getProperty("token");	
		this.port=p.getProperty("port","443");	
		if (p.containsKey("env")) {
			this.environment="/e/"+p.getProperty("env");
		}
		if (p.containsKey("dtcookie")) {
			this.customcookie=p.getProperty("dtcookie");
		}
		this.httpclient=createAcceptingClient() /*HttpClients.createDefault()*/;
	}

	public ApiClient(String[] args) {
		this.url=args[0];
		this.port=args[1];	
		this.token=args[args.length-1]; // last param = args[2] (Saas) or args[3] (Managed)
		if (args.length>3) {
			this.environment="/e/"+args[2];
		}
		this.httpclient=createAcceptingClient() /*HttpClients.createDefault()*/;
	}

	public synchronized JSONObject executeJsonGetRequest(String endpoint,Map<String,List<String>> params) throws Exception {
		
		URIBuilder ub=new URIBuilder().setScheme("https").setHost(url).setPath(environment+endpoint);
		if (!port.equals("")) {
			ub.setPort(Integer.parseInt(port));
		}
		for (Entry<String, List<String>> paramvalues:params.entrySet()){
			for (String value:paramvalues.getValue()){
				ub.setParameter(paramvalues.getKey(), value);
			}
		}
		if (!headerauth) ub.setParameter("api-token", token);
		
		URI uri = ub.build();

		HttpGet httpget = new HttpGet(uri);
		if (headerauth) 
			httpget.addHeader("Authorization", "Api-Token "+token);
		httpget.addHeader("Accept", "application/json");

		if (customcookie!=null) 
			httpget.addHeader("Cookie", customcookie);
		
				
		HttpResponse hr=httpclient.execute(httpget);
		return new JSONObject(EntityUtils.toString(hr.getEntity()));
	}

	public synchronized String executePlainGetRequest(String endpoint,Map<String,List<String>> params) throws Exception {
		
		URIBuilder ub=new URIBuilder().setScheme("https").setHost(url).setPath(environment+endpoint);
		if (!port.equals("")) {
			ub.setPort(Integer.parseInt(port));
		}
		for (Entry<String, List<String>> paramvalues:params.entrySet()){
			for (String value:paramvalues.getValue()){
				ub.setParameter(paramvalues.getKey(), value);
			}
		}
		if (!headerauth) ub.setParameter("api-token", token);
		
		URI uri = ub.build();

		HttpGet httpget = new HttpGet(uri);
		httpget.addHeader("Accept", "text/plain");
		if (headerauth) 
			httpget.addHeader("Authorization", "Api-Token "+token);
		if (customcookie!=null) 
			httpget.addHeader("Cookie", customcookie);
				
		HttpResponse hr=httpclient.execute(httpget);
		return EntityUtils.toString(hr.getEntity());
	}
	
	
	public synchronized JSONArray executePagedGetRequest(String endpoint,Map<String,List<String>> params,String datafield) throws Exception {
		
		boolean hasnext=true;
		
		JSONArray data=null;
		String nextpagekey="";
		
		while (hasnext) {
			JSONObject response;
			if (nextpagekey.length()==0) {
				response=executeJsonGetRequest(endpoint, params);
			} else {
				Map<String,List<String>> params2=new HashMap<String, List<String>>();
				List<String> nextpage=new ArrayList<String>();
				nextpage.add(nextpagekey);
				params2.put("nextPageKey", nextpage);
				response=executeJsonGetRequest(endpoint, params2);
			}
			
			
			if (data==null) {
				try {
					int count=response.getInt("totalCount");
					if (count==0) return new JSONArray();
				} catch (Exception e) {
					
					System.err.println(response);
					return new JSONArray();
				}
				
				data=response.getJSONArray(datafield);
			} else {
				
				//System.err.println(response);
				
				JSONArray newdata=response.getJSONArray(datafield);
				for (int i=0;i<newdata.length();i++) {
					data.put(newdata.get(i));
				}
			}
			try {
				nextpagekey=response.getString("nextPageKey");
			} catch (Exception e) {
				hasnext=false;
			}
		}
				
		return data;		
	}
	
	
	public synchronized String executePostRequest(String endpoint,Map<String,List<String>> params,String payload) throws Exception {
		
		URIBuilder ub=new URIBuilder().setScheme("https").setHost(url).setPath(environment+endpoint);
		if (!port.equals("")) {
			ub.setPort(Integer.parseInt(port));
		}
		for (Entry<String, List<String>> paramvalues:params.entrySet()){
			for (String value:paramvalues.getValue()){
				ub.setParameter(paramvalues.getKey(), value);
			}
		}
		if (!headerauth) ub.setParameter("api-token", token);
		
		URI uri = ub.build();
		HttpPost httppost = new HttpPost(uri);
		if (headerauth) httppost.addHeader("Authorization", "Api-Token "+token);
		httppost.addHeader("Accept", "application/json");

		if (customcookie!=null) httppost.addHeader("Cookie", customcookie);
		
		StringEntity jsone=new StringEntity(payload,ContentType.create("application/json","UTF-8"));
		
		httppost.setEntity(jsone);
		
		HttpResponse hr=httpclient.execute(httppost);
		
		System.err.println(hr.getStatusLine());
		System.err.println(hr.getStatusLine().getReasonPhrase());
		try {
			String rtext=EntityUtils.toString(hr.getEntity());
			System.err.println(rtext);
			return rtext;
		} catch (Exception e) {
		}
		
		return hr.getStatusLine().toString();
	}

	public synchronized void executePutRequest(String endpoint,Map<String,List<String>> params,String payload) throws Exception {
		
		URIBuilder ub=new URIBuilder().setScheme("https").setHost(url).setPath(environment+endpoint);
		if (!port.equals("")) {
			ub.setPort(Integer.parseInt(port));
		}
		for (Entry<String, List<String>> paramvalues:params.entrySet()){
			for (String value:paramvalues.getValue()){
				ub.setParameter(paramvalues.getKey(), value);
			}
		}
		if (!headerauth) ub.setParameter("api-token", token);
		
		URI uri = ub.build();
		HttpPut httpput = new HttpPut(uri);
		if (headerauth) httpput.addHeader("Authorization", "Api-Token "+token);
		httpput.addHeader("Accept", "application/json");
		if (customcookie!=null) httpput.addHeader("Cookie", customcookie);
		
		StringEntity jsone=new StringEntity(payload,ContentType.create("application/json","UTF-8"));	
		httpput.setEntity(jsone);
		HttpResponse hr=httpclient.execute(httpput);
		
		System.err.println(hr.getStatusLine());
		System.err.println(hr.getStatusLine().getReasonPhrase());
		try {
			String rtext=EntityUtils.toString(hr.getEntity());
			System.err.println(rtext);
		} catch (Exception e) {
			// no entity found
		}
		
//		return rtext;
	}

	public synchronized void executeDeleteRequest(String endpoint,Map<String,List<String>> params) throws Exception {
		
		URIBuilder ub=new URIBuilder().setScheme("https").setHost(url).setPath(environment+endpoint);
		if (!port.equals("")) {
			ub.setPort(Integer.parseInt(port));
		}
		for (Entry<String, List<String>> paramvalues:params.entrySet()){
			for (String value:paramvalues.getValue()){
				ub.setParameter(paramvalues.getKey(), value);
			}
		}
		if (!headerauth) ub.setParameter("api-token", token);
		
		URI uri = ub.build();
		HttpDelete httpdel = new HttpDelete(uri);
		if (headerauth) httpdel.addHeader("Authorization", "Api-Token "+token);
		httpdel.addHeader("Accept", "application/json");
		if (customcookie!=null) httpdel.addHeader("Cookie", customcookie);
		
		HttpResponse hr=httpclient.execute(httpdel);
		
		System.err.println(hr.getStatusLine());
		System.err.println(hr.getStatusLine().getReasonPhrase());
		try {
			String rtext=EntityUtils.toString(hr.getEntity());
			System.err.println(rtext);
		} catch (Exception e) {
			// no entity found
		}
		
//		return rtext;
	}
	
	
}
