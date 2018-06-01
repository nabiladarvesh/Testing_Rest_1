package com.service;

import java.util.Properties;
import org.json.JSONException;

/*GetState class ::
	Step1: Setup state service call
	Step2: Get the response from service
	Step3: Verify response */
public class GetState extends BaseTest {
	Properties props = new Properties();

	public void setupURI() {
		props = readConfig();
		super.setupURI(props.getProperty("state.service"));
	}

	public void executeAPI() throws JSONException {
		super.executeAPI(props.getProperty("state.service"));
	}

	public void verifyStateInResponse() {
		super.verifyResponse(props.getProperty("state.response"));
	}
}