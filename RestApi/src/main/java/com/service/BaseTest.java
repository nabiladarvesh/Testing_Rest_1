package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("deprecation")
public class BaseTest {
	HttpGet getRequest;
	HttpResponse response;
	DefaultHttpClient httpClient;
	String inline;
	List<String> list;
	Properties props = new Properties();

	public Properties readConfig() {
		File configFile = new File("src/test/resources/config.properties");

		try {
			FileReader reader = new FileReader(configFile);
			props.load(reader);
			reader.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return props;
	}

	public void setupURI(String uri) {

		httpClient = new DefaultHttpClient();
		getRequest = new HttpGet(uri);

		getRequest.addHeader("accept", "application/json");
	}

	public void executeAPI(String uri) throws JSONException {
		try {
			inline = "";

			// Execute the GET request
			response = httpClient.execute(getRequest);

			// Print the status code of the response
			int statusCode = response.getStatusLine().getStatusCode();

			assertEquals("Request is fulfilled", response.getStatusLine().getStatusCode(), 200);

			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			if (statusCode != 200)
				throw new RuntimeException("HttpResponseCode: " + statusCode);
			else {
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
			}

		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	public void verifyResponse(String country) {

		list = new ArrayList<String>();

		JSONObject jso = new JSONObject(inline);
		// System.out.println(jso.getJSONObject("RestResponse").get("result"));
		JSONArray jsonArray = (JSONArray) jso.getJSONObject("RestResponse").get("result");
		// System.out.println("JSONy Length :" + jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jso2 = jsonArray.getJSONObject(i);
			list.add(jso2.get("name").toString());
		}
		assertTrue(list.contains(country));
	}
}