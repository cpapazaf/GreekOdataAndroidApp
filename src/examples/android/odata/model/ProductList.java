package examples.android.odata.model;

import java.util.ArrayList;
import java.util.List;

public class ProductList implements AbstractObjectType{
	public List<Product> product_list;
	
	public ProductList(){
		product_list = new ArrayList<Product>();
	}
}
