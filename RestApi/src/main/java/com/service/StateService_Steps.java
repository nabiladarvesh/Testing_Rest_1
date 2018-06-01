package com.service;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class StateService_Steps {
	GetState getState = new GetState();

	@Given("I setup state service call")
	public void givenISetupStateService() {
		getState.setupURI();
	}

	@When("I request for state")
	public void whenIRequestStates() {
		getState.executeAPI();
	}

	@Then("I should receive $stateName in response")
	public void thenIShouldReceiveExpectedState() {
		getState.verifyStateInResponse();
	}
}