package base;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteBook {
    private ArrayList<Folder> folders;
    public NoteBook(){
        folders = new ArrayList<>();
    }
    public boolean insertNote(String folderName,Note note){

        for(Folder f:folders){

            if(f.getName().equals(folderName)){
                for(Note n:f.getNotes()){
                    if(n.getTitle().equals(note.getTitle())){
                        System.out.println("Creating note " + note.getTitle() + " under folder " + folderName + " failed.");
                        return false;
                    }
                }
                f.addNote(note);
                return true;
            }

        }

        Folder newFolder = new Folder(folderName);
        newFolder.addNote(note);
        folders.add(newFolder);
        return true;
    }
    public boolean createTextNote(String folderName,String title){
       TextNote note = new TextNote(title);
       return insertNote(folderName,note);
    }
    public boolean createTextNote(String folderName,String title, String content){
        TextNote note = new TextNote(title,content);
        return insertNote(folderName,note);
    }
    public boolean createImageNote(String folderName,String title){
    ImageNote note = new ImageNote(title);
    return insertNote(folderName,note);
    }
    public ArrayList<Folder> getFolders(){
        return this.folders;
    }
    public void sortFolders(){
        for ( Folder folder : folders){
            folder.sortNotes();
        }
        Collections.sort(folders);
    }

    public List<Note> searchNotes(String s) {
        List<Note> result = new ArrayList<>();
        for (Folder folder : folders) {
            result.addAll(folder.searchNotes(s));
        }
        return result;
    }

}
