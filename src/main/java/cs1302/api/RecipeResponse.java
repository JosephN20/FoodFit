package cs1302.api;

/**
 * Represents a response from the 
 * Spoonacular Recipe - Food - Nutrition AP API. 
 * This is used by Gson to
 * create an object from the JSON response body. 
 */
public class RecipeResponse {
    String id;
    String title;
    String image;
    String calories;
    String protein;
    String fat;
    String carbs;

}
