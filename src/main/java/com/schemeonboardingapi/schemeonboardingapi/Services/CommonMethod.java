package com.schemeonboardingapi.schemeonboardingapi.Services;

import java.beans.Statement;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

@Service
@ComponentScan
public class CommonMethod {
	
	private JdbcTemplate jdbcTemplatebmsdb;
	
	public CommonMethod(@Qualifier("jdbcTemplatebmsdb") JdbcTemplate jdbcTemplatebmsdb) {
        this.jdbcTemplatebmsdb = jdbcTemplatebmsdb;

    }
	
	public String getDocumentscheme() {
	
		String defaultvalue="-Please Select-";
			
			 String query = "SELECT id as id, document_name as name FROM public.master_document where document_type=4 AND status='1'";
			  return fillDropdown(query, defaultvalue);

		
	}
	public String getRequiredList() {
		
		String defaultvalue="-Please Select-";
			
			 String query = "SELECT value as id, name as name FROM public.master_required ";
			  return fillDropdown(query, defaultvalue);

		
	}
    public String getAPIFeildList()  {
        String query = "SELECT id||'~'||feildname||'~'||api_ref_no as id  ,api_feildoption_name as name FROM public.meta_apifeild_list where status='1'";
        return fillDropdown(query);
    }

    public String getPrefilledList()  {
        String query = "SELECT id||'~'||ref_table_name as id, listname  as name FROM public.meta_prfilled_list where status='1'";
        return fillDropdown(query);
    }
    public String fillDropdown(String query, String defaultValue) {
       
 
        String options_string = "";
        
    	try {
    		List<Map<String, Object>> rows1 = jdbcTemplatebmsdb.queryForList(query);
    		options_string += "<option value=\"\">" + defaultValue + "</option>";
    		for (Map<String, Object> row : rows1) {
    			 options_string += "<option value=\"" + row.get("id").toString()  + "\" title =\"" + row.get("name").toString()  + "\">" + row.get("name").toString()  + "</option>";
    	        
    		}
    		
    		
    		
    	
    	}catch(Exception e) {
    		
    		
    	    e.printStackTrace();
    	} 
    	 return options_string;
  
    }
    public String fillDropdown(String query) {
        

        String options_string = "";
        
        
        try {
    		List<Map<String, Object>> rows1 = jdbcTemplatebmsdb.queryForList(query);
    		
    		for (Map<String, Object> row : rows1) {
    			options_string += "<option value=\"" + row.get("id").toString() + "\" title =\"" + row.get("name").toString() + "\">" + row.get("name").toString() + "</option>";
    	        
    		}
    		
    		
    		
    	
    	}catch(Exception e) {
    		
    		
    	    e.printStackTrace();
    	} 
        
        
   

        return options_string;
    }
    
    public String getComponnetList(String queryString) {
    	String 	scheme_code="",	rc_mem="",	beneficiary_id="";
    	JSONArray returnarray = new JSONArray();

    	
    	
    	
    	
    	try {
    		
    		JSONObject obj =new JSONObject(queryString);
    		String schemeid=obj.getString("schemeid").toString();
    
  
    		 String query = "SELECT sl_no,COALESCE(field_id,0) as field_id, field_type, field_json FROM scheme_onboarding.scheme_ob_additional_info_draft WHERE scheme_ob_additional_info_draft.reference_id= '" + schemeid + "'";
    	 
    	 List<Map<String, Object>> rows1 = jdbcTemplatebmsdb.queryForList(query);
    		
    		for (Map<String, Object> row : rows1) {
    			
    			JSONObject returnobj = new JSONObject();
    			returnobj.put("sl_no",row.get("sl_no").toString());
    			returnobj.put("field_id", row.get("field_id").toString());
    			returnobj.put("field_type", row.get("field_type").toString());
    			returnobj.put("field_json", row.get("field_json").toString());
    			returnarray.put(returnobj);
    		
    
    		  }
    		  
    			// Fecthing Documnet list 
    	        
    		 String querydoc = "SELECT sl_no,COALESCE(is_required,'N') as is_required, document_id FROM scheme_onboarding.scheme_ob_bsic_info_required_document_draft WHERE scheme_ob_bsic_info_required_document_draft.scheme_id= '" + schemeid + "'";
        	 
        	 List<Map<String, Object>> rows2 = jdbcTemplatebmsdb.queryForList(querydoc);
        		
        		for (Map<String, Object> row : rows2) {
        			
        			JSONObject returnobj = new JSONObject();
        			returnobj.put("sl_no",row.get("sl_no").toString());
        			returnobj.put("document_id", row.get("document_id").toString());
        			returnobj.put("is_required", row.get("is_required").toString());
        			returnobj.put("field_type", "document");
        			returnarray.put(returnobj);
        		
        
        		  }
    			 
    			
    	        
    		
    	 
    	
    	}catch(Exception e) {
    		

    	
    	    e.printStackTrace();
    		
    	}
    	return returnarray.toString();
    	
    }

    public String postSchemeSpec_info(String scheme_json) {
    	String 	scheme_code="",	rc_mem="",	response_msg="";
    

    	
    	
    	
    	
    	try {
    		JSONObject response_obj =new JSONObject(scheme_json);
    		String scheme_id = response_obj.optString("scheme_id");
    		String scheme_spec_info = response_obj.optString("scheme_spec_info");
    		
    		   String query="select scheme_onboarding.save_scheme_spec_info('"+scheme_id.toString()+"','"+scheme_spec_info.toString()+"')";
    		   
    		   System.out.println(query);
    		   
    		    response_msg= jdbcTemplatebmsdb.queryForObject(query, String.class);
            
    			 
    			
    	        
    		
    	 
    	
    	}catch(Exception e) {
    		

    	
    	    e.printStackTrace();
    		
    	}
    	return response_msg;
    	
    }
}
