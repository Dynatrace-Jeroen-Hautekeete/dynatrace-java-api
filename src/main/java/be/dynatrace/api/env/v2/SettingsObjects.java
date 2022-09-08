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

public class SettingsObjects {
	public static final String SETTINGSOBJECTS="/api/v2/settings/objects";
	public static final String SCHEMAIDS="schemaIds";
	public static final String SCOPES="scopes";
	public static final String FIELDS="fields";
	
	public static JSONArray getSettingsObjects(ApiClient ac,String[] schemas,String[] scopes) throws Exception {
		Map<String,List<String>> params=new HashMap<String,List<String>>();	
		
		List<String> schemavalues=new ArrayList<String>();
		schemavalues.add(ApiUtil.arrayToCsv(schemas));
		params.put(SCHEMAIDS, schemavalues);
		
		List<String> scopevalues=new ArrayList<String>();
		scopevalues.add(ApiUtil.arrayToCsv(scopes));
		params.put(SCOPES, scopevalues);
		
		List<String> fieldvalues=new ArrayList<String>();
		fieldvalues.add(ApiUtil.arrayToCsv(new String[] {"objectId","value","scope","schemaId"}));
		params.put(FIELDS, fieldvalues);

		return ac.executePagedGetRequest(SETTINGSOBJECTS, params, "items");
	}

	public static JSONArray getSettingsObjects(ApiClient ac,String schema) throws Exception {
		Map<String,List<String>> params=new HashMap<String,List<String>>();	
		
		List<String> schemaids=new ArrayList<String>();
		schemaids.add(schema);
		params.put(SCHEMAIDS, schemaids);
				
		List<String> fieldvalues=new ArrayList<String>();
		fieldvalues.add(ApiUtil.arrayToCsv(new String[] {"objectId","value","scope","schemaId"}));
		params.put(FIELDS, fieldvalues);

		return ac.executePagedGetRequest(SETTINGSOBJECTS, params, "items");
	}
		
	public static JSONObject getSettingsObject(ApiClient ac, String objectid) throws Exception {
		Map<String,List<String>> params=new HashMap<String,List<String>>();				
		return ac.executeJsonGetRequest(SETTINGSOBJECTS+"/"+objectid, params);
	}

}
