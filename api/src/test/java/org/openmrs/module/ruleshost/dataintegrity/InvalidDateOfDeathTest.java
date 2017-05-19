package org.openmrs.module.ruleshost.dataintegrity;


import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.dataintegrity.rule.RuleResult;
import org.openmrs.test.BaseContextMockTest;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Context.class)

public class InvalidDateOfDeathTest extends BaseContextMockTest {

    protected static final String STANDARD_DATASET_XML = "org/openmrs/module/ruleshost/include/standardTestDataset.xml";
    protected static final String DATA_VIOLATIONS_XML = "org/openmrs/module/ruleshost/dataintegrity/include/dataViolations.xml";

    InvalidDateOfDeath invalidDateOfDeath;
    @Mock
    SessionFactory mockedSessionFactory;
    @Mock
    Criteria criteria;
    @Mock
    Criterion criterion;
    @Mock
    Session session;

    Logger logger = Logger.getLogger("logger");

    @Before
    public void initialize() throws Exception {

        criteria = Mockito.mock(Criteria.class);
        criterion = Mockito.mock(Criterion.class);

        invalidDateOfDeath = new InvalidDateOfDeath();
        mockedSessionFactory = Mockito.mock(SessionFactory.class);
        PowerMockito.mockStatic(Context.class);
        PowerMockito.when(Context.getRegisteredComponent("sessionFactory", SessionFactory.class))
                .thenReturn(mockedSessionFactory);
    }

    @Test
    public void EvaluateTestEmptyPatientList () {
        when(mockedSessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createCriteria(Patient.class, "patient")).thenReturn(criteria);

        when(criteria.add(Restrictions.isNotNull("deathDate"))).thenReturn(criteria);
        when(criteria.add(Restrictions.eq("voided", false))).thenReturn(criteria);
        when(criteria.add(Restrictions.gt("deathDate", new Date()))).thenReturn(criteria);

        List<Patient> patientList = new ArrayList<>();
        when(criteria.list()).thenReturn(patientList);

        List<RuleResult<Patient>> list = invalidDateOfDeath.evaluate();
        assertEquals(0, list.size());
    }
}
