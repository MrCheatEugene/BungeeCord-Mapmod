package ru.leymooo.botfilter.captcha.generator.map;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Color;
import ru.leymooo.botfilter.captcha.generator.map.MapPalette;
import javax.*;
import javax.xml.bind.DatatypeConverter;
import ru.leymooo.botfilter.packets.MapDataPacket;
import java.io.ByteArrayInputStream;
import java.nio.file.*;

public class CraftMapCanvas
{
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
            byte[] imageBytes = Files.readAllBytes(Paths.get(System.getProperty("user.dir"),"image.jpg"));
            BufferedImage in = ImageIO.read(imageBytes);
            
            BufferedImage newImage = new BufferedImage(
                in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);
            
            Graphics2D g = newImage.createGraphics();
            g.drawImage(in, 0, 0, null);
            g.dispose();
        return newImage;
        }catch(Exception e){ 
            System.out.println("Error!");
            return new BufferedImage(128, 128, BufferedImage.TYPE_3BYTE_BGR);}
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
    public void drawImage(int x, int y, BufferedImage image)
    {
        int[] bytes = MapPalette.imageToBytes( image );
        int width = image.getWidth( null );
        int height = image.getHeight( null );

        for ( int x2 = 0; x2 < width; ++x2 )
        {
            for ( int y2 = 0; y2 < height; ++y2 )
            {
                this.setPixel( x + x2, y + y2, (byte) bytes[y2 * width + x2] );
            }
        }

    }

    public MapDataPacket.MapData getMapData()
    {
        return new MapDataPacket.MapData( 128, 128, 0, 0, this.buffer );
    }
}
