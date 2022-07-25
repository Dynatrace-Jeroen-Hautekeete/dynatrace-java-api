package be.dynatrace.api.model.v2;

import org.json.JSONArray;
import org.json.JSONObject;

import be.dynatrace.api.client.ApiClient;
import be.dynatrace.api.env.v2.MonitoredEntities;
//import be.dynatrace.api.env.v2.SettingsSchemas;

public class EntityDetail { 
	
	private String version="";
	private String schemaid="";
	private String displayname="";
	private String[] scopes= {};
	
	public static EntityDetail load(ApiClient ac,String eid) {
		try  {
			return new EntityDetail(MonitoredEntities.getEntity(ac,eid));
		} catch (Exception e) {
			return new EntityDetail();
		}
	}

	protected EntityDetail(){};
	
	
	protected EntityDetail(JSONObject schema) {
		version=schema.getString("version");
		schemaid=schema.getString("schemaId");
		displayname=schema.getString("displayName");
		
		JSONArray asa=schema.getJSONArray("allowedScopes");
		scopes=new String[asa.length()];
		
		for (int i=0;i<scopes.length;i++) {
			scopes[i]=asa.getString(i);
		}
		
	}

	public String getVersion() {
		return version;
	}

	public String getSchemaid() {
		return schemaid;
	}

	public String getDisplayname() {
		return displayname;
	}

	public String[] getScopes() {
		return scopes;
	}
}
