package org.openmrs.module.ruleshost.dataintegrity;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.dataintegrity.rule.RuleDefinition;
import org.openmrs.module.dataintegrity.rule.RuleResult;
import org.openmrs.module.dataintegrity.DataIntegrityRule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvalidDateOfDeath implements RuleDefinition<Patient> {
	
	@Override
	public List<RuleResult<Patient>> evaluate() {
		SessionFactory sessionFactory = Context.getRegisteredComponent("sessionFactory", SessionFactory.class);
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Patient.class, "patient")
		        .add(Restrictions.isNotNull("deathDate")).add(Restrictions.eq("voided", false))
		        .add(Restrictions.gt("deathDate", new Date()));
		
		List<Patient> patientList = criteria.list();
		
		return patientToRuleResultTransformer(patientList);
	}
	
	private List<RuleResult<Patient>> patientToRuleResultTransformer(List<Patient> patients){

        List<RuleResult<Patient>> ruleResults = new ArrayList<>();
        for (Patient patient : patients) {
            RuleResult<Patient> ruleResult = new RuleResult<>();
            ruleResult.setActionUrl("");
            ruleResult.setNotes("Patient with invalid date");
            ruleResult.setEntity(patient);

            ruleResults.add(ruleResult);
        }

        return ruleResults;
    }
	
	public DataIntegrityRule getRule() {
		return null;
	}
}
