package service;


import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import model.Track;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProcessFileServiceTest {

  private final ProcessFileService processFileService = new ProcessFileService();

  @Test
  void shouldThrowExceptionWhenFileHasSpecialCharacterTest() {
    File file = processFile("Test4.txt");
    Assertions.assertThrows(Exception.class, () -> processFileService.processData(file));
  }

  @Test
  void shouldTrackWhenInputNormalFile() {
    File file = processFile("Test.txt");
    Track track = processFileService.processData(file);
    boolean has = track.getAfternoonSession().getTalkList().stream().findAny().isPresent();
    Assertions.assertTrue(has);
  }

  private File processFile(String pathName) {
    URL url = getClass().getClassLoader().getResource(pathName);
    assert url != null;
    try {
      Path path = Paths.get(url.toURI());
      return new File(path.toUri());
    } catch (Exception exception) {
      throw new IllegalArgumentException("路径错误");
    }
  }

}