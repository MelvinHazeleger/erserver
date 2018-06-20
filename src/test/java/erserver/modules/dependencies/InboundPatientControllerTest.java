
/*
 *
 * @author Melvin Hazeleger
 * @created Tuesday 19-Jun-18 20:44
 *
 */


package erserver.modules.dependencies;

import erserver.modules.testtypes.Patient;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class InboundPatientControllerTest {

   @Test
   public void testInboundXmlConversion() {
      //prepare
      final String xmlForScenario = ""
              + "<Inbound>\n"
              + "   <Patient>\n"
              + "      <TransportId>1</TransportId>\n"
              + "      <Name>John Doe</Name>\n"
              + "      <Condition>heart arrhythmia</Condition>\n"
              + "      <Priority>YELLOW</Priority>\n"
              + "      <Birthdate></Birthdate>\n"
              + "   </Patient>\n"
              + "</Inbound>";

      //execute
      List<Patient> patients = InboundPatientController.buildPatientsFromXml(xmlForScenario);

      //verify
      assertThat(patients, is(notNullValue()));
      assertThat(patients.size(), is(1));

      Patient patient = patients.get(0);

      assertThat(patient.getTransportId(), is(1));
      assertThat(patient.getPriority(), is(Priority.YELLOW));
      assertThat(patient.getCondition(), is("heart arrhythmia"));
      assertThat(patient.getName(), is("John Doe"));
      assertThat(patient.getBirthDate(), is(nullValue()));
   }
}