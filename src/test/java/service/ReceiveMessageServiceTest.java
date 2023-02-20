package service;

import java.io.IOException;
import java.util.List;
import model.Talk;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReceiveMessageServiceTest {

  @Test
  void receivedMessageToTalkTest() throws IOException {
    ReceiveMessageService receiveMessageService = new ReceiveMessageService();
    List<Talk> talks = receiveMessageService.processData("D:\\IoTestFile\\Test.txt");
    talks.forEach(talk -> {
      Assertions.assertNotNull(talk.getMessage());
      Assertions.assertNotNull(talk.getTime());
    });
  }
}