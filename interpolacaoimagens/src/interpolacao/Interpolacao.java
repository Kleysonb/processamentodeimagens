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
                novaMatrizPixel[linha+1][coluna+1] = this.matrizPixel[i][j];
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

    public Image bilinearReducao() {
        System.out.println("Reduzindo por Bilinear");
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
                novaMatrizPixel[linha][coluna] = this.suporteMediaBilinear(this.matrizPixel[linha][coluna], this.matrizPixel[linha][coluna+1],
                                                                            this.matrizPixel[linha+1][coluna], this.matrizPixel[linha+1][coluna+1]);
                coluna++;
            }
            linha++;
        }
        //Chamada da função que reconstruirá a imagem nova
        GetSetPixels.exibirImagem(novaAltura, novaLargura, novaMatrizPixel);
        return null;
    }

    public Image bilinearAmpliacao(){
        System.out.println("Ampliando por Bilinear");
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

        //System.out.println("Matriz: " + novaMatrizPixel[0][0]);
        int linha=0, coluna;
        //int colunaAux=0, linhaAux=0;
        //Populando os dados da nova matriz, com base na ampliação por bilinear da imagem real
        for(int i = aY; i < cY; i++){
            coluna=0;
            for(int j = aX; j < bX; j++){
                int a = this.suporteMediaBilinear(this.matrizPixel[i][j], this.matrizPixel[i][j+1]);
                //int e = this.suporteMediaBilinear(this.matrizPixel[i+1][j], this.matrizPixel[i+1][j+1]);
                int b = this.suporteMediaBilinear(this.matrizPixel[i][j], this.matrizPixel[i+1][j]);
                //int d = this.suporteMediaBilinear(this.matrizPixel[i][j+1], this.matrizPixel[i+1][j+1]);
                int c = this.suporteMediaBilinear(this.matrizPixel[i][j], this.matrizPixel[i][j+1], this.matrizPixel[i+1][j], this.matrizPixel[i+1][j+1]);

                novaMatrizPixel[linha][coluna+1] = a;
                novaMatrizPixel[linha+1][coluna] = b;
                novaMatrizPixel[linha+1][coluna+1] = c;

                //if(colunaAux+2 < novaLargura) novaMatrizPixel[linha+1][coluna+2] = d;
                //if(linhaAux+2 < novaAltura) novaMatrizPixel[linha+2][coluna+1] = e;

                //colunaAux+=2;
                coluna+=2;
            }
            //linhaAux+=2;
            linha+=2;
        }

        //A nova matriz é usada para recontrução da nova imagem
        GetSetPixels.exibirImagem(novaAltura, novaLargura, novaMatrizPixel);
        return null;
    }

    private int suporteMediaBilinear(int p1, int p2){

        //Recuperar Alpha
        int a1 = (p1>>24) & 0xff;
        int a2 = (p2>>24) & 0xff;
        //Novo Tom de Alpha
        int a = (a1+a2)/2;

        //Recupera Red
        int r1 = (p1>>16) & 0xff;
        int r2 = (p2>>16) & 0xff;
        //Novo Tom de Red
        int r = (r1+r2)/2;

        //Recupera Green
        int g1 = (p1>>8) & 0xff;
        int g2 = (p2>>8) & 0xff;
        //Novo Tom de Green
        int g = (g1+g2)/2;

        //Recupera Blue
        int b1 = p1 & 0xff;
        int b2 = p2 & 0xff;
        //Novo Tom de Blue
        int b = (b1+b2)/2;

        //Reconstruir Pixel com valores atualizados
        int p = (a<<24) | (r<<16) | (g<<8) | b;
        return p;
    }

    private int suporteMediaBilinear(int p1, int p2, int p3, int p4){

        //Recuperar Alpha
        int a1 = (p1>>24) & 0xff;
        int a2 = (p2>>24) & 0xff;
        int a3 = (p3>>24) & 0xff;
        int a4 = (p4>>24) & 0xff;
        //Novo Tom de Alpha
        int a = (a1+a2+a3+a4)/4;

        //Recupera Red
        int r1 = (p1>>16) & 0xff;
        int r2 = (p2>>16) & 0xff;
        int r3 = (p3>>16) & 0xff;
        int r4 = (p4>>16) & 0xff;
        //Novo Tom de Red
        int r = (r1+r2+r3+r4)/4;

        //Recupera Green
        int g1 = (p1>>8) & 0xff;
        int g2 = (p2>>8) & 0xff;
        int g3 = (p3>>8) & 0xff;
        int g4 = (p4>>8) & 0xff;
        //Novo Tom de Green
        int g = (g1+g2+g3+g4)/4;

        //Recupera Blue
        int b1 = p1 & 0xff;
        int b2 = p2 & 0xff;
        int b3 = p3 & 0xff;
        int b4 = p4 & 0xff;
        //Novo Tom de Blue
        int b = (b1+b2+b3+b4)/4;

        //Reconstruir Pixel com valores atualizados
        int p = (a<<24) | (r<<16) | (g<<8) | b;
        return p;
    }

}
