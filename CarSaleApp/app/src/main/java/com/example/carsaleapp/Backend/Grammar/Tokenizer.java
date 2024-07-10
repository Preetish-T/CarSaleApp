package com.example.carsaleapp.Backend.Grammar;

public class Tokenizer {
    private String buffer;          
    private Token currentToken;

    // @author: Preetish Thirumalai u7157098
    // Grammar to query cars based on price

    /** Grammar ->
     *  * <exp>   ::=  '<' 'price' | '>' 'price'
     *  * <less>  ::=  '<'
     *  * <more>  ::=  '>'
     *  * <price> ::=  <unsigned integer>
     */

    public static class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String errorMessage) {
            super(errorMessage);
        }
    }

    public Tokenizer(String text) {
        buffer = text;
        next();
    }

    public void next() {
        buffer = buffer.trim();

        if (buffer.isEmpty()) {
            currentToken = null;
            return;
        }

        char charred = buffer.charAt(0);        //first char

        if (charred == '<') {
            currentToken = new Token(String.valueOf(charred), Token.Type.CHAR);
            buffer = buffer.substring(1);
        }

        if (charred == '>') {
            currentToken = new Token(String.valueOf(charred), Token.Type.CHAR);
            buffer = buffer.substring(1);
        }
        if (Character.isDigit(charred)) {
            currentToken = new Token(buffer, Token.Type.STRING);
            buffer = "";
        }
    }
            public Token current () {
                return currentToken;
            }

            public boolean hasNext () {
                return currentToken != null;
    }
}


