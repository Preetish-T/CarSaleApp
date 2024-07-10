package com.example.carsaleapp;

import static org.junit.Assert.*;

import com.example.carsaleapp.Backend.Grammar.IntExp;
import com.example.carsaleapp.Backend.Grammar.Parser;
import com.example.carsaleapp.Backend.Grammar.Tokenizer;

import org.junit.Test;

//  @author: Preetish Thirumalai u7157098
public class ParserTest {
    @Test
    public void lessThanTest() {
        String input = "< 40000";
        Tokenizer token = new Tokenizer(input);
        IntExp IE = new Parser(token).parseExp();
        int i = IE.getValue();
        assertEquals(i, 40000);
        assertTrue(IE.lessOrGreater());
    }
    public void greaterThanTest() {
        String input = "> 23450";
        Tokenizer token = new Tokenizer(input);
        IntExp IE = new Parser(token).parseExp();
        int i = IE.getValue();
        assertEquals(i, 23450);
        assertFalse(IE.lessOrGreater());
    }
    @Test
    public void invalidTest() {      //test an invalid output
        String input = "23000 <=";
        Tokenizer token = new Tokenizer(input);
        assertThrows(Parser.IllegalProductionException.class, () -> {
            Parser p = new Parser(token);
            p.parseExp();  //should raise an exception
        });
    }
    @Test
    public void emptyTest() {
        String input = "";
        Tokenizer token = new Tokenizer(input);
        assertThrows(Parser.IllegalProductionException.class, () -> {
            Parser p = new Parser(token);
            p.parseExp();  //should raise an exception
        });
    }

}