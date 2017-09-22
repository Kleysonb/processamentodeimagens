package operacoes;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class GetSetPixels{

    public static int[][] gerarMatrizPixel(BufferedImage img){
        //Recuperando Altura e Largura da Imagem
        int altura = img.getWidth();
        int largura = img.getHeight();
        System.out.println("Altura: " + altura + "\nLargura: " + largura + "\n");

        //Criando matriz que receberá os pixels da imagem real
        int matrizPixel[][] = new int[altura][largura];
        //Populando matriz com os pixels
        for(int i=0; i<altura; i++){
            for(int j=0; j<largura; j++){
                matrizPixel[i][j] = img.getRGB(i, j);
            }
        }

        //exibirImagem(altura, largura, matrizPixel, img);
        return matrizPixel;
    }

    public static File exibirImagem(int altura, int largura, int[][] matrizPixel, String nome){
        //Gerando uma nova imagem com as suas respectivas dimensões
        BufferedImage novaImagem = new BufferedImage( altura, largura, BufferedImage.TYPE_INT_RGB);

        //Atualizando a imagem gerada pixel a pixel
        for(int i=0; i<altura; i++){
            for(int j=0; j<largura; j++){
                //Dada as coordenadas 'i' e 'j', temos cada posição de pixel da nova imagem, que pode ser alterada por qualquer pixel desejado
                novaImagem.setRGB(i, j, matrizPixel[i][j]);
            }
        }
        try{
            System.out.println("Nova Imagem Gerada");
            File f = new File(nome+".jpg");
            ImageIO.write(novaImagem, "jpg", f);
            return f;
        }catch(IOException e){
            System.out.println(e);
            return null;
        }
    }

    public static void main(String args[])throws IOException{
        BufferedImage img = null;
        File f;

        //Leitura da Imagem
        try{
            f = new File("C:\\Users\\kleys\\Desktop\\6 Periodo\\Processamento de Imagens\\processamentodeimagens\\interpolacaoimagens\\src\\interpolacao\\futurama.jpg");
            img = ImageIO.read(f);

        }catch(IOException e){
            System.out.println(e + "\nErro");
        }

        gerarMatrizPixel(img);

        //Aqui é recuperado o valor do Pixel da Imagem
//        int p = img.getRGB(0,0);
//
//        System.out.println("Pixel: " + p);
//        //Recupera alpha
//        int a = (p>>24) & 0xff;
//
//        //Recupera red
//        int r = (p>>16) & 0xff;
//
//        //Recupera green
//        int g = (p>>8) & 0xff;
//
//        //Recupera blue
//        int b = p & 0xff;
//
//        System.out.println("Alpha: " + a);
//        System.out.println("Red: " + r);
//        System.out.println("Green: " + g);
//        System.out.println("Blue: " + b);

//        a = 255;
//        r = 100;
//        g = 150;
//        b = 200;
//
//        //set the pixel value
//        p = (a<<24) | (r<<16) | (g<<8) | b;
        //img.setRGB(0, 0, p);

        //write image
//        try{
//            f = new File("Output.jpg");
//            ImageIO.write(img, "jpg", f);
//        }catch(IOException e){
//            System.out.println(e);
//        }
    }//main() ends here
}//class ends here