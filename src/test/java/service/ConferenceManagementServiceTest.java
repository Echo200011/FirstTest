package service;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Cache;
import model.Session;
import model.Talk;
import model.Track;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.ProcessTimeUtil;

class ConferenceManagementServiceTest {

  ConferenceManagementService conferenceManagementService = new ConferenceManagementService();

  @Test
  void shouldReturnTalkListIfInputNormalPath() throws IOException {
    ProcessFileService processFileService = new ProcessFileService();
    List<Talk> talks = processFileService.processData("D:\\IoTestFile\\Test.txt");
    File file = new File("D:\\IoTestFile\\Test.txt");
    List<String> talkList = FileUtils.readLines(file, "UTF-8");
    List<String> message = new ArrayList<>();
    talks.forEach(talk -> message.add(talk.getMessage()));
    Assertions.assertEquals(18, talks.size());
    Assertions.assertEquals(talkList, message);
  }

  @Test
  void shouldReturnNullIfInputErrorpathTest() throws IOException {
    ProcessFileService processFileService = new ProcessFileService();
    Assertions.assertNull(processFileService.processData("D:\\IoTestFile\\1.txt"));
    Assertions.assertNull(processFileService.processData("xzczxc"));
  }

  @Test
  void shouldReturnCacheIfNormalInputCauseTest() throws IOException {
    ProcessFileService processFileService = new ProcessFileService();
    List<Talk> talkList = processFileService.processData("D:\\IoTestFile\\Test.txt");
    Cache cache = new Cache(talkList);
    Assertions.assertNotNull(conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(9, 12), cache));
  }

  @Test
  void shouldRetrunNullIfInputErrorParameter() throws IOException {
    ProcessFileService processFileService = new ProcessFileService();
    List<Talk> talkList = processFileService.processData("dsadasd");
    Cache cache = new Cache(talkList);
    Cache cache1 = new Cache(new ArrayList<>());
    Assertions.assertNull(conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(9, 12), cache));
    Assertions.assertNull(conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(1000000000, 12), cache));
    Assertions.assertNull(conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(9, 12), cache));
  }



/*  @Test
  void() {

  }*/

 /*  @Test
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
  }*/
}