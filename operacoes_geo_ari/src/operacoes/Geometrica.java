package operacoes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Felipe Reis on 19/09/2017.
 */
public class Geometrica {

    public int matrizPixel[][];
    public BufferedImage img;

    public Geometrica(File arquivo) throws IOException {
        //Arquivo é recebido e convertido em imagem
        this.img = ImageIO.read(arquivo);
        //Retorna uma Matriz com os Pixels da Imagem
        this.matrizPixel = GetSetPixels.gerarMatrizPixel(this.img);
    }

    // rotação, translação, redução, ampliação, espelhamento, reflexão

    public Image Shear(double valorInclincacao) {
        System.out.println("Shear");

        int altura = this.img.getWidth();
        int largura = this.img.getHeight();

        int novaAltura = (int) ((altura*valorInclincacao)+altura);
        int novaLargura = (int) ((largura*valorInclincacao)+largura);

        //Criando a matriz da nova imagem
        int novaMatrizPixel[][] = new int[novaAltura][novaLargura];
        for (int i = 0; i <novaAltura; i++){
            for (int j = 0; j<novaLargura; j++){
                novaMatrizPixel[i][j] = 0;
            }
        }

        int linha = 0, coluna;
        int x, y;
        //Populando os dados da nova matriz
        for (int i = 0; i < altura; i++) {
            coluna = 0;
            for (int j = 0; j < largura; j++) {
                x = i;
                y = (int) (valorInclincacao*i + j);
                // System.out.println(Math.sin(90));
                if(x < 0 && y < 0){
                    x = 0;
                    y = 0;
                    novaMatrizPixel[x][y] = this.matrizPixel[i][j];
                }else if (y < 0){
                    y = 0;
                    novaMatrizPixel[x][y] = this.matrizPixel[i][j];
                }else if (x < 0){
                    x = 0;
                    novaMatrizPixel[x][y] = this.matrizPixel[i][j];
                } else {
                    novaMatrizPixel[x][y] = this.matrizPixel[i][j];
                }
                // System.out.println("X("+x+" , "+y+")");
                coluna++;
            }
            linha++;
        }
        //Chamada da função que reconstruirá a imagem nova
        GetSetPixels.exibirImagem(novaAltura, novaLargura, novaMatrizPixel, "Shear");

        return null;
    }

    public Image translação() {

        return null;
    }

    public Image redução() {

        return null;
    }

    public Image ampliação() {

        return null;
    }

    public Image espelhamento() {

        return null;
    }

    public Image reflexão() {

        return null;
    }
}
