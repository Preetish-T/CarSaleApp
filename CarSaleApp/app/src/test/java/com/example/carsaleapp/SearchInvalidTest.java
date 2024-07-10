package com.example.carsaleapp;
import static org.junit.Assert.assertEquals;

import com.example.carsaleapp.Backend.SearchInvalid;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SearchInvalidTest {
    @Test
    public void CorrectWordsInDictionary() {
        // Test when all words in the query are already in the dictionary
        String query = "Manual;Petrol;Blue";
        ArrayList<String> expected  = new ArrayList<>();
        expected.add("Manual");
        expected.add("Petrol");
        expected.add("Blue");
        List<String> result = SearchInvalid.suggestCorrections(query);
        assertEquals(expected, result);
    }

    @Test
    public void SomeWordsNotInDictionary() {
        // Test when some words in the query are not in the dictionary
        String query = "Autto;Green;12;Western Australi";
        ArrayList<String> expected  = new ArrayList<>();
        expected.add("Auto");
        expected.add("Green");
        expected.add("12");
        expected.add("Western Australia");
        List<String> result = SearchInvalid.suggestCorrections(query);
        assertEquals(expected, result);
    }

    @Test
    public void EmptyQuery() {
        // Test when the query is empty
        String query = "";
        ArrayList<String> expected  = new ArrayList<>();
        expected.add("");
        List<String> result = SearchInvalid.suggestCorrections(query);
        assertEquals(expected, result);
    }

    @Test
    public void OnlyNumbers() {
        // Test when the query contains only numeric values
        String query = "123;456;789";
        ArrayList<String> expected  = new ArrayList<>();
        expected.add("123");
        expected.add("456");
        expected.add("789");
        List<String> result = SearchInvalid.suggestCorrections(query);
        assertEquals(expected, result);
    }

    @Test
    public void MixedQuery() {
        // Test when the query contains a mix of numeric values and words
        String query = "mnul;123;SUV;10;Victorrrrriiw";
        ArrayList<String> expected  = new ArrayList<>();
        expected.add("Manual");
        expected.add("123");
        expected.add("SUV");
        expected.add("10");
        expected.add("Victoria");
        List<String> result = SearchInvalid.suggestCorrections(query);
        assertEquals(expected, result);
    }


}