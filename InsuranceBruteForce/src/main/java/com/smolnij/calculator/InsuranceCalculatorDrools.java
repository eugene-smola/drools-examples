package com.smolnij.calculator;

import java.math.BigDecimal;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;

import com.smolnij.domain.Client;
import com.smolnij.exception.UnsupportedProductException;

public class InsuranceCalculatorDrools implements InsuranceCalculator {

	@Override
	public BigDecimal calcInsurancePrice(Client c) throws UnsupportedProductException {

		KnowledgeBuilder kbuilder = initKnowledgeBuilder();
		KnowledgeBase kbase = populateKnowledgeBase(kbuilder);
		StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();

		ksession.execute(c);;
		
		return c.getInsurancePrice();
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

		if (kbuilder.hasErrors()) {
			System.err.println(kbuilder.getErrors().toString());
		}
		return kbuilder;
	}

	private void addRule(KnowledgeBuilder kbuilder, String path) {
		kbuilder.add(ResourceFactory.newClassPathResource(path, getClass()),
				ResourceType.DRL);
	}
}