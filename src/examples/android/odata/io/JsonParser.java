package examples.android.odata.io;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import examples.android.odata.model.PoiProductPrice;
import examples.android.odata.model.PoiProductPriceList;
import examples.android.odata.model.Product;
import examples.android.odata.model.ProductList;

import android.util.Log;

public class JsonParser {

	private static final String TAG = JsonParser.class.getSimpleName();
	
	public static final int MAX_JSON_OBJECTS = 1000;

	
	public static ProductList parseProductsList(String result) throws JSONException{
		JSONObject root = new JSONObject(result);
		JSONObject jo = null;
		JSONArray dataArray = null;
		ProductList pl = new ProductList();

		if (root.has("d"))
			dataArray = root.getJSONArray("d");
		else
			throw new JSONException("Malformed Response");
		
		if (dataArray != null) {

			//Log.i(TAG, "processing Hag JSON Data Array");
			int top = Math.min(MAX_JSON_OBJECTS, dataArray.length());

			for (int i = 0; i < top; i++) {
				jo = dataArray.getJSONObject(i);
				pl.product_list.add(parseSingleProductFromList(jo));
			}
		}
		
		return pl;
	}
	
	private static Product parseSingleProductFromList(JSONObject jo) throws JSONException{
		Product p = new Product();
		if(jo.has("ProductId") && !jo.isNull("ProductId")){
			Log.d(TAG, "ProductId: "+jo.getString("ProductId"));
			p.ProductId = jo.getString("ProductId");
		}
		
		if(jo.has("Name") && !jo.isNull("Name")){
			Log.d(TAG, "Name: "+jo.getString("Name"));
			p.Name = jo.getString("Name");
		}
		
		if(jo.has("ProductCompanyId") && !jo.isNull("ProductCompanyId")){
			Log.d(TAG, "ProductCompanyId: "+jo.getString("ProductCompanyId"));
			p.ProductCompanyId = jo.getString("ProductCompanyId");
		}
		
		if(jo.has("CategoryId") && !jo.isNull("CategoryId")){
			Log.d(TAG, "CategoryId: "+jo.getString("CategoryId"));
			p.CategoryId = jo.getString("CategoryId");
		}
		
		if(jo.has("Source") && !jo.isNull("Source")){
			Log.d(TAG, "Source: "+jo.getString("Source"));
			p.Source = jo.getString("Source");
		}

		if(jo.has("SourceId") && !jo.isNull("SourceId")){
			Log.d(TAG, "SourceId: "+jo.getString("SourceId"));
			p.SourceId = jo.getString("SourceId");
		}
		
		Log.d(TAG, "------------------------------");
		return p;
	}
	
	public static PoiProductPriceList parseProductPricesList(String result) throws JSONException{
		JSONObject root = new JSONObject(result);
		JSONObject jo = null;
		JSONArray dataArray = null;
		
		PoiProductPriceList pppl = new PoiProductPriceList();

		if (root.has("d"))
			dataArray = root.getJSONArray("d");
		else
			throw new JSONException("Malformed Response");
		
		if (dataArray != null) {

			//Log.i(TAG, "processing Hag JSON Data Array");
			int top = Math.min(MAX_JSON_OBJECTS, dataArray.length());

			for (int i = 0; i < top; i++) {
				jo = dataArray.getJSONObject(i);
				pppl.product_prices.add(parseSinglePoiProductPrice(jo));
			}
		}
		return pppl;
	}
	
	private static PoiProductPrice parseSinglePoiProductPrice(JSONObject jo) throws JSONException{
		
		PoiProductPrice ppp = new PoiProductPrice();
		if(jo.has("PoiProductPriceId") && !jo.isNull("PoiProductPriceId")){
			Log.d(TAG, "PoiProductPriceId: "+jo.getString("PoiProductPriceId"));
			ppp.PoiProductPriceId = jo.getString("PoiProductPriceId");
		}
		
		if(jo.has("PoiId") && !jo.isNull("PoiId")){
			Log.d(TAG, "PoiId: "+jo.getString("PoiId"));
			ppp.PoiId = jo.getString("PoiId");
		}
		
		if(jo.has("ProductId") && !jo.isNull("ProductId")){
			Log.d(TAG, "ProductId: "+jo.getString("ProductId"));
			ppp.ProductId = jo.getString("ProductId");
		}
		
		if(jo.has("Price") && !jo.isNull("Price")){
			Log.d(TAG, "Price: "+jo.getString("Price"));
			ppp.Price = jo.getString("Price");
		}
		Log.d(TAG, "------------------------------");
		return ppp;
	}
}