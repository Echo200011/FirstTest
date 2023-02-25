package service;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.Talk;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProcessFileServiceTest {

  private final ProcessFileService processFileService = new ProcessFileService();

  @Test
  void shouldReturnTalkListIfInputPathIsExistTest() throws IOException {
    File file = processFile("Test3.txt");
    List<Talk> talkList = processFileService.processData(file);
    Assertions.assertEquals(3, talkList.size());
    boolean isExist = talkList.stream().anyMatch(talk -> talk.getMessage().contains("Writing Fast Tests Against Enterprise Rails 60min"));
    Assertions.assertTrue(isExist);
    File file1 = new File("D:\\IoTestFile\\Test3.txt");
    List<String> testFile = FileUtils.readLines(file1, "UTF-8");
    List<String> message = new ArrayList<>();
    talkList.forEach(talk -> message.add(talk.getMessage()));
    Assertions.assertEquals(testFile, message);
  }


  @Test
  void shouldReturnTalkListIfABlankInFileTest() {
    File file = processFile("Test2.txt");
    List<Talk> talkList = processFileService.processData(file);
    Assertions.assertEquals(18, talkList.size());
    boolean isExist = talkList.stream().anyMatch(talk -> talk.getMessage().equals(""));
    Assertions.assertFalse(isExist);
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