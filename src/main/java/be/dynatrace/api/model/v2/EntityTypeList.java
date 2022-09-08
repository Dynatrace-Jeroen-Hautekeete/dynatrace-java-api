package be.dynatrace.api.model.v2;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import be.dynatrace.api.client.ApiClient;
import be.dynatrace.api.env.v2.MonitoredEntities;

public class EntityTypeList {

	// private int totalcount=0;
	private Map<String,EntityTypeSummary> entitytypes=null;
	
	protected EntityTypeList(){}
	

	public static EntityTypeList load(ApiClient ac,String selector) {
		// DEBUG
		// System.out.println("loading entities for selector: "+selector);
		
		try  {
			return new EntityTypeList(MonitoredEntities.getEntitytypes(ac,selector,null,null,null,null));
		} catch (Exception e) {
			return new EntityTypeList();
		}
	}

	public static EntityTypeList load(ApiClient ac,String selector,String from) {
		// DEBUG
		// System.out.println("loading entities for selector: "+selector);
		
		try  {
			return new EntityTypeList(MonitoredEntities.getEntitytypes(ac,selector,from,null,null,null));
		} catch (Exception e) {
			return new EntityTypeList();
		}
	}
	
	protected EntityTypeList(JSONArray aentitytypes) {
		
		// DEBUG
		// System.out.println(aentities);
		
		entitytypes=new HashMap<String,EntityTypeSummary>();
		
		// totalcount=schemas.getInt("totalCount");
		aentitytypes.forEach( (item) -> {
			EntityTypeSummary sitem=new EntityTypeSummary((JSONObject)item);
			entitytypes.put(sitem.getType(),sitem);
			
			// DEBUG
			// System.out.println(sitem.getEntityid()+" :: "+sitem.getName());
		});				
	}


	public Map<String, EntityTypeSummary> getEntitytypes() {
		return entitytypes;
	}
	
	public EntityTypeSummary getEntityType(String id) {
		return entitytypes.get(id);
	}
	
	public boolean hasEntitytypes() {
		return ((entitytypes!=null) && (entitytypes.size()>0));
	}
	
	public int size() {
		return (entitytypes==null ? 0 : entitytypes.size());
	}
}
