package cenariosTeste;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import resources.Utils;

public class CarrinhosEndpointTests extends Utils {
	RequestSpecification request_car;
	RequestSpecification request_del_car;
	ValidatableResponse response_del_car;
	RequestSpecification request_car_auth;
	ValidatableResponse response;
	ValidatableResponse response_final;

	@Test
	public void visualizarCarrinhos() throws IOException {
		request_car = given().spec(requestCar());

		String response = request_car.when().get("/carrinhos")
				.then()
				.assertThat().statusCode(200)
				.extract().asString();

		JsonPath json = new JsonPath(response);
		int precoTotal = json.getInt("carrinhos[0].precoTotal");
		int somaUnitarios = 0;
		for (int i = 0; i < json.getInt("carrinhos[0].produtos.size()"); i++) {
			int quantidade = json.getInt("carrinhos[0].produtos[" + i + "].quantidade");
			int precoUnitario = json.getInt("carrinhos[0].produtos[" + i + "].precoUnitario");
			somaUnitarios += quantidade * precoUnitario;
		}
		Assert.assertEquals(precoTotal, somaUnitarios);
	}

	@Test
	public void criarCarrinho() throws IOException {

		request_login = given().spec(requestLogin("validEmail","validPassword"));	
		String responseLogin = request_login.when().post("/login").then().assertThat().statusCode(200).extract().asString();
		JsonPath json = new JsonPath(responseLogin);
		String authToken = json.getString("authorization");

		request_car_auth = given().spec(requestCarAuth(authToken, "idProduto"));		
		response = request_car_auth.when().post("/carrinhos")
				.then()
				.assertThat()
				.statusCode(201)
				.body("message", equalTo("Cadastro realizado com sucesso"));


		request_del_car = given().spec(deleteCar(authToken));	
		response_del_car = request_del_car.when().delete("/carrinhos/cancelar-compra").then().assertThat().statusCode(200);

	}

	@Test
	public void criarSegundoCarrinho() throws IOException {

		request_login = given().spec(requestLogin("validEmail","validPassword"));	
		String responseLogin = request_login.when().post("/login").then().extract().asString();
		JsonPath json = new JsonPath(responseLogin);
		String authToken = json.getString("authorization");

		request_car_auth = given().spec(requestCarAuth(authToken, "idProduto"));	
		response = request_car_auth.when().post("/carrinhos").then().assertThat().statusCode(201);
		response_final = request_car_auth.when().post("/carrinhos")
				.then()
				.assertThat()
				.statusCode(400)
				.body("message", equalTo("Não é permitido ter mais de 1 carrinho"));


		request_del_car = given().spec(deleteCar(authToken));	
		response_del_car = request_del_car.when().delete("/carrinhos/cancelar-compra").then().assertThat().statusCode(200);
	}

	@Test
	public void criarCarrinhoComTokenInvalido() throws IOException {

		request_car_auth = given().spec(requestCarAuth(input("empty"), "idProduto"));		
		response = request_car_auth.when().post("/carrinhos")
				.then()
				.assertThat()
				.statusCode(401)
				.body("message", equalTo("Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"));

	}

}
