package examples.android.odata.model;

import java.util.ArrayList;
import java.util.List;

public class PoiProductPriceList implements AbstractObjectType{

	public List<PoiProductPrice> product_prices;
	
	public PoiProductPriceList(){
		product_prices = new ArrayList<PoiProductPrice>();
	}
}
