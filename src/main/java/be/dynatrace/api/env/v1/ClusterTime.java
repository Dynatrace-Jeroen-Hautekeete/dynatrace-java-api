package be.dynatrace.api.env.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.dynatrace.api.client.ApiClient;

public class ClusterTime {
	public static final String CLUSTERTIME="/api/v1/time";
	
	public static long getTime(ApiClient ac) throws Exception {
		Map<String,List<String>> params=new HashMap<String,List<String>>();		
		return Long.parseLong(ac.executePlainGetRequest(CLUSTERTIME, params));
	}

}
