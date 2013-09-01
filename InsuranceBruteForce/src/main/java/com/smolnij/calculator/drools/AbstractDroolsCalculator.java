package com.smolnij.calculator.drools;

import java.util.List;

import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smolnij.calculator.InsuranceCalculator;
import com.smolnij.domain.Client;
import com.smolnij.drools.listener.DroolsRulesApplianceListener;

public abstract class AbstractDroolsCalculator implements InsuranceCalculator {
	private Logger LOG = LoggerFactory.getLogger(getClass());
	
	protected DroolsRulesApplianceListener listener = null;
	protected StatelessKnowledgeSession ksession = null;
	
	abstract StatelessKnowledgeSession createKnowledgeSession();
	
	public AbstractDroolsCalculator() {
		ksession = createKnowledgeSession();
		listener = new DroolsRulesApplianceListener();
		LOG.info("Knowledge session populated");
	}

	@Override
	public void calcInsurancePrice(List<Client> clients) {
		//TODO Make validation rule and interrupt rule flow in case of client is invalid
		ksession.addEventListener(listener);
		ksession.execute(clients);
	}
	@Override
	public List<String> getFiredRules() {
		return listener.getFiredRules();
	}
}