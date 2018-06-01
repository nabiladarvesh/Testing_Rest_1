package com.service;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class CountryService_Steps {
	GetCountry getCountry = new GetCountry();

	@Given("I setup country service call")
	public void givenISetupCountryService() {
		getCountry.setupURI();
	}

	@When("I request for countries")
	public void whenIRequestCountries() {
		getCountry.executeAPI();
	}

	@Then("I should receive $countryName in response")
	public void thenIShouldReceiveExpectedCountry() {
		getCountry.verifyCountryInResponse();
	}
}