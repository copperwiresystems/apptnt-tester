package copperwire.io.tnt;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import tntRestAutomation.Resources;
import tntRestAutomation.ReusableMethods;

import static io.restassured.RestAssured.*;

public class CopperWire extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		RestAssured.baseURI = Resources.BASE_URI;
		RestAssured.useRelaxedHTTPSValidation();
}

	@Test
	public void doLogIn() {
		RestAssured.baseURI = "http://tnt7.copperwiresystems.com";

		Response response = given().log().all().header("Content-Type", "application/json")
				.body(ReusableMethods.getLogInCredentials())
				.when().log().all().post("/api/auth/login")

				.then().log().all().assertThat().statusCode(200).extract().response();// response().seasString();
		ReusableMethods.setSessionId(response.header("Authorization"));
	}

}
