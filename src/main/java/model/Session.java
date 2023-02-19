package model;

import lombok.Data;

import java.util.List;
@Data
public class Session {
    private List<Talk> planList;

    private String timeFrame;

    public Session(List<Talk> planList,String timeFrame){
        this.planList=planList;
        this.timeFrame=timeFrame;
    }
}
