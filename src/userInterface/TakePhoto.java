package userInterface;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;

public class TakePhoto {
 public TakePhoto(){
   // get default webcam and open it
   Webcam webcam = Webcam.getDefault();
   webcam.open();

   // get image
   BufferedImage image = webcam.getImage();

   // save image to PNG file
   try {
  ImageIO.write(image, "PNG", new File("test.png"));
  webcam.close();
 } catch (IOException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
 }
 }
 

}