package service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.Talk;
import model.Track;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConferenceManagementServiceTest {

  private final ConferenceManagementService conferenceManagementService = new ConferenceManagementService();

  @Test
  void shouldEmptyTrackIfInputEmptyTalkList() {
    List<Talk> talkList = new ArrayList<>();
    Track track = conferenceManagementService.processTrack(talkList);
    Assertions.assertNotNull(track);
    Assertions.assertEquals(0, track.getMorningSession().getTalkList().size());
  }

  @Test
  void shouldDifferentTwoTalkListInTrackIfInputNormalFileTest() {
    File file = processFile("Test.txt");
    List<Talk> talkList = processData(file);
    Track track = conferenceManagementService.processTrack(talkList);
    List<String> stringList1 = track.getMorningSession().getTalkList().stream().map(Talk::getTitle).collect(Collectors.toList());
    List<String> stringList2 = track.getAfternoonSession().getTalkList().stream().map(Talk::getTitle).collect(Collectors.toList());
    //StringList1中的任意一条数据都不应该在StringList2中出现
    boolean isExist = stringList1.stream().anyMatch(s -> stringList2.stream().anyMatch(s1 -> StringUtils.contains(s, s1)));
    Assertions.assertFalse(isExist);
  }

  @Test
  void shouldAnyOfTalkListMessageInTestFileIfInputNormalFileTest() throws IOException {
    File file = processFile("Test.txt");
    List<String> testFile = FileUtils.readLines(file, "UTF-8");
    List<Talk> talkList = processData(file);
    Track track = conferenceManagementService.processTrack(talkList);
    List<String> stringList1 = track.getMorningSession().getTalkList().stream().map(Talk::getTitle).collect(Collectors.toList());
    List<String> stringList2 = track.getAfternoonSession().getTalkList().stream().map(Talk::getTitle).collect(Collectors.toList());
    boolean isExist = stringList1.stream().anyMatch(s -> testFile.stream().anyMatch(t -> StringUtils.contains(s, t)));
    Assertions.assertTrue(isExist);
    isExist = stringList2.stream().anyMatch(s -> testFile.stream().anyMatch(t -> StringUtils.contains(s, t)));
    Assertions.assertTrue(isExist);
  }

  @Test
  void shouldBeDifferentMessageInTwoTrackIfInputNormalFileTest() {
    File file = processFile("Test.txt");
    List<Talk> talkList = processData(file);
    Track track1 = conferenceManagementService.processTrack(talkList);
    Track track2 = conferenceManagementService.processTrack(talkList);
    List<String> stringList1 = track1.getMorningSession().getTalkList().stream().map(Talk::getTitle).collect(Collectors.toList());
    List<String> stringList2 = track2.getMorningSession().getTalkList().stream().map(Talk::getTitle).collect(Collectors.toList());
    Assertions.assertNotEquals(stringList1, stringList2);
  }

  @Test
  void shouldNoWhitespaceIfInputFileHasBlankSpaceTest() {
    File file = processFile("Test2.txt");
    List<Talk> talkList = processData(file);
    Track track = conferenceManagementService.processTrack(talkList);
    List<String> stringList1 = track.getMorningSession().getTalkList().stream().map(Talk::getTitle).collect(Collectors.toList());
    List<String> stringList2 = track.getAfternoonSession().getTalkList().stream().map(Talk::getTitle).collect(Collectors.toList());
    stringList1.forEach(s -> Assertions.assertNotEquals("", s));
    stringList2.forEach(s -> Assertions.assertNotEquals("", s));
  }

  @Test
  void shouldTimeIsObtainedNormallyIfInputFileContentHasNumbersTest() {
    File file = processFile("Test3.txt");
    List<Talk> talkList = processData(file);
    Track track = conferenceManagementService.processTrack(talkList);
    List<Integer> times = track.getMorningSession().getTalkList().stream().map(Talk::getActualTime).collect(Collectors.toList());
    boolean isExist = times.stream().anyMatch(t -> StringUtils.equals(t.toString(), "60"));
    Assertions.assertTrue(isExist);
    isExist = times.stream().anyMatch(t -> StringUtils.equals(t.toString(), "20"));
    Assertions.assertFalse(isExist);
    isExist = times.stream().anyMatch(t -> StringUtils.equals(t.toString(), "6"));
    Assertions.assertFalse(isExist);
  }


  private List<Talk> processData(File file) {
    return initTalkList(getConferenceInformation(file));
  }

  private List<String> getConferenceInformation(File file) {
    try {
      return FileUtils.readLines(file, "UTF-8");
    } catch (Exception e) {
      throw new IllegalArgumentException("读取文件失败", e);
    }
  }

  private List<Talk> initTalkList(List<String> conferenceInformation) {
    return conferenceInformation.stream()
        .filter(m -> !m.equals(""))
        .map(Talk::initTalk)
        .collect(Collectors.toList());
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