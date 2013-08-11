package com.smolnij.drools.listener;

import java.util.LinkedList;
import java.util.List;


public class DroolsRulesApplianceListener extends AbstractAgendaEventListener {
	
	List<String> firedRules = new LinkedList<String>();

	@Override
	public void afterActivationFired(org.drools.event.rule.AfterActivationFiredEvent event) {
		firedRules.add(event.getActivation().getRule().getName());
	}
	public List<String> getFiredRules() {
		return firedRules;
	}
}