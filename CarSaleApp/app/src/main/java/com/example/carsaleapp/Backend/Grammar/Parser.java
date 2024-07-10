package com.example.carsaleapp.Backend.Grammar;

/**
 * Parser takes a bunch of tokens and evaluates them based on their inferred type
 */
// @author: Preetish Thirumalai u7157098

/** Grammar ->
 *  * <exp>   ::=  <compare> <price>
 *  * <price> ::=  <unsigned integer>
 *  * <compare>  ::=  "<" | ">"
 */
// @author: Preetish Thirumalai u7157098

public class Parser {
    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }

    Tokenizer tokenizer;

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public IntExp parseExp() {
        if (!tokenizer.hasNext()) {
            throw new Parser.IllegalProductionException("fail");
        }
        //if it is greater or less than sign, update the boolean
        //than add the value to int exp
        if (tokenizer.current().getType() == Token.Type.CHAR) {
            if (tokenizer.current().toString().equals("<")) {
                boolean b = true;
                tokenizer.next();
                return new IntExp(Integer.parseInt(tokenizer.current().toString()), b);
            }
            if (tokenizer.current().toString().equals(">")) {
                boolean b = false;
                tokenizer.next();
                return new IntExp(Integer.parseInt(tokenizer.current().toString()), b);
            }
        }
        throw new Parser.IllegalProductionException("fail");
    }
}