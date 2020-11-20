package tntRestAutomation;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class LogIn {

	@Test
	public void doLogIn() {

		RestAssured.baseURI = "http://tnt7.copperwiresystems.com";

		Response response = given().log().all().header("Content-Type", "application/json")
				.body(ReusableMethods.getLogInCredentials()).when().log().all().post("/api/auth/login")

				.then().log().all().assertThat().statusCode(200).extract().response();// response().seasString();
		ReusableMethods.setSessionId(response.header("Authorization"));
	}

}
