package be.dynatrace.api.model.v2;

import org.json.JSONObject;
//import be.dynatrace.api.client.ApiClient;

public class EntitySummary {
	
	private String entityid="";
	private String name="";
	
	protected EntitySummary(JSONObject entity) {
		entityid=entity.getString("entityId");
		name=entity.getString("displayName");
	}

	public String getEntityid() {
		return entityid;
	}
	public String getName() {
		return name;
	}

}
