package org.openmrs.module.ruleshost.dataintegrity;


import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.dataintegrity.rule.RuleResult;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Context.class)

public class InvalidDateOfDeathTest extends BaseModuleContextSensitiveTest {

    protected static final String STANDARD_DATASET_XML = "org/openmrs/module/ruleshost/include/standardTestDataset.xml";
    protected static final String DATA_VIOLATIONS_XML = "org/openmrs/module/ruleshost/dataintegrity/include/dataViolations.xml";

    InvalidDateOfDeath invalidDateOfDeath;
    @Mock
    SessionFactory mockedSessionFactory;
    @Mock
    Criteria criteria;
    @Mock
    Criterion criterion;

    @Before
    public void initialize() throws Exception {
        Logger logger = Logger.getLogger("logger");
        /*executeDataSet(STANDARD_DATASET_XML);
        executeDataSet(DATA_VIOLATIONS_XML);
        logger.info("here one");

        criteria = Mockito.mock(Criteria.class);
        criterion = Mockito.mock(Criterion.class);*/
        invalidDateOfDeath = new InvalidDateOfDeath();
        mockedSessionFactory = Mockito.mock(SessionFactory.class);
        PowerMockito.mockStatic(Context.class);
        PowerMockito.when(Context.getRegisteredComponent("sab_lite", SessionFactory.class))
                .thenReturn(mockedSessionFactory);
        logger.info("here two");
    }



    @Test
    public void EvaluateTestEmptyPatientList () {
        /*List<RuleResult<Patient>> list = invalidDateOfDeath.evaluate();
        when(mockedSessionFactory.getCurrentSession().createCriteria(anyString())
        .add(criterion).add(criterion).add(criterion)).thenReturn(criteria);

        List<Patient> patientList = new ArrayList<>();
        when(criteria.list()).thenReturn(patientList);*/

        List<RuleResult<Patient>> list = invalidDateOfDeath.evaluate();
        assertEquals(0, list.size());
    }
}
