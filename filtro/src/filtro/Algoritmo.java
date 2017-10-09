package filtro;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Algoritmo {

    private int[][] matrizPixels;
    private BufferedImage img;

    public Algoritmo(File arquivo) throws IOException {
        this.img = ImageIO.read(arquivo);
        this.matrizPixels = GetSetPixels.gerarMatrizPixel(this.img);
    }

    public void aplicarFiltroMedia(){

        int altura = img.getWidth();
        int largura = img.getHeight();

        int novaMatrizPixel[][] = new int[altura][largura];

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

        for(int i = 1 ; i < altura-1 ; i ++){
            for (int j = 1; j < largura-1; j ++){
                int a = this.matrizPixels[i-1][j-1];
                int b = this.matrizPixels[i-1][j];
                int c = this.matrizPixels[i-1][j+1];
                int d = this.matrizPixels[i][j-1];
                int e = this.matrizPixels[i][j];
                int f = this.matrizPixels[i][j+1];
                int g = this.matrizPixels[i+1][j-1];
                int h = this.matrizPixels[i+1][j];
                int k = this.matrizPixels[i+1][j+1];
                novaMatrizPixel[i][j] = suporteMedia(a,b,c,d,e,f,g,h,k);
            }
        }
        //exibirAux(altura, largura, this.matrizPixels);

        //A nova matriz é usada para recontrução da nova imagem
        GetSetPixels.exibirImagem(altura, largura, novaMatrizPixel, "filtroMedio");

    }

    private int suporteMedia(int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8, int p9){

        //Recuperar Alpha
        int a1 = (p1>>24) & 0xff;
        int a2 = (p2>>24) & 0xff;
        int a3 = (p3>>24) & 0xff;
        int a4 = (p4>>24) & 0xff;
        int a5 = (p5>>24) & 0xff;
        int a6 = (p6>>24) & 0xff;
        int a7 = (p7>>24) & 0xff;
        int a8 = (p8>>24) & 0xff;
        int a9 = (p9>>24) & 0xff;
        //Novo Tom de Alpha
        int a = (a1+a2+a3+a4+a5+a6+a7+a8+a9)/9;

        //Recupera Red
        int r1 = (p1>>16) & 0xff;
        int r2 = (p2>>16) & 0xff;
        int r3 = (p3>>16) & 0xff;
        int r4 = (p4>>16) & 0xff;
        int r5 = (p5>>16) & 0xff;
        int r6 = (p6>>16) & 0xff;
        int r7 = (p7>>16) & 0xff;
        int r8 = (p8>>16) & 0xff;
        int r9 = (p9>>16) & 0xff;
        //Novo Tom de Red
        int r = (r1+r2+r3+r4+r5+r6+r7+r8+r9)/9;

        //Recupera Green
        int g1 = (p1>>8) & 0xff;
        int g2 = (p2>>8) & 0xff;
        int g3 = (p3>>8) & 0xff;
        int g4 = (p4>>8) & 0xff;
        int g5 = (p5>>8) & 0xff;
        int g6 = (p6>>8) & 0xff;
        int g7 = (p7>>8) & 0xff;
        int g8 = (p8>>8) & 0xff;
        int g9 = (p9>>8) & 0xff;
        //Novo Tom de Green
        int g = (g1+g2+g3+g4+g5+g6+g7+g8+g9)/9;

        //Recupera Blue
        int b1 = p1 & 0xff;
        int b2 = p2 & 0xff;
        int b3 = p3 & 0xff;
        int b4 = p4 & 0xff;
        int b5 = p5 & 0xff;
        int b6 = p6 & 0xff;
        int b7 = p7 & 0xff;
        int b8 = p8 & 0xff;
        int b9 = p9 & 0xff;
        //Novo Tom de Blue
        int b = (b1+b2+b3+b4+b5+b6+b7+b8+b9)/9;

        //Reconstruir Pixel com valores atualizados
        int p = (a<<24) | (r<<16) | (g<<8) | b;
        return p;
    }

    private void exibirAux(int altura, int largura, int[][] novaMatrizPixel) {
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                System.out.print(novaMatrizPixel[i][j] + "");
            }
            System.out.println();
        }
    }
}
