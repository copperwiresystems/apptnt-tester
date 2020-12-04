package copperwire.io.tnt.menu;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import tntRestAutomation.Resources;
import tntRestAutomation.ReusableMethods;

/**
 * Positive test to verify main menu flow i.e. <b>FullFill</b> : <br>
 * FullFill -> Inspect_PACK - > RFQ_BOOK -> PICKUP_SHIP -> INVOICE ->
 * Deliver_ACCEPT<br>
 * 
 * @author abhishek.rathore
 *
 */

public class FullFillMenuValidation {

	@BeforeTest
	public void beforeTest() {
		System.out.println("Executing Test Class : <VerifyFullFillMenu>");
		RestAssured.baseURI = Resources.BASE_URI;
		RestAssured.useRelaxedHTTPSValidation();

		// Do Login
		Response response = given().log().all().header("Content-Type", "application/json")
				.body(ReusableMethods.getLogInCredentials()).when().log().all().post("/api/auth/login")

				.then().log().all().assertThat().statusCode(200).extract().response();
		ReusableMethods.setSessionId(response.header("Authorization"));

		resolveCustomInputs();
	}

	@Test(priority = 1, description = "INSPECT_AND_PACK")
	public void inspect_Pack() {

		HashMap<String, String> queryParam = new LinkedHashMap<String, String>();
		queryParam.put("page", "1");
		queryParam.put("limit", "15");
		queryParam.put("sort", "id");
		queryParam.put("active", "Sale");
		queryParam.put("keyword", "");

		given().log().all()

				.header("Content-Type", "application/json").header("Authorization", ReusableMethods.getSessionId())
				.queryParams(queryParam)

				.when().get(Resources.CREATE_PALLETS_FOR_NEW_ORDER).then().log().all().assertThat().statusCode(200)
				.extract().response().asString();

	}

	@Test(priority = 2, description = "RFQ")
	public void verifyRfq_Book() {

		HashMap<String, String> queryParam = new LinkedHashMap<String, String>();
		queryParam.put("page", "1");
		queryParam.put("limit", "15");
		queryParam.put("sort", "+id");
		queryParam.put("active", "Sale");
		queryParam.put("keyword", "");

		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParams(queryParam)

				.when().get(Resources.RFQS)

				.then().log().all().assertThat().statusCode(200);

	}

	@Test(priority = 3, description = "PICKUP_AND_SHIP")
	public void verifyPickup_Ship() {

		HashMap<String, String> queryParam = new LinkedHashMap<String, String>();
		queryParam.put("page", "1");
		queryParam.put("limit", "15");
		queryParam.put("sort", "+id");
		queryParam.put("active", "Sale");
		queryParam.put("keyword", "");

		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParams(queryParam)

				.when().get(Resources.PICKUP_N_SHIP)

				.then().log().all().assertThat().statusCode(200);

	}

	@Test(priority = 4, description = "INVOICE")
	public void verifyInvoices() {

		HashMap<String, String> queryParam = new LinkedHashMap<String, String>();
		queryParam.put("page", "1");
		queryParam.put("limit", "15");
		queryParam.put("sort", "+id");
//		queryParam.put("active","Sale");
		queryParam.put("keyword", "");

		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParams(queryParam)

				.when().get(Resources.INVOICE)

				.then().log().all().assertThat().statusCode(200);

	}

	@Test(priority = 5, description = "DELIVER_ACCEPT")
	public void verifyDeliver_Accept() {

		HashMap<String, String> queryParam = new LinkedHashMap<String, String>();
		queryParam.put("page", "1");
		queryParam.put("limit", "15");
		queryParam.put("sort", "+id");
		queryParam.put("keyword", "");

		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParams(queryParam)

				.when().get(Resources.DELIVER_ACCEPT)

				.then().log().all().assertThat().statusCode(200);

	}

	////////////////// RESOLVE INPUTS //////////////////////////////////////////////
	private void resolveCustomInputs() {
		ReusableMethods.setExecutionFromMaven(true);
		// Read from config file
		ReusableMethods.initStage("ALL");
	}

}
