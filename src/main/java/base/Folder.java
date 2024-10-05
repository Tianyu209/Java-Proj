package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Folder implements Comparable<Folder> {
    private ArrayList<Note> notes;
    private String name;

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
        String[] parts = keywords.split(" ");
        List<Note> result = new ArrayList<>();
        for (Note note : notes) {
            boolean state = true;
            boolean matches = false;
            boolean orCondition = false;

            for (String part : parts) {
                if (part.equalsIgnoreCase("or")) {
                    orCondition = true;
                    continue;
                }
                boolean partMatches;
                if (note instanceof ImageNote) {
                    partMatches = note.getTitle().toLowerCase().contains(part.toLowerCase());
                } else {
                    partMatches = note.getTitle().toLowerCase().contains(part.toLowerCase()) ||
                            ((TextNote) note).content.toLowerCase().contains(part.toLowerCase());
                }
                if (orCondition) {
                    state = state || partMatches;
                    orCondition = false;
                } else {
                    state = state && partMatches;
                }
            }

            if (state) {
                matches = true;
            }

            if (matches) {
                result.add(note);
            }
        }
        return result;
    }

}

