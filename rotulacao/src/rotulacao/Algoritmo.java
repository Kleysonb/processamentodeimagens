package rotulacao;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Algoritmo {

    private int[][] matrizPixels;
    private BufferedImage img;
    public BufferedImage binaria, rotulada;
    public int quantidadeRegioes;

    public Algoritmo(File arquivo) throws IOException {
        this.img = ImageIO.read(arquivo);
        this.matrizPixels = GetSetPixels.gerarMatrizPixel(this.img);
    }

    public void rotular() throws IOException {
        System.out.println("Rotulando elementos");
        Map<Integer, ArrayList<Integer>> equivalente = new HashMap<Integer, ArrayList<Integer>>();

        ArrayList<Integer> chave = new ArrayList<>();
        ArrayList<Integer> valor = new ArrayList<>();

        int altura = img.getWidth();
        int largura = img.getHeight();

        Color preto = new Color(0,0,0,255);
        Color branco = new Color(255,255,255, 255);

        int BRANCO = branco.getRGB();
        int PRETO = preto.getRGB();

        int novaMatrizPixel[][] = new int[altura][largura];
        int matrizBinaria[][] = converterParaBinaria();

        int label = 2;
        //ArrayList<Integer> labelUsada = new ArrayList<>();

        for (int i = 1; i < altura; i++) {
            for (int j = 1; j < largura; j++) {
                //Se for Branco, move para o próximo Pixel
                if (matrizBinaria[i][j] == PRETO) {
                    //System.out.println(matrizBinaria[i][j] + " == " + PRETO);
                    //Se for Preto, analisa-se s e r
                    int r = matrizBinaria[i - 1][j];
                    int s = matrizBinaria[i][j - 1];
                    if (s == r && r == BRANCO) {
                        //Se s e r forem branco e p for preto assinala-se um novo label para p e
                        //anota-se que esse label já foi usado
                        novaMatrizPixel[i][j] = label;
                        //labelUsada.add(label);
                        label++;
                    } else {
                        if (s == r && r == PRETO) {
                            //Se r e s forem preto e possuem diferentes labels, assinala-se
                            //um dos labels a p e anota-se que esses labels são
                            //equivalentes.
                            if (novaMatrizPixel[i][j - 1] != novaMatrizPixel[i - 1][j]) {
                                //p recebe r
                                novaMatrizPixel[i][j] = novaMatrizPixel[i - 1][j];
                                //Assinalando equivalência, usando a tabela ASCII

                                if(equivalente.containsKey(novaMatrizPixel[i][j])){
                                    if(!equivalente.get(novaMatrizPixel[i][j]).contains(novaMatrizPixel[i][j - 1])){
                                        equivalente.get(novaMatrizPixel[i][j]).add(novaMatrizPixel[i][j - 1]);
                                    }
                                }else{
                                    equivalente.put(novaMatrizPixel[i][j], new ArrayList<>());
                                    equivalente.get(novaMatrizPixel[i][j]).add(novaMatrizPixel[i][j - 1]);
                                }

                                chave.add(novaMatrizPixel[i - 1][j]);
                                valor.add(novaMatrizPixel[i][j - 1]);
                            } else {
                                //Se r e s são preto e possuem labels iguais
                                //p recebe r
                                //System.out.println("Label Igual");
                                novaMatrizPixel[i][j] = novaMatrizPixel[i - 1][j];
                            }
                        } else {
                            if (r == PRETO || s == PRETO) {
                                if (r == PRETO) {
                                    //p recebe r
                                    novaMatrizPixel[i][j] = novaMatrizPixel[i - 1][j];
                                } else {
                                    //p recebe s
                                    novaMatrizPixel[i][j] = novaMatrizPixel[i][j - 1];
                                }
                            }
                        }
                    }
                }
//                else {
//                    novaMatrizPixel[i][j] = BRANCO;
//                }
            }
        }
        //exibirAux(altura, largura, novaMatrizPixel);
        System.out.println();
        verificarEquivalencia(equivalente, altura, largura, novaMatrizPixel);
        //GetSetPixels.exibirImagem(altura, largura, novaMatrizPixel);
    }

    private void verificarEquivalencia(Map<Integer, ArrayList<Integer>> equivalente, int altura, int largura, int[][] novaMatrizPixel) throws IOException {
        System.out.println("Verificando Equivalência");
        ArrayList<Integer> chave = new ArrayList<>();
        ArrayList<Integer> valor = new ArrayList<>();
        for (int key : equivalente.keySet()) {
            //ArrayList<Integer> value = equivalente.get(key);
            for (int j = 0; j < equivalente.get(key).size(); j++) {
                chave.add(key);
                valor.add(equivalente.get(key).get(j));
            }
            //System.out.println(key + " = " + value.toString());
        }

        for (int i = 0; i < chave.size(); i++){
            int valorTemp = valor.get(i);
            //System.out.println("Valor = " + valorTemp);
            for (int j = 0; j < valor.size(); j++) {
                //System.out.println(chave.get(j) + " = " + valor.get(j));
                //System.out.println(chave.get(j) + " == " + valorTemp);
                if(chave.get(j) == valorTemp){
                    chave.set(j, chave.get(i));
                }
            }
        }
//        for (int j = 0; j < chave.size(); j++) {
//            System.out.println(chave.get(j) + " = " + valor.get(j));
//        }

        ArrayList<Integer> pixel = new ArrayList<>();
        ArrayList<Integer> cor = new ArrayList<>();

        Random gerador = new Random();

        for(int i = 0; i < altura; i++){
            for(int j = 0; j < largura; j++){
                if(novaMatrizPixel[i][j] != 0){
                    int aux = colorir(chave, valor, novaMatrizPixel[i][j]);
                    if(pixel.contains(aux)){
                        int novoPixel = cor.get(pixel.indexOf(aux));
                        novaMatrizPixel[i][j] = novoPixel;
                    }else{
                        pixel.add(aux);
                        int a = 255;
                        int r = gerador.nextInt(255);
                        int g = gerador.nextInt(255);
                        int b = gerador.nextInt(255);
                        int p = p = (a<<24) | (r<<16) | (g<<8) | b;
                        cor.add(p);
                    }
                }else{
                    novaMatrizPixel[i][j] = -1;
                }
            }
        }

        this.quantidadeRegioes = cor.size();
        System.out.println("Quantidade de Regiões: " + this.quantidadeRegioes);
        this.rotulada = ImageIO.read(GetSetPixels.exibirImagem(altura, largura, novaMatrizPixel, "rotulacaoNova.jpg"));
          //exibirAux(altura, largura, novaMatrizPixel);
    }

    private int colorir(ArrayList<Integer> chave, ArrayList<Integer> valor, int pixel){
        for(int i = 0; i < chave.size(); i++){
            if(chave.get(i) == pixel){
                return pixel;
            }
        }
        for(int i = 0; i < chave.size(); i++){
            if(valor.get(i) == pixel){
                return chave.get(i);
            }
        }
        return 0;
    }

    private void exibirAux(int altura, int largura, int[][] novaMatrizPixel) {
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                System.out.print(novaMatrizPixel[i][j] + "");
            }
            System.out.println();
        }
    }

    public int[][] converterParaBinaria() throws IOException {
        System.out.println("Convertendo imagem para binária");
        int altura = img.getWidth();
        int largura = img.getHeight();

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

        //exibirAux(altura, largura, novaMatrizPixel);
        this.binaria = ImageIO.read(GetSetPixels.exibirImagem(altura, largura, novaMatrizPixel, "binaria.jpg"));
        return novaMatrizPixel;

    }


}
