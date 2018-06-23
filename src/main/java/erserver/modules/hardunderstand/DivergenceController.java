package erserver.modules.hardunderstand;

import erserver.modules.dependencies.Bed;
import erserver.modules.dependencies.ERServerMainController;
import erserver.modules.dependencies.EmergencyResponseService;
import erserver.modules.dependencies.InboundPatientSource;
import erserver.modules.dependencies.Priority;
import erserver.modules.dependencies.Staff;
import erserver.modules.dependencies.StaffAssignmentManager;
import erserver.modules.dependencies.StaffRole;
import erserver.modules.dependencies.vendorpagersystem.PagerSystem;
import erserver.modules.dependencies.vendorpagersystem.PagerTransport;
import erserver.modules.testtypes.Patient;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DivergenceController {

   private static final String ADMIN_ON_CALL_DEVICE = "111-111-1111";

   private boolean redDivergence;
   private boolean yellowDivergence;
   private boolean greenDivergence;
   private int redCount;
   private int yellowCount;
   private int greenCount;
   private int allowedCount;
   private int redBedOverflowAllowed;
   private int yellowBedOverflowAllowed;
   private int greenBedOverflowAllowed;


   public DivergenceController() {
      this.redDivergence = false;
      this.yellowDivergence = false;
      this.greenDivergence = false;
      this.redCount = 0;
      this.yellowCount = 0;
      this.greenCount = 0;
      this.allowedCount = 3;
      this.redBedOverflowAllowed = 0;
      this.yellowBedOverflowAllowed = 1;
      this.greenBedOverflowAllowed = 4;
   }

   /**
    * checks for divergence?
    */
   public void check() {
      StaffAssignmentManager manager =
              new ERServerMainController().getStaffAssignmentManager();
      InboundPatientSource controller =
              new ERServerMainController().getInboundPatientController();

      // red patient needs 1 doctor, 2 nurses
      int[] staffReqForRedPatient = {1, 2};
      // yellow patient needs 1 doctor, 1 nurse
      int[] staffReqForYellowPatient = {1, 1};
      // green patient needs 1 nurse
      int[] staffReqForGreenPatient = {0, 1};

      boolean redIncremented = false;
      boolean yellowIncremented = false;
      boolean greenIncremented = false;

      List<Staff> staff = manager.getAvailableStaff();
      List<Bed> beds = manager.getAvailableBeds();

      int redInboundPatients = 0;
      int yellowInboundPatients = 0;
      int greenInboundPatients = 0;
      int[] availableStaff = {0, 0};

      int criticalBedsAvailable = calculateCriticalBedsAvailable(beds);

      // determine the num of inbound patients for each priority
      List<Patient> patients = patientsAffectingDivergence(controller.currentInboundPatients());
      for (Patient patient : patients) {
         if (Priority.RED.equals(patient.getPriority())) {
            redInboundPatients++;
         } else if (Priority.YELLOW.equals(patient.getPriority())) {
            yellowInboundPatients++;
         } else if (Priority.GREEN.equals(patient.getPriority())) {
            greenInboundPatients++;
         }
      }

      // determine the num of available doctors and nurses
      for (Staff cur : staff) {
         if (StaffRole.DOCTOR.equals(cur.getRole())) {
            availableStaff[0]++;
         } else if (StaffRole.NURSE.equals(cur.getRole())) {
            availableStaff[1]++;
         }
      }

      // increment red priority diversion if not enough critical beds
      // for inbound red priority patients
      if (redInboundPatients > (criticalBedsAvailable + redBedOverflowAllowed)) {
         redCount++;
         redIncremented = true;
      }


      if (yellowInboundPatients + greenInboundPatients >
          (beds.size() - criticalBedsAvailable + yellowBedOverflowAllowed +
           greenBedOverflowAllowed)) {
         if ((greenInboundPatients > (beds.size() - criticalBedsAvailable
                                      + greenBedOverflowAllowed))
             && (yellowInboundPatients <= (beds.size() - criticalBedsAvailable
                                           + yellowBedOverflowAllowed))) {
            greenCount++;
            greenIncremented = true;
         } else {
            greenCount++;
            yellowCount++;
            greenIncremented = true;
            yellowIncremented = true;
         }
      }

      int[] neededStaff = calculateNeededStaff(staffReqForRedPatient, staffReqForYellowPatient,
                                               staffReqForGreenPatient, redInboundPatients,
                                               yellowInboundPatients, greenInboundPatients);

      // determine if diversion increment need to occur based of doctor num
      if (neededStaff[0] > availableStaff[0]) {
         int diff = neededStaff[0] - availableStaff[0];
         if ((greenInboundPatients * staffReqForGreenPatient[0]) >= diff) {
            if (!greenIncremented) {
               greenIncremented = true;
               greenCount++;
            }
         } else {

            int both =
                    (yellowInboundPatients * staffReqForYellowPatient[0]) + (greenInboundPatients *
                                                                             staffReqForGreenPatient[0]);
            if (both >= diff) {
               if (!greenIncremented) {
                  greenIncremented = true;
                  greenCount++;
               }
               if (!yellowIncremented) {
                  yellowIncremented = true;
                  yellowCount++;
               }
            } else {
               if (!greenIncremented) {
                  greenIncremented = true;
                  greenCount++;
               }
               if (!yellowIncremented) {
                  yellowIncremented = true;
                  yellowCount++;
               }
               if (!redIncremented) {
                  redIncremented = true;
                  redCount++;
               }
            }
         }
      }

      // determine if diversion increment need to occur based of nurse num
      if (neededStaff[1] > availableStaff[1]) {
         int diff = neededStaff[1] - availableStaff[1];
         if ((greenInboundPatients * staffReqForGreenPatient[1]) >= diff) {
            if (!greenIncremented) {
               greenIncremented = true;
               greenCount++;
            }
         } else {
            int both = (yellowInboundPatients * staffReqForYellowPatient[1]) + (greenInboundPatients
                                                                                *
                                                                                staffReqForGreenPatient[1]);
            if (both >= diff) {
               if (!greenIncremented) {
                  greenIncremented = true;
                  greenCount++;
               }
               if (!yellowIncremented) {
                  yellowIncremented = true;
                  yellowCount++;
               }
            } else {
               if (!greenIncremented) {
                  greenIncremented = true;
                  greenCount++;
               }
               if (!yellowIncremented) {
                  yellowIncremented = true;
                  yellowCount++;
               }
               if (!redIncremented) {
                  redIncremented = true;
                  redCount++;
               }
            }
         }
      }

      DivergenceReportBuilder reportBuilder =
              new DivergenceReportBuilder(redInboundPatients,
                                          yellowInboundPatients,
                                          greenInboundPatients,
                                          availableStaff,
                                          neededStaff,
                                          beds.size(),
                                          criticalBedsAvailable);

      String divergenceReport = reportBuilder.buildReport();


      // go into divergence mode if divergence is necessary
      EmergencyResponseService transportService =
              new EmergencyResponseService("http://localhost", 4567, 1000);
      if (redIncremented) {
         if ((redCount > allowedCount) && !redDivergence) {
            redDivergence = true;
            transportService.requestInboundDiversion(Priority.RED);
            sendDivergencePage("Entered divergence for RED priority patients!", true);
            redCount = 0;
         }
      } else {
         redCount = 0;
         if (redDivergence) {
            transportService.removeInboundDiversion(Priority.RED);
            sendDivergencePage("Ended divergence for RED priority patients.", false);
            redDivergence = false;
         }
      }
      if (yellowIncremented) {
         if ((yellowCount > allowedCount) && !yellowDivergence) {
            yellowDivergence = true;
            transportService.requestInboundDiversion(Priority.YELLOW);
            sendDivergencePage("Entered divergence for YELLOW priority patients!", true);
            yellowCount = 0;
         }
      } else {
         yellowCount = 0;
         if (yellowDivergence) {
            transportService.removeInboundDiversion(Priority.YELLOW);
            sendDivergencePage("Ended divergence for YELLOW priority patients.", false);
            yellowDivergence = false;
         }
      }
      if (greenIncremented) {
         if ((greenCount > allowedCount) && !greenDivergence) {
            greenDivergence = true;
            transportService.requestInboundDiversion(Priority.GREEN);
            sendDivergencePage("Entered divergence for GREEN priority patients!", true);
            greenCount = 0;
         }
      } else {
         greenCount = 0;
         if (greenDivergence) {
            transportService.removeInboundDiversion(Priority.GREEN);
            sendDivergencePage("Ended divergence for GREEN priority patients.", false);
            greenDivergence = false;
         }
      }
   }

   List<Patient> patientsAffectingDivergence(final List<Patient> patients) {
      Stream<Patient> allPatients = patients.stream();

      Stream<Patient> filteredPatients = allPatients.filter(patient -> {
         return !(patient.getCondition().toLowerCase().contains("ambulatory")
                  && patient.getCondition().toLowerCase().contains("non-emergency"))
                || !(patient.getPriority().equals(Priority.GREEN));
      });

      return filteredPatients.collect(Collectors.toList());

   }

   private int[] calculateNeededStaff(final int[] staffReqForRedPatient,
                                      final int[] staffReqForYellowPatient,
                                      final int[] staffReqForGreenPatient,
                                      final int redInboundPatients,
                                      final int yellowInboundPatients,
                                      final int greenInboundPatients) {
      int[] neededStaff = {0, 0};
      neededStaff[0] = redInboundPatients * staffReqForRedPatient[0];
      neededStaff[0] += yellowInboundPatients * staffReqForYellowPatient[0];
      neededStaff[0] += greenInboundPatients * staffReqForGreenPatient[0];
      neededStaff[1] = redInboundPatients * staffReqForRedPatient[1];
      neededStaff[1] += yellowInboundPatients * staffReqForYellowPatient[1];
      neededStaff[1] += greenInboundPatients * staffReqForGreenPatient[1];
      return neededStaff;
   }

   /**
    * extracted method. This can be covered by using 'make static' refactoring.
    * Also we could later move this to a more appropriate class.
    *
    * @param beds
    * @return
    */
   private int calculateCriticalBedsAvailable(final List<Bed> beds) {
      int criticalBedsAvailable = 0;
      for (Bed bed : beds) {
         if (bed.isCriticalCare()) {
            criticalBedsAvailable++;
         }
      }
      return criticalBedsAvailable;
   }

   /**
    * sends a page in case of a divergence situations.
    *
    * @param text
    * @param requireAck
    */
   private void sendDivergencePage(String text, boolean requireAck) {
      try {
         PagerTransport transport = PagerSystem.getTransport();
         transport.initialize();
         if (requireAck) {
            transport.transmitRequiringAcknowledgement(ADMIN_ON_CALL_DEVICE, text);
         } else {
            transport.transmit(ADMIN_ON_CALL_DEVICE, text);
         }
      } catch (Throwable t) {
         t.printStackTrace();
      }

   }

}
