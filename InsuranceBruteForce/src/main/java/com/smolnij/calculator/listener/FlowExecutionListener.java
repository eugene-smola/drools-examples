package com.smolnij.calculator.listener;

import java.util.List;

public interface FlowExecutionListener {
	void ruleApplied(String ruleName);
	List<String> getAppliedRules();
}