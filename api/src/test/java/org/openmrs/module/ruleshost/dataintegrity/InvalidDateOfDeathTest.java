package org.openmrs.module.ruleshost.dataintegrity;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.dataintegrity.rule.RuleResult;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class InvalidDateOfDeathTest extends BaseModuleContextSensitiveTest {
	
	private static final String STANDARD_DATASET_XML = "org/openmrs/module/ruleshost/include/standardTestDataset.xml";
	
	@Mock
	InvalidDateOfDeath invalidDateOfDeath;
	
	@Mock
	SessionFactory mockedSessionFactory;
	
	Session session;
	
	Criteria criteria;
	
	Logger logger = Logger.getLogger("logger");
	
	@Before
	public void initialize() throws Exception {
		executeDataSet(STANDARD_DATASET_XML);
		session = Context.getRegisteredComponent("sessionFactory", SessionFactory.class).getCurrentSession();
		mockedSessionFactory = Mockito.mock(SessionFactory.class);
		criteria = session.createCriteria(Patient.class, "patient");
		invalidDateOfDeath = new InvalidDateOfDeath();
	}
	
	@Test
	public void EvaluateTestNonEmptyPatientList() {
		invalidDateOfDeath.setSessionFactory(mockedSessionFactory);
		when(mockedSessionFactory.getCurrentSession()).thenReturn(session);
		
		List<Patient> patients = Context.getPatientService().getAllPatients();
		for (Patient patient : patients)
			logger.info(patient.getPerson().getDeathDate() + "");
		
		List<RuleResult<Patient>> list = invalidDateOfDeath.evaluate();
		assertEquals(2, list.size());
	}
}
