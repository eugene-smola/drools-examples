package com.smolnij.drools;

import static com.smolnij.util.PropertyKeys.BUSINESS_RULES_LOCATION_FOLDER;

import java.io.FilenameFilter;

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
}