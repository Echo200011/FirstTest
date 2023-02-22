package service;

import java.util.List;
import model.Talk;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReceiveMessageServiceTest {

  @Test
  void receivedMessageToTalkTest() {
    ProcessFileService processFileService = new ProcessFileService();
    List<Talk> talks = processFileService.processData("D:\\IoTestFile\\Test.txt");
    talks.forEach(talk -> {
      Assertions.assertNotNull(talk.getMessage());
      Assertions.assertNotNull(talk.getTime());
    });
  }
}