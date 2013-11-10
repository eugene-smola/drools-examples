package com.smolnij.main;

import java.io.IOException;
import java.util.Collection;

import org.drools.definition.KnowledgePackage;
import org.drools.runtime.StatefulKnowledgeSession;

import com.smolinj.drools.DrlKnowledgeSessionBuilder;
import com.smolinj.drools.DroolsRulesApplianceListener;
import com.smolinj.drools.zoo.domain.SomeAnimal;

public class Main {

	public static void main(String[] args) throws IOException {
		SomeAnimal a = new SomeAnimal();
		a.addSign("wings");
		a.setSpecie("ostrich");
		a.setName("ostrich one");

		DrlKnowledgeSessionBuilder sessionBuilder = new DrlKnowledgeSessionBuilder();
		StatefulKnowledgeSession session = sessionBuilder
				.populateStatefulKnowledgeSession();
		
		DroolsRulesApplianceListener listener = new DroolsRulesApplianceListener();
		session.addEventListener(listener);
		
		System.out.println("Rules executing starting: ");
		System.out.println("---------------------------- ");
		session.insert(a);
		session.fireAllRules();
		
		printCurrentFacts(session);
		
		System.out.println("The inferences are made");
		System.out.println("Now system suddenly gets rule or make inference that ostrich is not flying");
		System.in.read();
		// Now system suddenly gets rule or make inference that ostrich is not flying
		Collection<KnowledgePackage> kps = 
				new KnowledgePackagesLoader().loadKnowledgePackages("newRules/ostrich.drl");
		sessionBuilder.getKbase().addKnowledgePackages(kps);
		
		session.fireAllRules();
		printCurrentFacts(session);
		//System reverted isFlying fact and the FlyingCellOrder fact made on the isFlying basis
		
//		printAppliedRuleNames(listener);
		
		System.out.println("Adding one more ostrich");
		System.in.read();
		
		SomeAnimal a2 = new SomeAnimal();
		a2.addSign("wings");
		a2.setSpecie("ostrich");
		a2.setName("ostrich two");
		
		session.insert(a2);
		session.fireAllRules();
		
		session.dispose();
	}

	private static void printAppliedRuleNames(DroolsRulesApplianceListener listener) {
		System.out.println("Applied rules: ");
		for (String s : listener.getFiredRules()) {
			System.out.println(s);
		}
	}

	private static void printCurrentFacts(StatefulKnowledgeSession session) {
		System.out.println("Current Facts in memory");
		Collection<Object> c = session.getObjects();
		for (Object o : c) {
			System.out.println(o.getClass());
		}
	}
}