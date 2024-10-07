package hk.ust.comp3021;

public class RiderMonthTaskCountRank implements TaskRank{
    public int compare(Task t1,Task t2){
        return t1.rider.monthTaskCount.compareTo(t2.rider.monthTaskCount);
    };
}
