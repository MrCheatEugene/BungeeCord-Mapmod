package ru.leymooo.botfilter.captcha.generator.map;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.*;
import javax.xml.bind.DatatypeConverter;
import ru.leymooo.botfilter.packets.MapDataPacket;
import java.io.ByteArrayInputStream;
import java.nio.file.*;

public class CraftMapCanvas
{

    public static BufferedImage convertTo3ByteBGR(BufferedImage originalImage) {
        BufferedImage convertedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);
        convertedImage.getGraphics().drawImage(originalImage, 0, 0, null);
        return convertedImage;
    }
    
    private static final ThreadLocal<byte[]> mcPixelsBuffer = ThreadLocal.withInitial( () -> new byte[128 * 128] );
    private final byte[] buffer;

    public CraftMapCanvas()
    {
        this.buffer = mcPixelsBuffer.get();
        Arrays.fill( this.buffer, (byte) -1 );
        this.drawbg();
    }
    public BufferedImage getbg(){
        try{

            byte[] imageBytes = Files.readAllBytes(Paths.get(System.getProperty("user.dir"),"image.png"));
            BufferedImage img = convertTo3ByteBGR(ImageIO.read(new ByteArrayInputStream(imageBytes)));
        return img;
        }catch(Exception e){ return new BufferedImage(128, 128, BufferedImage.TYPE_3BYTE_BGR);}
    }
    public void drawbg(){
        try{
            drawImage(0, 0, getbg());
        }catch(Exception e){}
    }
    public void setPixel(int x, int y, byte color)
    {
        if ( x >= 0 && y >= 0 && x < 128 && y < 128 )
        {
            if ( this.buffer[y * 128 + x] != color )
            {
                this.buffer[y * 128 + x] = color;
            }
        }
    }

@SuppressWarnings("deprecation")
public void drawImage(int x, int y, BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();

    for (int x2 = 0; x2 < width; x2++) {
        for (int y2 = 0; y2 < height; y2++) {
            Color color = new Color(image.getRGB(x2, y2));
            byte mcColor = MapPalette.matchColor(color);
            setPixel(x + x2, y + y2, mcColor);
        }
    }
}




    public MapDataPacket.MapData getMapData()
    {
        return new MapDataPacket.MapData( 128, 128, 0, 0, this.buffer );
    }
}
