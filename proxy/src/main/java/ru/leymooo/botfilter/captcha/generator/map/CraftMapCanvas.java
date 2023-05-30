package ru.leymooo.botfilter.captcha.generator.map;

import java.awt.image.BufferedImage;
import java.util.Arrays; 
import javax.imageio.ImageIO;
import javax.*;
import javax.xml.bind.DatatypeConverter;
import ru.leymooo.botfilter.packets.MapDataPacket;
import java.io.ByteArrayInputStream;

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
        String base64Image = "iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAIAAABMXPacAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAK3SURBVHhe7d3bdcMgDIDhzNWBOk+nyTIdpnUwIIQkIK3tXs7/vVnIQoKcvPoGAAAAAADwm7zeP7T7a17RXt7ec4J4f3tJa7WGeTde2Zitk5qpl8teTSc51D3bLb0mouIX84/Abce7gHjmIl7Z+LunzMHS9y9gVPxScqb75rUx7wbUmNOZi3il6s+zDeXX9HP/wrQZHRkX/ym1x0SOQkifonSs397Nrqbqz3Nj3tI50YF2W1opf1L8Ut6hPnid+LnDmbdFM63lzD85o3Hbf+gC+lbqs9dJcAbpqSvUPJppLWd+OeH9NZtS626x+12t2i11ZF78Ms0UmteK9K2k1KDQtmZPw3APwK/oF+kK2C37yDPFT9a2sjVQR3GacS8gn5k7UaphT8NwL+BBV+1XxdMX8LBaHAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADwx8j3+duP2Zuo+xn/nfsR/CDf5uoP6m/0F/8H+5piw1KjQk0pVcOd7GjmqBMTHfTvthnnS/r3ctTG81KjQjnLTdG/hxPIrs08NuqnxeYFJCBDyu+v7LK071qpIipZ30jhmjXY9xB+OyYadR2x+XIi6ZC6x6IPr+y7WKoIStbwHq1vr837ddKOL+8fp+npCjNlFzDrWR8P95VtV0sVUbwhdxfnHCWcMMsNxGmzC+jtBVdPZ9jevvVqqSKKF+2W/nCH8tsx0VnXvXaKRh0oqtfHvTz5fabYaqkiimfRP9dZ/HZMdNK1Mc0P5uzDbp0uabFUMW6truqXzuO3Y6Ljrq15vmTIrHJm5S2vjslaK1U8O8rJ/HZMtJnR8H4rK1PGNeWd0b5LaXb7cWt1Ne77WH47JhpP+PULSOSXmulq8b5210mpYtxaXb3qAgAAAAAAAADgn7ndPgHT1FOMaMNPpgAAAABJRU5ErkJggg==";
            byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
        return img;
        }catch(Exception e){ return new BufferedImage(128, 128, 1);}
    }
    public void drawbg(){
        try{
            drawImage(0, 0, getbg());
        }catch(Exception e){}
    }
    public void setPixel(int x, int y, byte color)
    {
        if (color==255){
            return;
        }
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
