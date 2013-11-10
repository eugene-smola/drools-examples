package com.smolnij.main;

import java.io.IOException;
import java.util.Collection;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.compiler.PackageBuilderConfiguration;
import org.drools.core.util.DroolsStreamUtils;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;

public class KnowledgePackagesLoader {
	 public Collection<KnowledgePackage> loadKnowledgePackages(String... classPathResources) {
	        return loadKnowledgePackages(null, classPathResources);
	    }
	 
	 public Collection<KnowledgePackage> loadKnowledgePackages( KnowledgeBuilderConfiguration kbuilderConf, String... classPathResources) {
	        return loadKnowledgePackages(kbuilderConf, true, classPathResources);
	    }
	 
	 public Collection<KnowledgePackage> loadKnowledgePackages( KnowledgeBuilderConfiguration kbuilderConf, boolean serialize, String... classPathResources) {
	        if (kbuilderConf == null) {
	            kbuilderConf = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();
	        }

	        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(kbuilderConf);
	        for (String classPathResource : classPathResources) {
	            kbuilder.add(ResourceFactory.newClassPathResource(classPathResource, getClass()), ResourceType.DRL);
	        }

	        if (kbuilder.hasErrors()) {
	            System.out.println(kbuilder.getErrors().toString());
	        }

	        Collection<KnowledgePackage> knowledgePackages = null;
	        if ( serialize ) {
	            try {
	                knowledgePackages = serializeObject(kbuilder.getKnowledgePackages(), ((PackageBuilderConfiguration) kbuilderConf).getClassLoader());
	            } catch (Exception e) {
	                throw new RuntimeException(e);
	            }
	        } else {
	            knowledgePackages = kbuilder.getKnowledgePackages();
	        }
	        return knowledgePackages;
	    }
	 
	 public static <T> T serializeObject(T obj) throws IOException, ClassNotFoundException {
	        return serializeObject(obj, null);
	    }

	    public static <T> T serializeObject(T obj, ClassLoader classLoader) throws IOException, ClassNotFoundException {
	        return (T)DroolsStreamUtils.streamIn(DroolsStreamUtils.streamOut(obj), classLoader);
	    }
}