

package erserver;

/**
 * Example that shows how to unit test around a legacy constructor
 * that initializes all kinds of dependencies without being
 * reachable (in a reasonable manner at least).
 *
 * @author Melvin Hazeleger
 */
public class LegacyConstructor {

   private BigDependency bigDep;
   private AnotherAwfulThing awfulThing;

   // before
   public LegacyConstructor() {
      this.bigDep = new BigDependency(new HugeDBConnection());
      this.awfulThing = new AnotherAwfulThing();
   }

   // trick: create an overloaded constructor, so legacy
   // code can still use old constructor.
   // However you can create instances using this constructor.
   // Now you can pass mocks, instead of trying to create
   // these terrible dependencies.
   public LegacyConstructor(BigDependency d, AnotherAwfulThing a) {
      this.bigDep = d;
      this.awfulThing = a;
   }

   //ignore for example sake
   private class BigDependency {
      BigDependency(HugeDBConnection c) {
      }
   }

   //ignore for example sake
   private class AnotherAwfulThing {}

   //ignore for example sake
   private class HugeDBConnection {}
}