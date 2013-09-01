package com.smolnij.calculator.drools;

import org.drools.runtime.StatelessKnowledgeSession;

import com.smolnij.drools.PkgKnowledgeSessionBuilder;

public class InsuranceCalculatorDroolsPkg extends AbstractDroolsCalculator {
	//TODO Make validation rule and interrupt rule flow in case of client is invalidS
	public StatelessKnowledgeSession createKnowledgeSession() {
		return new PkgKnowledgeSessionBuilder().populateStatelessKnowledgeSession();
	}
}