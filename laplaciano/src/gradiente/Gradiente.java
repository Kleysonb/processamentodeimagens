package gradiente;

import laplaciano.GetSetPixels;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gradiente {
    private int[][] matrizPixels;
    private BufferedImage img;

    public Gradiente(File arquivo) throws IOException {
        this.img = ImageIO.read(arquivo);
        this.matrizPixels = GetSetPixels.gerarMatrizPixel(this.img);
    }

    public void aplicarSobel(){
        int altura = img.getWidth();
        int largura = img.getHeight();

        int novaMatrizPixel[][] = imagemNiveldeCinza();

        Color preto = new Color(0,0,0);
        //Aplicando 0 aos resultados não calculáveis
        for(int i = 0 ; i < largura ; i ++){
            this.matrizPixels[0][i] = preto.getRGB();
            this.matrizPixels[altura-1][i] = preto.getRGB();
            novaMatrizPixel[0][i] = preto.getRGB();
            novaMatrizPixel[altura-1][i] = preto.getRGB();
        }
        for(int i = 0 ; i < altura ; i ++){
            this.matrizPixels[i][0] = preto.getRGB();
            this.matrizPixels[i][largura-1] = preto.getRGB();
            novaMatrizPixel[i][0] = preto.getRGB();
            novaMatrizPixel[i][largura-1] = preto.getRGB();
        }

        mascaraSobelHorizontal(altura, largura, novaMatrizPixel);
        mascaraSobelVertical(altura, largura, novaMatrizPixel);
    }

    public void mascaraSobelHorizontal(int altura, int largura, int[][] matrizPixel){

        int novaMatrizPixel[][] = matrizPixel;

        for(int i = 1 ; i < altura-1 ; i ++){
            for (int j = 1; j < largura-1; j ++){
                int a = this.matrizPixels[i-1][j-1];
                int b = this.matrizPixels[i-1][j];
                int c = this.matrizPixels[i-1][j+1];


                int g = this.matrizPixels[i+1][j-1];
                int h = this.matrizPixels[i+1][j];
                int k = this.matrizPixels[i+1][j+1];
                novaMatrizPixel[i][j] = suporteMascaraHorizontal(a,b,c,g,h,k);
            }
        }
        GetSetPixels.exibirImagem(altura, largura, novaMatrizPixel, "gradiente_sobel_1");
    }

    public void mascaraSobelVertical(int altura, int largura, int[][] matrizPixel){

        int novaMatrizPixel[][] = matrizPixel;

        for(int i = 1 ; i < altura-1 ; i ++){
            for (int j = 1; j < largura-1; j ++){
                int a = this.matrizPixels[i-1][j-1];
                int b = this.matrizPixels[i][j-1];
                int c = this.matrizPixels[i+1][j-1];

                int d = this.matrizPixels[i-1][j+1];
                int e = this.matrizPixels[i][j+1];
                int f = this.matrizPixels[i+1][j+1];

                novaMatrizPixel[i][j] = suporteMascaraVertical(a,b,c,d,e,f);
            }
        }
        GetSetPixels.exibirImagem(altura, largura, novaMatrizPixel, "gradiente_sobel_2");
    }

    public int[][] imagemNiveldeCinza() {
        int altura = this.img.getWidth();
        int largura = this.img.getHeight();

        int[][] imagemNivelCinza = new int[altura][largura];
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                imagemNivelCinza[i][j] = suporteCorNivelCinza(this.matrizPixels[i][j]);
            }
        }
        return imagemNivelCinza;
    }

    private int suporteCorNivelCinza(int pixel) {
        Color cor = new Color(pixel, true);
        int novoPixel;

        int red = cor.getRed();
        int green = cor.getGreen();
        int blue = cor.getBlue();

        int nivelDeCinza = (red + green + blue) / 3;

        int alpha = nivelDeCinza;
        red = nivelDeCinza;
        green = nivelDeCinza;
        blue = nivelDeCinza;

        Color novaCor = new Color(red, green, blue, alpha);
        novoPixel = novaCor.getRGB();
        return novoPixel;
    }

    public int suporteMascaraHorizontal(int p1, int p2, int p3, int p4, int p5, int p6){
        int novoPixel;
        Color s1 = new Color(p1);
        Color s2 = new Color(p2);
        Color s3 = new Color(p3);
        Color s4 = new Color(p4);
        Color s5 = new Color(p5);
        Color s6 = new Color(p6);

        int x1 = s1.getRed() * (-1);
        int x2 = s2.getRed() * (-2);
        int x3 = s3.getRed() * (-1);
        int x4 = s4.getRed() * 1;
        int x5 = s5.getRed() * 2;
        int x6 = s6.getRed() * 1;

//        System.out.println(p1 +" * -1 = "+x);
//        System.out.println(p5 +" * 8 = "+c);

        int laplace = x1+x2+x3+x4+x5+x6;

        if(laplace < 0){
            laplace = 0;
        }else if (laplace > 255){
            laplace = 255;
        }
        Color novaCor = new Color(laplace, laplace, laplace, laplace);
        novoPixel = novaCor.getRGB();
        return novoPixel;
    }

    public int suporteMascaraVertical(int p1, int p2, int p3, int p4, int p5, int p6){
        int novoPixel;
        Color s1 = new Color(p1);
        Color s2 = new Color(p2);
        Color s3 = new Color(p3);
        Color s4 = new Color(p4);
        Color s5 = new Color(p5);
        Color s6 = new Color(p6);

        int x1 = s1.getRed() * (-1);
        int x2 = s2.getRed() * (-2);
        int x3 = s3.getRed() * (-1);
        int x4 = s4.getRed() * 1;
        int x5 = s5.getRed() * 2;
        int x6 = s6.getRed() * 1;



//        System.out.println(p1 +" * -1 = "+x);
//        System.out.println(p5 +" * 8 = "+c);

        int laplace = x1+x2+x3+x4+x5+x6;

        if(laplace < 0){
            laplace = 0;
        }else if (laplace > 255){
            laplace = 255;
        }
        Color novaCor = new Color(laplace, laplace, laplace, laplace);
        novoPixel = novaCor.getRGB();
        return novoPixel;
    }


}
