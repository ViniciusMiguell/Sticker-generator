import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

public class GeradorDeFigurinhas {
    
    public void cria(String url, String text, String fileName, boolean stickerPersonalised) throws Exception{
        // Leitura da imagem
        InputStream inputStream = new URL(url).openStream();
        BufferedImage image = ImageIO.read(inputStream);
        
        int width = image.getWidth();
        int height = (int) (image.getHeight() * 1.1);
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);

        if (stickerPersonalised== true){
            width = image.getWidth();
            height = (int) (image.getHeight() * 1.2);
            newImage = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        }

        Graphics2D graphics = (Graphics2D) newImage.getGraphics();
        graphics.drawImage(image, 0, 0, null);

        var font = new Font(Font.SANS_SERIF, Font.BOLD, (width / 8));
        graphics.setColor(Color.GREEN);
        graphics.setFont(font);

        FontMetrics fm = graphics.getFontMetrics();
        int x = (width - fm.stringWidth(text)) / 2;

        graphics.drawString(text, x, (int) (height * 0.985));

        // Drawing outline
        TextLayout t1 = new TextLayout(text, font, graphics.getFontRenderContext());
        Shape shape = t1.getOutline(null);

        graphics.translate(x, (int) (height * 0.985));
        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke((int) (width / 216)));
        graphics.draw(shape);


        // Escrever a nova imagem em um arquivo
        var Directory = new File("figurinhas/");
        Directory.mkdir();
        ImageIO.write(newImage, "png", new File("figurinhas/"+fileName+".png"));
    }
}