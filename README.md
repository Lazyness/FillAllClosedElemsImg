# Fill all objects into image 🎨🖌️

![Project Video](src/main/resources/img_for_git/test_work_app.gif)


> This application receives a 40x40 black and white image of an 
> .bmp format as input. 
> During operation, 
> a contour all objects on image search is performed 
> and fill those objects random color.

## Preview 🎨
1. This project uses `jdk 17`;
2. I recommend that when creating an image for testing, 
make it `40 by 40 pixels in size`. 
3. File must be `.bmp` format.
4. Result program you can see in console also you can 
see in image in the address:  `src/main/resources/img_res/result.bmp`.
5. To specify which image to read and process,
   you need to change the path to the image in the method by class 
ImageProcess: 
`imageProcessor.createColorObjectsOnImgWithContours(<fileNameInitial>,<fileNameResult>)`
6. You must create image using pixels, the outline should be thin, 
i.e., there should not be a situation where there are 4 pixels 
like in the picture on the right. ![img.png](src/main/resources/img_for_git/rule.png)
<br>In image u can see the desired pixel above, 
and below it does not match the rules.

## Warning ⚠️
  - Code can be improved if u want to do it, change code that response for find
contour and memorize inner contour for inner shapes.
