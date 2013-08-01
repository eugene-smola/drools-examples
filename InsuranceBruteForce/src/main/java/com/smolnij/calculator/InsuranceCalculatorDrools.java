package com.smolnij.calculator;

import java.util.List;

import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smolnij.domain.Client;
import com.smolnij.drools.KnowledgeSessionBuilder;

public class InsuranceCalculatorDrools implements InsuranceCalculator {
	private Logger LOG = LoggerFactory.getLogger(getClass());
	StatelessKnowledgeSession ksession = new KnowledgeSessionBuilder().populateStatelessKnowledgeSession();

	public InsuranceCalculatorDrools() {
		ksession = new KnowledgeSessionBuilder().populateStatelessKnowledgeSession();
		LOG.info("Knowledge session populated");
	}
	//TODO Make example for list of clients
	//TODO Make validation rule
	@Override
	public void calcInsurancePrice(List<Client> clients) {
		ksession.execute(clients);
	}
}