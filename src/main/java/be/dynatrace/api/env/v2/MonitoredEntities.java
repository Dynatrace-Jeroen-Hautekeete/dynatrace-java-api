package be.dynatrace.api.env.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
//import org.json.JSONArray;
import org.json.JSONObject;

import be.dynatrace.api.client.ApiClient;
import be.dynatrace.api.client.ApiUtil;

public class MonitoredEntities {
	public static final String ENTITIES=   "/api/v2/entities";
	public static final String ENTITYTYPES="/api/v2/entityTypes";
	
	
	public static final String ENTITYSELECTOR="entitySelector";
	public static final String FROM=          "scopes";
	public static final String TO=            "to";
	public static final String FIELDS=        "fields";
	public static final String SORT=          "sort";
	
	public static JSONArray getEntities(ApiClient ac,String selector,String from, String to,String[] fields,String sort) throws Exception {
		Map<String,List<String>> params=new HashMap<String,List<String>>();	

		List<String> pselector=new ArrayList<String>();
		pselector.add(selector);
		params.put(ENTITYSELECTOR, pselector);
		
		if (from!=null) {
			List<String> pfrom=new ArrayList<String>();
			pfrom.add(from);
			params.put(FROM, pfrom);			
		}
		if (to!=null) {
			List<String> pto=new ArrayList<String>();
			pto.add(to);
			params.put(TO, pto);			
		}
		if ((fields!=null)&&(fields.length>0)) {
			List<String> pfields=new ArrayList<String>();
			pfields.add(ApiUtil.arrayToCsv(fields));
			params.put(FIELDS, pfields);			
		}
		if (sort!=null) {
			List<String> psort=new ArrayList<String>();
			psort.add(sort);
			params.put(TO, psort);			
		}
		
		return ac.executePagedGetRequest(ENTITIES, params, "entities");
	}

	public static JSONObject getEntity(ApiClient ac, String entityid) throws Exception {
		Map<String,List<String>> params=new HashMap<String,List<String>>();				
		return ac.executeJsonGetRequest(ENTITIES+"/"+entityid, params);
	}

}
