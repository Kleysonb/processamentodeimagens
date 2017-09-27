package transformacao;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Algoritmo{

    private int[][] matrizPixels;
    private BufferedImage img;

    public Algoritmo(File arquivo) throws IOException {
        this.img = ImageIO.read(arquivo);
        this.matrizPixels = GetSetPixels.gerarMatrizPixel(this.img);
    }

    public void powerLaw(){
        int altura = this.img.getWidth();
        int largura = this.img.getHeight();

        int[][] novaMatrizPixels = new int[altura][largura];
        for(int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                //novaMatrizPixels[i][j] = (int) Math.pow(this.matrizPixels[i][j], 0.40);
                novaMatrizPixels[i][j] = suporteCores(this.matrizPixels[i][j], 5, 1);
            }
        }

        GetSetPixels.exibirImagem(altura, largura, novaMatrizPixels, "powerLaw");
    }

    private int suporteCores(int pixel, double y, int constante){
        Color cor = new Color(pixel, true);
        int novoPixel;

        int red = normaliza(cor.getRed(), y, constante);
        int green = normaliza(cor.getGreen(), y, constante);
        int blue = normaliza(cor.getBlue(), y, constante);
        int alpha = normaliza(cor.getAlpha(), y, constante);

        //System.out.println("RED: " + red + "\nGREEN: " + green + "\nBLUE: " + blue + "\nALPHA: " + alpha);
        //Color novaCor = new Color((int) Math.log(cor.getRed()+1), (int) Math.log(cor.getGreen()+1), (int) Math.log(cor.getBlue()+1), (int) Math.log(cor.getAlpha()+1));
        Color novaCor = new Color(red, green, blue, alpha);
        //Color novaCor = new Color(255-cor.getRed(), 255-cor.getGreen(), 255-cor.getBlue(), 255-cor.getAlpha());
        novoPixel = novaCor.getRGB();

       return novoPixel;
    }

    private int normaliza(double nivel, double y, int constante){

        double aux = nivel/255;
        //BigDecimal bd = new BigDecimal(aux).setScale(8, RoundingMode.HALF_EVEN);
        //System.out.println(aux);
        //System.out.println(Math.pow(aux, y));
        double normaliza = (Math.pow(aux, y))*255;

        return (int) normaliza;
    }
}
