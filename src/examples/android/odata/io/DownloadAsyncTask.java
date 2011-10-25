package examples.android.odata.io;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Formatter;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadAsyncTask extends AsyncTask<String, Integer, String> {

	public enum COM_ERROR {
		UnknownHostException, MalformedUrl, UnknownError, NoError
	}

	private static final String productList = "http://epriceservice.cloudapp.net/PriceServices.svc/Product";
	private static final String findProductPricesForAProductId = "http://epriceservice.cloudapp.net/PriceServices.svc/PoiProductPrice?$filter=ProductId%seq%s%s&$orderby=Price%sdesc";
	private static final String findProductByProductId = "http://epriceservice.cloudapp.net/PriceServices.svc/Product?$filter=ProductId%seq%s%s";
	
	
	private COM_ERROR error = COM_ERROR.NoError;
	private String errorReason;

	private String[] returnedParameters = null;

	private DownloadAsyncTaskIface iFace = null;

	private static final String TAG = DownloadAsyncTask.class.getSimpleName();

	public DownloadAsyncTask(DownloadAsyncTaskIface iface) {
		iFace = iface;
	}

	public DownloadAsyncTask(DownloadAsyncTaskIface iface,
			String... returnParams) {
		iFace = iface;
	}

	protected void onPreExecute() {
		iFace.onTaskStart();
	}

	protected String doInBackground(String... urls) {
		returnedParameters = urls;
		String str = null;
		try {
			str = convertInputStreamToString(downloadFromUrl(urls[0]));
			error = COM_ERROR.NoError;
		} catch (UnknownHostException uhe) {
			error = COM_ERROR.UnknownHostException;
			errorReason = "Communication Error";
		} catch (IOException ioe) {
			error = COM_ERROR.MalformedUrl;
			errorReason = "Communication Error";
		} catch (Exception e) {
			error = COM_ERROR.UnknownError;
			e.printStackTrace();
			errorReason = "Communication Error";
		}

		return str;
	}

	protected void onCancelled() {
	}

	protected void onProgressUpdate(Integer... progress) {
	}

	protected void onPostExecute(String result) {
		if (error.equals(COM_ERROR.NoError))
			iFace.onTaskFinished(result, returnedParameters);
		else
			iFace.onErrorOccured(error, errorReason);
	}

	public static InputStream downloadFromUrl(String url) throws IOException {
		Log.d(TAG, "downloading " + url);

		HttpClient httpClient = CustomHttpClient.getHttpClient();

		HttpGet request = new HttpGet(url);
		request.setHeader("Accept-Encoding", "gzip");
		request.setHeader("Accept", "application/json");
		HttpResponse response = httpClient.execute(request);

		InputStream instream = response.getEntity().getContent();

		if (response.containsHeader("Content-Encoding")
				&& response.getFirstHeader("Content-Encoding").getValue()
						.equalsIgnoreCase("gzip")) {
			instream = new GZIPInputStream(instream);
		}

		return instream;
	}

	public static String convertInputStreamToString(InputStream ginstream)
			throws IOException {
		StringBuffer theText = new StringBuffer();

		if (ginstream != null) {
			InputStreamReader reader = new InputStreamReader(ginstream);
			BufferedReader in = new BufferedReader(reader);
			String readed;
			while ((readed = in.readLine()) != null) {
				theText.append(readed + "\n");
			}
			ginstream.close();
		}

		return theText.toString();
	}

	public static final String getFindProductPricesForAProductIdFormatedURL(String productId) {
		Formatter formatter = new Formatter();
		formatter.format(findProductPricesForAProductId, "%20", "%20", productId, "%20");
		return formatter.toString();
	}
	
	public static final String getFindProductByProductIdFormatedURL(String productId) {
		Formatter formatter = new Formatter();
		formatter.format(findProductByProductId, "%20", "%20", productId);
		return formatter.toString();
	}
	
	public static final String getProductListFormatedURL() {
		return productList;
	}

}
