
/*
 *
 * @author Melvin Hazeleger
 * @created Saturday 23-Jun-18 18:24
 *
 */


package erserver.modules.hardunderstand;

import erserver.modules.dependencies.Priority;
import erserver.modules.testtypes.Patient;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DivergenceControllerTest {

   private ArrayList<Patient> incomingPatients;
   /**
    * instance of the class under test
    */
   private DivergenceController controller;


   @Before
   public void setUp() {
      controller = new DivergenceController();
      incomingPatients = new ArrayList<>();
   }

   @Test
   public void DivergenceControllerTest() {
      //prepare
      Patient nonEmergP = createPatients(Priority.GREEN, "non-emergency situation, patient is "
                                                         + "ambulatory");
      incomingPatients.add(nonEmergP);
      incomingPatients.add(createPatients(Priority.RED, "non-emergency situation, patient is "
                                                        + "ambulatory"));
      incomingPatients.add(createPatients(Priority.YELLOW, "non-emergency situation, patient is "
                                                           + "ambulatory"));
      incomingPatients.add(createPatients(Priority.GREEN, "ambulatory bleeding"));
      incomingPatients.add(createPatients(Priority.GREEN, "non-emergency, but unable to walk"));


      //execute
      List<Patient> filteredList = controller.patientsAffectingDivergence(incomingPatients);

      //verify
      assertThat(filteredList.contains(nonEmergP), is(false));

   }

   private Patient createPatients(Priority priority, String condition) {
      Patient patient = new Patient();
      patient.setPriority(priority);
      patient.setCondition(condition);
      return patient;
   }


}