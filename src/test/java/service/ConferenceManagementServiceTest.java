package service;


import com.sun.xml.internal.ws.policy.AssertionSet;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
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
  @SneakyThrows
  void shouldReturnTalkListIfInputNormalPathTest() {
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
  @SneakyThrows
  void shouldReturnNullIfInputErrorpathTest() {
    ProcessFileService processFileService = new ProcessFileService();
    List<Talk> talkList = processFileService.processData("D:\\IoTestFile\\1.txt");
    Assertions.assertNull(talkList);
    talkList = processFileService.processData("xzczxc");
    Assertions.assertNull(talkList);
    talkList = processFileService.processData("D:\\IoTestFile\\Test2.txt");
    Assertions.assertNotNull(talkList);
    Assertions.assertEquals(18, talkList.size());
    boolean isExist = talkList.stream().filter(talk -> talk.getMessage().equals("")).findAny().isPresent();
    Assertions.assertFalse(isExist);
  }

  @Test
  @SneakyThrows
  void shouldReturnCacheIfNormalInputNormalTalkListTest() {
    ProcessFileService processFileService = new ProcessFileService();
    List<Talk> talkList = processFileService.processData("D:\\IoTestFile\\Test3.txt");
    Cache cache = new Cache(talkList);
    Assertions.assertNotNull(conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(9, 12), cache));
    boolean isExist = cache.getAlready().stream()
        .filter(talk -> talk.getMessage().contains("Lua for the Masses 30min"))
        .findAny()
        .isPresent();
    Assertions.assertTrue(isExist);
    isExist = cache.getAlready().stream()
        .filter(talk -> talk.getTimes() == 30)
        .findAny()
        .isPresent();
    Assertions.assertTrue(isExist);
  }

  @Test
  @SneakyThrows
  void shouldRetrunNullIfInputErrorTalkListOrErrorTimeOrErrorCacheTest() {
    ProcessFileService processFileService = new ProcessFileService();
    List<Talk> talkList = processFileService.processData("dsadasd");
    Cache cache = new Cache(talkList);
    Cache cache1 = new Cache(new ArrayList<>());
    Cache cache2 = conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(9, 12), cache);
    Assertions.assertNull(cache2);
    cache2 = conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(9, 12), cache1);
    Assertions.assertNull(cache2);
    cache2 = conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(1000000, 12), cache);
    Assertions.assertNull(cache2);
  }


  @Test
  @SneakyThrows
  void shouldRetrunSessionIfInputNormalCacheTest() {
    ProcessFileService processFileService = new ProcessFileService();
    List<Talk> talkList = processFileService.processData("D:\\IoTestFile\\Test3.txt");
    Cache cache = new Cache(talkList);
    conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(9, 12), cache);
    Session session = conferenceManagementService.processSession(cache,ProcessTimeUtil.of(9, 12));
    Assertions.assertNotNull(session.getTalkList());
    boolean isExist = session.getTalkList().stream()
        .filter(talk -> talk.getMessage().contains("Lua for the Masses 30min"))
        .findAny()
        .isPresent();
    Assertions.assertTrue(isExist);
    isExist = session.getTalkList().stream()
        .filter(talk -> talk.getTimes() == 30)
        .findAny()
        .isPresent();
    Assertions.assertTrue(isExist);
  }

  @Test
  @SneakyThrows
  void shouldRetrunNullIfInputErrorCacheTest() {
    ProcessFileService processFileService = new ProcessFileService();
    List<Talk> talkList = processFileService.processData("asdasdad");
    Cache cache = new Cache(talkList);
    conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(9, 12), cache);
    Session session = conferenceManagementService.processSession(cache, ProcessTimeUtil.of(9, 12));
    Assertions.assertNull(session);
    session = conferenceManagementService.processSession(null, ProcessTimeUtil.of(9, 12));
    Assertions.assertNull(session);
  }


  @Test
  void shouldRetrunTrackIfInputNormalSessionTest() {
    ProcessFileService processFileService = new ProcessFileService();
    List<Talk> talkList = processFileService.processData("D:\\IoTestFile\\Test.txt");
    Cache cache = new Cache(talkList);
    conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(9, 12), cache);
    Session morningSession = conferenceManagementService.processSession(cache, ProcessTimeUtil.of(9, 12));
    Cache cache1 = conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(13, 17), cache);
    Session afterSession = conferenceManagementService.processSession(cache1,ProcessTimeUtil.of(13, 17));
    Track track = conferenceManagementService.processTrack(morningSession, afterSession);
    Assertions.assertNotNull(track.getMorningSession().getTalkList());
    Assertions.assertNotNull(track.getAfternoonSession().getTalkList());
  }

  @Test
  void shouldRetrunNullIfInputErrorSessionTest() {
    Session morningSession = new Session(true,new ArrayList<>());
    Session afterSession =  new Session(false,new ArrayList<>());
    Track track = conferenceManagementService.processTrack(morningSession,afterSession);
    Assertions.assertNull(track);
  }

  @Test
  void shouldReverseIfInputReverseTest() {
    ProcessFileService processFileService = new ProcessFileService();
    List<Talk> talkList = processFileService.processData("D:\\IoTestFile\\Test.txt");
    Cache cache = new Cache(talkList);
    conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(9, 12), cache);
    Session morningSession = conferenceManagementService.processSession(cache, ProcessTimeUtil.of(9, 12));
    Cache cache1 = conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(13, 17), cache);
    Session afterSession = conferenceManagementService.processSession(cache1,ProcessTimeUtil.of(13, 17));
    Track track = conferenceManagementService.processTrack(morningSession,afterSession);
    Track track1 =conferenceManagementService.processTrack(afterSession,morningSession);
    Assertions.assertEquals(track.getMorningSession().getTalkList(),track1.getMorningSession().getTalkList());
  }

  @Test
  void shouldReturnTrackButTalkListInTheAfternoonIsNullIfAfternoonsessionIsnulltTest() {
    ProcessFileService processFileService = new ProcessFileService();
    List<Talk> talkList = processFileService.processData("D:\\IoTestFile\\Test3.txt");
    Cache cache = new Cache(talkList);
    conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(9, 12), cache);
    Session morningSession = conferenceManagementService.processSession(cache, ProcessTimeUtil.of(9, 12));
    Cache cache1 = conferenceManagementService.processTalk(talkList, ProcessTimeUtil.of(13, 17), cache);
    Session afterSession = conferenceManagementService.processSession(cache1,ProcessTimeUtil.of(13, 17));
    Track track = conferenceManagementService.processTrack(morningSession,afterSession);
    Assertions.assertNotNull(track);
    Assertions.assertTrue(track.getMorningSession().getTalkList().size()>0);
    Assertions.assertEquals(0,track.getAfternoonSession().getTalkList().size());
  }


}