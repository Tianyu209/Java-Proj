package base;

public class TextNote extends Note{
    String content;
    public TextNote(String title){
        super(title);
        content = "";
    }
    public TextNote(String title, String content){
        super(title);
        this.content = content;
    }
    public String toString(){
        String res = "TextNote:" + super.toString();
        if (content != null && content.contains(".")){
            res += "\t";
            res += content.substring(0,Math.min(30, content.indexOf(".")));
            return res;
        }
        return res;

    }

}
