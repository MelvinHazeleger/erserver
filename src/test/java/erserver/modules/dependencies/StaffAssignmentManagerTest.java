
/*
 *
 * @author Melvin Hazeleger
 * @created Wednesday 20-Jun-18 18:58
 *
 */


package erserver.modules.dependencies;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class StaffAssignmentManagerTest {

   @Test
   public void physiciansAndResidentsReturnForPhysiciansOnDuty() {
      //prepare
      StaffAssignmentManager manager = new StaffAssignmentManager();

      //execute
      List<Staff> physiciansOnDuty = manager.getPhysiciansOnDuty();

      //verify

   }


}