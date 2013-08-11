package com.smolnij.calculator.drools;

import org.drools.runtime.StatelessKnowledgeSession;

import com.smolnij.drools.DrlKnowledgeSessionBuilder;

public class InsuranceCalculatorDroolsRules extends AbstractDroolsCalculator {
	//TODO Make validation rule and interrupt rule low in case of client is invalidS
	public StatelessKnowledgeSession createKnowledgeSession() {
		return new DrlKnowledgeSessionBuilder().populateStatelessKnowledgeSession();
	}
}