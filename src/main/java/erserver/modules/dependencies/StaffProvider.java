
/*
 *
 * @author Melvin Hazeleger
 * @created Wednesday 20-Jun-18 19:29
 *
 */


package erserver.modules.dependencies;


import java.util.List;

public interface StaffProvider {
   List<Staff> getShiftStaff();
}
