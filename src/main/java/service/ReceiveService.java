package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Talk;
import org.apache.commons.io.FileUtils;

public class ReceiveService {

  public List<Talk> processData(String path) throws IOException {
    Pattern pattern = Pattern.compile("[^0-9]");
    File file = new File(path);
    List<String> message = FileUtils.readLines(file, "UTF-8");
    List<Talk> talkList = new ArrayList<>();
    message.forEach(m -> {
      Matcher matcher = pattern.matcher(m);
      Talk talk = new Talk(m, Integer.parseInt(matcher.replaceAll("").trim()));
      talkList.add(talk);
    });

    return talkList;
  }
}
