package com.smolinj.drools;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.definition.rule.Rule;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractKnowledgeSessionBuilder {
	private static Logger LOG = LoggerFactory.getLogger(AbstractKnowledgeSessionBuilder.class);
	protected Properties appProperties;
	private KnowledgeBase kbase;
	
	public AbstractKnowledgeSessionBuilder() {
		loadAppProperties();
	}

	public StatefulKnowledgeSession populateStatefulKnowledgeSession() {
		KnowledgeBase kbase = populateKnowledgeBase();
		return kbase.newStatefulKnowledgeSession();
	}
	
	public StatelessKnowledgeSession populateStatelessKnowledgeSession() {
		KnowledgeBase kbase = populateKnowledgeBase();
		return kbase.newStatelessKnowledgeSession();
	}
	
	abstract ResourceType getResourceType();
	abstract String getResourceLocationFolder();
	abstract FilenameFilter getResourceFilenameFilter();


	private KnowledgeBase populateKnowledgeBase() {
		kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(initKnowledgeBuilder().getKnowledgePackages());
		return kbase;
	}

	private KnowledgeBuilder initKnowledgeBuilder() {
		File[] rules = loadResources(getResourceLocationFolder(), getResourceFilenameFilter());
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		addResourcesToKnowledgeBuilder(kbuilder, rules, getResourceType());
		loadMoreResources(kbuilder);
		return kbuilder;
	}

	protected KnowledgeBuilder addResourcesToKnowledgeBuilder(KnowledgeBuilder kbuilder,File[] rules, ResourceType resourceType) {
		
		for (int i = 0; i < rules.length; i++) {
			Resource resource = ResourceFactory.newFileResource(rules[i]);
			kbuilder.add(resource, resourceType);
			runDebugActions(resource);
			validateKnowledgeBase(kbuilder);
		}
		System.out.println("Current rules in knowledge base:");
		for (KnowledgePackage kp : kbuilder.getKnowledgePackages()) {
			for (Rule r : kp.getRules()) {
				System.out.println(r.getName());
			}
		}
		return kbuilder;
	}

	/**
	 * Use if you have more than one type of resources in your knowledge base.
	 * @param kbuilder knowledge builder to add resources to
	 */
	protected void loadMoreResources(KnowledgeBuilder kbuilder) {
		//by default do nothing
	}
	protected void runDebugActions(Resource resource) {
		//by default do nothing
	}

	protected File[] loadResources(String resourceLocationFolder, FilenameFilter resourceFilenameFilter) {
		File rulesFolder = new File(resourceLocationFolder);
		File[] rules = rulesFolder.listFiles(resourceFilenameFilter);
		if (rules == null || rules.length == 0) {
			throw new IllegalStateException("No business rules discovered at ".concat(resourceLocationFolder));
		}
		return rules;
	}

	private void loadAppProperties() {
		appProperties = new Properties();
		try {
			appProperties.load(getClass().getResourceAsStream("/ExpertApp.properties"));
		} catch (IOException e) {
			LOG.error("unable to load application properties", e);
		}
	}

	private void validateKnowledgeBase(KnowledgeBuilder kbuilder) {
		if (kbuilder.hasErrors()) {
			LOG.error(kbuilder.getErrors().toString());
			System.out.println(kbuilder.getErrors().toString());
		}
	}

	public KnowledgeBase getKbase() {
		return kbase;
	}
}