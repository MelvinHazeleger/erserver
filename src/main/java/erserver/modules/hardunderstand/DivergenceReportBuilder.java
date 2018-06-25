
/*
 *
 * @author Melvin Hazeleger
 * @created Saturday 23-Jun-18 18:50
 *
 */


package erserver.modules.hardunderstand;


public class DivergenceReportBuilder {


   private final int redInboundPatients;
   private final int yellowInboundPatients;
   private final int greenInboundPatients;
   private final int[] availableStaff;
   private final int[] neededStaff;
   private final int bedsAvailable;
   private final int criticalBedsAvailable;

   public DivergenceReportBuilder(int redInboundPatients,
                                  int yellowInboundPatients,
                                  int greenInboundPatients,
                                  int[] availableStaff,
                                  int[] neededStaff,
                                  int bedsAvailable,
                                  int criticalBedsAvailable) {

      this.redInboundPatients = redInboundPatients;
      this.yellowInboundPatients = yellowInboundPatients;
      this.greenInboundPatients = greenInboundPatients;
      this.availableStaff = availableStaff;
      this.neededStaff = neededStaff;
      this.bedsAvailable = bedsAvailable;
      this.criticalBedsAvailable = criticalBedsAvailable;
   }


   public String buildReport() {
      return "\nSituation Status:\n"
             + "Inbound patients requiring beds: "
             + redInboundPatients + " Red, "
             + yellowInboundPatients + " Yellow, "
             + greenInboundPatients + " Green.\n"
             + "Available Docs/nurses: " + availableStaff[0] + "/" + availableStaff[1] + "\n"
             + "Needed Docs/nurses: " + neededStaff[0] + "/" + neededStaff[1] + "\n"
             + "Available Total Beds/Crit Beds: " + bedsAvailable + "/" + criticalBedsAvailable;

   }
}
