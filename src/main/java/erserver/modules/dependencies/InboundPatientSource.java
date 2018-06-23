
/*
 *
 * @author Melvin Hazeleger
 * @created Wednesday 20-Jun-18 20:01
 *
 */


package erserver.modules.dependencies;


import erserver.modules.testtypes.Patient;

import java.util.List;

public interface InboundPatientSource {
   List<Patient> currentInboundPatients();

   void informOfPatientArrival(int transportId);
}
