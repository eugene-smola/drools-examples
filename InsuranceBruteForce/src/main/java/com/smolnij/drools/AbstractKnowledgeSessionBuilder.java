package com.smolnij.drools;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
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

import com.smolnij.drools.exception.NoRulesFoundException;

public abstract class AbstractKnowledgeSessionBuilder {
	private Logger LOG = LoggerFactory.getLogger(getClass());
	protected Properties appProperties;

	public StatelessKnowledgeSession populateStatelessKnowledgeSession() {
//TODO uncomment later. Commented out for better tests performance.
//		verifyRules(); 

		KnowledgeBase kbase = populateKnowledgeBase();
		return kbase.newStatelessKnowledgeSession();
	}
	
	abstract ResourceType getResourceType();
	abstract String getResourceLocationFolder();
	abstract FilenameFilter getResourceFilenameFilter();

	private void verifyRules() {
		Verifier verifier = checkForErrors();

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

	private Verifier checkForErrors() {
		VerifierBuilder vbuilder = VerifierBuilderFactory.newVerifierBuilder();
		Verifier verifier = vbuilder.newVerifier();

		File[] rules = loadRules();
		for (int i = 0; i < rules.length; i++) {
			verifier.addResourcesToVerify(ResourceFactory.newFileResource(rules[i]), getResourceType());
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

	private KnowledgeBase populateKnowledgeBase() {
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(initKnowledgeBuilder().getKnowledgePackages());
		return kbase;
	}

	private KnowledgeBuilder initKnowledgeBuilder() {
		File[] rules = loadRules();

		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		for (int i = 0; i < rules.length; i++) {
			Resource resource = ResourceFactory.newFileResource(rules[i]);
			kbuilder.add(resource, getResourceType());
			runDebugActions(resource);
			validateKnowledgeBase(kbuilder);
		}
		return kbuilder;
	}

	protected void runDebugActions(Resource resource) {
		//by default do nothing
	}

	private File[] loadRules() {
		loadAppProperties();
		File rulesFolder = new File(getResourceLocationFolder());
		File[] rules = rulesFolder.listFiles(getResourceFilenameFilter());
		if (rules == null || rules.length == 0) {
			throw new NoRulesFoundException("No business rules discovered at ".concat(getResourceLocationFolder()));
		}
		return rules;
	}

	private void loadAppProperties() {
		appProperties = new Properties();
		try {
			appProperties.load(getClass().getResourceAsStream("/insuranceApp.properties"));
		} catch (IOException e) {
			LOG.error("unable to load application properties", e);
		}
	}

	private void validateKnowledgeBase(KnowledgeBuilder kbuilder) {
		if (kbuilder.hasErrors()) {
			LOG.error(kbuilder.getErrors().toString());
		}
	}
}