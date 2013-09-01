package com.smolnij.drools;

import static com.smolnij.util.PropertyKeys.BUSINESS_RULES_LOCATION_FOLDER;
import static com.smolnij.util.PropertyKeys.DSLR_LOCATION_FOLDER;
import static com.smolnij.util.PropertyKeys.DSL_LOCATION_FOLDER;

import java.io.File;
import java.io.FilenameFilter;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.ResourceType;

import com.smolnij.drools.utils.FilenameExtensionFilter;

public class DrlKnowledgeSessionBuilder extends AbstractKnowledgeSessionBuilder {

	@Override
	public ResourceType getResourceType() {
		return ResourceType.DRL;
	}

	@Override
	public String getResourceLocationFolder() {
		return appProperties.getProperty(BUSINESS_RULES_LOCATION_FOLDER);
	}

	@Override
	public FilenameFilter getResourceFilenameFilter() {
		return new FilenameExtensionFilter(".drl");
	}
	
	@Override
	protected void loadMoreResources(KnowledgeBuilder kbuilder) {
		addDsls(kbuilder);
		addDslrs(kbuilder);
	}

	private void addDsls(KnowledgeBuilder kbuilder) {
		File[] dsls = loadResources(appProperties.getProperty(DSL_LOCATION_FOLDER), new FilenameExtensionFilter(".dsl"));
		addResourcesToKnowledgeBuilder(kbuilder, dsls, ResourceType.DSL);
	}
	
	private void addDslrs(KnowledgeBuilder kbuilder) {
		File[] dslrs = loadResources(appProperties.getProperty(DSLR_LOCATION_FOLDER), new FilenameExtensionFilter(".dslr"));
		addResourcesToKnowledgeBuilder(kbuilder, dslrs, ResourceType.DSLR);
	}
}