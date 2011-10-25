package examples.android.odata.io;

import examples.android.odata.io.JsonProcessorAsyncTask.DATA_HANDLER_ERROR;
import examples.android.odata.model.AbstractObjectType;

public interface JsonProcessorAsyncTaskInterface {
	public void onTaskFinished(AbstractObjectType hamo);
	public void onErrorOccured(DATA_HANDLER_ERROR error);
}
