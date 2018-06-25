
/*
 *
 * @author Melvin Hazeleger
 * @created Monday 25-Jun-18 19:52
 *
 */


package erserver.modules.hardunderstand;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NeededStaffCalculatorTest {

   private int[] redStaffRequired = {1, 2};
   private int[] yellowStaffRequired = {1, 1};
   private int[] greenStaffRequired = {0, 1};


   @Test
   public void nursesRequiredReducesBytOneWhenOver5() {
      // prepare
      NeededStaffCalculator neededStaff = new NeededStaffCalculator(redStaffRequired,
                                                                    yellowStaffRequired,
                                                                    greenStaffRequired,
                                                                    1, 1, 1);

      // execute
      int[] result = neededStaff.overall();

      // verify
      assertThat(result[0], is(2));
      assertThat(result[1], is(4));


   }

   @Test
   public void docsNursesValuesCalculatedCorrectly_over5Nurses() {
      //prepare
      NeededStaffCalculator neededStaff =
              new NeededStaffCalculator(redStaffRequired, yellowStaffRequired,
                                        greenStaffRequired,
                                        1, 3, 3);

      //execute
      int[] result = neededStaff.overall();

      //verify
      assertThat(result[0], is(4));
      assertThat(result[1], is(7));

   }
}