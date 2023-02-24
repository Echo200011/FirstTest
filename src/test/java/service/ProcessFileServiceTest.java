package service;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Talk;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProcessFileServiceTest {

  @Test
  void shouldReturnTalkListIfInputPathIsExistTest() throws IOException {
    ProcessFileService processFileService = new ProcessFileService();
    List<Talk> talkList = processFileService.processData("D:\\IoTestFile\\Test3.txt");
    Assertions.assertEquals(3, talkList.size());
    boolean isExist = talkList.stream()
        .anyMatch(talk -> talk.getMessage().contains("Writing Fast Tests Against Enterprise Rails 60min"));
    Assertions.assertTrue(isExist);
    File file = new File("D:\\IoTestFile\\Test3.txt");
    List<String> testFile = FileUtils.readLines(file, "UTF-8");
    List<String> message = new ArrayList<>();
    talkList.forEach(talk -> message.add(talk.getMessage()));
    Assertions.assertEquals(testFile,message);
  }

  @Test
  void shouldReturnNullIfInputErrorPathOrPathNonexistentTest(){
    ProcessFileService processFileService = new ProcessFileService();
    List<Talk> talkList = processFileService.processData("D:\\IoTestFile\\1.txt");
    Assertions.assertNull(talkList);
  }

  @Test
  void shouldReturnTalkListIfABlankInFileTest(){
    ProcessFileService processFileService = new ProcessFileService();
    List<Talk> talkList = processFileService.processData("D:\\IoTestFile\\Test2.txt");
    Assertions.assertEquals(18, talkList.size());
    boolean isExist = talkList.stream().anyMatch(talk -> talk.getMessage().equals(""));
    Assertions.assertFalse(isExist);
  }


}