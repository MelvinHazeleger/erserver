
/*
 *
 * @author Melvin Hazeleger
 * @created Thursday 21-Jun-18 19:39
 *
 */


package erserver.modules.dependencies;


import java.util.ArrayList;

class AlertTransmitterTestDouble implements AlertTransmitter {

   private ArrayList<String> alertsReceived;
   private ArrayList<String> alertsReceivedRequiringAck;

   public AlertTransmitterTestDouble() {
      this.alertsReceived = new ArrayList<>();
      this.alertsReceivedRequiringAck = new ArrayList<>();
   }

   @Override
   public void transmit(String targetDevice, String pageText) {
      this.alertsReceived.add(targetDevice + ": " + pageText);
   }

   @Override
   public void transmitRequiringAcknowl(String targetDevice, String pageText) {
      this.alertsReceivedRequiringAck.add(targetDevice + ": " + pageText);
   }

   public ArrayList<String> getAlertsReceived() { return alertsReceived; }

   public ArrayList<String> getAlertsReceivedRequiringAck() { return alertsReceivedRequiringAck; }
}
