package examples.android.odata.io;

import examples.android.odata.io.DownloadAsyncTask.COM_ERROR;

public interface DownloadAsyncTaskIface {
	public void onTaskStart();
	public void onTaskFinished(String result, String... newParam);
	public void onErrorOccured(COM_ERROR error, String reason);
}
