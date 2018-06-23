
/*
 *
 * @author Melvin Hazeleger
 * @created Thursday 21-Jun-18 19:14
 *
 */


package erserver.modules.dependencies;


public interface AlertTransmitter {
   void transmit(String targetDevice, String pageText);

   void transmitRequiringAcknowl(String targetDevice, String pageText);
}
