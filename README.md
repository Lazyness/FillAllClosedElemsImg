# FillAllClosedElemsImg

## Preview
1. This project uses jdk 11;
2. Result program you can see in console also you can see in image in the address:  `src/main/resources/img_res/result.png`.
3. You must change the value `private static final int COUNT_OBJECT = YOUR_VALUE;`
of the constant to control the number of objects in the image in Logic class.
4. To specify which image to read and process, you need to change the path to the image in the Main class `BufferedImage image = ImageIO.read(new File(
   "src\\main\\resources\\img_ex\\YOUR_IMAGE.bmp"));`.
## Program testing
### Result of running the program for 60 seconds
Images is very small because 40*40 pixels.<br>
![img.png](src/main/resources/img_ex/figures_4.bmp)
![img_1.png](src/main/resources/img_res/result.png)
