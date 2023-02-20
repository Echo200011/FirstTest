package service;


import java.io.IOException;
import java.util.List;
import model.Cache;
import model.Session;
import model.Talk;
import model.Track;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.ProcessTimeUtil;

class CompetenceManagementServiceTest {

  List<Talk> talkList;

  ConferenceManagementService conferenceManagementService = new ConferenceManagementService();

  @BeforeEach
  void ReceiveMessage() throws IOException {
    ReceiveMessageService receiveMessageService = new ReceiveMessageService();
    talkList = receiveMessageService.processData("D:\\IoTestFile\\Test.txt");
  }

  @Test
  void TheTypeAfterProcessingTalkTest() {
    Cache cache = new Cache(talkList);
    conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(9, 12),
        cache);
    conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(13, 17),
        cache);
    Assertions.assertNotNull(cache.getAlready());
    talkList.forEach(talk -> Assertions.assertSame(talk.getClass(), Talk.class));

  }

  @Test
  void TheTypeAfterProcessingSessionTest() {
    Cache cache = new Cache(talkList);
    conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(9, 12),
        cache);
    Session morningSession=conferenceManagementService.processSession(cache);
    conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(13, 17),
        cache);
    Session afternoonSession=conferenceManagementService.processSession(cache);
    morningSession.getTalkList().forEach(talk ->  Assertions.assertSame(talk.getClass(),Talk.class));
    afternoonSession.getTalkList().forEach(talk ->  Assertions.assertSame(talk.getClass(),Talk.class));
  }

  @Test
  void processTrack() {
    Cache cache = new Cache(talkList);
    conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(9, 12),
        cache);
    Session morningSession=conferenceManagementService.processSession(cache);
    conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(13, 17),
        cache);
    Session afternoonSession=conferenceManagementService.processSession(cache);
    morningSession.getTalkList().forEach(talk ->  Assertions.assertSame(talk.getClass(),Talk.class));
    afternoonSession.getTalkList().forEach(talk ->  Assertions.assertSame(talk.getClass(),Talk.class));
    Track track = conferenceManagementService.processTrack(morningSession,afternoonSession);
      track.getMorningSession().getTalkList().forEach(talk ->  Assertions.assertSame(talk.getClass(),Talk.class));
      track.getAfternoonSession().getTalkList().forEach(talk ->Assertions.assertSame(talk.getClass(),Talk.class));
      Assertions.assertEquals(track.getMorningSession().getTalkList().get(0).getMessage(),talkList.get(0).getMessage());
  }
}