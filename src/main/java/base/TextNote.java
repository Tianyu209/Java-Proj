package base;

import java.io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
public class TextNote extends Note implements Serializable, Iconifiable{
    @Override
    public void iconify() {
        char firstCharacter = content.charAt(0);
        if ('a' <= firstCharacter && firstCharacter<='z'){
            content = new IconLowerCase(firstCharacter).base + content.substring(1);
        }
        else if ('A' <= firstCharacter && firstCharacter<='Z'){
            content = new IconUpperCase(firstCharacter).base + content.substring(1);
        } else if ('0' <= firstCharacter && firstCharacter<='9') {
            content = new IconDigit(firstCharacter).base + content.substring(1);
        }
    }
    String content;
    Icon icon;
    @Serial
    private static final long serialVersionUID = 1L;

    public TextNote(String title){
        super(title);
        content = "";
        this.icon = null;
    }
    public TextNote(String title, String content){
        super(title);
        this.content = content;
    }
    public TextNote() {
        super();
        this.content = "";
    }
    public TextNote (TextNote note) {
        super(note);
        this.content = note.content;
    }

    public TextNote(File f ) throws IOException {
        super(f.getName());
        this.content = getTextFromFile(f.getAbsolutePath());

    }

    private String getTextFromFile(String absolutePath) throws IOException {
        StringBuilder res = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                res.append(line).append(System.lineSeparator());
            }
        }
        return res.toString();
    }
    public void exportTextToFile(String pathFolder) throws IOException {
        String fileName = super.getTitle().replaceAll(" ", "_") ;
        File file = new File(pathFolder + File.separator + fileName + ".txt");
        try (BufferedWriter w = new BufferedWriter(new FileWriter(file))) {
            w.write(this.content);
        }
    }

    @Override
    public String toString(){
        String res = "TextNote:" + super.toString();
        iconify();
        if (content != null && content.contains(".")){
            res += "\t";
            res += content.substring(0,Math.min(30, content.indexOf(".")));
            return res;
        }
        return res;

    }



}
