package com.smolnij.drools;

import static com.smolnij.util.PropertyKeys.BUSINESS_RULES_LOCATION_FOLDER;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smolnij.drools.exception.NoRulesFoundException;
import com.smolnij.drools.utils.DrlFilenameFilter;

public class KnowledgeSessionBuilder {
	private Logger LOG = LoggerFactory.getLogger(getClass());
	private Properties appProperties;
	
	public StatelessKnowledgeSession populateStatelessKnowledgeSession() {
		KnowledgeBase kbase = populateKnowledgeBase();
		return kbase.newStatelessKnowledgeSession();
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
			kbuilder.add(ResourceFactory.newFileResource(rules[i]), ResourceType.DRL);
			validateKnowledgeBase(kbuilder);
		}
		return kbuilder;
	}

	private File[] loadRules() {
		loadAppProperties();
		String rulesPath = appProperties.getProperty(BUSINESS_RULES_LOCATION_FOLDER);

		File rulesFolder = new File(rulesPath);
		File[] rules = rulesFolder.listFiles(new DrlFilenameFilter());
		if (rules == null || rules.length == 0) {
			throw new NoRulesFoundException("No business rules discovered at ".concat(rulesPath));
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