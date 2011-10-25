package examples.android.odata;
import examples.android.odata.io.DownloadAsyncTask;
import examples.android.odata.io.DownloadAsyncTaskIface;
import examples.android.odata.io.JsonProcessorAsyncTaskInterface;
import examples.android.odata.io.DownloadAsyncTask.COM_ERROR;
import examples.android.odata.io.JsonProcessorAsyncTask.DATA_HANDLER_ERROR;
import examples.android.odata.io.JsonProcessorAsyncTask;
import examples.android.odata.model.AbstractObjectType;
import examples.android.odata.model.AbstractObjectType.DataType;
import examples.android.odata.model.PoiProductPriceList;
import examples.android.odata.model.ProductList;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SimpleOdataParsingActivity extends Activity {
	
	private static final String TAG = SimpleOdataParsingActivity.class.getSimpleName();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void getProductListButtonClicked(View v) {
    	new DownloadAsyncTask(new DownloadAsyncTaskIface() {
			
			@Override
			public void onTaskStart() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTaskFinished(String result, String... newParam) {
				//Log.d(TAG, "Result is: "+result);
				new JsonProcessorAsyncTask(new JsonProcessorAsyncTaskInterface() {
					
					@Override
					public void onTaskFinished(AbstractObjectType hamo) {
						// TODO Auto-generated method stub
						if(hamo instanceof ProductList){
							Log.d(TAG, "ProductList has: " + ((ProductList)hamo).product_list.size() + " products");
						}
					}
					
					@Override
					public void onErrorOccured(DATA_HANDLER_ERROR error) {
						// TODO Auto-generated method stub
						
					}
				}).execute(result, DataType.PRODUCT_LIST.toString());
			}
			
			@Override
			public void onErrorOccured(COM_ERROR error, String reason) {
				Log.d(TAG, "Error is: "+error.toString() + " reason: " + reason);
				
			}
		}).
		execute(DownloadAsyncTask.getProductListFormatedURL());
    }
    
    public void onSingleProductButtonClicked(View v) {
    	new DownloadAsyncTask(new DownloadAsyncTaskIface() {
			
			@Override
			public void onTaskStart() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTaskFinished(String result, String... newParam) {
				//Log.d(TAG, "Result is: "+result);
				new JsonProcessorAsyncTask(new JsonProcessorAsyncTaskInterface() {
					
					@Override
					public void onTaskFinished(AbstractObjectType hamo) {
						if(hamo instanceof PoiProductPriceList)
						Log.d(TAG, "PoiProductPriceList has: " + ((PoiProductPriceList)hamo).product_prices.size() + " options");
					}
					
					@Override
					public void onErrorOccured(DATA_HANDLER_ERROR error) {
						// TODO Auto-generated method stub
						
					}
				}).execute(result, DataType.PRODUCT_PRICES_FOR_PRODUCT_ID.toString());
			}
			
			@Override
			public void onErrorOccured(COM_ERROR error, String reason) {
				Log.d(TAG, "Error is: "+error.toString() + " reason: " + reason);
				
			}
		}).
		execute(DownloadAsyncTask.getFindProductPricesForAProductIdFormatedURL("3263"));
    }
}