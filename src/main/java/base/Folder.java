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

    @Override
    public String toString() {
        return "Folder{" +
                "notes=" + notes +
                ", name='" + name + '\'' +
                '}';
    }

    public boolean equals(Folder f){
        return (f.name.equals(this.name));
    }



}
