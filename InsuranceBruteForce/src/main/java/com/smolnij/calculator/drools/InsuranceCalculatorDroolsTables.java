package com.smolnij.calculator.drools;

import org.drools.runtime.StatelessKnowledgeSession;

import com.smolnij.drools.DecisionTableKnowledgeSessionBuilder;

public class InsuranceCalculatorDroolsTables extends AbstractDroolsCalculator {
	

	public StatelessKnowledgeSession createKnowledgeSession() {
		return new DecisionTableKnowledgeSessionBuilder().populateStatelessKnowledgeSession();
	}
}