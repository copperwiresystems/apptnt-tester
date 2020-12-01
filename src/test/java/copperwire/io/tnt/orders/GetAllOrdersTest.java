package copperwire.io.tnt.orders;

import static io.restassured.RestAssured.given;

import org.testng.TestNG;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import copperwire.io.listeners.TestAnnotationTransformerListener;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import tntRestAutomation.Resources;
import tntRestAutomation.ReusableMethods;

public class GetAllOrdersTest {
//	public static Logger log = LogManager.getLogger(GetAllOrdersTest.class.getName());

	@BeforeTest
	public void beforeTest() {
		System.out.println("Executing Test Class : <GetAllOrdersTest>");

		RestAssured.baseURI = Resources.BASE_URI;
		RestAssured.useRelaxedHTTPSValidation();

		// Do Login
		Response response = given().log().all().header("Content-Type", "application/json")
				.body(ReusableMethods.getLogInCredentials()).when().log().all().post("/api/auth/login")

				.then().log().all().assertThat().statusCode(200).extract().response();
		ReusableMethods.setSessionId(response.header("Authorization"));

	}

	@Test(priority = 1)
	public void fetchAllOrders() {
		given().log().all().header("Content-Type", "application/json")
				.header("Authorization", ReusableMethods.getSessionId()).queryParam("page", "1")
				.queryParam("limit", "15").queryParam("sort", "+id").queryParam("active", "Sales")
				.queryParam("keyword", "")

				.when().log().all().get(Resources.FETCH_ALL_ORDER)

				.then().log().all().assertThat().statusCode(200).extract().response().asString();
	}
}
