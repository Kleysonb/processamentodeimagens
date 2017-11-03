package algoritmo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Algoritmo {

    public int[][] matrizPixels;
    private BufferedImage img;
    public int altura;
    public int largura;

    public Algoritmo(File arquivo) throws IOException {
        this.img = ImageIO.read(arquivo);
        this.matrizPixels = GetSetPixels.gerarMatrizPixel(this.img);
        altura = img.getWidth();
        largura = img.getHeight();
    }

    public int[][] dilatacao(int matriz[][], int quantidade){
        System.out.println("Dilatando Imagem");

        int novaMatrizPixel[][] = null;
        Color branco = new Color(255,255,255, 255);

        for(int k = 0; k < quantidade; k++){
            novaMatrizPixel = new int[altura][largura];
            for(int i = 1; i < altura-1; i ++){
                for(int j = 1; j < largura-1; j++){
                    if(matriz[i][j] == branco.getRGB()){
                        novaMatrizPixel[i-1][j-1] = branco.getRGB();
                        novaMatrizPixel[i-1][j] = branco.getRGB();
                        novaMatrizPixel[i-1][j+1] = branco.getRGB();

                        novaMatrizPixel[i][j-1] = branco.getRGB();
                        novaMatrizPixel[i][j] = branco.getRGB();
                        novaMatrizPixel[i][j+1] = branco.getRGB();

                        novaMatrizPixel[i+1][j-1] = branco.getRGB();
                        novaMatrizPixel[i+1][j] = branco.getRGB();
                        novaMatrizPixel[i+1][j+1] = branco.getRGB();
                    }
                }
            }
            matriz = novaMatrizPixel;
        }

        return novaMatrizPixel;
    }

    public int[][] erosao(int matriz[][], int quantidade){
        System.out.println("Erodindo Imagem");

        int novaMatrizPixel[][] = null;
        Color branco = new Color(255,255,255, 255);

        for(int k = 0; k < quantidade; k++){
            novaMatrizPixel = new int[altura][largura];
            for(int i = 1; i < altura-1; i ++){
                for(int j = 1; j < largura-1; j++){
                    if(     matriz[i-1][j-1] == branco.getRGB() &&
                            matriz[i-1][j] == branco.getRGB() &&
                            matriz[i-1][j+1] == branco.getRGB() &&
                            matriz[i][j-1] == branco.getRGB() &&
                            matriz[i][j] == branco.getRGB() &&
                            matriz[i][j+1] == branco.getRGB() &&
                            matriz[i+1][j-1] == branco.getRGB() &&
                            matriz[i+1][j] == branco.getRGB() &&
                            matriz[i+1][j+1] == branco.getRGB()){

                        novaMatrizPixel[i][j] = branco.getRGB();

                    }
                }
            }
            matriz = novaMatrizPixel;
        }

        return novaMatrizPixel;
    }

    public int[][] abertura(int matriz[][], int quantidade){
        int novaMatrizPixel[][] = erosao(matriz, quantidade);
        return dilatacao(novaMatrizPixel, quantidade);
    }

    public int[][] fechamento(int matriz[][], int quantidade){
        int novaMatrizPixel[][] = dilatacao(matriz, quantidade);
        return erosao(novaMatrizPixel, quantidade);
    }

    public int[][] converterParaBinaria() throws IOException {
        System.out.println("Convertendo imagem para binária");

        int novaMatrizPixel[][] = new int[altura][largura];

        Color preto = new Color(0,0,0,255);
        Color branco = new Color(255,255,255, 255);

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {

                if ((this.matrizPixels[i][j] & 0xff) > 200) {
                    //Branco
                    novaMatrizPixel[i][j] = branco.getRGB();
                } else {
                    //Preto
                    novaMatrizPixel[i][j] = preto.getRGB();
                }
            }
        }

        this.matrizPixels = novaMatrizPixel;
        return novaMatrizPixel;

    }

    public int getAltura() {
        return altura;
    }

    public int getLargura() {
        return largura;
    }

    public int[][] getMatrizPixels() {
        return matrizPixels;
    }
}
