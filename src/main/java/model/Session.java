package model;

import lombok.Data;

import java.util.List;
@Data
public class Session {
    private List<Plan> planList;

    private String timeFrame;

    public Session(List<Plan> planList,String timeFrame){
        this.planList=planList;
        this.timeFrame=timeFrame;
    }
}
