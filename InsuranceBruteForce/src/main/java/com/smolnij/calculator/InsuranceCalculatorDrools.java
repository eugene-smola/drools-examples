package com.smolnij.calculator;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smolnij.domain.Client;
import com.smolnij.exception.UnsupportedProductException;

public class InsuranceCalculatorDrools implements InsuranceCalculator {
	
	private Logger LOG = LoggerFactory.getLogger(getClass());

	@Override
	public void calcInsurancePrice(Client c) throws UnsupportedProductException {

		KnowledgeBuilder kbuilder = initKnowledgeBuilder();
		KnowledgeBase kbase = populateKnowledgeBase(kbuilder);
		StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();

		ksession.execute(c);
	}

	private KnowledgeBase populateKnowledgeBase(KnowledgeBuilder kbuilder) {
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase;
	}

	private KnowledgeBuilder initKnowledgeBuilder() {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		
		addRule(kbuilder, "com/smolnij/pricerules/BaseCost.drl");
		addRule(kbuilder, "com/smolnij/pricerules/SportCar.drl");
		addRule(kbuilder, "com/smolnij/pricerules/Bargains.drl");
		addRule(kbuilder, "com/smolnij/pricerules/Colors.drl");

		validateKnowledgeBase(kbuilder);
		return kbuilder;
	}

	private void addRule(KnowledgeBuilder kbuilder, String path) {
		kbuilder.add(ResourceFactory.newClassPathResource(path, getClass()),
				ResourceType.DRL);
		validateKnowledgeBase(kbuilder);
	}

	private void validateKnowledgeBase(KnowledgeBuilder kbuilder) {
		if ( kbuilder.hasErrors() ) {
			LOG.error( kbuilder.getErrors().toString() );
		}
	}
}