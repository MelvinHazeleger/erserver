
/*
 *
 * @author Melvin Hazeleger
 * @created Monday 25-Jun-18 19:47
 *
 */


package erserver.modules.hardunderstand;


public class NeededStaffCalculator {

   final int[] staffReqForRedPatient;
   final int[] staffReqForYellowPatient;
   final int[] staffReqForGreenPatient;
   final int redInboundPatients;
   final int yellowInboundPatients;
   final int greenInboundPatients;

   public NeededStaffCalculator(final int[] staffReqForRedPatient,
                                final int[] staffReqForYellowPatient,
                                final int[] staffReqForGreenPatient,
                                final int redInboundPatients,
                                final int yellowInboundPatients,
                                final int greenInboundPatients) {

      this.staffReqForRedPatient = staffReqForRedPatient;
      this.staffReqForYellowPatient = staffReqForYellowPatient;
      this.staffReqForGreenPatient = staffReqForGreenPatient;
      this.redInboundPatients = redInboundPatients;
      this.yellowInboundPatients = yellowInboundPatients;
      this.greenInboundPatients = greenInboundPatients;
   }

   /**
    * specified all needed docs + nurses for all inbound patients
    *
    * @return
    */
   public int[] overall() {
      int[] neededStaff = {0, 0};
      neededStaff[0] = redInboundPatients * staffReqForRedPatient[0];
      neededStaff[0] += yellowInboundPatients * staffReqForYellowPatient[0];
      neededStaff[0] += greenInboundPatients * staffReqForGreenPatient[0];
      neededStaff[1] = redInboundPatients * staffReqForRedPatient[1];
      neededStaff[1] += yellowInboundPatients * staffReqForYellowPatient[1];
      neededStaff[1] += greenInboundPatients * staffReqForGreenPatient[1];

      if (neededStaff[1] > 5) {
         neededStaff[1] -= 1;
      }

      return neededStaff;
   }

}
