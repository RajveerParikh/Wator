package wator;

/**
 * Convenience class for holding simulation parameters for Wator.
 * Since all values and methods are static, they can be referred to
 * by Parameters.value or Parameters.method().
 * <P>
 * These parameters "should" all be private, with getter methods.
 * However, that just adds a lot of boilerplate methods with no real
 * advantage.
 * 
 * @author David Matuszek
 * @author Rajveer Parikh
 */
public class Parameters {
    static int numberOfSharks;
    static int sharkGestationPeriod;
    static int sharkStarvationPeriod;
    
    static int numberOfFish;
    static int fishGestationPeriod;
    static int fishStarvationPeriod;
    
    /**
     * Sets the parameters for sharks.
     * @param n The initial number.
     * @param g The gestation period.
     * @param s The starvation period.
     */
    public static void setSharkStatistics(int n, int g, int s) {
        numberOfSharks = n;
        sharkGestationPeriod = g;
        sharkStarvationPeriod = s;
    }
    
    /**
     * Sets the parameters for fish.
     * @param n The initial number.
     * @param g The gestation period.
     * @param s The starvation period.
     */
    public static void setFishStatistics(int n, int g, int s) {
        numberOfFish = n;
        fishGestationPeriod = g;
        fishStarvationPeriod = s;
    }
}
