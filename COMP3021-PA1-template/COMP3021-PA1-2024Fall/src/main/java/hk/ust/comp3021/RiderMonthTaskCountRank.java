package hk.ust.comp3021;

public class RiderMonthTaskCountRank implements TaskRank{
    public int compare(Task t1,Task t2){
        return Integer.compare(t1.rider.monthTaskCount,t2.rider.monthTaskCount);
    };
}
