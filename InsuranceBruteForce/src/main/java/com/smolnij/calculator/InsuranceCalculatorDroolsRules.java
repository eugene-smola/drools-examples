package com.smolnij.calculator;

import java.util.List;

import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smolnij.domain.Client;
import com.smolnij.drools.DrlKnowledgeSessionBuilder;

public class InsuranceCalculatorDroolsRules implements InsuranceCalculator {
	private Logger LOG = LoggerFactory.getLogger(getClass());
	StatelessKnowledgeSession ksession = null;

	public InsuranceCalculatorDroolsRules() {
		ksession = new DrlKnowledgeSessionBuilder().populateStatelessKnowledgeSession();
		
		LOG.info("Knowledge session populated");
	}
	//TODO Make validation rule and interrupt rule low in case of client is invalidS
	@Override
	public void calcInsurancePrice(List<Client> clients) {
		ksession.execute(clients);
	}
}