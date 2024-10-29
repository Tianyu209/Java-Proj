package hk.ust.comp3021.lab;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;

import base.TextNote;
import org.junit.jupiter.api.Test;

import base.Note;
import base.NoteBook;

public class JUnitTest {

	@Test
	public void testSearchNote() {
		NoteBook nb = new NoteBook();
		nb.createTextNote("Note1", "Javascript", "comp3021");
		nb.createTextNote("Note2", "Assignment", "due on 2023-10-13");
		nb.createTextNote("Note3", "lab","need to attend weekly");
		nb.createTextNote("Note4", "lab7","testing");
		List<Note> notes = nb.searchNotes("java or DUE or testing");
		System.out.println(notes.size());
		assertEquals(3, notes.size(), "The size of the search results is not match");
		HashSet<String> titles = new HashSet<>();
		for (Note note : notes) {
			titles.add(note.getTitle());
		}
		HashSet<String> expectedOutputs = new HashSet<>();
		expectedOutputs.add("Javascript");
		expectedOutputs.add("Assignment");
		expectedOutputs.add("lab7");
		assertEquals(expectedOutputs, titles, "The search results is not match");
	}
	@Test
	public void testUnknownFunction() {
		TextNote testNote = new TextNote("banana","apple pie");
		char mostFrequentChar = testNote.unknownFunction();
		assertEquals('a', mostFrequentChar, "The most frequent character is incorrect");
	}
	@Test
	public void testUnknownFunctionWithTie() {
		TextNote testNote = new TextNote("abab","cdcd");
		char mostFrequentChar = testNote.unknownFunction();
		assertEquals('A', mostFrequentChar, "Results is not match.");
	}
	@Test
	public void testUnknownFunctionWithNumber() {
		TextNote testNote = new TextNote("11a1","2222a");
		char mostFrequentChar = testNote.unknownFunction();
		assertEquals('2', mostFrequentChar, "Results is not match.");
	}
	@Test
	public void testUnknownFunctionWithNull() {
		TextNote testNote = new TextNote("a","");
		char mostFrequentChar = testNote.unknownFunction();
		assertEquals('a', mostFrequentChar, "Results is not match.");
	}
	// To do
	// Design the second test case which reveals the bug in function unknownFunction()
}
