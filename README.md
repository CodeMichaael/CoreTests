# CoreTests
Simple code execution system, this tool allows users to run code stored in files and choose whether to save error details, providing a helpful way to test and troubleshoot code.

# Examples

```java

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;

public class CodeRunner {

    public static boolean runCode(String filePath, boolean log) {
        boolean success = true;  // Assume success initially

        try {
            String code = new String(Files.readAllBytes(Paths.get(filePath)));
            Runtime.getRuntime().exec("python -c \"" + code + "\"");
        } catch (IOException e) {
            success = false;
            System.err.println("(Error): Found " + e.getClass().getSimpleName() + ": " + e.getMessage() +
                    " [" + LocalTime.now() + "]");

            if (log) {
                try {
                    Files.write(Paths.get("Error_" + LocalTime.now() + ".txt"),
                            ("(Error): Found " + e.getClass().getSimpleName() + ": " + e.getMessage()).getBytes());
                    System.out.println("Logged Error Successfully.");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }

        return success;
    }

    public static void main(String[] args) {
        String filePath = "example_script.py";
        boolean logErrors = true;
        runCode(filePath, logErrors);
    }
}
```
