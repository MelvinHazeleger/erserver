package erserver.modules.testtypes;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DosingCalculatorTest {

   private DosingCalculator dosingCalculator;
   private PatientForTesting patient;

   @Before
   public void setUp() {
      dosingCalculator = new DosingCalculator();
      patient = new PatientForTesting();
   }

   @Test
   public void returnsCorrectDosesForNeonateBroken() {
      //minusMonth(1) failed after cloning it from github, so made it minusDays(27)
      patient.setBirthDate(LocalDate.now().minusDays(27));
      String singleDose = dosingCalculator.getRecommendedSingleDose(patient, "Tylenol Oral Suspension");
      assertThat(singleDose, is("0"));
   }

   @Test
   public void returnsCorrectDosesForNeonateFixed() {
      //prepare
      patient.setBirthDate(LocalDate.of(2016, 2, 28));
      patient.setCurrentDate(LocalDate.of(2016, 3, 28));

      //execute
      String singleDose = dosingCalculator.getRecommendedSingleDose(patient, "Tylenol Oral Suspension");

      //verify
      assertThat(singleDose, is("0"));
   }

   @Test
   public void returnsCorrectDosesForInfant() {
      patient.setBirthDate(LocalDate.now().minusDays(40));
      String singleDose = dosingCalculator.getRecommendedSingleDose(patient, "Tylenol Oral Suspension");
      assertThat(singleDose, is("2.5 ml"));
   }

   @Test
   public void returnsCorrectDosesForChild() {
      patient.setBirthDate(LocalDate.now().minusYears(3));
      String singleDose = dosingCalculator.getRecommendedSingleDose(patient, "Tylenol Oral Suspension");
      assertThat(singleDose, is("5 ml"));
   }

   @Test
   public void returnsCorrectDosesForNeonateAmox() {
      patient.setBirthDate(LocalDate.now().minusDays(20));
      String singleDose = dosingCalculator.getRecommendedSingleDose(patient, "Amoxicillin Oral Suspension");
      assertThat(singleDose, is("15 mg/kg"));
   }

   @Test(expected = RuntimeException.class)
   public void returnsExceptionForAdults() {
      patient.setBirthDate(LocalDate.now().minusYears(16));
      dosingCalculator.getRecommendedSingleDose(patient, "Amoxicillin Oral Suspension");
   }

   @Test(expected = RuntimeException.class)
   public void returnsNullForUnrecognizedMedication() {
      patient.setBirthDate(LocalDate.now().minusYears(16));
      dosingCalculator.getRecommendedSingleDose(patient, "No Such Med");
   }
}