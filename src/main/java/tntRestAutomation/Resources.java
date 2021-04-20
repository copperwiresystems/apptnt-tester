package tntRestAutomation;
import java.io.File;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class Resources {

	public static final String BASE_URI;

	public static final String CREATE_CUSTOMER = "/api/customer";

	public static final String GET_CUSTOMER_BY_ID = "/api/customer/8";

	public static final String GET_CUSTOMER_BY_NAME = "/api/customer";

	public static final String CREATE_SALES_ORDER = "/api/orders";

	public static final String CREATE_PALLETS_FOR_NEW_ORDER = "/api/inspectpack";
	
	public static final String SAVE_PALLET_IN_BCSERVER="/api/inspectpacks/saveInBlockchain";

	public static final String GET_CUSTOMERS_BY_EMAIL_ID = "/api/customers/emailid";

	public static final String FETCH_ALL_ORDER = "/api/orders";

	public static final String FETCH_ORDERED_PRODUCT_DETAILS_BY_ID = "/api/orderproducts";

	// Under ORDER page ->Order Tracking tab
	public static final String VERIFY_INSPECT_AND_PACK = "/api/inspectpacks/GetMutablepropertiesApi";
	public static final String VALIDATE_INSPECT_AND_PACK ="/api/customerfeedback/getmutablepropertiesloginapi";

	// uNDER Ordr page -> Order tracking -> RFQ->Verify
	public static final String VERIFY_RFQ = "/api/rfq/getmutablepropertiesapi";  

	public static final String RFQS = "/api/rfqs";

	public static final String BOOK_RFQ = "/api/quotation";

	public static final String PICKUP_N_SHIP = "/api/pickupship";

	public static final String PICKUP_N_SHIP_SEND_MAIL = "/api/pickupship/sendmail";

	public static final String CUSTOMER_FEEBACK = "/api/feedback";

	public static final String INVOICE = "/api/invoices";
	
	public static final String DELIVER_ACCEPT="/api/customerfeedback";
	
	public static final String COM_INVOICE="/api/cominvoice";
	
	//Static property init
	static {
		BASE_URI = TestPropertyReader.getBaseUri();
	}
}
