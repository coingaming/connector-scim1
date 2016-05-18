package com.evolveum.polygon.salesfrconn;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.filefilter.NotFileFilter;
import org.identityconnectors.common.CollectionUtil;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.AttributeUtil;
import org.identityconnectors.framework.common.objects.ConnectorObject;
import org.identityconnectors.framework.common.objects.Name;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.ResultsHandler;
import org.identityconnectors.framework.common.objects.Uid;
import org.identityconnectors.framework.common.objects.filter.AndFilter;
import org.identityconnectors.framework.common.objects.filter.ContainsFilter;
import org.identityconnectors.framework.common.objects.filter.EndsWithFilter;
import org.identityconnectors.framework.common.objects.filter.EqualsFilter;
import org.identityconnectors.framework.common.objects.filter.Filter;
import org.identityconnectors.framework.common.objects.filter.FilterBuilder;
import org.identityconnectors.framework.common.objects.filter.NotFilter;
import org.identityconnectors.framework.common.objects.filter.OrFilter;
import org.identityconnectors.framework.common.objects.filter.StartsWithFilter;

import com.evolveum.polygon.test.slsfrc.JsonDataProvider;
 
public class Main {

	public static final Uid TEST_UID = new Uid("00558000000VcXnAAK");
	public static final ArrayList<ConnectorObject> result = new ArrayList<>();
	
    public static void main(String[] args) {
    	
    	
    	
    	ObjectClass userC = ObjectClass.ACCOUNT;
    	ObjectClass groupC = ObjectClass.GROUP;
    	
    	EqualsFilter aeq = (EqualsFilter)FilterBuilder.equalTo(TEST_UID);
    	
    /*TODO set for emails*/	EqualsFilter eq = (EqualsFilter)FilterBuilder.equalTo(AttributeBuilder.build("userName","johnsnow@winterfell.com"));
    	
    	ContainsFilter con = (ContainsFilter)FilterBuilder.contains(AttributeBuilder.build("userName","john"));
    	
    	ContainsFilter ct = (ContainsFilter)FilterBuilder.contains(AttributeBuilder.build("userName","aeoinoaiedoiaedionasinad"));
    	
    
    	
    	OrFilter orf = (OrFilter) FilterBuilder.or(eq, ct);
    	
    	AndFilter andf = (AndFilter) FilterBuilder.and(con, orf);
    	
    	NotFilter not= (NotFilter)FilterBuilder.not(eq);
    
    	
    	//System.out.println(eq.);
    	
    	Attribute attribute = ((EqualsFilter) eq).getAttribute();
    	
    	String s = AttributeUtil.getStringValue(attribute);
    	
    	
    	SalesFrcConfiguration conf= new SalesFrcConfiguration();
    	SalesfrcConnector conn = new SalesfrcConnector();
    	conn.init(conf);

        
    	/// 
       conn.create(userC, GenericBuilderTest(), null);
    	//conn.executeQuery(userC, aeq, handler, null);
    	
    	
    }
    
    private static Set<Attribute> GenericBuilderTest(){
    	
    	// Setting up attribute
    	Set<Attribute> attr = new HashSet<Attribute>();
        
    	// Map for Maultivalue attribute name
    	
    	Map<String,Map<String, Object>> nameMap = new HashMap<String,Map<String,Object>>();
    	Map<String, Object> names = new HashMap<String, Object>();
    	
    	// Map for Maultivalue attribute schema extension
    	
    	Map<String,Map<String, Object>> extensionMap = new HashMap<String,Map<String,Object>>();
    	Map<String, Object> extensionAtributes = new HashMap<String, Object>();
    	
    	
    	// Map for multilayered attribute Email
        Map<String, Collection<Map<String, Object>>> emailMap = new HashMap<String, Collection<Map<String, Object>>>();
        Map<String, Object> emailAttribute1 = new HashMap<String, Object>();
        Map<String, Object> emailAttribute2 = new HashMap<String, Object>();
        
        // Map for multilayered attribute phoneNumbers
        Map<String, Collection<Map<String, Object>>> phoneMap = new HashMap<String, Collection<Map<String, Object>>>();
        Map<String, Object> phoneAttribute1 = new HashMap<String, Object>();
        Map<String, Object> phoneAttribute2 = new HashMap<String, Object>();
        
     // Map for multilayered attribute entitlements
        Map<String, Collection<Map<String, Object>>> entitlementMap = new HashMap<String, Collection<Map<String, Object>>>();
        Map<String, Object> entitlementAttribute1 = new HashMap<String, Object>();
        Map<String, Object> entitlementAttribute2 = new HashMap<String, Object>();
        
        //Name
        names.put("formatted", "Harry Potter");
        names.put("familyName", "Potter");
        names.put("givenName", "Harry");
        
        nameMap.put("name", names);
        
        //extension ########## Enterprise extension
        extensionAtributes.put("organization", "00D58000000YfgfEAC");
        
        extensionMap.put("urn:scim:schemas:extension:enterprise:1.0", extensionAtributes);
        
        // Email 
        emailMap.put("emails", new ArrayList<Map<String,Object>>());
        emailMap.get("emails").add(emailAttribute1);
       
        
        emailAttribute1.put("type", "home");
        
        emailAttribute1.put ("value","someone@hometest554xz.com");
        
        emailAttribute2.put ("primary",true);
        
        emailAttribute2.put("type", "work");
        
        emailAttribute2.put("value","someone@hometest554xz.com");
        
        
        //Entitlements
        
        entitlementMap.put("entitlements", new ArrayList<Map<String,Object>>());
        entitlementMap.get("entitlements").add(entitlementAttribute1);
        
        entitlementAttribute1.put("display", "Custom: Support Profile");
        
        entitlementAttribute1.put ("value","00e58000000qvhqAAA");
        
        entitlementAttribute1.put("primary", true);
        
        
        //Phone
        
        phoneMap.put("phoneNumbers", new ArrayList<Map<String,Object>>());
        phoneMap.get("phoneNumbers").add(phoneAttribute1);
        phoneMap.get("phoneNumbers").add(phoneAttribute2);
        
        phoneAttribute1.put("type", "mobile");
        
        phoneAttribute1.put ("value","+421 910039218");
        
        phoneAttribute2.put("type", "work");
        
        phoneAttribute2.put("value","+421 915039218");
        
        
        // Attribute 
        attr.add(AttributeBuilder.build("layeredAttrribute", emailMap));
        attr.add(AttributeBuilder.build("layeredAttrribute", phoneMap));
        attr.add(AttributeBuilder.build("layeredAttrribute", entitlementMap));
        attr.add(AttributeBuilder.build("multiValueAttrribute", nameMap));
        attr.add(AttributeBuilder.build("multiValueAttrribute",extensionMap));
        attr.add(AttributeBuilder.build("nickName", "HP"));
        attr.add(AttributeBuilder.build("userName", "harryp0234@hogwarts.com"));
        
        return attr;
    }
    
    private static Set<Attribute> OldBuilderTest(){
    	

    	
       	Set<Attribute> attr = new HashSet<Attribute>();
       	
       		
       	attr.add(AttributeBuilder.build("userName", "stefan@stefansplace.com"));
              
           
           Map<String, String> names = CollectionUtil.newCaseInsensitiveMap();
           
           
           Map<String, String> foo = CollectionUtil.newCaseInsensitiveMap();
           
           Map<String, Map<String, Object>> phoneNumbers = CollectionUtil.newCaseInsensitiveMap();
          
           Map<String, Object> type = CollectionUtil.newCaseInsensitiveMap();
           
           Map<String, Map<String, Object>> emails = CollectionUtil.newCaseInsensitiveMap();
          
           Map<String, Object> emailAtr = CollectionUtil.newCaseInsensitiveMap();
          
          foo.put("organization", "00D58000000YfgfEAC");
          
          names.put("formatted", "Ms. Barbara J Jensen III");
          names.put("familyName", "Jensen");
          names.put("givenName", "Barbara");
          
          //names.put("middleName", "Jane");
          //names.put("honorificPrefix", "Ms.");
          //names.put("honorificSuffix", "III");
          
          emails.put("work",emailAtr);
          emailAtr.put("type", "work");
          emailAtr.put("value", "bjensen@example.com");
          emailAtr.put("primary", true);
           
          phoneNumbers.put("home", type);
           
           type.put("display", "Custom: Support Profile");
           type.put("value", "00e58000000qvhqAAA");
           type.put("primary", true);
           
           
           attr.add(AttributeBuilder.build("nickName", "Babs"));
           attr.add(AttributeBuilder.build("entitlements", phoneNumbers));
           
           attr.add(AttributeBuilder.build("emails", emails));
           attr.add(AttributeBuilder.build("urn:scim:schemas:extension:enterprise:1.0", foo));
           
        

    	
    	return attr;
    }
    
    
    static ResultsHandler handler= new ResultsHandler() {
		
		@Override
		public boolean handle(ConnectorObject connectorObject) {
			result.add(connectorObject);
			return true;
		}
	};
	
	
    
    }

