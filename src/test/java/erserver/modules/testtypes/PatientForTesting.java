
/*
 *
 * @author Melvin Hazeleger
 * @created Saturday 23-Jun-18 17:05
 *
 */


package erserver.modules.testtypes;


import java.time.LocalDate;

public class PatientForTesting extends Patient{

   private LocalDate currentDate;

   public PatientForTesting() {
      this.currentDate = LocalDate.now();
   }

   @Override
   protected LocalDate getSystemCurrentDate() {
      return currentDate;
   }

   public LocalDate getCurrentDate() { return currentDate; }

   public void setCurrentDate(final LocalDate currentDate) {
      this.currentDate = currentDate; }
}
