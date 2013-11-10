package com.smolinj.drools;

import static com.smolinj.drools.PropertyKeys.BUSINESS_RULES_LOCATION_FOLDER;

import java.io.FilenameFilter;

import org.drools.builder.ResourceType;

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
}