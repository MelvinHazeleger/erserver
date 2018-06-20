
/*
 *
 * @author Melvin Hazeleger
 * @created Wednesday 20-Jun-18 18:58
 *
 */


package erserver.modules.dependencies;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StaffAssignmentManagerTest {

   @Test
   public void physiciansAndResidentsReturnForPhysiciansOnDuty() {
      //prepare
      final StaffProviderDouble staffRepoDouble = new StaffProviderDouble();
      staffRepoDouble.addStaffMemberToReturn(new Staff(1, "John Doctor", StaffRole.DOCTOR));
      staffRepoDouble.addStaffMemberToReturn(new Staff(2, "Frank Resident", StaffRole.RESIDENT));

      StaffAssignmentManager manager = new StaffAssignmentManager(staffRepoDouble, new BedProviderDouble());

      //execute
      List<Staff> physiciansOnDuty = manager.getPhysiciansOnDuty();

      //verify
      assertThat(physiciansOnDuty.size(), is(2));
      assertThat(physiciansOnDuty.get(0).getStaffId(), is(1));
      assertThat(physiciansOnDuty.get(1).getStaffId(), is(2));
   }

   @Test
   public void sameTestWithMockito() {
      //prepare
      List<Staff> docsAndRes = Arrays.asList(new Staff(1, "John Doctor", StaffRole.DOCTOR),
                                             new Staff(2, "Frank Resident", StaffRole.RESIDENT));
      List<Bed> beds = new ArrayList<>();

      StaffProvider mockStaffProvider = mock(StaffProvider.class);
      when(mockStaffProvider.getShiftStaff()).thenReturn(docsAndRes);

      BedProvider mockBedProvider = mock(BedProvider.class);
      when(mockBedProvider.getAllBeds()).thenReturn(beds);

      StaffAssignmentManager manager = new StaffAssignmentManager(mockStaffProvider, mockBedProvider);

      //execute
      List<Staff> physiciansOnDuty = manager.getPhysiciansOnDuty();

      //verify
      assertThat(physiciansOnDuty.size(), is(2));
      assertThat(physiciansOnDuty.get(0).getStaffId(), is(1));
      assertThat(physiciansOnDuty.get(1).getStaffId(), is(2));
   }


   private static class StaffProviderDouble implements StaffProvider {
      private List<Staff> staffToReturn;

      private StaffProviderDouble() {
         this.staffToReturn = new ArrayList<>();
      }

      public void addStaffMemberToReturn(Staff staff) {
         this.staffToReturn.add(staff);
      }

      @Override
      public List<Staff> getShiftStaff() {
         return staffToReturn;
      }
   }


   private class BedProviderDouble implements BedProvider {
      @Override
      public List<Bed> getAllBeds() {
         return new ArrayList<>();
      }
   }
}