package copperwire.io.tnt;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;

public class BaseTest {
	private static String order_id;
	private static String order_no;
	private static String inspect_id;
	private static String rfq_id;
	private static String book_id;
	private static String pickupship_id;
	private static String invoice_id;
	private static String customerFeedback_id;
	private static String location_id;
	private static String rfq_date;
	private static String rfq_closing_date;
	private static String rfq_carrier_id;
	private static String estimate_pick_date;
	private static String estimate_delivery_date;
	private static String quote_id;
	private static String rfq_carrier_quote_id;
	private static String quoted_date;
	private static String deliverydate;	
	private static String carrier_id;
	
	public static Logger log = null;

	public BaseTest() {
		String x = this.getClass().getSimpleName();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		String path = dateFormat.format(new Date()) +"\\"+ x + ".log";
		System.setProperty("logfile.name", path);
		log = LogManager.getLogger(x);
	}

	public static String getOrder_id() {
		return order_id;
	}

	public static void setOrder_id(String order_id) {
		BaseTest.order_id = order_id;
	}

	public static String getOrder_no() {
		return order_no;
	}

	public static void setOrder_no(String order_no) {
		BaseTest.order_no = order_no;
	}

	public static String getInspect_id() {
		return inspect_id;
	}

	public static void setInspect_id(String inspect_id) {
		BaseTest.inspect_id = inspect_id;
	}

	public static String getRfq_id() {
		return rfq_id;
	}

	public static void setRfq_id(String rfq_id) {
		BaseTest.rfq_id = rfq_id;
	}

	public static String getBook_id() {
		return book_id;
	}

	public static void setBook_id(String book_id) {
		BaseTest.book_id = book_id;
	}

	public static String getPickupship_id() {
		return pickupship_id;
	}

	public static void setPickupship_id(String pickupship_id) {
		BaseTest.pickupship_id = pickupship_id;
	}

	public static String getInvoice_id() {
		return invoice_id;
	}

	public static void setInvoice_id(String invoice_id) {
		BaseTest.invoice_id = invoice_id;
	}

	public static String getCustomerFeedback_id() {
		return customerFeedback_id;
	}

	public static void setCustomerFeedback_id(String customerFeedback_id) {
		BaseTest.customerFeedback_id = customerFeedback_id;
	}

	public static String getLocation_id() {
		return location_id;
	}

	public static void setLocation_id(String location_id) {
		BaseTest.location_id = location_id;
	}
	public static String getRfq_date() {
		return rfq_date;
	}

	public static void setRfq_date(String rfq_date) {
		BaseTest.rfq_date = rfq_date;
	}

	public static String getRfq_closing_date() {
		return rfq_closing_date;
	}

	public static void setRfq_closing_date(String rfq_closing_date) {
		BaseTest.rfq_closing_date = rfq_closing_date;
	}

	public static String getEstimate_pick_date() {
		return estimate_pick_date;
	}

	public static void setEstimate_pick_date(String estimate_pick_date) {
		BaseTest.estimate_pick_date = estimate_pick_date;
	}

	public static String getEstimate_delivery_date() {
		return estimate_delivery_date;
	}

	public static void setEstimate_delivery_date(String estimate_delivery_date) {
		BaseTest.estimate_delivery_date = estimate_delivery_date;
	}

	public static String getQuoted_date() {
		return quoted_date+" 00:00:00";
	}

	public static void setQuoted_date(String quoted_date) {
		BaseTest.quoted_date = quoted_date;
	}

	public static String getDeliverydate() {
		return deliverydate;
	}

	public static void setDeliverydate(String deliverydate) {
		BaseTest.deliverydate = deliverydate;
	}

	public static String getRfq_carrier_id() {
		return rfq_carrier_id;
	}

	public static void setRfq_carrier_id(String rfq_carrier_id) {
		BaseTest.rfq_carrier_id = rfq_carrier_id;
	}

	public static String getQuote_id() {
		return quote_id;
	}

	public static void setQuote_id(String quote_id) {
		BaseTest.quote_id = quote_id;
	}

	public static String getCarrier_id() {
		return carrier_id;
	}

	public static void setCarrier_id(String carrier_id) {
		BaseTest.carrier_id = carrier_id;
	}

	public static String getRfq_carrier_quote_id() {
		return rfq_carrier_quote_id;
	}

	public static void setRfq_carrier_quote_id(String rfq_carrier_quote_id) {
		BaseTest.rfq_carrier_quote_id = rfq_carrier_quote_id;
	}
}
