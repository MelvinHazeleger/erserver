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
      InboundPatientTestDouble inboundDouble = new InboundPatientTestDouble();
      inboundDouble.simulateNewInboundPatient(createTestPatient(11, Priority.RED, "stroke"));
      inboundDouble.simulateNewInboundPatient(createTestPatient(12, Priority.YELLOW, "mild stroke"));
      AlertTransmitterTestDouble transmitterDouble = new AlertTransmitterTestDouble();

      AlertScanner scanner = new AlertScanner(inboundDouble, transmitterDouble);

      //execute
      scanner.scan();

      //verify
      final ArrayList<String> receivedReqAck = transmitterDouble.getAlertsReceivedRequiringAck();

      assertThat(receivedReqAck.size(), is(1));
      assertThat(receivedReqAck.get(0), is("111-111-1111: New inbound critical "
                                           + "patient: 11"));
   }

   @Test
   public void scanAlertsForYellowHeartArrhythmiaPatients() {
      //prepare
      InboundPatientTestDouble inboundDouble = new InboundPatientTestDouble();
      inboundDouble.simulateNewInboundPatient(createTestPatient(11, Priority.GREEN, "shortness of breath"));
      inboundDouble.simulateNewInboundPatient(createTestPatient(12, Priority.RED, "heart arrhythmia"));

      AlertTransmitterTestDouble transmitterDouble = new AlertTransmitterTestDouble();

      AlertScanner scanner = new AlertScanner(inboundDouble, transmitterDouble);

      //execute
      scanner.scan();

      //verify
      final List<String> alertReceived = transmitterDouble.getAlertsReceived();

//      assertThat(alertReceived.size(), is(1));
//      assertThat(alertReceived.get(0), is("111-111-1111: New inbound critical patient: 12"));
      //todo fix
   }

   @Test
   public void onlyTransmitOnceForGivenInboundPatient() {
      //prepare
      InboundPatientTestDouble patient = new InboundPatientTestDouble();
      patient.simulateNewInboundPatient(createTestPatient(11, Priority.GREEN, "shortness of breath"));
      patient.simulateNewInboundPatient(createTestPatient(12, Priority.YELLOW, "heart arrhythmia"));
      AlertTransmitterTestDouble transmitter = new AlertTransmitterTestDouble();

      AlertScanner scanner = new AlertScanner(patient, transmitter);

      //execute
      scanner.scan();
      scanner.scan();


      //verify
      assertThat(transmitter.getAlertsReceived().size(), is(1));
      assertThat(transmitter.getAlertsReceived().get(0), is("111-111-1111: New inbound critical patient: "
                                                            + "12"));

   }

   private Patient createTestPatient(int transportId, Priority priority, String condition) {
      Patient patient = new Patient();
      patient.setTransportId(transportId);
      patient.setPriority(priority);
      patient.setCondition(condition);
      return patient;
   }

}