package com.smolnij.calculator.listener;

import java.util.LinkedList;
import java.util.List;

public class RulesApplianceListener implements FlowExecutionListener {
	
	List<String> appliedRules = new LinkedList<String>();

	@Override
	public void ruleApplied(String ruleName) {
		appliedRules.add(ruleName);
	}

	@Override
	public List<String> getAppliedRules() {
		return appliedRules;
	}
}