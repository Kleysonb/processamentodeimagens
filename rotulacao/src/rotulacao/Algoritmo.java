package rotulacao;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Algoritmo {

    private int[][] matrizPixels;
    private BufferedImage img;

    public Algoritmo(File arquivo) throws IOException {
        this.img = ImageIO.read(arquivo);
        this.matrizPixels = GetSetPixels.gerarMatrizPixel(this.img);
    }

    public void rotular(){
        System.out.println("Rotulando elementos");
        Map<Integer,Integer> equivalente = new HashMap<Integer, Integer>();
        int altura = img.getWidth();
        int largura = img.getHeight();
        int BRANCO = -1;
        int PRETO = 0;

        int novaMatrizPixel[][] = new int[altura][largura];
        int matrizBinaria[][] = converterParaBinaria();

        int label = 97;
        //ArrayList<Integer> labelUsada = new ArrayList<>();

        for(int i = 1; i < altura; i++){
            for(int j = 1; j < largura; j++){
                //Se for Branco, move para o próximo Pixel
                if(matrizBinaria[i][j] == PRETO){
                    //Se for Preto, analisa-se s e r
                    int r = matrizBinaria[i-1][j];
                    int s = matrizBinaria[i][j-1];
                    if(s == r && r == BRANCO){
                        //Se s e r forem branco e p for preto assinala-se um novo label para p e
                        //anota-se que esse label já foi usado
                        novaMatrizPixel[i][j] = label;
                        //labelUsada.add(label);
                        label++;
                    }else{
                        if(s == r && r == PRETO){
                            //Se r e s forem preto e possuem diferentes labels, assinala-se
                            //um dos labels a p e anota-se que esses labels são
                            //equivalentes.
                            if(novaMatrizPixel[i][j-1] != novaMatrizPixel[i-1][j]){
                                //p recebe r
                                novaMatrizPixel[i][j] = novaMatrizPixel[i-1][j];
                                //Assinalando equivalência, usando a tabela ASCII
                                equivalente.put(novaMatrizPixel[i][j], novaMatrizPixel[i][j-1]);
                            }else{
                                //Se r e s são preto e possuem labels iguais
                                //p recebe r
                                novaMatrizPixel[i][j] = novaMatrizPixel[i-1][j];
                            }
                        }else {
                            if (r == PRETO || s == PRETO) {
                                if(r == PRETO){
                                    //p recebe r
                                    novaMatrizPixel[i][j] = novaMatrizPixel[i-1][j];
                                }else{
                                    //p recebe s
                                    novaMatrizPixel[i][j] = novaMatrizPixel[i][j-1];
                                }
                            }
                        }
                    }
                }else{
                    novaMatrizPixel[i][j] = BRANCO;
                }
            }
        }
        verificarEquivalencia(equivalente, altura, largura, novaMatrizPixel);
        System.out.println();
        //exibirAux(altura, largura, novaMatrizPixel);
        GetSetPixels.exibirImagem(altura, largura, novaMatrizPixel);
    }

    private void verificarEquivalencia(Map<Integer, Integer> equivalente, int altura, int largura, int[][] novaMatrizPixel){
        ArrayList<Integer> passou = new ArrayList<>();
        for(int i = 0; i < altura; i++){
            for(int j = 0; j < largura; j++){
                if(novaMatrizPixel[i][j] != 0){
                    passou.add(novaMatrizPixel[i][j]);
                    while(equivalente.get(novaMatrizPixel[i][j]) != null && !passou.contains(equivalente.get(novaMatrizPixel[i][j])) ){
                        int aux = equivalente.get(novaMatrizPixel[i][j]);
                        passou.add(aux);
                        novaMatrizPixel[i][j] = aux;
                    }
                }
            }
        }
    }

    private void exibirAux(int altura, int largura, int[][] novaMatrizPixel){
        for(int i = 0; i < altura; i++){
            for(int j = 0; j < largura; j++){
                System.out.print(novaMatrizPixel[i][j] + " ");
            }
            System.out.println();
        }
    }

    /*
        1- Se p = branco, move-se para o próximo pixel;
        2- Se p = preto, analisa-se s e r:
        2.1- Se r e s forem branco, assinala-se um novo label para p e
        anota-se que esse label já foi usado;
        2.2- Se r ou s for preto, assinala-se label correspondente a p;
        2.3- Se r ou s forem preto e têm o mesmo label assinala-se esse
        label a p;
        2.4- Se r e s forem preto e possuem diferentes labels assinala-se
        um dos labels a p e anota-se que esses labels são
        equivalentes.
        3- Troca-se cada label pelo seu equivalente.
    */

    public int[][] converterParaBinaria(){
        System.out.println("Convertendo imagem para binária");
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

        //GetSetPixels.exibirImagem(altura, largura, novaMatrizPixel);
        return novaMatrizPixel;

    }



}
