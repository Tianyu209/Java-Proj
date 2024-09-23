package base;
import java.util.Date;
public class Note {
    private Date date;
    private String title;
    public Note (String title){
        this.title = title;
        this.date = new Date();
    }
    public String getTitle(){
        return this.title;
    }
    public boolean equals(Note n){
        if(n == null) return false;
        return( n.title.equals(this.title));
    }
}
