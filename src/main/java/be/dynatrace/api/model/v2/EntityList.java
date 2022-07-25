package be.dynatrace.api.model.v2;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import be.dynatrace.api.client.ApiClient;
import be.dynatrace.api.env.v2.MonitoredEntities;

public class EntityList {

	// private int totalcount=0;
	private Map<String,EntitySummary> entities=null;
	
	protected EntityList(){}
	

	public static EntityList load(ApiClient ac,String selector) {
		// DEBUG
		// System.out.println("loading entities for selector: "+selector);
		
		try  {
			return new EntityList(MonitoredEntities.getEntities(ac,selector,null,null,null,null));
		} catch (Exception e) {
			return new EntityList();
		}
	}

	protected EntityList(JSONArray aentities) {
		
		// DEBUG
		// System.out.println(aentities);
		
		entities=new HashMap<String,EntitySummary>();
		
		// totalcount=schemas.getInt("totalCount");
		aentities.forEach( (item) -> {
			EntitySummary sitem=new EntitySummary((JSONObject)item);
			entities.put(sitem.getEntityid(),sitem);
			
			// DEBUG
			// System.out.println(sitem.getEntityid()+" :: "+sitem.getName());
		});				
	}


	public Map<String, EntitySummary> getEntities() {
		return entities;
	}
	
	public EntitySummary getEntity(String id) {
		return entities.get(id);
	}
	
	public boolean hasEntities() {
		return ((entities!=null) && (entities.size()>0));
	}
}
