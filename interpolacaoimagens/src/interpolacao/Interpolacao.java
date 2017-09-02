package interpolacao;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Interpolacao {

    public int matrizPixel[][];
    public BufferedImage img;

    public Interpolacao(File arquivo) throws IOException {
        //Arquivo é recebido e convertido em imagem
        this.img = ImageIO.read(arquivo);
        //Retorna uma Matriz com os Pixels da Imagem
        this.matrizPixel = GetSetPixels.gerarMatrizPixel(this.img);
    }

    public Image vizinhoReducao() {
        System.out.println("Reduzindo por Vizinho mais Próximo");
        int altura = this.img.getWidth();
        int largura = this.img.getHeight();

        //As novas dimensões serão 2 vezes menores que a real
        int novaAltura = altura/2;
        int novaLargura = largura/2;

        //Criando a matriz da nova imagem
        int novaMatrizPixel[][] = new int[novaAltura][novaLargura];

        int linha=0, coluna;
        //Populando os dados da nova matriz
        for(int i=0; i<altura; i+=2){
            coluna=0;
            for(int j=0; j<largura; j+=2){
                novaMatrizPixel[linha][coluna] = this.matrizPixel[i][j];
                coluna++;
            }
            linha++;
        }
        //Chamada da função que reconstruirá a imagem nova
        GetSetPixels.exibirImagem(novaAltura, novaLargura, novaMatrizPixel);
        return null;
    }

    public Image vizinhoAmpliacao(){
        System.out.println("Ampliando por Vizinho mais Próximo");
        int altura = this.img.getWidth();
        int largura = this.img.getHeight();

        //Aqui é definido a quantidade de Zoom que será aplicado, alterando a porcentagem
        double porcentademBorda = 0.2;
        //Cálculo para saber o quanto de borda será removido, dada a porcentagem
        int alturaAux = (int) (altura * porcentademBorda);
        int larguraAux = (int) (largura * porcentademBorda);

        //Definindo os pontos da imagem que serão utilizados para aplicar o Zoom (Retângulo)
        int aX = larguraAux;
        int aY = alturaAux;

        int bX = largura-larguraAux;
        int bY = alturaAux;

        int cX = larguraAux;
        int cY = altura-alturaAux;

        int dX = largura - larguraAux;
        int dY = altura-alturaAux;

        //Calculando as novas dimensões da imagem, que serão o dobro do recorte da imagem, relativo a porcentagem
        int novaAltura = (altura - (2*alturaAux))*2;
        int novaLargura = (largura - (2*larguraAux))*2;

//        System.out.println("Nova Altura: " + novaAltura + "\nNova Largura: " + novaLargura);
//        System.out.println("aX: " + aX + "\naY: " + aY);
//        System.out.println("cY: " + cY + "\nbX: " + bX);

        //Criando a matriz da nova imagem
        int novaMatrizPixel[][] = new int[novaAltura][novaLargura];

        int linha=0, coluna;
        //Populando os dados da nova matriz, com base na ampliação por vizinho mais próximos da imagem real
        for(int i = aY; i < cY; i++){
            coluna=0;
            for(int j = aX; j < bX; j++){
                novaMatrizPixel[linha][coluna] = this.matrizPixel[i][j];
                novaMatrizPixel[linha][coluna+1] = this.matrizPixel[i][j];
                novaMatrizPixel[linha+1][coluna] = this.matrizPixel[i][j];
                novaMatrizPixel[linha+1][coluna] = this.matrizPixel[i][j];
                coluna+=2;
            }
            //aux++;
            //System.out.println(aux+"/"+tot);
            linha+=2;
        }

        //A nova matriz é usada para recontrução da nova imagem
        GetSetPixels.exibirImagem(novaAltura, novaLargura, novaMatrizPixel);
        return null;
    }

}
