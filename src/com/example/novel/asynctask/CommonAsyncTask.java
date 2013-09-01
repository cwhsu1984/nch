package com.example.novel.asynctask;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;

abstract class CommonAsyncTask extends AsyncTask<Void, Void, Object> {

	final Activity activity; // Activity execute this task.
	String request; // Request to be performed. This should be the url of a specific api.
	String responseBody; // Response of the request.

	public CommonAsyncTask(Activity activity, String r) {
		this.activity = activity;
		request = r;
	}

	@Override
	protected final Object doInBackground(Void... arg0) {
		Object object = null;
		try {

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost post = new HttpPost(request);

			System.out.println("executing request " + post.getURI());

			// Create a response handler
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpclient.execute(post, responseHandler);
			//System.out.println(responseBody);

			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();

			// Perform the background method for each subclass
			object = doInBackGround();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		onPostExecuteMethod(result);
	}

	abstract Object doInBackGround();
	abstract void onPostExecuteMethod(Object result);
}
