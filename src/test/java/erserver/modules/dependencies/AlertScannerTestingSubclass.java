
/*
 *
 * @author Melvin Hazeleger
 * @created Wednesday 20-Jun-18 20:15
 *
 */


package erserver.modules.dependencies;


import erserver.modules.testtypes.Patient;

import java.util.ArrayList;
import java.util.List;

public class AlertScannerTestingSubclass extends AlertScanner{

   public List<Patient> patientsAlertedFor;

   public AlertScannerTestingSubclass(final InboundPatientSource inboundPatientSource) {
      super(inboundPatientSource);
      patientsAlertedFor = new ArrayList<>();
   }

   @Override
   protected void alertForNewCriticalPatient(final Patient patient) {
      patientsAlertedFor.add(patient);
   }
}
