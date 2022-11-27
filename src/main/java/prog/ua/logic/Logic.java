package prog.ua.logic;

import prog.ua.exceptions.ExceptionNotFoundIndex;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;
import java.util.HashMap;

public class Logic {

    private static int width;
    private static int height;
    private static int[][] arrayPixel;
    private static final int COUNT_OBJECT = 3;
    private static HashMap<Integer, String>[] hashMapStoreContour;
    private static int countObjectInc = 1;

    private static void initializerHashMapArray() {
        hashMapStoreContour = new HashMap[COUNT_OBJECT];
        for (int i = 0; i < COUNT_OBJECT; i++)
            hashMapStoreContour[i] = new HashMap<>();
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

    private static boolean brokerContours(int i, int j) throws ExceptionNotFoundIndex {
        if (countObjectInc > 1) {
            int l = COUNT_OBJECT + 1 - countObjectInc;
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

    private static int searchPixelContour(int constUpOrDown, int constLeftOrRight,
                                          int upOrDown, int leftOrRight,
                                          int counter, HashMap<Integer, String> hashMap) throws ExceptionNotFoundIndex {
        if (arrayPixel[upOrDown][leftOrRight] == 1) {
            if (counter > 1) {
                if (searchEndSearch(constUpOrDown, constLeftOrRight, upOrDown, leftOrRight, hashMap)) return 0;
                if (checkPixelInContour(upOrDown, leftOrRight, hashMap)) {
                    arrayPixel[constUpOrDown][constLeftOrRight] = countObjectInc;
                    return 1;
                }
            } else {
                return 1;
            }
        }
        return -1;
    }

    private static boolean searchEndSearch(int constUpOrDown, int constLeftOrRight,
                                           int upOrDown, int leftOrRight,
                                           HashMap<Integer, String> hashMap) throws ExceptionNotFoundIndex {
        if (returnIndex(hashMap.get(1), IndexEnum.FIRST) == upOrDown &&
                returnIndex(hashMap.get(1), IndexEnum.SECOND) == leftOrRight) {
            System.out.println("Contour is successful find!" + hashMap);
            arrayPixel[constUpOrDown][constLeftOrRight] = countObjectInc;
            arrayPixel[upOrDown][leftOrRight] = countObjectInc;
            ++countObjectInc;
            return true;
        }
        return false;
    }

    public static void searchObjectsOnImage() throws ExceptionNotFoundIndex {
        initializerHashMapArray();
        for (HashMap<Integer, String> hashMap : hashMapStoreContour) {
            int counter = 0;
            int j = 0;
            int i = 0;
            boolean flag = false;
            FIRST:
            while (i < arrayPixel.length) {
                while (j < arrayPixel[i].length) {
                    if (brokerContours(i, j)) { //check on the not free pixels
                        if (arrayPixel[i][j] == 1) { // put cursor on the start contour
                            hashMap.put(++counter, Integer.toString(i).concat("|") + j);
                            if (!flag) flag = true;

                            if (searchPixelContour(i, j, i, j + 1, counter, hashMap) == 0) { //right
                                break FIRST;
                            } else if (searchPixelContour(i, j, i, j + 1, counter, hashMap) == 1) {
                                j++;
                                continue;
                            }

                            if (searchPixelContour(i, j, i + 1, j, counter, hashMap) == 0) { //down
                                break FIRST;
                            } else if (searchPixelContour(i, j, i + 1, j, counter, hashMap) == 1) {
                                i++;
                                continue;
                            }

                            if (searchPixelContour(i, j, i, j - 1, counter, hashMap) == 0) { //left
                                break FIRST;
                            } else if (searchPixelContour(i, j, i, j - 1, counter, hashMap) == 1) {
                                j--;
                                continue;
                            }

                            if (searchPixelContour(i, j, i - 1, j, counter, hashMap) == 0) { //up
                                break FIRST;
                            } else if (searchPixelContour(i, j, i - 1, j, counter, hashMap) == 1) {
                                i--;
                                continue;
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

    private static int returnIndex(String value, IndexEnum indexEnum) throws ExceptionNotFoundIndex {
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

    private static boolean checkPixelInContour(int varFuture1, int varFuture2,
                                               HashMap<Integer, String> hashMap) throws ExceptionNotFoundIndex {
        for (int i = 1; i < hashMap.size(); i++) {
            if (varFuture1 == returnIndex(hashMap.get(i), IndexEnum.FIRST) &&
                    varFuture2 == returnIndex(hashMap.get(i), IndexEnum.SECOND)) return false;
        }
        return true;
    }

    public static int[][] fillImageColor() throws ExceptionNotFoundIndex {
        int[][] newArray = deepCopyArray();
        for (int q = 0; q < hashMapStoreContour.length; q++) {
            for (int i = searchMin(IndexEnum.FIRST, hashMapStoreContour[q]);
                 i < searchMax(IndexEnum.FIRST, hashMapStoreContour[q]); i++) {

                ArrayList<String>[] listArrays = new ArrayList[3]; // initialize array ArrayLists
                listArrays[IndexArrayListArrays.PAST.getIndex()] = new ArrayList<>(); // past
                listArrays[IndexArrayListArrays.CURRENT.getIndex()] = new ArrayList<>(); // current
                listArrays[IndexArrayListArrays.FUTURE.getIndex()] = new ArrayList<>(); // future

                ArrayList<String>[] arrayListHashMapI =
                        processingLineByLineContour(i, hashMapStoreContour[q], listArrays); // get for two pairs arraylists pixels contour

                baubleSortTempValueI(arrayListHashMapI[IndexArrayListArrays.PAST.getIndex()]); // sort lists pixels
                baubleSortTempValueI(arrayListHashMapI[IndexArrayListArrays.CURRENT.getIndex()]);
                baubleSortTempValueI(arrayListHashMapI[IndexArrayListArrays.FUTURE.getIndex()]);

                fillStringColor(searchStringInMustColor(arrayListHashMapI), newArray, q); // fill object color

                listArrays[0].clear();
                listArrays[1].clear();
            }
        }
        return newArray;
    }

    public static void fillStringColor(ArrayList<String> list, int[][] newArray, int color) throws ExceptionNotFoundIndex {
        for (int i = 0; i < list.size(); i += 2) {
            for (int j = returnIndex(list.get(i), IndexEnum.SECOND); j < returnIndex(list.get(i + 1), IndexEnum.SECOND); j++) {
                if (newArray[returnIndex(list.get(i), IndexEnum.FIRST)][j] != 1)
                    newArray[returnIndex(list.get(i), IndexEnum.FIRST)][j] = color + 1;
            }
        }
    }

    public static ArrayList<String> searchStringInMustColor(ArrayList<String>[] arrayListHashMapI) throws ExceptionNotFoundIndex {
        ArrayList<String> fillArrayList = new ArrayList<>();
        for (int i = 0; i < arrayListHashMapI[1].size(); i++) {
            int incrementMin = i > 0 ? i - 1 : 0;
            int incrementMax = i < arrayListHashMapI[1].size() - 1 ? i + 1 : arrayListHashMapI[1].size() - 1;

            boolean flag = false;
            boolean flagCheck = false;
            boolean flagDoubleChecker = false;
            if(fillArrayList.size()%2==0) flagDoubleChecker = true;

            if (returnIndex(arrayListHashMapI[1].get(incrementMax), IndexEnum.SECOND) -
                    returnIndex(arrayListHashMapI[1].get(i), IndexEnum.SECOND) <= 1) {
                if (!flagDoubleChecker)
                flagCheck = returnIndex(arrayListHashMapI[1].get(i), IndexEnum.SECOND) -
                        returnIndex(arrayListHashMapI[1].get(incrementMin), IndexEnum.SECOND) > 1;
            } else flagCheck = true;

            if (flagCheck) {
                for (int j = 0; j < arrayListHashMapI[2].size(); j++) {
                    if (returnIndex(arrayListHashMapI[1].get(i), IndexEnum.SECOND) ==
                            returnIndex(arrayListHashMapI[2].get(j), IndexEnum.SECOND)) {
                        fillArrayList.add(arrayListHashMapI[1].get(i));
                        flag = true;
                    }
                }
                if (!flag) {
                    for (int j = 0; j < arrayListHashMapI[0].size(); j++) {
                        if (returnIndex(arrayListHashMapI[1].get(i), IndexEnum.SECOND) ==
                                returnIndex(arrayListHashMapI[0].get(j), IndexEnum.SECOND)) {
                            fillArrayList.add(arrayListHashMapI[1].get(i));
                        }
                    }
                }
            }
        }
        if (fillArrayList.size() % 2 != 0) fillArrayList.remove(fillArrayList.size() - 1);
        return fillArrayList;
    }

    public static void baubleSortTempValueI(ArrayList<String> arrayListHashMapI) throws ExceptionNotFoundIndex {
        for (int i = 0; i < arrayListHashMapI.size() - 1; i++) {
            for (int j = i + 1; j < arrayListHashMapI.size(); j++) {
                if (returnIndex(arrayListHashMapI.get(i), IndexEnum.SECOND) > returnIndex(arrayListHashMapI.get(j), IndexEnum.SECOND)) {
                    String temp = arrayListHashMapI.get(j);
                    arrayListHashMapI.set(j, arrayListHashMapI.get(i));
                    arrayListHashMapI.set(i, temp);
                }
            }
        }
    }

    public static int[][] deepCopyArray() {
        int[][] newArray = new int[height][width];
        for (int i = 0; i < newArray.length; i++)
            System.arraycopy(arrayPixel[i], 0, newArray[i], 0, newArray[i].length);
        return newArray;
    }

    private static ArrayList<String>[] processingLineByLineContour(int index, HashMap<Integer, String> hashMapStoreContour,
                                                                   ArrayList<String>[] listArrays)
            throws ExceptionNotFoundIndex {
        for (int i = 1; i <= hashMapStoreContour.size(); i++) {
            if (index < searchMax(IndexEnum.FIRST, hashMapStoreContour) - 1) {
                if (returnIndex(hashMapStoreContour.get(i), IndexEnum.FIRST) == index) {
                    listArrays[0].add(hashMapStoreContour.get(i));
                }
                if (returnIndex(hashMapStoreContour.get(i), IndexEnum.FIRST) == index + 1) {
                    listArrays[1].add(hashMapStoreContour.get(i));
                }
                if (returnIndex(hashMapStoreContour.get(i), IndexEnum.FIRST) == index + 2) {
                    listArrays[2].add(hashMapStoreContour.get(i));
                }
            }
        }
        return listArrays;
    }

    private static int searchMax(IndexEnum indexEnum, HashMap<Integer, String> hashMapStoreContour) throws ExceptionNotFoundIndex {
        int max = returnIndex(hashMapStoreContour.get(1), indexEnum);
        for (int i = 1; i <= hashMapStoreContour.size(); i++) {
            if (max < returnIndex(hashMapStoreContour.get(i), indexEnum))
                max = returnIndex(hashMapStoreContour.get(i), indexEnum);
        }
        return max;
    }

    private static int searchMin(IndexEnum indexEnum, HashMap<Integer, String> hashMapStoreContour) throws ExceptionNotFoundIndex {
        int min = returnIndex(hashMapStoreContour.get(1), indexEnum);
        for (int i = 1; i <= hashMapStoreContour.size(); i++) {
            if (min > returnIndex(hashMapStoreContour.get(i), indexEnum))
                min = returnIndex(hashMapStoreContour.get(i), indexEnum);
        }
        return min;
    }
}
