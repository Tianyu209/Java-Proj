package base;
import java.util.Date;
import java.util.Objects;

public class Note implements Comparable<Note>{
    private Date date;
    private String title;

    public Note (String title){
        this.title = title;
        this.date = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(title, note.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title);
    }



    public String getTitle(){
        return this.title;
    }

    @Override
    public int compareTo(Note o) {
        return o.date.compareTo(this.date);
    }
    @Override
    public String toString(){
        return date.toString() + "\t" + title;
    }
}
