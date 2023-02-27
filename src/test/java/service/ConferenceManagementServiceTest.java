package service;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import model.Session;
import model.Talk;
import model.Time;
import model.Track;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConferenceManagementServiceTest {

  private final ConferenceManagementService conferenceManagementService = new ConferenceManagementService();

  @Test
  void shouldDifferentTwoTalkListInTrackIfIputNormalFileTest() {
    File file = processFile("Test.txt");
    Track track = conferenceManagementService.processTrack(file);
    List<String> stringList1 = track.getMorningSession().getTalkList().stream().map(talk -> talk.getMessage()).collect(Collectors.toList());
    List<String> stringList2 = track.getAfternoonSession().getTalkList().stream().map(talk -> talk.getMessage()).collect(Collectors.toList());
    //StringList1中的任意一条数据都不应该在StringList2中出现
    boolean isExist = stringList1.stream().anyMatch(s -> stringList2.stream().anyMatch(s1 -> StringUtils.contains(s, s1)));
    Assertions.assertFalse(isExist);
  }

  @Test
  void shouldAnyOfTalkListMessageInTestFileIfIputNormalFileTest() throws IOException {
    File file = processFile("Test.txt");
    List<String> testFile = FileUtils.readLines(file, "UTF-8");
    Track track = conferenceManagementService.processTrack(file);
    List<String> stringList1 = track.getMorningSession().getTalkList().stream().map(talk -> talk.getMessage()).collect(Collectors.toList());
    List<String> stringList2 = track.getAfternoonSession().getTalkList().stream().map(talk -> talk.getMessage()).collect(Collectors.toList());
    boolean isExist = stringList1.stream().anyMatch(s -> testFile.stream().anyMatch(t -> StringUtils.contains(s, t)));
    Assertions.assertTrue(isExist);
    isExist = stringList2.stream().anyMatch(s -> testFile.stream().anyMatch(t -> StringUtils.contains(s, t)));
    Assertions.assertTrue(isExist);
  }

  @Test
  void shouldBeDifferentMessageInTwoTrackIfIputNormalFileTest() throws IOException {
    File file = processFile("Test.txt");
    Track track1 = conferenceManagementService.processTrack(file);
    Track track2 = conferenceManagementService.processTrack(file);
    List<String> stringList1 = track1.getMorningSession().getTalkList().stream().map(talk -> talk.getMessage()).collect(Collectors.toList());
    List<String> stringList2 = track2.getMorningSession().getTalkList().stream().map(talk -> talk.getMessage()).collect(Collectors.toList());
    Assertions.assertNotEquals(stringList1, stringList2);
  }

  @Test
  void shouldNoWhitespaceIfIputFileHasBlankSpaceTest() throws IOException {
    File file = processFile("Test2.txt");
    Track track = conferenceManagementService.processTrack(file);
    List<String> stringList1 = track.getMorningSession().getTalkList().stream().map(talk -> talk.getMessage()).collect(Collectors.toList());
    List<String> stringList2 = track.getAfternoonSession().getTalkList().stream().map(talk -> talk.getMessage()).collect(Collectors.toList());
    Assertions.assertNotEquals("", stringList1);
    Assertions.assertNotEquals("", stringList2);
  }

  @Test
  void shouldTimeIsObtainedNormallyIfIputFileContentHasNumbersTest() throws IOException {
    File file = processFile("Test3.txt");
    Track track = conferenceManagementService.processTrack(file);
    List<Integer> times = track.getMorningSession().getTalkList().stream().map(talk -> talk.getTimes()).collect(Collectors.toList());
    List<LocalTime> time = track.getMorningSession().getTalkList().stream().map(talk -> talk.getTime()).collect(Collectors.toList());
    boolean isExist = times.stream().anyMatch(t -> StringUtils.equals(t.toString(), "60"));
    Assertions.assertTrue(isExist);
    isExist = times.stream().anyMatch(t -> StringUtils.equals(t.toString(), "20"));
    Assertions.assertFalse(isExist);
    isExist = times.stream().anyMatch(t -> StringUtils.equals(t.toString(), "6"));
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