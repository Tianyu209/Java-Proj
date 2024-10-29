package base;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Folder implements Comparable<Folder>  , Serializable, Cloneable {
    private ArrayList<Note> notes;
    private String name;
    @Serial
    private static final long serialVersionUID = 1L;


    public Folder(String name) {
        this.name = name;
        this.notes = new ArrayList<>();
    }

    public void addNote(Note n) {
        notes.add(n);
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Note> getNotes() {
        return this.notes;
    }
    @Override
    public Folder clone(){
        try {
            Folder clone = (Folder) super.clone();
            clone.notes = new ArrayList<>();
            for (Note note: notes) {
                Note newNote;
                if (note instanceof TextNote) newNote = new TextNote((TextNote) note);
                else newNote = new ImageNote((ImageNote) note);
                clone.notes.add(newNote);
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    @Override
    public String toString() {
        int nText = 0;
        int nImage = 0;
        for (Note n : notes) {
            if (n instanceof ImageNote) nImage++;
            if (n instanceof TextNote) nText++;
        }
        return name + ':' + nText + ':' + nImage;
    }

    public boolean equals(Folder f) {
        return (f.name.equals(this.name));
    }

    public void sortNotes() {
        Collections.sort(notes);
    }

    @Override
    public int compareTo(Folder f) {
        return this.name.compareTo(f.name);
    }

    public List<Note> searchNotes(String keywords) {
        List<Note> result = new ArrayList<>();
        ArrayList<ArrayList<String>> patterns = new ArrayList<>();

        String[] parts = keywords.split(" ");
        int i = 0;
        while (i < parts.length) {
            if (parts[i].equalsIgnoreCase("or")) {
                i++;
                if (i < parts.length) {
                    patterns.get(patterns.size() - 1).add(parts[i].toLowerCase());
                }
            } else {
                ArrayList<String> newArr = new ArrayList<>();
                newArr.add(parts[i].toLowerCase());
                patterns.add(newArr);
            }
            i++;
        }

        for (Note note : notes) {
            String toBeSearched = note.getTitle().toLowerCase();

            if (note instanceof TextNote) {
                toBeSearched += ((TextNote) note).content.toLowerCase();
            }

            boolean matches = true;

            for (ArrayList<String> pattern : patterns) {
                boolean foundPattern = false;
                for (String keyword : pattern) {
                    if (toBeSearched.contains(keyword)) {
                        foundPattern = true;
                        break;
                    }
                }
                if (!foundPattern) {
                    matches = false;
                    break;
                }
            }

            if (matches) {
                result.add(note);
            }
        }

        return result;
    }


}

