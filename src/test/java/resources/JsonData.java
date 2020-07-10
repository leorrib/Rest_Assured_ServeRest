package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.AddItemToCart;
import pojo.ItemSpecs;
import pojo.Login;

public class JsonData {
	
	public Login loginPayload(String email, String password) {
		Login log = new Login();
		log.setEmail(email);
		log.setPassword(password);
		return log;
	}
	
	public AddItemToCart addItemPayload(String id_prod, int quantidade) {
		AddItemToCart add = new AddItemToCart();
		
		ItemSpecs is = new ItemSpecs();
		is.setIdProduto(id_prod);
		is.setQuantidade(quantidade);
		
		List<ItemSpecs> mylist = new ArrayList<ItemSpecs>();
		mylist.add(is);
		
		add.setProdutos(mylist);
		return add;
	}
}
