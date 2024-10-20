package base;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;

public class ImageNote extends Note implements Serializable {
    File image;
    @Serial
    private static final long serialVersionUID = 1L;

    public ImageNote(String title) {
        super(title);
    }

    public String toString(){
        return "ImageNote:" + super.toString();

    }
}

