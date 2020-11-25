package copperwire.io.tnt.orders;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import copperwire.io.tnt.BaseTest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import tntRestAutomation.Resources;
import tntRestAutomation.ReusableMethods;

public class GetOrderByIdTest extends BaseTest {

	private static String order_id = "";
	private static String order_no = "";
	private static String inspect_id = "";
	private static String rfq_id = "";
	private static String book_id = "";
	private static String pickupship_id = "";
	private static String invoice_id = "";
	private static String customerFeedback_id = "";

	@BeforeTest
	public void beforeTest() {
		RestAssured.baseURI = Resources.BASE_URI;
		RestAssured.useRelaxedHTTPSValidation();

		// Do Login
		Response response = given().log().all().header("Content-Type", "application/json")
				.body(ReusableMethods.getLogInCredentials()).when().log().all().post("/api/auth/login")

				.then().log().all().assertThat().statusCode(200).extract().response();// response().seasString();
		ReusableMethods.setSessionId(response.header("Authorization"));
	}

	// This is dependent API of fetchOrderById

	@Test(priority = 1, description = "GET_ORDER", dependsOnMethods = { "fetchOrderById" })
	public void fetchProductDetailsById() {
		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId())

				.when().log().all().get(Resources.FETCH_ORDERED_PRODUCT_DETAILS_BY_ID + "/" + order_id)

				.then().log().all().assertThat().statusCode(200).extract().response().asString();
	}

	@Parameters({ "order_id", "upto_stage" })
	@Test
	public void fetchOrderById(String order_id, String upto_stage) {
		System.out.println("Received Param : " + upto_stage + " " + order_id);
		ReusableMethods.initStage(upto_stage);
		GetOrderByIdTest.order_id = order_id;

		String res = given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId())

				.when().log().all().get(Resources.FETCH_ALL_ORDER + "/" + order_id)

				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath var = new JsonPath(res);
		order_id = Integer.toString((Integer) var.get("data.id"));
		order_no = var.get("data.orderno");
		inspect_id = Integer.toString((Integer) var.get("data.InspectPacking[0].id"));
		rfq_id = Integer.toString((Integer) var.get("data.Quotations[0].rfqs_carrier.rfq_id"));
		book_id = Integer.toString((Integer) var.get("data.Invoice[0].quote_id"));
		pickupship_id = Integer.toString((Integer) var.get("data.Invoice[0].pickupship_id"));
		invoice_id = Integer.toString((Integer) var.get("data.Invoice[0].invoice_attachment[0].invoice_id"));
		customerFeedback_id = Integer.toString((Integer) var.get("data.CustomerFeedback[0].id"));
	}

	// This is similar to click on verify link of first option of Inspect and Pack
	@Test(priority = 2, description = "INSPECT_AND_PACK")
	public void verifyInspectAndPack() {

		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("orderno", order_no);
		queryParam.put("order_id", order_id);
		queryParam.put("inspect_id", inspect_id);

		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParams(queryParam)

				.when().get(Resources.VERIFY_INSPECT_AND_PACK)

				.then().log().all().assertThat().statusCode(200).extract().response().asString();
	}

	@Test(priority = 3, description = "RFQ")
	public void verifyRfq() {

		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("orderno", order_no);
		queryParam.put("order_id", order_id);
		queryParam.put("rfq_id", rfq_id);

		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParams(queryParam)

				.when().get(Resources.VERIFY_RFQ)

				.then().log().all().assertThat().statusCode(200).extract().response().asString();

	}

	@Test(priority = 4, description = "BOOK")
	public void verifyBook() {

		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("orderno", order_no);
		queryParam.put("order_id", order_id);
		queryParam.put("book_id", book_id);

		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParams(queryParam)

				.when().get(Resources.VERIFY_RFQ)

				.then().log().all().assertThat().statusCode(200).extract().response().asString();

	}

	@Test(priority = 5, description = "PICKUP_AND_SHIP")
	public void verifyPickupAndShip() {

		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("orderno", order_no);
		queryParam.put("order_id", order_id);
		queryParam.put("pickupship_id", pickupship_id);

		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParams(queryParam)

				.when().get(Resources.VERIFY_RFQ)

				.then().log().all().assertThat().statusCode(200).extract().response().asString();

	}

	@Test(priority = 6, description = "FEEDBACK")
	public void verifyCustomerAcceptance() {

		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("orderno", order_no);
		queryParam.put("order_id", order_id);
		queryParam.put("customerfeedback_id", customerFeedback_id);

		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParams(queryParam)

				.when().get(Resources.VERIFY_RFQ)

				.then().log().all().assertThat().statusCode(200).extract().response().asString();

	}

	@Test(priority = 7, description = "INVOICE")
	public void verifyInvoice() {

		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("orderno", order_no);
		queryParam.put("order_id", order_id);
		queryParam.put("invoice_id", invoice_id);

		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParams(queryParam)

				.when().get(Resources.VERIFY_RFQ)

				.then().log().all().assertThat().statusCode(200).extract().response().asString();

	}
}
