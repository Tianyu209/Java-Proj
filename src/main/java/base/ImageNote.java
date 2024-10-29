package base;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;

public class ImageNote extends Note implements Serializable {
    File image;
    Icon icon;
    @Serial
    private static final long serialVersionUID = 1L;

    public ImageNote(String title) {
        super(title);
        char firstCharacter = title.charAt(0);
        if ('a' <= firstCharacter && firstCharacter<='z'){
            this.icon = new IconLowerCase(firstCharacter);
        }
        else if ('A' <= firstCharacter && firstCharacter<='Z'){
            this.icon = new IconUpperCase(firstCharacter);
        } else if ('0' <= firstCharacter && firstCharacter<='9') {
            this.icon = new IconDigit(firstCharacter);
        }
    }
    public ImageNote (ImageNote note) {
        super(note);
        this.icon = note.icon;
    }
    @Override
    public String toString(){
        return "ImageNote:"+icon + "" + super.toString();

    }
}

