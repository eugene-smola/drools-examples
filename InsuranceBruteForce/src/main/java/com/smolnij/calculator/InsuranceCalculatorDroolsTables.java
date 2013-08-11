package com.smolnij.calculator;

import java.util.List;

import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smolnij.domain.Client;
import com.smolnij.drools.DecisionTableKnowledgeSessionBuilder;

public class InsuranceCalculatorDroolsTables implements InsuranceCalculator {
	
	private Logger LOG = LoggerFactory.getLogger(getClass());
	StatelessKnowledgeSession ksession = null;


	public InsuranceCalculatorDroolsTables() {
		ksession = new DecisionTableKnowledgeSessionBuilder().populateStatelessKnowledgeSession();
		
		LOG.info("Knowledge session populated");
	}
	//TODO Make validation rule and interrupt rule low in case of client is invalid
	@Override
	public void calcInsurancePrice(List<Client> clients) {
		ksession.execute(clients);
	}
}