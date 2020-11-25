package copperwire.io.tnt.orders;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import tntRestAutomation.Resources;
import tntRestAutomation.ReusableMethods;

import static io.restassured.RestAssured.*;

public class LogInTest {

	@Test
	public void doLogIn() {

		RestAssured.baseURI = Resources.BASE_URI;

		Response response = given().log().all().header("Content-Type", "application/json")
				.body(ReusableMethods.getLogInCredentials()).when().log().all().post("/api/auth/login")

				.then().log().all().assertThat().statusCode(200).extract().response();// response().seasString();
		ReusableMethods.setSessionId(response.header("Authorization"));
	}

}
