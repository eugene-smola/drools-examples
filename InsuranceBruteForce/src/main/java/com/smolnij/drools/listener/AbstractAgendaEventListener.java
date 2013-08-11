package com.smolnij.drools.listener;

import org.drools.event.rule.ActivationCancelledEvent;
import org.drools.event.rule.ActivationCreatedEvent;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.AgendaEventListener;
import org.drools.event.rule.AgendaGroupPoppedEvent;
import org.drools.event.rule.AgendaGroupPushedEvent;
import org.drools.event.rule.BeforeActivationFiredEvent;
import org.drools.event.rule.RuleFlowGroupActivatedEvent;
import org.drools.event.rule.RuleFlowGroupDeactivatedEvent;

public class AbstractAgendaEventListener implements AgendaEventListener {

	@Override
	public void activationCreated(ActivationCreatedEvent event) {
	}

	@Override
	public void activationCancelled(ActivationCancelledEvent event) {
	}

	@Override
	public void beforeActivationFired(BeforeActivationFiredEvent event) {
	}

	@Override
	public void afterActivationFired(AfterActivationFiredEvent event) {
	}

	@Override
	public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
	}

	@Override
	public void agendaGroupPushed(AgendaGroupPushedEvent event) {
	}

	@Override
	public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
	}

	@Override
	public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
	}

	@Override
	public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
	}

	@Override
	public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
	}
}