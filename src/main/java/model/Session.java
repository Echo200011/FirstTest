package model;

import lombok.Data;

import java.util.List;
@Data
public class Session {
    private List<Talk> talkList;

    public Session(List<Talk> talkList){
        this.talkList=talkList;
    }
}
