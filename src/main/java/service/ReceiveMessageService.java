package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Talk;
import org.apache.commons.io.FileUtils;

public class ReceiveMessageService {

  public List<Talk> processData(String path) throws IOException {
    Pattern pattern = Pattern.compile("[^0-9]");
    List<Talk> talkList = new ArrayList<>();
    getMessage(path).forEach(m -> talkList.add(initTalk(m, pattern)));
    return talkList;
  }

  private List<String> getMessage(String path) throws IOException {
    File file = new File(path);
    return FileUtils.readLines(file, "UTF-8");
  }

  private Talk initTalk(String message, Pattern pattern) {
    Matcher matcher = pattern.matcher(message);
    return new Talk(message, Integer.parseInt(matcher.replaceAll("").trim()));
  }
}
