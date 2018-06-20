
/*
 *
 * @author Melvin Hazeleger
 * @created Wednesday 20-Jun-18 20:02
 *
 */


package erserver.modules.dependencies;

import erserver.modules.testtypes.Patient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AlertScannerTest {

   @Test
   public void scanAlertsForPriorityRedPatients() {
      //prepare
      InboundPatientDouble inboundDouble = new InboundPatientDouble();
      inboundDouble.simulateNewInboudPatien(createTestPatient(10, Priority.RED, "stroke"));
      inboundDouble.simulateNewInboudPatien(createTestPatient(11, Priority.YELLOW, "mild stroke"));

      AlertScannerTestingSubclass scanner = new AlertScannerTestingSubclass(inboundDouble);

      //execute
      scanner.scan();

      //verify
      assertThat(scanner.patientsAlertedFor.size(), is(1));
      assertThat(scanner.patientsAlertedFor.get(0).getTransportId(), is(10));
   }

   @Test
   public void scanAlertsForYellowHeartArrhythmiaPatients() {
      //prepare
      InboundPatientDouble inboundDouble = new InboundPatientDouble();
      inboundDouble.simulateNewInboudPatien(createTestPatient(10, Priority.GREEN, "shortness of breath"));
      inboundDouble.simulateNewInboudPatien(createTestPatient(11, Priority.RED, "heart arrhythmia"));

      AlertScannerTestingSubclass scanner = new AlertScannerTestingSubclass(inboundDouble);

      //execute
      scanner.scan();

      //verify
      assertThat(scanner.patientsAlertedFor.size(), is(1));
      assertThat(scanner.patientsAlertedFor.get(0).getTransportId(), is(11));
   }


   private Patient createTestPatient(int transportId, Priority priority, String condition) {
      Patient patient = new Patient();
      patient.setTransportId(transportId);
      patient.setPriority(priority);
      patient.setCondition(condition);
      return patient;
   }


   private class InboundPatientDouble implements InboundPatientSource {

      private List<Patient> inbounds;

      public InboundPatientDouble(){
         this.inbounds = new ArrayList<>();
      }

      public void simulateNewInboudPatien(Patient inbound) {
         inbounds.add(inbound);
      }

      @Override
      public List<Patient> currentInboundPatients() {
         return inbounds;
      }

      @Override
      public void informOfPatientArrival(int transportId) {
         //impl later when testing needed for this function
      }
   }
}