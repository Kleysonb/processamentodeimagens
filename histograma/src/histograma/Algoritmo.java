package histograma;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Algoritmo {

    private int[][] matrizPixels;
    private BufferedImage img;
    private double nivelDeCinza = 255;
    private int totalPixels;
    private int[] vetorReferentePixel, vetorEqualizadoINT;
    private double[] vetorFrequencia, vetorFrequenciaSomada, vetorEqualizado;

    public Algoritmo(File arquivo) throws IOException {
        this.img = ImageIO.read(arquivo);
        this.matrizPixels = GetSetPixels.gerarMatrizPixel(this.img);
        inicializarVetores();
    }

    public void inicializarVetores(){
        vetorFrequencia = new double[256];
        vetorFrequenciaSomada = new double[256];
        vetorEqualizado = new double[256];
        vetorReferentePixel = new int[256];
        vetorEqualizadoINT = new int[256];
        for(int i=0; i<256; i++){
            vetorReferentePixel[i] = 0;
            vetorFrequencia[i] = 0;
            vetorFrequenciaSomada[i] = 0;
            vetorEqualizado[i] = 0;
            vetorEqualizadoINT[i] = 0;
        }
    }

    public void histograma() {
        int altura = this.img.getWidth();
        int largura = this.img.getHeight();

        this.totalPixels = altura * largura;
        System.out.println("Total de Pixels: " + this.totalPixels);

        int[][] imagemNiveldeCinza = imagemNiveldeCinza();
        GetSetPixels.exibirImagem(altura, largura, imagemNiveldeCinza, "nivel_de_cinza");

        calcularFrequencia();
        frequenciaSomada();
        equalizar();
        arredondarEqualizado();

        int [][] imagemEqualizada = new int[altura][largura];

        int corDoPixel, red, green, blue, alpha;
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                Color cor = new Color(imagemNiveldeCinza[i][j], true);
                red = green = blue = alpha = vetorEqualizadoINT[cor.getRed()];
                Color novaCor = new Color(red, green, blue, alpha);
                corDoPixel = novaCor.getRGB();
                imagemEqualizada[i][j] = corDoPixel;
            }
        }
        GetSetPixels.exibirImagem(altura, largura, imagemEqualizada, "imagem_equalizada");
    }

    public void imprimirTabela(){
        for(int i=0; i<256; i++){
            System.out.println("Nivel: "+i+ " = " +vetorReferentePixel[i] +" - " +
                    vetorFrequencia[i]+" - "+vetorFrequenciaSomada[i]+" - "+vetorEqualizado[i] +" - "+vetorEqualizadoINT[i]);
        }
    }

    public void calcularFrequencia() {
        double totalPixelsImagem, quantidadePixels;
        totalPixelsImagem = this.totalPixels;
        for(int i=0; i<256; i++){
            quantidadePixels = this.vetorReferentePixel[i];
            vetorFrequencia[i] = quantidadePixels/totalPixelsImagem;
        }
    }

    public void frequenciaSomada(){
        vetorFrequenciaSomada[0] = vetorFrequencia[0];
        for(int i=1; i<256; i++){
            vetorFrequenciaSomada[i] = vetorFrequencia[i] + vetorFrequenciaSomada[i-1] ;
        }
    }

    public void equalizar(){
        for(int i=0; i<256; i++){
            vetorEqualizado[i] = vetorFrequenciaSomada[i]*nivelDeCinza;
        }
    }

    public void arredondarEqualizado(){
        for(int i=0; i<256; i++){
            vetorEqualizadoINT[i] = (int) Math.round(vetorEqualizado[i]);
        }
    }

    public void verificarPixelInt(int pixel) {
        vetorReferentePixel[pixel]++;
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

        verificarPixelInt(nivelDeCinza);

        Color novaCor = new Color(red, green, blue, alpha);
        novoPixel = novaCor.getRGB();
        return novoPixel;
    }
}
