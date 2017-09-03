package rotulacao;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Algoritmo {

    private int[][] matrizPixels;
    private BufferedImage img;

    public Algoritmo(File arquivo) throws IOException {
        this.img = ImageIO.read(arquivo);
        this.matrizPixels = GetSetPixels.gerarMatrizPixel(this.img);
    }

    /*
        -1 == Branco
        0 == Preto

        1- Se p = -1, move-se para o próximo pixel;
        2- Se p = 0, analisa-se s e r:
        2.1- Se r e s forem -1, assinala-se um novo label para p e
        anota-se que esse label já foi usado;
        2.2- Se r ou s for 0, assinala-se label correspondente a p;
        2.3- Se r ou s forem 0 e têm o mesmo label assinala-se esse
        label a p;
        2.4- Se r e s forem 0 e possuem diferentes labels assinala-se
        um dos labels a p e anota-se que esses labels são
        equivalentes.
        3- Troca-se cada label pelo seu equivalente.
    */

    public void rotular(){
        int altura = img.getWidth();
        int largura = img.getHeight();

        //int label = 1;
        int novaMatrizPixel[][] = new int[altura][largura];
        int matrizBinaria[][] = converterParaBinaria();

        int label = 97;
        ArrayList<Integer> labelUsada = new ArrayList<>();

        for(int i = 1; i < altura; i++){
            for(int j = 1; j < largura; j++){
                if(matrizBinaria[i][j] == 0){
                    int s = matrizBinaria[i-1][j];
                    int r = matrizBinaria[i][j-1];
                    if(s == r && r == -1){
                        novaMatrizPixel[i][j] = label;
                        labelUsada.add(label);
                        label++;
                    }else{
                        if(r == 0 || s == 0){
                            if(novaMatrizPixel[i][j-1] == novaMatrizPixel[i-1][j]){
                                novaMatrizPixel[i][j] = novaMatrizPixel[i-1][j];
                            }else{
                                if(r == 0) novaMatrizPixel[i][j] = novaMatrizPixel[i][j-1];
                                else novaMatrizPixel[i][j] = novaMatrizPixel[i-1][j];
                            }
                        }else{

                        }
                    }
                }
            }
        }
    }

    private int[][] converterParaBinaria(){
        int altura = img.getWidth();
        int largura = img.getHeight();

        int novaMatrizPixel[][] = new int[altura][largura];

        for(int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {

                if((this.matrizPixels[i][j] & 0xff) > 127 ){
                    //Branco
                    novaMatrizPixel[i][j] = -1;
                }else{
                    //Preto
                    novaMatrizPixel[i][j] = 0;
                }
            }
        }

        return novaMatrizPixel;
        //GetSetPixels.exibirImagem(altura, largura, novaMatrizPixel);

    }



}
