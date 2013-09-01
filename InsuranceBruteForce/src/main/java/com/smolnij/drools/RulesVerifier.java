package com.smolnij.drools;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.verifier.Verifier;
import org.drools.verifier.VerifierError;
import org.drools.verifier.builder.VerifierBuilder;
import org.drools.verifier.builder.VerifierBuilderFactory;
import org.drools.verifier.data.VerifierReport;
import org.drools.verifier.report.components.MissingRange;
import org.drools.verifier.report.components.Severity;
import org.drools.verifier.report.components.VerifierMessageBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RulesVerifier {
	private static Logger LOG = LoggerFactory.getLogger(RulesVerifier.class);

	public static void verifyRules(File[] rules, ResourceType rt) {
		Verifier verifier = checkForErrors(rules, rt);

		verifier.fireAnalysis();
		VerifierReport result = verifier.getResult();
		Collection<VerifierMessageBase> noteMessages = result.getBySeverity(Severity.NOTE);
		for (VerifierMessageBase msg : noteMessages) {
			LOG.info("Note: " + msg.getMessage() + " type: " + msg.getMessageType() + " on: "
					+ msg.getFaulty());
		}
		Collection<VerifierMessageBase> errorMessages = result.getBySeverity(Severity.ERROR);
		for (VerifierMessageBase msg : errorMessages) {
			LOG.error("Note: " + msg.getMessage() + " type: " + msg.getMessageType() + " on: "
					+ msg.getFaulty());
		}
		Collection<VerifierMessageBase> warningMessages = result.getBySeverity(Severity.WARNING);
		for (VerifierMessageBase msg : warningMessages) {
			LOG.warn("Note: " + msg.getMessage() + " type: " + msg.getMessageType() + " on: "
					+ msg.getFaulty());
		}
		Collection<MissingRange> rangeCheckCauses = result.getRangeCheckCauses();
		for (MissingRange missingRange : rangeCheckCauses) {
			LOG.warn(missingRange.toString());
		}
		verifier.dispose();
	}
	
	private static Verifier checkForErrors(File[] rules, ResourceType rt) {
		VerifierBuilder vbuilder = VerifierBuilderFactory.newVerifierBuilder();
		Verifier verifier = vbuilder.newVerifier();

		for (int i = 0; i < rules.length; i++) {
			verifier.addResourcesToVerify(ResourceFactory.newFileResource(rules[i]), rt);
		}

		if (verifier.hasErrors()) {
			List<VerifierError> errors = verifier.getErrors();
			for (VerifierError ve : errors) {
				LOG.error(ve.getMessage());
			}
			throw new RuntimeException("rules with errors");
		}
		return verifier;
	}
}