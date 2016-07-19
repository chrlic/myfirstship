package cz.gargoyle.beerstore;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;

class ElasticSearchHttpResult  {
	HttpResponse response;
	String body;
	String errorMessage;
	String errorBody;
	
	public ElasticSearchHttpResult(HttpResponse httpResponse) {
		try {
		response = httpResponse;
		if (response.getStatusLine().getStatusCode() != 200) {
			BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
		
			String result = "";
			String line = "";
			while ((line = rd.readLine()) != null) {
				result += line + "\n";
			}
			errorBody = result.toString();
		} else {
			ResponseHandler<String> handler = new BasicResponseHandler();
			body = handler.handleResponse(response);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getResponseBody() {
		return body;
	}
	
	public int getResponseCode() {
		return response.getStatusLine().getStatusCode();
	}
	
	public String getResponseMessage() {
		return response.getStatusLine().getReasonPhrase();
	}
	
	public String getResponseErrorBody() {
		return errorBody;
	}
}
