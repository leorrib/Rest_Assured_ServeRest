package cenariosTeste;

import static io.restassured.RestAssured.given;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import resources.Utils;

public class ProdutosEndpointTests extends Utils {

	RequestSpecification request_info;
	ValidatableResponse response;

	@Test
	public void produtosInfo() throws IOException {

		request_info = given().spec(visualizarItem("nomeAtributo1", "valorAtributo1", "nomeAtributo2", "valorAtributo2"));

		String response = request_info.when().get("/produtos")
				.then()
				.assertThat()
				.statusCode(200).extract().asString();

		JsonPath js = new JsonPath(response);
		String resposta_nome = js.getString("produtos[0].nome");
		String resposta_descricao = js.getString("produtos[0].descricao");

		Assert.assertEquals(resposta_nome, input("valorAtributo1"));
		Assert.assertEquals(resposta_descricao, input("valorAtributo2"));

	}
}
