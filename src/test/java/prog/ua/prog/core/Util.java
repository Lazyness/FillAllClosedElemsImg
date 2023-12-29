package prog.ua.prog.core;

import prog.ua.prog.core.model.ImageInPixels;

public class Util {
    private Util() {
    }

    public static int[][] getArrayFromString(String strTwoDimensionalArray, ImageInPixels imageInPixels) {
        int[][] twoDimensionalArray = new int[imageInPixels.getHeight()][imageInPixels.getWidth()];

        String[] splitStrTwoDimensionalArray = strTwoDimensionalArray.split("\n");
        String[] tempSplitStrTwoDimensionalArrayWithoutSpaces = new String[splitStrTwoDimensionalArray.length];

        for (int i = 0; i < splitStrTwoDimensionalArray.length; i++) {
            tempSplitStrTwoDimensionalArrayWithoutSpaces[i] = splitStrTwoDimensionalArray[i].trim();
        }

        for (int i = 0; i < imageInPixels.getHeight(); i++) {
            for (int j = 0; j < imageInPixels.getWidth(); j++) {
                int intValuePixel = tempSplitStrTwoDimensionalArrayWithoutSpaces[i].charAt(j) - 48;
                twoDimensionalArray[i][j] = intValuePixel;
            }
        }
        return twoDimensionalArray;
    }
}
