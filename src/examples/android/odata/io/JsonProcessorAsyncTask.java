package examples.android.odata.io;


import java.util.Calendar;

import org.json.JSONException;

import examples.android.odata.model.AbstractObjectType;
import examples.android.odata.model.AbstractObjectType.DataType;

import android.os.AsyncTask;
import android.util.Log;

public class JsonProcessorAsyncTask extends AsyncTask<String, Integer, AbstractObjectType> {
	
	public enum DATA_HANDLER_ERROR{
		PARSE_ERROR,
		NO_ERROR
	}
	
	private long comTime = 0; 
	
	private JsonProcessorAsyncTaskInterface iFace = null;
	
	private static final String TAG = JsonProcessorAsyncTask.class.getSimpleName();

	public JsonProcessorAsyncTask(JsonProcessorAsyncTaskInterface iface) {
		iFace = iface;
	}

	protected void onPreExecute() {
		comTime = Calendar.getInstance().getTimeInMillis();
	}

	protected AbstractObjectType doInBackground(String... urls) {
		
		AbstractObjectType hm = null;
		try{
			String result = urls[0];
			String json_parser = urls[1];
			switch (DataType.valueOf(json_parser)) {
			case PRODUCT_LIST:
				hm = JsonParser.parseProductsList(result);
				break;
			case PRODUCT_PRICES_FOR_PRODUCT_ID:
				hm = JsonParser.parseProductPricesList(result);
				break;
			default:
				break;
			}
			
			
		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
		return hm;
	}

	protected void onCancelled(){
		
	}
	
	protected void onProgressUpdate(Integer... progress) {
		
	}

	protected void onPostExecute(AbstractObjectType i) {
		if(i == null){
			iFace.onErrorOccured(DATA_HANDLER_ERROR.PARSE_ERROR);
		}else{
			iFace.onTaskFinished(i);
		}
		
	}

}
