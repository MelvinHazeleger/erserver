
/*
 *
 * @author Melvin Hazeleger
 * @created Saturday 23-Jun-18 17:00
 *
 */


package erserver.modules.dependencies;


import erserver.modules.testtypes.Patient;

import java.util.ArrayList;
import java.util.List;

public class InboundPatientTestDouble implements InboundPatientSource {

   private List<Patient> inbounds;

   public InboundPatientTestDouble() {
      this.inbounds = new ArrayList<>();
   }

   public void simulateNewInboundPatient(Patient inbound) {
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
