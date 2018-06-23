
/*
 *
 * @author Melvin Hazeleger
 * @created Thursday 21-Jun-18 19:09
 *
 */


package erserver.modules.dependencies;

import erserver.modules.dependencies.vendorpagersystem.PagerSystem;
import erserver.modules.dependencies.vendorpagersystem.PagerTransport;

/**
 * Example for how to wrap external API calls to increase test-ability
 */
public class PagerSystemAlertTransmitter implements AlertTransmitter {

   @Override
   public void transmit(String targetDevice, String pageText) {
      PagerTransport transport = getInitializedTransport();
      transport.transmit(targetDevice, pageText);
   }

   @Override
   public void transmitRequiringAcknowl(String targetDevice, String pageText) {

      PagerTransport transport = getInitializedTransport();
      transport.transmitRequiringAcknowledgement(targetDevice, pageText);

   }

   private PagerTransport getInitializedTransport() {
      PagerTransport transport = PagerSystem.getTransport();
      transport.initialize();
      return transport;
   }

}
