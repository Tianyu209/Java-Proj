package base;
import java.util.Date;
import java.util.Objects;

public class Note {
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

    private Date date;
    private String title;
    public Note (String title){
        this.title = title;
        this.date = new Date();
    }
    public String getTitle(){
        return this.title;
    }

}
