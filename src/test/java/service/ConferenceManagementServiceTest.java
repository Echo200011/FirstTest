package service;


import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.Session;
import model.Talk;
import model.Time;
import model.Track;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConferenceManagementServiceTest {

  private final ConferenceManagementService conferenceManagementService = new ConferenceManagementService();

  private final ProcessFileService processFileService = new ProcessFileService();


  @Test
  void shouldReturnCacheIfNormalInputNormalTalkListTest() {
    File file = processFile("Test3.txt");
    List<Talk> talkList = processFileService.processData(file);
    List<Talk> returnTheTalkList = conferenceManagementService.processTalk(talkList, Time.of(9, 12));
    Assertions.assertNotNull(conferenceManagementService.processTalk(talkList, Time.of(9, 12)));
    boolean isExist = returnTheTalkList.stream().anyMatch(talk -> talk.getMessage().contains("Lua for the Masses 30min"));
    Assertions.assertTrue(isExist);
    isExist = returnTheTalkList.stream().anyMatch(talk -> talk.getTimes() == 30);
    Assertions.assertTrue(isExist);
  }


  @Test
  void shouldRetrunNewTalkListIfInputErrorTalkListOrErrorTimeOrErrorCacheTest() {
    File file = processFile("Test3.txt");
    List<Talk> talkList = processFileService.processData(file);
    List<Talk> returnTheTalkList = conferenceManagementService.processTalk(new ArrayList<>(), Time.of(9, 12));
    Assertions.assertEquals(0, returnTheTalkList.size());
    returnTheTalkList = conferenceManagementService.processTalk(talkList, Time.of(1000000, 12));
    Assertions.assertEquals(0, returnTheTalkList.size());
    returnTheTalkList = conferenceManagementService.processTalk(talkList, null);
    Assertions.assertEquals(0, returnTheTalkList.size());
  }


  @Test
  void shouldRetrunSessionIfInputNormalCacheTest() {
    File file = processFile("Test3.txt");
    List<Talk> talkList = processFileService.processData(file);
    List<Talk> returnTheTalkList = conferenceManagementService.processTalk(talkList, Time.of(9, 12));
    Session session = conferenceManagementService.processSession(returnTheTalkList, Time.of(9, 12));
    Assertions.assertNotNull(session.getTalkList());
    boolean isExist = session.getTalkList().stream().anyMatch(talk -> talk.getMessage().contains("Lua for the Masses 30min"));
    Assertions.assertTrue(isExist);
    isExist = session.getTalkList().stream().anyMatch(talk -> talk.getTimes() == 30);
    Assertions.assertTrue(isExist);
  }

  @Test
  void shouldRetrunNewSessionIfInputErrorCacheTest() {
    File file = processFile("Test3.txt");
    List<Talk> talkList = processFileService.processData(file);
    List<Talk> returnTheTalkList = conferenceManagementService.processTalk(talkList, Time.of(9, 12));
    Session session = conferenceManagementService.processSession(null, Time.of(9, 12));
    Assertions.assertEquals(0, session.getTalkList().size());
    session = conferenceManagementService.processSession(talkList, null);
    Assertions.assertEquals(0, session.getTalkList().size());
  }


  @Test
  void shouldRetrunTrackIfInputNormalSessionTest() {
    File file = processFile("Test.txt");
    List<Talk> talkList = processFileService.processData(file);
    List<Talk> returnTheTalkList1 = conferenceManagementService.processTalk(talkList, Time.of(9, 12));
    Session morningSession = conferenceManagementService.processSession(returnTheTalkList1, Time.of(9, 12));
    List<Talk> returnTheTalkList2 = conferenceManagementService.processTalk(talkList, Time.of(13, 17));
    Session afterSession = conferenceManagementService.processSession(returnTheTalkList2, Time.of(13, 17));
    Track track = conferenceManagementService.processTrack(morningSession, afterSession);
    Assertions.assertNotNull(track.getMorningSession().getTalkList());
    Assertions.assertNotNull(track.getAfternoonSession().getTalkList());
  }

  @Test
  void shouldRetrunNewTrackIfInputErrorSessionTest() {
    Session morningSession = new Session(true, new ArrayList<>());
    Session afterSession = new Session(false, new ArrayList<>());
    Track track = conferenceManagementService.processTrack(morningSession, afterSession);
    Assertions.assertEquals(0, track.getMorningSession().getTalkList().size());
    Assertions.assertEquals(0, track.getAfternoonSession().getTalkList().size());
  }

  @Test
  void shouldRetrunNewTrackIfInputNullTest() {
    Track track = conferenceManagementService.processTrack(null, null);
    Assertions.assertEquals(0, track.getMorningSession().getTalkList().size());
    Assertions.assertEquals(0, track.getAfternoonSession().getTalkList().size());
  }

  @Test
  void shouldReturnTrackButTalkListInTheAfternoonIsNullIfAfternoonsessionIsnulltTest() {
    File file = processFile("Test3.txt");
    List<Talk> talkList = processFileService.processData(file);
    List<Talk> returnTheTalkList1 = conferenceManagementService.processTalk(talkList, Time.of(9, 12));
    Session morningSession = conferenceManagementService.processSession(returnTheTalkList1, Time.of(9, 12));
    List<Talk> returnTheTalkList2 = conferenceManagementService.processTalk(talkList, Time.of(13, 17));
    Session afterSession = conferenceManagementService.processSession(returnTheTalkList2, Time.of(13, 17));
    Track track = conferenceManagementService.processTrack(morningSession, afterSession);
    Assertions.assertNotNull(track);
    Assertions.assertTrue(track.getMorningSession().getTalkList().size() > 0);
    Assertions.assertEquals(0, track.getAfternoonSession().getTalkList().size());
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