package com.smolnij.drools;

import static com.smolnij.util.PropertyKeys.DECISION_TABLES_LOCATION_FOLDER;

import java.io.FilenameFilter;
import java.io.IOException;

import org.drools.builder.ResourceType;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.drools.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smolnij.drools.utils.FilenameExtensionFilter;

public class DecisionTableKnowledgeSessionBuilder extends AbstractKnowledgeSessionBuilder {
	private Logger LOG = LoggerFactory.getLogger(getClass());

	@Override
	ResourceType getResourceType() {
		return ResourceType.DTABLE;
	}

	@Override
	String getResourceLocationFolder() {
		return appProperties.getProperty(DECISION_TABLES_LOCATION_FOLDER);
	}

	@Override
	public FilenameFilter getResourceFilenameFilter() {
		return new FilenameExtensionFilter(".xls");
	}

	@Override
	protected void runDebugActions(Resource resource) {
		SpreadsheetCompiler compiler = new SpreadsheetCompiler();
		String drl = null;
		try {
			drl = compiler.compile(resource.getInputStream(), InputType.XLS);
		} catch (IOException e) {
			LOG.equals(e);
		}
		LOG.debug(drl);
	}
}