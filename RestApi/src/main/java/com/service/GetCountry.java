package com.service;

import java.util.Properties;
import org.json.JSONException;

/*
GetCountry class ::
	Step1: Setup country service call
	Step2: Get the response from service
	Step3: Verify response */
public class GetCountry extends BaseTest {
	Properties props = new Properties();

	public void setupURI() {
		props = readConfig();
		super.setupURI(props.getProperty("country.service"));
	}

	public void executeAPI() throws JSONException {
		super.executeAPI(props.getProperty("country.service"));
	}

	public void verifyCountryInResponse() {
		super.verifyResponse(props.getProperty("country.response"));
	}
}