package cz.gargoyle.beerstore;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

class ElasticSearchHttpClient {
	HttpClient httpClient;
	String serverUri;

	ElasticSearchHttpResult result;

	private static String QUERY_METHOD = "/_search?search_type=query_then_fetch";
	
	
	public HttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public String getServerUri() {
		return serverUri;
	}

	public void setServerUri(String serverUri) {
		this.serverUri = serverUri;
	}

	public ElasticSearchHttpResult query(String index, String type, String query) {
		try {
			HttpPost httpPost = new HttpPost(serverUri + "/" + index + "/" + type + QUERY_METHOD);
			httpPost.setHeader("Content-Type", "application/json");
			StringEntity strEntity = new StringEntity(query);
			httpPost.setEntity(strEntity );
			result = new ElasticSearchHttpResult(httpClient.execute(httpPost));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ElasticSearchHttpResult update(String index, String type, String id, int retryOnFailureCount, String updateDoc) {
		try {
			HttpPost httpPost = new HttpPost(serverUri + "/" + index + "/" + type + 
					"/" + id + "/_update?retry_on_conflict=" + retryOnFailureCount);
			httpPost.setHeader("Content-Type", "application/json");
			StringEntity strEntity = new StringEntity(updateDoc);
			httpPost.setEntity(strEntity );
			result = new ElasticSearchHttpResult(httpClient.execute(httpPost));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
