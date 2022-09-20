package com.example.sping_portfolio;

import java.lang.reflect.Array;
import java.lang.Math;

public class Abacus {
    
    public int[][] abacusArray;
    /* Since were gonna recieve input in abacus notation, we make the abacus array public and the decimal private
     * Decimal is private because if the abacus array and decimal are updated at the same time there will be errors
     * Abacus notation is 12 columns witn row[0] being the top row and row[1] being the bottom row*/
    private int decimal;

    // Returns the deciimal value of the abacusArray so we can do real math with it and check answers
    public void getDecimal(int[][] abacusArray) {
        
        int add = 0;    // The amount we add per level of significance
        decimal = 0;    // The decimal value of the array

        for (int i = abacusArray.length; i > 0; i--) {
            
            if (abacusArray[i][0] == 1) {
                add = 5;
            }
            
            System.out.print(add);
        }
    }

}
