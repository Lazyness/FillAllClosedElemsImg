package prog.ua.logic;

import prog.ua.exceptions.ExceptionNotFoundIndex;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.HashMap;

public class Logic {

    private static int width;
    private static int height;
    private static int[][] arrayPixel;
    private static final int COUNT_OBJECT = 3;
    private static HashMap<Integer, String>[] hashMapStoreContour;

    private static void initializerHashMapArray() {
        hashMapStoreContour = new HashMap[COUNT_OBJECT];
        for (int i = 0; i < COUNT_OBJECT; i++)
            hashMapStoreContour[i] = new HashMap<>();
    }

    public static int[][] getArrayPixel() {
        return arrayPixel;
    }

    public static int[] getPixels(BufferedImage image) {
        width = image.getWidth();
        height = image.getHeight();
        int numPixels = width * height;
        int[] rawPixels = new int[numPixels];

        PixelGrabber grabber = new PixelGrabber(image, 0, 0, width, height, rawPixels, 0, width);
        try {
            grabber.grabPixels();
        } catch (InterruptedException e) {
            //do nothing
        }
        return rawPixels;
    }

    public static int[][] getModifierBlackWhitePixels(int[] pixels) {
        arrayPixel = new int[height][width];
        for (int x = 0; x < arrayPixel.length; x++) {
            for (int y = 0; y < arrayPixel[x].length; y++) {
                if (pixels[x * width + y] == -1) {
                    arrayPixel[x][y] = 0;
                } else if (pixels[x * width + y] == -16777216) {
                    arrayPixel[x][y] = 1;
                }
            }
        }
        return arrayPixel;
    }

    public static boolean brokerContours(int i, int j, int countObject) throws ExceptionNotFoundIndex {
        if (countObject > 1) {
            int l = COUNT_OBJECT + 1 - countObject;
            for (int k = 0; k < hashMapStoreContour.length - l; k++) {
                if (arrayPixel[i][j] != 0) {
                    for (int q = 1; q <= hashMapStoreContour[k].size(); q++) {
                        if (i == returnIndex(hashMapStoreContour[k].get(q), IndexEnum.FIRST)
                                && j == returnIndex(hashMapStoreContour[k].get(q), IndexEnum.SECOND)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static void searchObjectImage() throws ExceptionNotFoundIndex {
        initializerHashMapArray();
        int countObject = 1;
        for (HashMap<Integer, String> hashMap : hashMapStoreContour) {
            int counter = 0;
            int j = 0;
            int i = 0;
            boolean flag = false;
            FIRST:
            while (i < arrayPixel.length) {
                while (j < arrayPixel[i].length) {
                    //проверка на уже занятые пиксели
                    if (brokerContours(i, j, countObject)) {
                        // установка курсора на начало контура
                        if (arrayPixel[i][j] == 1) {
                            hashMap.put(++counter, Integer.toString(i) + '|' + j);
                            if (counter == 1) {
                                if (arrayPixel[i][j + 1] == 1) {
                                    j++;
                                    continue;
                                }
//                                //влево
                                if (arrayPixel[i][j - 1] == 1) {
                                    j--;
                                    continue;
                                }
//                                //вниз
                                if (arrayPixel[i + 1][j] == 1) {
                                    i++;
                                    break;
                                }
//
//                                //вверх
                                if (arrayPixel[i - 1][j] == 1) {
                                    i--;
                                    break;
                                }
                            }
                            if (!flag) flag = true;
                            if (counter > 1 && !hashMap.get(counter).equals(hashMap.get(counter - 1))) {
//                                //вправо
                                if (arrayPixel[i][j + 1] == 1
                                ) {
                                    if (returnIndex(hashMap.get(1), IndexEnum.FIRST) == i &&
                                            returnIndex(hashMap.get(1), IndexEnum.SECOND) == j + 1) {
                                        System.out.println("Contour is successful find!" + hashMap);
                                        arrayPixel[i][j] = countObject;
                                        arrayPixel[i][j + 1] = countObject;
                                        ++countObject;
                                        break FIRST;
                                    }
                                    if (checkPixelInContour(i, j + 1, hashMap)) {
                                        arrayPixel[i][j] = countObject;
                                        j++;
                                        continue;
                                    }
                                }

                                //вниз
                                if (arrayPixel[i + 1][j] == 1
                                ) {
                                    if (returnIndex(hashMap.get(1), IndexEnum.FIRST) == i + 1 &&
                                            returnIndex(hashMap.get(1), IndexEnum.SECOND) == j) {
                                        System.out.println("Contour is successful find!" + hashMap);
                                        arrayPixel[i][j] = countObject;
                                        arrayPixel[i + 1][j] = countObject;
                                        ++countObject;
                                        break FIRST;
                                    }
                                    if (checkPixelInContour(i + 1, j, hashMap)) {
                                        arrayPixel[i][j] = countObject;
                                        i++;
                                        break;
                                    }
                                }

                                //влево
                                if (arrayPixel[i][j - 1] == 1
                                ) {
                                    if (returnIndex(hashMap.get(1), IndexEnum.FIRST) == i &&
                                            returnIndex(hashMap.get(1), IndexEnum.SECOND) == j - 1) {
                                        System.out.println("Contour is successful find!" + hashMap);
                                        arrayPixel[i][j] = countObject;
                                        arrayPixel[i][j - 1] = countObject;
                                        ++countObject;
                                        break FIRST;
                                    }
                                    if (checkPixelInContour(i, j - 1, hashMap)) {
                                        arrayPixel[i][j] = countObject;
                                        j--;
                                        continue;
                                    }
                                }

                                //вверх
                                if (arrayPixel[i - 1][j] == 1
                                ) {
                                    if (returnIndex(hashMap.get(1), IndexEnum.FIRST) == i - 1 &&
                                            returnIndex(hashMap.get(1), IndexEnum.SECOND) == j) {
                                        System.out.println("Contour is successful find!" + hashMap);
                                        arrayPixel[i][j] = countObject;
                                        arrayPixel[i - 1][j] = countObject;
                                        ++countObject;
                                        break FIRST;
                                    }
                                    if (checkPixelInContour(i - 1, j, hashMap)) {
                                        arrayPixel[i][j] = countObject;
                                        i--;
                                        break;
                                    }

                                }
                            }
                        }
                        if (!flag) j++;
                    } else break;
                }
                if (!flag) {
                    i++;
                    j = 0;
                }
            }
        }
    }

    public static int returnIndex(String value, IndexEnum indexEnum) throws ExceptionNotFoundIndex {
        String[] arrayIndex = value.split("\\|");
        switch (indexEnum) {
            case FIRST: {
                return Integer.parseInt(arrayIndex[0]);
            }
            case SECOND: {
                return Integer.parseInt(arrayIndex[1]);
            }
            default: {
                throw new ExceptionNotFoundIndex("You write not found index.");
            }
        }
    }

    public static boolean checkPixelInContour(int varFuture1, int varFuture2, HashMap<Integer, String> hashMap) throws ExceptionNotFoundIndex {
        for (int i = 1; i < hashMap.size(); i++) {
            if (varFuture1 == returnIndex(hashMap.get(i), IndexEnum.FIRST) &&
                    varFuture2 == returnIndex(hashMap.get(i), IndexEnum.SECOND)) return false;
        }
        return true;
    }

    public static int[][] fillImageColor() throws ExceptionNotFoundIndex {
        int[][] newArray = deepCopyArray();
        for (int q = 0; q < hashMapStoreContour.length; q++)
            for (int i = searchMin(IndexEnum.FIRST, hashMapStoreContour[q]); i < searchMax(IndexEnum.FIRST, hashMapStoreContour[q]); i++) {
                for (int j = searchMinIForJ(i, hashMapStoreContour[q]); j < searchMaxIForJ(i, hashMapStoreContour[q]); j++) {
                    if (newArray[i][j] != 1) {
                        newArray[i][j] = q + 1;
                    }
                }
            }
        return newArray;
    }

    public static int[][] deepCopyArray() {
        int[][] newArray = new int[height][width];
        for (int i = 0; i < newArray.length; i++)
            System.arraycopy(arrayPixel[i], 0, newArray[i], 0, newArray[i].length);
        return newArray;
    }

    public static int searchMinIForJ(int index, HashMap<Integer, String> hashMapStoreContour) throws ExceptionNotFoundIndex {
        int min = searchMax(IndexEnum.SECOND, hashMapStoreContour);
        for (int i = 1; i <= hashMapStoreContour.size(); i++) {
            if (returnIndex(hashMapStoreContour.get(i), IndexEnum.FIRST) == index) {
                if (min > returnIndex(hashMapStoreContour.get(i), IndexEnum.SECOND))
                    min = returnIndex(hashMapStoreContour.get(i), IndexEnum.SECOND);
            }
        }
        return min;
    }

    public static int searchMaxIForJ(int index, HashMap<Integer, String> hashMapStoreContour) throws ExceptionNotFoundIndex {
        int max = searchMin(IndexEnum.SECOND, hashMapStoreContour);
        for (int i = 1; i <= hashMapStoreContour.size(); i++) {
            if (returnIndex(hashMapStoreContour.get(i), IndexEnum.FIRST) == index) {
                if (max < returnIndex(hashMapStoreContour.get(i), IndexEnum.SECOND))
                    max = returnIndex(hashMapStoreContour.get(i), IndexEnum.SECOND);
            }
        }
        return max;
    }

    public static int searchMax(IndexEnum indexEnum, HashMap<Integer, String> hashMapStoreContour) throws ExceptionNotFoundIndex {
        int max = returnIndex(hashMapStoreContour.get(1), indexEnum);
        for (int i = 1; i <= hashMapStoreContour.size(); i++) {
            if (max < returnIndex(hashMapStoreContour.get(i), indexEnum))
                max = returnIndex(hashMapStoreContour.get(i), indexEnum);
        }
        return max;
    }

    public static int searchMin(IndexEnum indexEnum, HashMap<Integer, String> hashMapStoreContour) throws ExceptionNotFoundIndex {
        int min = returnIndex(hashMapStoreContour.get(1), indexEnum);
        for (int i = 1; i <= hashMapStoreContour.size(); i++) {
            if (min > returnIndex(hashMapStoreContour.get(i), indexEnum))
                min = returnIndex(hashMapStoreContour.get(i), indexEnum);
        }
        return min;
    }
}
