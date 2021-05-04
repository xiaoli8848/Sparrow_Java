package com.Sparrow.Utils;

import com.Sparrow.UI.JavaFX.launcherUI_JavaFX;
import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;
import org.to2mbn.jmccc.auth.yggdrasil.core.Texture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class texture {
    private static int num = 0;
    private Image fullTexture;
    private Image headTexture;

    public Image getFullTexture() {
        return fullTexture;
    }

    public Image getHeadTexture() {
        return headTexture;
    }

    public texture(Texture Ttexture) throws IOException{
        try {
            File result = new File(launcherUI_JavaFX.controller.TempPath+File.separator+"temp_"+num+++".png");
            FileUtils.copyURLToFile(new URL(Ttexture.getUrl()),result);
            this.fullTexture = new Image(result.toURL().toString());
            File result2 = new File(launcherUI_JavaFX.controller.TempPath + File.separator + "temp_" + num++ + ".png");
            saveHeadTexture(ImageIO.read(result), result2);
            this.headTexture = new Image(result2.toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static boolean saveHeadTexture(BufferedImage image, File saveFile) throws IOException {
        //判断x、y方向是否超过图像最大范围
        int xCoordinate = 8;
        int yCoordinate = 8;
        int xLength = 8;
        int yLength = 8;
        if((xCoordinate + xLength) >= image.getWidth()) {
            xLength = image.getWidth() - xCoordinate;
        }
        if ((yCoordinate + yLength) >= image.getHeight()) {
            yLength = image.getHeight() - yCoordinate;
        }
        BufferedImage resultImage = new BufferedImage(40, 40, image.getType());
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                int rgb = image.getRGB(x + xCoordinate, y + yCoordinate);
                for(int i=0;i<5;i++){
                    for(int j=0;j<5;j++){
                        resultImage.setRGB(x*5+i,y*5+j,rgb);
                    }
                }
            }
        }
        return ImageIO.write(resultImage,"PNG",saveFile);
    }
}
