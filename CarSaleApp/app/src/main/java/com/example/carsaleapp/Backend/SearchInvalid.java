package com.example.carsaleapp.Backend;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a method to suggest corrections for a query based on Levenshtein distance.
 * @author Zhangheng Xu u6818456
 */
public class SearchInvalid {

    // Create a dictionary to store correct word spellings
    private static final ArrayList<String> dictionary = new ArrayList<>();

    // Initialize the dictionary with correct words.

    static {
        // empty for unexpected input
        dictionary.add("");
        // operation
        dictionary.add(">");
        dictionary.add("<");
        dictionary.add("=");
        // gear box
        dictionary.add("Manual");
        dictionary.add("Auto");
        // body type
        dictionary.add("MPV");
        dictionary.add("SUV");
        dictionary.add("Sports Car");
        dictionary.add("Lorry");
        dictionary.add("Convertible");
        dictionary.add("Pick-up");
        dictionary.add("Three-box car");
        dictionary.add("Passenger Cars");
        dictionary.add("Station Wagon");
        dictionary.add("Hatchback");
        dictionary.add("Two-vehicle");
        dictionary.add("Toyota");
        dictionary.add("toyota");
        dictionary.add("Jeep");
        dictionary.add("jeep");
        dictionary.add("tesla");
        dictionary.add("Tesla");
        dictionary.add("Suzuki");
        dictionary.add("suzuki");
        dictionary.add("Honda");
        dictionary.add("honda");
        // Fuel type
        dictionary.add("Electric");
        dictionary.add("Hybrid");
        dictionary.add("Petrol");
        dictionary.add("Diesel");
        dictionary.add("Other");
        // Location
        dictionary.add("Western Australia");
        dictionary.add("New South Wales");
        dictionary.add("Victoria");
        dictionary.add("Tasmania");
        dictionary.add("Northern Territory");
        dictionary.add("Australian Capital Territory");
        dictionary.add("South Australia");
        dictionary.add("Queensland");
        // Colour
        dictionary.add("Blue");
        dictionary.add("Black");
        dictionary.add("Multicolour");
        dictionary.add("Champagne");
        dictionary.add("Coffee");
        dictionary.add("Orange");
        dictionary.add("DarkGrey");
        dictionary.add("Red");
        dictionary.add("Silver");
        dictionary.add("White");
        dictionary.add("Yellow");
        dictionary.add("Purple");
        dictionary.add("Green");
        dictionary.add("Grey");
    }

    /**
     * Calculates the Levenshtein distance between two words.
     *
     * @param word1 The first word.
     * @param word2 The second word.
     * @return The Levenshtein distance between the two words.
     * @author Zhangheng Xu, u6818456
     */
    private static int levenshteinDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        // Create a 2D array to store Levenshtein distances
        int[][] dp = new int[m + 1][n + 1];

        // Fill in the dynamic programming array
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    // Calculate the cost of a character match/mismatch
                    int cost = (word1.charAt(i - 1) == word2.charAt(j - 1)) ? 0 : 1;
                    // Calculate the minimum of insertion, deletion, or substitution
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
                }
            }
        }

        return dp[m][n]; // Return the final Levenshtein distance
    }

    /**
     * Suggests corrections for a query.
     *
     * @param query The input query.
     * @return A list of suggested corrections.
     * @author Zhangheng Xu, u6818456
     */
    public static List<String> suggestCorrections(String query) {
        // Tokenize the query into words using the semicolon as a delimiter
        String[] queryTokens = query.split(";");

        // Create a list to store the corrected query
        List<String> correctedQuery = new ArrayList<>();

        // For each token in the query
        for (String token : queryTokens) {
            // Check if the token is a number (numeric value)
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                // If it's a number, leave it as is
                correctedQuery.add(token);
            } else {
                // Otherwise, proceed with Levenshtein distance calculation
                int minDistance = Integer.MAX_VALUE;
                String suggestedWord = token;

                // Compare the token with words in the dictionary
                for (String word : dictionary) {
                    int distance = levenshteinDistance(token, word);

                    // If the current word is closer, update the suggestion
                    if (distance < minDistance) {
                        minDistance = distance;
                        suggestedWord = word;
                    }
                }

                // Add the suggested word to the corrected query
                correctedQuery.add(suggestedWord);
            }
        }
        return correctedQuery; // Return the list of suggested corrections
    }
}
