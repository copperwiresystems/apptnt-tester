package copperwire.io.tnt.orders;

import static io.restassured.RestAssured.given;

import java.io.File;

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

public class CreatePurchaseOrderAndVerifyOrdersTnt8 extends BaseTest {
	@BeforeSuite
	
	  public void setUp() {
	    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	  }
	@BeforeTest
	public void beforeTest() {
		RestAssured.baseURI =  TestPropertyReader.getProperty("baseURI1");
		RestAssured.useRelaxedHTTPSValidation();

		// Do Login
		Response response = given().log().all().header("Content-Type", "application/json")
				.body(ReusableMethods.getLogInCredentials()).when().log().all().post("/api/auth/login")

				.then().log().all().assertThat().statusCode(200).extract().response();
		
		ReusableMethods.setSessionId(response.header("Authorization"));

		
	}

	@Test(priority = 1)
	public void createNewPurchaseOrder() {
		String response = given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId())
				.body(ReusableMethods.getNewSalesOrder(
						"Automation " + ReusableMethods.getDateWithHrMinuteSecond(),
						ReusableMethods.getDateOnly(),"PV Automation"))
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
	public void createCommercialInvoice() {
		//setLocation_id("1");
		File f = new File("./src/test/resources/pallets_2.png");
		Response response = given().log().all()
		// @formatter:off
				.header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId())
				.header("Content-Type", "multipart/form-data; boundary=---WebKitFormBoundarySSG6RACdGi6e2nAy")
				.multiPart("id","0")
				.multiPart("order_id", getOrder_id())
				.multiPart("orderno", getOrder_no())
				.multiPart("invoice_date", ReusableMethods.getDateWithHrMinuteSecond())
				.multiPart("invoice_id", "InvoiceId-"+getOrder_no())
				.multiPart("invoice_amount","123")
				.multiPart("invoice_document", new File("./src/test/resources/pallets_2.png"))
				.multiPart("invoice_uom","Invoice_UOM")
				.multiPart("exporter_id", "2")
				.multiPart("buyer_id", "2")
				.multiPart("consignee_id", "2")
				.multiPart("country_of_origin", "US")
				.multiPart("pre_carriage", "pre_carriage")
				.multiPart("place_of_receipt", "Washington")
				.multiPart("mode_of_transport", "Flight")
				.multiPart("port_of_loading", "Seattle")
				.multiPart("port_of_discharge", "Seattle")
				.multiPart("final_destination", "Alberta")
				.multiPart("description_goods", "done by automation")
				.multiPart("packed_date", ReusableMethods.getDateWithHrMinuteSecond())
				.multiPart("pallet_dimension_id", "1")
				.multiPart("no_of_bales", "2")
				.multiPart("width", "100")
				.multiPart("width_uom", "cm")
				.multiPart("total_qty", "2")
				.multiPart("net_wt", "300")
				.multiPart("wt_uom", "kg")
				.multiPart("fob_rate", "100")
				.multiPart("fob_uom", "USD")
                .multiPart("payment_currency","USD")
				.multiPart("payment_amount","100")
				.multiPart("payment_date",ReusableMethods.getDateOnly())
				.multiPart("payment_method","Other")
				.multiPart("payment_status","Paid")
				.multiPart("images[0]", new File("./src/test/resources/pallets_2.png"))
				.post(Resources.COM_INVOICE).then().log().all().assertThat().statusCode(200).extract()
				.response();

				// @formatter:on
	}

	
}
