package tntRestAutomation;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class ReusableMethods {

	private static String sessionId;
	private static Map<String, Boolean> allowedExecutionOrder = new LinkedHashMap<String, Boolean>();
	private static String executeUpto = null;
	private static boolean executionFromMaven;
	private static String orderId = null;
	private static String Transactionid;

	public static String getTransactionid() {
		return Transactionid;
	}

	public static void setTransactionid(String transactionid) {
		Transactionid = transactionid;
	}

	public static String getSessionId() {
		return sessionId;
	}

	public static void setSessionId(String sessionIdRec) {
		sessionId = "Bearer " + sessionIdRec;
	}
  
	public static String getLogInCredentials() {
		String email = TestPropertyReader.getProperty("login.email.id");
		String password = TestPropertyReader.getProperty("login.email.password");
		return "{\"email\":\"" + email + "\",\"password\":\"" + password + "\",\"remember\":false}";
	}

	public static String getBookPayload(String orderId, String rfq_id, String rfqs_carrier_id, String carrier_id,
			String quote_id, String quote_date) {
		return "{\r\n" + "  \"order_id\": " + orderId + ",\r\n" + "  \"rfq_id\": " + rfq_id + ",\r\n"
				+ "  \"rfqs_carriers_id\": " + rfqs_carrier_id + ",\r\n" + "  \"carrier_id\": " + carrier_id + ",\r\n"
				+ "  \"quote_id\": \"" + quote_id + "\",\r\n" + "  \"quote_date\": \"" + quote_date + "\"\r\n" + "}";
	}

	public static String getNewSalesOrder(String orderName, String orderDate) {
		// @formatter:off
		String payload="{\r\n" + 
				"  \"order_name\": \""+orderName+"\",\r\n" + 
				"  \"order_date\": \""+orderDate+"\",\r\n" + 
				"  \"order_type\": 1,\r\n" + 
				"  \"customer_id\": 3,\r\n" + 
				"  \"channel_id\": 1,\r\n" + 
				"  \"address1\": \"123 New Dom,\\nbareilli,IN-67564\",\r\n" + 
				"  \"totalqty\": 0,\r\n" + 
				"  \"orderdate\": \"\",\r\n" + 
				"  \"productdetails\": [\r\n" + 
				"    {\r\n" + 
				"      \"product_id\": 1,\r\n" + 
				"      \"sku\": \"TA12345\",\r\n" + 
				"      \"Product_name\": \"Ivory Top Table\",\r\n" + 
				"      \"qty\": 1,\r\n" + 
				"      \"price\": 105,\r\n" + 
				"      \"totalvalue\": 105\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"product_id\": 2,\r\n" + 
				"      \"sku\": \"CH45788\",\r\n" + 
				"      \"Product_name\": \"Gray Chair\",\r\n" + 
				"      \"qty\": 1,\r\n" + 
				"      \"price\": 90,\r\n" + 
				"      \"totalvalue\": 90\r\n" + 
				"    }\r\n" + 
				"  ],\r\n" + 
				"  \"display_time\": null\r\n" + 
				"}";
		//@formatter:on
		return payload;
	}
	public static String getNewSalesOrder(String orderName, String orderDate, String orderBy) {
		// @formatter:off
		String payload="{\r\n" + 
				"  \"order_name\": \""+orderName+"\",\r\n" + 
				"  \"order_date\": \""+orderDate+"\",\r\n" + 
				"  \"order_type\": 1,\r\n" + 
				"  \"customer_id\": 2,\r\n" + 
				"  \"channel_id\": 1,\r\n" + 
				"  \"address1\": \"R 2/3 seattle Washington,\\nseattle,US-98109\",\r\n" + 
				"  \"totalqty\": 0,\r\n" + 
				"  \"orderdate\": \"\",\r\n" + 
				"  \"required_ship_date\": \""+orderDate+"\",\r\n"+
				"  \"location_id\": 1,\r\n"+
				"  \"bill_to_id\" :2,\r\n"+
				"  \"billingAddress\" :\"R 2/3 seattle Washington,\\nseattle,US-98109\",\r\n" +
				"  \"vendor_id\" :2,\r\n" +
				"  \"vendorAddress\" :\"R 2/3 seattle Washington,\\nseattle,US-98109\",\r\n" +
				"  \"ordered_by\" :\"" +orderBy+"\",\r\n" +
				"  \"ship_via\" :\"UBER\",\r\n" +
				"  \"fob\" :2,\r\n"+
				"  \"terms_desc\" :\"This order is done using automation\" ,\r\n"+
				"  \"productdetails\": [\r\n" + 
				"    {\r\n" + 
				"      \"product_id\": 2,\r\n" + 
				"      \"sku\": \"011\",\r\n" + 
				"      \"Product_name\": \"Sofa\",\r\n" + 
				"      \"qty\": 10,\r\n" + 
				"      \"price\": 10,\r\n" + 
				"      \"totalvalue\": 100\r\n" + 
				"    }" + 
				"    ],\r\n" +
				"    \"display_time\": null\r\n" + 
				"}";
		//@formatter:on
		return payload;
	}
	
	public static String getDateOnly() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateOnly = dateFormat.format(new Date());
		return dateOnly;
	}

	public static String getDateWithHrMinuteSecond() {
		SimpleDateFormat dateFormatHr = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		String dateWithSeconds = dateFormatHr.format(new Date());
		return dateWithSeconds;
	}

	public static void initStage(String upto) {
		executeUpto = upto;
	}

	public static void initStageFromConfig() {
		executeUpto = TestPropertyReader.getProperty("upto_stage");
	}

	public static <T> void addMethod(Class<T> callingClass) {
		Method[] methods = callingClass.getMethods();

		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.toLowerCase().startsWith("beforeclass") || methodName.toLowerCase().startsWith("init")) {
				continue;
			}
			allowedExecutionOrder.put(methodName, false);
		}
	}

	public static boolean getStage(String annotationParam) {
		boolean status = false;
		// No data continue test
		if (null == executeUpto || 0 == executeUpto.length()) {
			status = false;
		}

		// Match found stop test execution after this
		else if (executeUpto.equalsIgnoreCase(annotationParam)) {
			status = true;
		}
		return status;
	}

	public static boolean isExecutionFromMaven() {
		return executionFromMaven;
	}

	public static void setExecutionFromMaven(boolean executionFromMaven) {
		ReusableMethods.executionFromMaven = executionFromMaven;
	}

	public static String getOrderId() {
		return orderId;
	}

	public static void setOrderId(String orderId) {
		ReusableMethods.orderId = orderId;
	}
	

	public static void initOrderIdFromConfig() {
		orderId = TestPropertyReader.getProperty("order_id");
	}

	public static String getBaseUri() {
		return TestPropertyReader.getProperty("baseURI");
	}

	public static void main(String []ar) {
		initStageFromConfig();
		initOrderIdFromConfig();
		System.out.println(executeUpto);
		System.out.println(orderId);
		
	}
	
}
