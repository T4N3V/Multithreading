import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SynchronizedExample1Test {
    public static void main(String[] args) throws Exception {
        Path java = Path.of(System.getProperty("java.home"), "bin", "java");
        Process example = new ProcessBuilder(java.toString(), "-cp",
                System.getProperty("java.class.path"), "SynchronizedExample1")
                .redirectErrorStream(true)
                .start();

        try {
            if (!example.waitFor(10, TimeUnit.SECONDS)) {
                throw new AssertionError("SynchronizedExample1 did not finish");
            }

            // Worker order is unspecified, but each message must appear exactly once.
            String output = new String(example.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            List<String> actual = output.lines().sorted().collect(Collectors.toList());
            List<String> expected = List.of("Hello there.", "How are you?", "Thank you very much!");
            if (example.exitValue() != 0 || !actual.equals(expected)) {
                throw new AssertionError("Expected three complete messages once each (any order). "
                        + "Exit code: " + example.exitValue() + "\nOutput:\n" + output);
            }
        } finally {
            if (example.isAlive()) {
                example.destroyForcibly();
            }
        }
    }
}
