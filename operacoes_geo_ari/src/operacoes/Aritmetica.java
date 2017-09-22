package operacoes;

import javax.imageio.ImageIO;
import javax.swing.plaf.synth.ColorType;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Created by Felipe Reis on 19/09/2017.
 */
public class Aritmetica {

    public int matrizPixel[][];
    public BufferedImage img;

    public int matrizPixel2[][];
    public BufferedImage img2;

    private String soma = "+";
    private String subtracao = "-";
    private String multiplicacao = "*";
    private String divisao = "/";

    public Aritmetica(File arquivo, File arquivo2) throws IOException {
        //Arquivo é recebido e convertido em imagem
        this.img = ImageIO.read(arquivo);
        this.img2 = ImageIO.read(arquivo2);
        //Retorna uma Matriz com os Pixels da Imagem
        this.matrizPixel = GetSetPixels.gerarMatrizPixel(this.img);
        this.matrizPixel2 = GetSetPixels.gerarMatrizPixel(this.img2);
    }

    // adição, subtração, divisão, multiplicação

    public Image adicao() {
        System.out.println("Adição");

        int altura = this.img.getWidth();
        int largura = this.img.getHeight();

        int altura2 = this.img2.getWidth();
        int largura2 = this.img2.getHeight();

        int novaAltura = altura;
        int novaLargura = largura;

        //Criando a matriz da nova imagem
        int novaMatrizPixel[][] = new int[novaAltura][novaLargura];

        int linha = 0, coluna;
        //Populando os dados da nova matriz
        for (int i = 0; i < altura; i++) {
            coluna = 0;
            for (int j = 0; j < largura; j++) {
                novaMatrizPixel[linha][coluna] = suporteCores(this.matrizPixel[i][j], this.matrizPixel2[i][j], this.soma);
                // System.out.println("soma: "+this.matrizPixel[i][j]+" + " +this.matrizPixel2[i][j] +" = "+novaMatrizPixel[linha][coluna]);
                coluna++;
            }
            linha++;
        }
        //Chamada da função que reconstruirá a imagem nova
        GetSetPixels.exibirImagem(novaAltura, novaLargura, novaMatrizPixel, "Adicao");

        return null;
    }

    public Image subtração() {
        System.out.println("Subtração");

        int altura = this.img.getWidth();
        int largura = this.img.getHeight();

        int altura2 = this.img2.getWidth();
        int largura2 = this.img2.getHeight();

        int novaAltura = altura;
        int novaLargura = largura;

        //Criando a matriz da nova imagem
        int novaMatrizPixel[][] = new int[novaAltura][novaLargura];

        int linha = 0, coluna;
        //Populando os dados da nova matriz
        for (int i = 0; i < altura; i++) {
            coluna = 0;
            for (int j = 0; j < largura; j++) {
                novaMatrizPixel[linha][coluna] = suporteCores(this.matrizPixel[i][j], this.matrizPixel2[i][j], this.subtracao);
                coluna++;
            }
            linha++;
        }
        //Chamada da função que reconstruirá a imagem nova
        GetSetPixels.exibirImagem(novaAltura, novaLargura, novaMatrizPixel, "Subtracao");

        return null;
    }

    public Image multiplicação() {
        System.out.println("Multiplicação");

        int altura = this.img.getWidth();
        int largura = this.img.getHeight();

        int altura2 = this.img2.getWidth();
        int largura2 = this.img2.getHeight();

        int novaAltura = altura;
        int novaLargura = largura;

        //Criando a matriz da nova imagem
        int novaMatrizPixel[][] = new int[novaAltura][novaLargura];

        int linha = 0, coluna;
        //Populando os dados da nova matriz
        for (int i = 0; i < altura; i++) {
            coluna = 0;
            for (int j = 0; j < largura; j++) {
                novaMatrizPixel[linha][coluna] = suporteCores(this.matrizPixel[i][j], this.matrizPixel2[i][j], this.multiplicacao);
                coluna++;
            }
            linha++;
        }
        //Chamada da função que reconstruirá a imagem nova
        GetSetPixels.exibirImagem(novaAltura, novaLargura, novaMatrizPixel, "Multiplicação");

        return null;
    }

    public Image divisão() {
        System.out.println("Divisão");

        int altura = this.img.getWidth();
        int largura = this.img.getHeight();

        int altura2 = this.img2.getWidth();
        int largura2 = this.img2.getHeight();

        int novaAltura = altura;
        int novaLargura = largura;

        //Criando a matriz da nova imagem
        int novaMatrizPixel[][] = new int[novaAltura][novaLargura];

        int linha = 0, coluna;
        //Populando os dados da nova matriz
        for (int i = 0; i < altura; i++) {
            coluna = 0;
            for (int j = 0; j < largura; j++) {
                //System.out.println("Divisao: "+this.matrizPixel[i][j] +" / "+ this.matrizPixel2[i][j]);
                novaMatrizPixel[linha][coluna] = suporteCores(this.matrizPixel[i][j], this.matrizPixel2[i][j], this.divisao);
                coluna++;
            }
            linha++;
        }
        //Chamada da função que reconstruirá a imagem nova
        GetSetPixels.exibirImagem(novaAltura, novaLargura, novaMatrizPixel, "Divisão");

        return null;
    }

    public int suporteCores(int pixel1, int pixel2, String operacao) {

        int green, red, blue, alpha;
        int novoPixel = 0;
        Color cor1 = new Color(pixel1);
        Color cor2 = new Color(pixel2);

        if (operacao == this.soma) {

            if ((cor1.getAlpha() + cor2.getAlpha()) > 255) {
                alpha = 255;
            } else {
                alpha = cor1.getAlpha() + cor2.getAlpha();
            }

            if ((cor1.getRed() + cor2.getRed()) > 255) {
                red = 255;
            } else {
                red = cor1.getRed() + cor2.getRed();
            }

            if ((cor1.getGreen() + cor2.getGreen()) > 255) {
                green = 255;
            } else {
                green = cor1.getGreen() + cor2.getGreen();
            }

            if ((cor1.getBlue() + cor2.getBlue()) > 255) {
                blue = 255;
            } else {
                blue = cor1.getBlue() + cor2.getBlue();
            }

            Color novaCor = new Color(red,green,blue,alpha);
            novoPixel = novaCor.getRGB();

        } else if (operacao == this.subtracao) {
            if ((cor1.getAlpha() - cor2.getAlpha()) < 0) {
                alpha = 0;
            } else {
                alpha = cor1.getAlpha() - cor2.getAlpha();
            }

            if ((cor1.getRed() - cor2.getRed()) < 0) {
                red = 0;
            } else {
                red = cor1.getRed() - cor2.getRed();
            }

            if ((cor1.getGreen() - cor2.getGreen()) < 0) {
                green = 0;
            } else {
                green = cor1.getGreen() - cor2.getGreen();
            }

            if ((cor1.getBlue() - cor2.getBlue()) < 0) {
                blue = 0;
            } else {
                blue = cor1.getBlue() - cor2.getBlue();
            }

            Color novaCor = new Color(red,green,blue,alpha);
            novoPixel = novaCor.getRGB();

        } else if (operacao == this.multiplicacao) {
            if ((cor1.getAlpha() * cor2.getAlpha()) > 255) {
                alpha = 255;
            } else {
                alpha = cor1.getAlpha() * cor2.getAlpha();
            }

            if ((cor1.getRed() * cor2.getRed()) > 255) {
                red = 255;
            } else {
                red = cor1.getRed() * cor2.getRed();
            }

            if ((cor1.getGreen() * cor2.getGreen()) > 255) {
                green = 255;
            } else {
                green = cor1.getGreen() * cor2.getGreen();
            }

            if ((cor1.getBlue() * cor2.getBlue()) > 255) {
                blue = 255;
            } else {
                blue = cor1.getBlue() * cor2.getBlue();
            }

            Color novaCor = new Color(red,green,blue,alpha);
            novoPixel = novaCor.getRGB();

        } else {

            if(cor2.getAlpha() != 0){
                if ((cor1.getAlpha() / cor2.getAlpha()) < 0) {
                    alpha = 0;
                } else {
                    alpha = cor1.getAlpha() / cor2.getAlpha();
                }
            }else{
                alpha = cor1.getAlpha();
            }

            if(cor2.getRed() != 0){
                if ((cor1.getRed() / cor2.getRed()) < 0) {
                    red = 0;
                } else {
                    red = cor1.getRed() / cor2.getRed();
                }
            }else{
                red = cor1.getRed();
            }

            if(cor2.getGreen() != 0){
                if ((cor1.getGreen() / cor2.getGreen()) < 0) {
                    green = 0;
                } else {
                    green = cor1.getGreen() / cor2.getGreen();
                }
            }else{
                green = cor1.getGreen();
            }

            if(cor2.getBlue() != 0){
                if ((cor1.getBlue() / cor2.getBlue()) < 0) {
                    blue = 0;
                } else {
                    blue = cor1.getBlue() / cor2.getBlue();
                }
            }else{
                blue = cor1.getBlue();
            }

            Color novaCor = new Color(red,green,blue,alpha);
            novoPixel = novaCor.getRGB();
        }

        return novoPixel;
    }

}
