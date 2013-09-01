package com.smolnij.drools;

import static com.smolnij.util.PropertyKeys.RULES_PACKAGE_LOCATION_FOLDER;

import java.io.FilenameFilter;

import org.drools.builder.ResourceType;

import com.smolnij.drools.utils.FilenameExtensionFilter;

public class PkgKnowledgeSessionBuilder extends AbstractKnowledgeSessionBuilder{

	@Override
	ResourceType getResourceType() {
		return ResourceType.PKG;
	}
	@Override
	String getResourceLocationFolder() {
		return appProperties.getProperty(RULES_PACKAGE_LOCATION_FOLDER);
	}
	@Override
	FilenameFilter getResourceFilenameFilter() {
		return new FilenameExtensionFilter(".pkg");
	}
}