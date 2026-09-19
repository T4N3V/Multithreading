import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SynchronizedExample2Test {
    public static void main(String[] args) throws Exception {
        Set<Thread> threadsBefore = Thread.getAllStackTraces().keySet();
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8.name()));
            SynchronizedExample2.main(new String[0]);

            for (Thread thread : Thread.getAllStackTraces().keySet()) {
                if (!threadsBefore.contains(thread) && !thread.isDaemon()) {
                    thread.join(5_000);
                    if (thread.isAlive()) {
                        throw new AssertionError("example thread did not finish");
                    }
                }
            }
        } finally {
            System.setOut(originalOut);
        }

        String[] actualLines =
                output.toString(StandardCharsets.UTF_8.name()).trim().split("\\R");
        Set<String> expectedLines = new HashSet<>(Arrays.asList(
                "Hello there.",
                "How are you?",
                "Thank you very much!"));

        if (actualLines.length != expectedLines.size()
                || !new HashSet<>(Arrays.asList(actualLines)).equals(expectedLines)) {
            throw new AssertionError(
                    "expected exactly three complete synchronized lines "
                            + expectedLines + ", but got " + Arrays.toString(actualLines));
        }
    }
}
