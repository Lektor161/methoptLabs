package linear;

import java.nio.file.Path;

public interface SaveMatrix extends Matrix {
    void saveToFile(Path file);
}
