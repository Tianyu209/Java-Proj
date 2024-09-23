package base;

import java.util.ArrayList;

public class Folder {
    private ArrayList<Note> notes;
    private String name;
    public Folder(String name){
        this.name = name;
    }
    public void addNote(Note n){
        notes.add(n);
    }
    public String getName(){
        return this.name;
    }
    public ArrayList<Note> getNotes(){
        return this.notes;
    }
    public String toString(){
        int nText = 0;
        int nImage = 0;
        for(Note n:notes){
            if(n instanceof ImageNote){

            }
        }
        return name + ':' + nText + ':' + nImage;
    }
    public boolean equals(Folder f){
        return (f.name.equals(this.name));
    }



}
