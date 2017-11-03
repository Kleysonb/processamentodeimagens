package sample;

//import interpolacao.Interpolacao;
import algoritmo.Algoritmo;
import algoritmo.GetSetPixels;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {

    @FXML
    private Button abrirArquivo;

    @FXML
    private ImageView fechamentoImg;

    @FXML
    private ImageView originalImg;

    private Algoritmo algoritmo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void seletorArquivo() {
        Stage mainStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir Arquivo");
        try{
            boolean b = fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Imagem", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(mainStage);
            if (selectedFile != null) {
                this.algoritmo = new Algoritmo(selectedFile);

                int[][] matrizPixels = this.algoritmo.converterParaBinaria();
                BufferedImage bufferedImage = GetSetPixels.exibirImagem(this.algoritmo.getAltura(), this.algoritmo.getLargura(), matrizPixels);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                this.originalImg.setImage(image);

                //Dilatação
//                int[][] novaMatriz = this.algoritmo.dilatacao(matrizPixels, 5);
//                BufferedImage bufferedImageN = GetSetPixels.exibirImagem(this.algoritmo.getAltura(), this.algoritmo.getLargura(), novaMatriz);
//                Image imageN = SwingFXUtils.toFXImage(bufferedImageN, null);
//                this.fechamentoImg.setImage(imageN);

//                //Erosão
//                int[][] novaMatriz = this.algoritmo.erosao(matrizPixels, 42);
//                BufferedImage bufferedImageN = GetSetPixels.exibirImagem(this.algoritmo.getAltura(), this.algoritmo.getLargura(), novaMatriz);
//                Image imageN = SwingFXUtils.toFXImage(bufferedImageN, null);
//                this.fechamentoImg.setImage(imageN);

                //Abertura
//                int[][] novaMatriz = this.algoritmo.abertura(matrizPixels, 80);
//                BufferedImage bufferedImageN = GetSetPixels.exibirImagem(this.algoritmo.getAltura(), this.algoritmo.getLargura(), novaMatriz);
//                Image imageN = SwingFXUtils.toFXImage(bufferedImageN, null);
//                this.fechamentoImg.setImage(imageN);

                //Fechamento
                int[][] novaMatriz = this.algoritmo.fechamento(matrizPixels, 80);
                BufferedImage bufferedImageN = GetSetPixels.exibirImagem(this.algoritmo.getAltura(), this.algoritmo.getLargura(), novaMatriz);
                Image imageN = SwingFXUtils.toFXImage(bufferedImageN, null);
                this.fechamentoImg.setImage(imageN);
            }

        } catch (IOException ex) {
            System.out.println("Error");
        }

    }

}