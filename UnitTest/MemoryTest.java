package UnitTest;

public class MemoryTest {

    private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

    public static void testMemoryUsage(){
        System.out.println("After program initialization:");
        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Total memory available (MB): " + bytesToMegabytes(runtime.totalMemory()));
        System.out.println("Free memory available (MB): " + bytesToMegabytes(runtime.freeMemory()));
        System.out.println("Used memory in megabytes: "
                + bytesToMegabytes(memory));
    }
}
