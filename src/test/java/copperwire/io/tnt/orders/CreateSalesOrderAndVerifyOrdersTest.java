package copperwire.io.tnt.orders;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import copperwire.io.tnt.BaseTest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import tntRestAutomation.Resources;
import tntRestAutomation.ReusableMethods;
import tntRestAutomation.TestPropertyReader;

public class CreateSalesOrderAndVerifyOrdersTest extends BaseTest {

	@BeforeSuite
	
	  public void setUp() {
	    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	  }
	@BeforeTest
	public void beforeTest() {
		RestAssured.baseURI = Resources.BASE_URI;
		
		RestAssured.useRelaxedHTTPSValidation();

		// Do Login
		Response response = given().log().all().header("Content-Type", "application/json")
				.body(ReusableMethods.getLogInCredentials()).when().log().all().post("/api/auth/login")

				.then().log().all().assertThat().statusCode(200).extract().response();
		
		ReusableMethods.setSessionId(response.header("Authorization"));

		resolveCustomInputs();
	}
	
	@Test(priority = 1)
	public void createNewSalesOrder() {
		String response = given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId())
				.body(ReusableMethods.getNewSalesOrder(
						"Automation " + ReusableMethods.getDateWithHrMinuteSecond(),
						ReusableMethods.getDateOnly()))
				.when().post(Resources.CREATE_SALES_ORDER).then().log().all().assertThat().statusCode(201).extract()
				.response().asString();
		JsonPath var = new JsonPath(response);
		String order_no = var.get("data.orderno");
		String order_id = Integer.toString((Integer) var.get("data.id"));
		setOrder_no(order_no);
		setOrder_id(order_id);
		System.out.println(order_no);
		System.out.println(order_id);
		

	}

	@Test(priority = 2)
	public void createPallets() {
		setLocation_id("1");
		File f = new File("./src/test/resources/pallets_2.png");
		Response response = given().log().all()
		// @formatter:off
				.header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId())
				.header("Content-Type", "multipart/form-data;")
				.multiPart("order_id", getOrder_id())
				.multiPart("inspected_by", "ASR-AUTOMATION")
				.multiPart("inspected_date", ReusableMethods.getDateWithHrMinuteSecond())
				.multiPart("packed_date", ReusableMethods.getDateWithHrMinuteSecond())
				.multiPart("pallet_dimension_id", "1")
				.multiPart("orderno", getOrder_no())
				.multiPart("location_id", getLocation_id())
				.multiPart("vgm", "1")
				.multiPart("noofpieces", "2")
				.multiPart("description", "Hi I am done by automation")
				.multiPart("DimL", "1")
				.multiPart("DimW", "2")
				.multiPart("DimH", "3")
				.multiPart("packagevgm", "1")
				.multiPart("packagevgmuom", "Kgs")
				.multiPart("packagenoofpieces", "2")
				.multiPart("packagecondition", "Good")
				.multiPart("inspect_packing_id", "undefined")
				.multiPart("status", "inspection")
				.multiPart("order_id", getOrder_id())
				.multiPart("images[0]", new File("./src/test/resources/pallets_2.png"))
				.post(Resources.CREATE_PALLETS_FOR_NEW_ORDER).then().log().all().assertThat().statusCode(200).extract()
				.response();
				// @formatter:on
	}

	@Test(priority = 3)
	public void savepalletinBlockchain() {
		HashMap<String, String> requestbody = new HashMap<String, String>();
		requestbody.put("order_id",getOrder_id());
		requestbody.put("order_no",getOrder_no());
		
		String response =given().log().all().header("Content-Type", "application/json").header("Authorization", ReusableMethods.getSessionId()).body(requestbody)
		.when().post(Resources.SAVE_PALLET_IN_BCSERVER).then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath var = new JsonPath(response);
		
		String transaction_id=var.get("data.transactionid[0]");
		setTransaction_id(transaction_id);
	}
	
	
	
	@Test(priority = 4)
	public void createRfq() {
		String rfq_date = ReusableMethods.getDateOnly();
		String rfq_closing_date = ReusableMethods.getDateOnly();
		String estimate_pick_date = ReusableMethods.getDateOnly();
		String estimate_delivery_date = ReusableMethods.getDateOnly();
		String quoted_date = ReusableMethods.getDateOnly();
		String deliverydate = ReusableMethods.getDateOnly();

		setRfq_date(rfq_date);
		setRfq_closing_date(rfq_closing_date);
		setEstimate_pick_date(estimate_pick_date);
		setEstimate_delivery_date(estimate_delivery_date);
		setQuoted_date(quoted_date);
		setDeliverydate(deliverydate);

		// http://tnt7.copperwiresystems.com/api/rfqs
		// @formatter:off
		Response response = given().log().all()
				.header("Accept", "application/json")
				.header("Authorization", ReusableMethods.getSessionId())
				.header("Content-Type", "multipart/form-data;")
				.multiPart("order_id", getOrder_id())
				.multiPart("rfq_date", rfq_date)
				.multiPart("estimate_pick_date", estimate_pick_date)
				.multiPart("estimate_delivery_date", estimate_delivery_date)
				.multiPart("location_id", getLocation_id())//This needs to be fetched from somewhere
				.multiPart("rfq_closing_date", rfq_closing_date)
				.multiPart("quotes[0][carrier_id]", "1")
				.multiPart("quotes[0][quoted_price]", "150")
				.multiPart("quotes[0][quoted_id]", "123")
				.multiPart("quotes[0][quoted_date]", quoted_date)
				.multiPart("quotes[0][deliverydate]", deliverydate)
				.multiPart("quotes[0][carrier_note]", "HI Carrier not by Automation")
				.multiPart("quotes[0][recordid]", "0")
				.multiPart("quotes[0][attached_documents]", new File("./src/test/resources/quotes.pdf"))
				.multiPart("rfq_document", new File("./src/test/resources/rfq_doc.pdf"))				
				.post(Resources.RFQS).then().log().all().assertThat().statusCode(200).extract()
				.response();
		 		// @formatter:on
	}

	@Test(priority = 5)
	public void getOrderDetails() {
		String response = given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).when()
				.get(Resources.CREATE_SALES_ORDER + "/" + getOrder_id()).then().log().all().assertThat().statusCode(200)
				.extract().response().asString();
		JsonPath var = new JsonPath(response);

		String rfq_id = Integer.toString((Integer) var.get("data.Rfqs[0].id"));
		setRfq_id(rfq_id);
	}

	@Test(priority = 6)
	public void getRfqDetails() {
		// Stage 2 : Fetch RFQ and carrier details
		// @formatter:off
				String response = given().log().all()
						.header("Accept", "application/json")
						.header("Authorization", ReusableMethods.getSessionId())			
						.get(Resources.RFQS+"/"+getRfq_id()).then().log().all().assertThat().statusCode(200).extract()
						.response().asString();
				 		// @formatter:on
		JsonPath var = new JsonPath(response);
		// String rfq_id = Integer.toString((Integer) var.get("data.id"));
		String rfq_carrier_id = Integer.toString((Integer) var.get("data.Rfqs_carrier[0].id"));
		String carrier_id = Integer.toString((Integer) var.get("data.Rfqs_carrier[0].carrier.id"));
		String rfq_carrier_quote_id = var.get("data.Rfqs_carrier[0].quote_id");
		// Set for future use
		// setRfq_id(rfq_id);
		setRfq_carrier_id(rfq_carrier_id);
		setCarrier_id(carrier_id);
		setRfq_carrier_quote_id(rfq_carrier_quote_id);

		// TODO: can assert on received values as they are exact same as sent in
		// createRfq()
	}

	@Test(priority = 7, enabled = true)
	public void bookRfq() {
		String bookPayload = ReusableMethods.getBookPayload(getOrder_id(), getRfq_id(), getRfq_carrier_id(),
				getCarrier_id(), getRfq_carrier_quote_id(), getQuoted_date());
		// @formatter:off
		String response = given().log().all()
				.header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId())
				.body(bookPayload)
				.when().post(Resources.BOOK_RFQ).then().log().all()
				.assertThat().statusCode(201)
				.extract().response().asString();
		// @formatter:on
		// Assert on status of book
		JsonPath var = new JsonPath(response);
		String bookStatus = var.get("data.status");
		Assert.assertEquals(bookStatus, "Booked");
	}

	@Test(priority = 8, enabled = true)
	public void getOrderDetailsForQuotation() {
		String response = given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).when()
				.get(Resources.CREATE_SALES_ORDER + "/" + getOrder_id()).then().log().all().assertThat().statusCode(200)
				.extract().response().asString();
		JsonPath var = new JsonPath(response);

		String quote_id = Integer.toString((Integer) var.get("data.Quotations[0].id"));
		setQuote_id(quote_id);
	}

	@Test(priority = 9)
	public void pickUpAndShip() {
		Response response = given().log().all()
		// @formatter:off
				.header("Accept", "application/json")
				.header("Authorization", ReusableMethods.getSessionId())
				.header("Content-Type", "multipart/form-data;")
				.multiPart("id","0")
				.multiPart("order_id", getOrder_id())
				.multiPart("rfq_id",getRfq_id() )
				.multiPart("orderno",getOrder_no() )
				.multiPart("quote_id",getQuote_id() )
				.multiPart("booking_id", "BKID123")
				.multiPart("shipping_trucking_no", "TruckNo USA CAL 012")
				.multiPart("pickup_date", ReusableMethods.getDateOnly())
				.multiPart("pickup_time", "12:00")
				.multiPart("location_id", getLocation_id())
				.multiPart("pickup_by","ASR")
				.multiPart("images[0]", new File("./src/test/resources/pallets_2.png"))
				.post(Resources.PICKUP_N_SHIP).then().log().all().assertThat().statusCode(200).extract()
				.response();
				// @formatter:on

		JsonPath var = new JsonPath(response.asString());
		String bookStatus = var.get("data.status");
		Assert.assertEquals(bookStatus, "Picked");
	}

	@Test(priority = 10)
	public void doCustomerFeedback() {
		Response response = given().log().all()
		// @formatter:off
				.header("Accept", "application/json")
				.header("Authorization", ReusableMethods.getSessionId())
				.header("Content-Type", "multipart/form-data;")
				.multiPart("id","0")
				.multiPart("order_id", getOrder_id())
				.multiPart("noofpackage","2" )
				.multiPart("condition","No Visible Damage" )
				.multiPart("comments","Hi i m customer and i have received package-via automation" )
				.multiPart("acceptance_status", "Accept")
				.multiPart("delivery_date", ReusableMethods.getDateWithHrMinuteSecond())//format is different expected is 2020-11-20 13:3:20
				.multiPart("images[0]", new File("./src/test/resources/pallets_2.png"))
				.post(Resources.CUSTOMER_FEEBACK).then().log().all().assertThat().statusCode(200).extract()
				.response();
				// @formatter:o
		
	}
	
	@Test(priority = 11)
	public void getOrderDetailsForPickupShipDetails() {
		String response = given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).when()
				.get(Resources.CREATE_SALES_ORDER + "/" + getOrder_id()).then().log().all().assertThat().statusCode(200)
				.extract().response().asString();
		JsonPath var = new JsonPath(response);

		String pickupship_id = Integer.toString((Integer) var.get("data.Pickupship[0].id"));
		setPickupship_id(pickupship_id);
	}

	
	@Test(priority = 12)
	public void invoce() {
		Response response = given().log().all()
		// @formatter:off
				.header("Accept", "application/json")
				.header("Authorization", ReusableMethods.getSessionId())
				.header("Content-Type", "multipart/form-data;")
				.multiPart("id","0")
				.multiPart("order_id", getOrder_id())
				.multiPart("rfq_id",getRfq_id())
				.multiPart("orderno",getOrder_no() )
				.multiPart("quote_id",getQuote_id() )
				.multiPart("pickupship_id", getPickupship_id())
				.multiPart("invoice_id", "InvoiceId-"+getOrder_no())
				.multiPart("invoice_date",ReusableMethods.getDateOnly())
				.multiPart("invoice_amount","123")
				.multiPart("invoice_due_date",ReusableMethods.getDateOnly())
				.multiPart("invoice_document", new File("./src/test/resources/invoice_documents.pdf"))
				.multiPart("payment_currency","USD")
				.multiPart("payment_amount","12")
				.multiPart("payment_date",ReusableMethods.getDateOnly())
				.multiPart("payment_method","Check")
				.multiPart("payment_status","Paid")
				.multiPart("images[0]", new File("./src/test/resources/pallets_2.png"))
				.post(Resources.INVOICE).then().log().all().assertThat().statusCode(200).extract()
				.response();
				// @formatter:o
	}
	
	//////////////////////////////////////////////////////////////////////////////
	///////////////////////////Verify Begins/////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	@Test(priority = 13,description = "GET_ORDER")
	public void fetchOrderById() {
		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId())

				.when().log().all().get(Resources.FETCH_ALL_ORDER + "/" + getOrder_id())

				.then().log().all().assertThat().statusCode(200).extract().response().asString();
	}

	@Test(priority = 14)
	public void fetchProductDetailsById() {
		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId())

				.when().log().all().get(Resources.FETCH_ORDERED_PRODUCT_DETAILS_BY_ID + "/" + getOrder_id())

				.then().log().all().assertThat().statusCode(200).extract().response().asString();
	}

	// This is similar to click on verify link of first option of Inspect and Pack
	@Test(priority = 15, description = "INSPECT_AND_PACK")
	public void verifyInspectAndPack() {

		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("orderno", getOrder_no());
		queryParam.put("order_id", getOrder_id());
		queryParam.put("inspect_id", getInspect_id());

		String response=given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParams(queryParam)

				.when().log().all().get(Resources.VERIFY_INSPECT_AND_PACK)

				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		
		//JsonPath var = new JsonPath(response);

		//String transaction_id = var.get("data.transactionid");
		//setTransaction_id(transaction_id);
		
	}
	@Test(priority = 16,description = "VALIDATE_INSPECT_AND_PACK" , enabled= true)
  public void inspect_Pack_Validate() {

		
		String response=given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId())

				.when().get((Resources.VALIDATE_INSPECT_AND_PACK)+"/"+getTransaction_id())

				.then().log().all().assertThat().statusCode(200).extract().response().toString();
		//JsonPath var = new JsonPath(response);
		//String transactionid = var.get("data.transactionid");
		//setTransaction_id(transactionid);

		
	}

	@Test(priority = 17, description = "RFQ")
	public void verifyRfq() {

		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("orderno", getOrder_no());
		queryParam.put("order_id", getOrder_id());
		queryParam.put("rfq_id", getRfq_id());

		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParams(queryParam)

				.when().get(Resources.VERIFY_RFQ)

				.then().log().all().assertThat().statusCode(200).extract().response().asString();

	}
	
	
	

	@Test(priority = 18, description = "BOOK")
	public void verifyBook() {

		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("orderno", getOrder_no());
		queryParam.put("order_id", getOrder_id());
		queryParam.put("book_id", getQuote_id());

		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParams(queryParam)

				.when().get(Resources.VERIFY_RFQ)

				.then().log().all().assertThat().statusCode(200).extract().response().asString();

	}

	@Test(priority = 19, description = "PICKUP_AND_SHIP")
	public void verifyPickupAndShip() {

		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("orderno", getOrder_no());
		queryParam.put("order_id", getOrder_id());
		queryParam.put("pickupship_id", getPickupship_id());

		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParams(queryParam)

				.when().get(Resources.VERIFY_RFQ)

				.then().log().all().assertThat().statusCode(200).extract().response().asString();

	}

	@Test(priority = 20, description = "INVOICE")
	public void verifyInvoice() {

		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("orderno", getOrder_no());
		queryParam.put("order_id", getOrder_id());
		queryParam.put("invoice_id", getInvoice_id());

		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParams(queryParam)

				.when().get(Resources.VERIFY_RFQ)

				.then().log().all().assertThat().statusCode(200).extract().response().asString();

	}

	@Test(priority = 21, description = "FEEDBACK")
	public void verifyCustomerAcceptance() {

		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("orderno", getOrder_no());
		queryParam.put("order_id", getOrder_id());
		queryParam.put("customerfeedback_id", getCustomerFeedback_id());

		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParams(queryParam)

				.when().get(Resources.VERIFY_RFQ)

				.then().log().all().assertThat().statusCode(200).extract().response().asString();

	}
	
	////////////////// RESOLVE INPUTS //////////////////////////////////////////////
	private void resolveCustomInputs() {
			ReusableMethods.setExecutionFromMaven(true);
			//Read from config file
			ReusableMethods.initStageFromConfig();
	}
	
}
