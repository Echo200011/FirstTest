package service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import model.Talk;
import model.Track;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OutputTrackListServiceTest {

  private final OutputTrackListService outputTrackListService = new OutputTrackListService();

  @Test
  void shouldReturnTrackIfInputNormalFile() throws IOException {
    File file = processFile();
    Track track = outputTrackListService.outputTrackList(file);
    assertNotNull(track);
    List<String> templateMessage = FileUtils.readLines(file, "UTF-8");
    List<String> message = track.getMorningSession().getTalkList().stream()
        .map(Talk::getMessage).collect(Collectors.toList());
    List<String> message2 = track.getAfternoonSession().getTalkList().stream()
        .map(Talk::getMessage).collect(Collectors.toList());
    message = (List<String>) CollectionUtils.union(message, message2);
    boolean isExist = message.stream().anyMatch(m -> templateMessage.stream().anyMatch(m::contains));
    Assertions.assertTrue(isExist);
  }


  private File processFile() {
    URL url = getClass().getClassLoader().getResource("Test.txt");
    assert url != null;
    try {
      Path path = Paths.get(url.toURI());
      return new File(path.toUri());
    } catch (Exception exception) {
      throw new IllegalArgumentException("路径错误");
    }
  }
}