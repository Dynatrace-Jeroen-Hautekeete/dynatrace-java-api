package be.dynatrace.api.model.v2;

import org.json.JSONObject;
//import be.dynatrace.api.client.ApiClient;

public class SettingsObjectSummary {
	
//	private String schemaversion="";
	private String objectid="";
	private String schemaid="";
	private JSONObject value=null;
	private String scope="";
//	private String summary="";
	
	// updateToken
	// author
	// created
	// modified
	
	protected SettingsObjectSummary(JSONObject schema) {
//		schemaversion=schema.getString("schemaVersion");
		objectid=schema.getString("objectId");
		schemaid=schema.getString("schemaId");
		value=schema.getJSONObject("value");
		scope=schema.getString("scope");
//		summary=schema.getString("summary");
	}


	public JSONObject getValue() {
		return value;
	}

	public String getScope() {
		return scope;
	}

	public String getObjectid() {
		return objectid;
	}

	public String getSchemaid() {
		return schemaid;
	}
/*
	public String getSummary() {
		return summary;
	}

	public String getSchemaversion() {
		return schemaversion;
	}
	
	public SettingsSchemaDetail loadDetail(ApiClient ac) {
		if (detail==null)	
			detail=SettingsSchemaDetail.load(ac, objectid);
		return detail;
	}
*/	
}
