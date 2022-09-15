package com.example.sping_portfolio;

import java.lang.reflect.Array;
import java.lang.Math;

public class Abacus {
    
    public int[][] abacusArray = {{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};
    /* Since were gonna recieve input in abacus notation, we make the abacus array public and the decimal private
     * Decimal is private because if the abacus array and decimal are updated at the same time there will be errors
     * Abacus notation is 12 columns witn row[0] being the top row and row[1] being the bottom row*/
    private int decimal;

    // Returns the deciimal value of the abacusArray so we can do real math with it and check answers
    public void getDecimal(int[][] abacusArray) {
        
        int total;  // This is the total per 
        decimal = 0;

        for (int i = abacusArray.length; i > 0; i--) {
            
            if (abacusArray[i][0] == 1) {
                total = 5;
            }
            
            total += abacusArray[i][1];
            decimal += total * Math.pow(10,Math.abs(i-11));
        }
    }

}
