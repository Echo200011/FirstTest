package model;

import lombok.Data;

import java.util.List;
@Data
public class Session {
    private List<Talk> talkList;

    private String timeFrame;

    public Session(List<Talk> talkList,String timeFrame){
        this.talkList=talkList;
        this.timeFrame=timeFrame;
    }
}
