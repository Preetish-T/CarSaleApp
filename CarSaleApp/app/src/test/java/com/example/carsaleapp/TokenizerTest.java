package com.example.carsaleapp;

import static org.junit.Assert.*;

import com.example.carsaleapp.Backend.Grammar.Tokenizer;

import org.junit.Test;

//  @author: Preetish Thirumalai u7157098
public class TokenizerTest {
    @Test
    public void testNext() {
        String input = "> 40000";
        Tokenizer t = new Tokenizer(input);
        t.next();
        assertEquals(t.current().getToken(), "40000");
    }
    @Test
    public void testNext2() {
        String input = "< 40000";
        Tokenizer t = new Tokenizer(input);
        t.next();
        assertEquals(t.current().getToken(), "40000");
    }
    @Test
    public void testInvalid() {     //invalid for the parser, but it should still work for the tokenizer
        String input = "40000 <";
        Tokenizer t = new Tokenizer(input);
        t.next();
        assertNull(t.current());
    }
    @Test
    public void testEmpty() {
        String input = "";
        Tokenizer t = new Tokenizer(input);
        assertNull(t.current());
    }

}