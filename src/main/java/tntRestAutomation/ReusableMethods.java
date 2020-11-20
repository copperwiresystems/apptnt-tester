package tntRestAutomation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReusableMethods {

	private static String sessionId;

	public static String getSessionId() {
		return sessionId;
	}

	public static void setSessionId(String sessionIdRec) {
		sessionId = "Bearer " + sessionIdRec;
	}

	public static String getLogInCredentials() {
		return "{\"email\":\"admin@copperwire.io\",\"password\":\"C0pp3rw1r3\",\"remember\":false}";
	}

	public static String getBookPayload(String orderId, String rfq_id, String rfqs_carrier_id, String carrier_id,
			String quote_id, String quote_date) {
		return "{\r\n" + "  \"order_id\": " + orderId + ",\r\n" + "  \"rfq_id\": " + rfq_id + ",\r\n"
				+ "  \"rfqs_carriers_id\": " + rfqs_carrier_id + ",\r\n" + "  \"carrier_id\": " + carrier_id
				+ ",\r\n" + "  \"quote_id\": \"" + quote_id + "\",\r\n" + "  \"quote_date\": \"" + quote_date
				+ "\"\r\n" + "}";
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
	
	public static void main(String []ar) {
		System.out.println(getBookPayload("120", "51", "56", "1", "123", "2020-11-20"));
	}

}
