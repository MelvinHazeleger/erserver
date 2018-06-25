
/*
 *
 * @author Melvin Hazeleger
 * @created Saturday 23-Jun-18 18:57
 *
 */


package erserver.modules.hardunderstand;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DivergenceReportBuilderTest {


   @Test
   public void testReport() {
      //prepare
      final DivergenceReportBuilder builder =
              new DivergenceReportBuilder(1, 2, 4, new int[]{3, 5},
                                          new int[]{3, 8}, 10, 4);


      //execute
      final String reportResult = builder.buildReport();

      //verify
      final String expectedReport =
              String.format("\nSituation Status:\nInbound patients requiring beds: "
                            + "%s Red, %s Yellow, %s Green.\n"
                            + "Available Docs/nurses: %s/%s\n"
                            + "Needed Docs/nurses: %s/%s\n"
                            + "Available Total Beds/Crit Beds: %s/%s",
                            1, 2, 4, 3, 5, 3, 8, 10, 4);

      assertThat(reportResult, is(expectedReport));
   }
}