package be.dynatrace.api.env.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import be.dynatrace.api.client.ApiClient;

public class ClusterVersion {
	public static final String CLUSTERVERSION="/api/v1/config/clusterversion";
	
	public static JSONObject getClusterVersion(ApiClient ac) throws Exception {
		Map<String,List<String>> params=new HashMap<String,List<String>>();				
		return ac.executeJsonGetRequest(CLUSTERVERSION, params);
	}

}
