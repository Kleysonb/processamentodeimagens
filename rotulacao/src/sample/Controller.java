package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import rotulacao.Algoritmo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button abrirArquivo;

    private  Algoritmo algoritmo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private ImageView binaria;

    @FXML
    private ImageView rotulada;

    @FXML
    private Label binariaTxt;

    @FXML
    private Label rotuladaTxt;

    @FXML
    private Label quantidadeTxt;

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
                System.out.println("Arquivo Enviado para Interpolação.");
                this.algoritmo = new Algoritmo(selectedFile);
                //this.algoritmo.converterParaBinaria();
                this.algoritmo.rotular();

                Image image1 = SwingFXUtils.toFXImage(this.algoritmo.binaria, null);
                this.binaria.setImage(image1);
                this.binariaTxt.setText("Binária");

                Image image2 = SwingFXUtils.toFXImage(this.algoritmo.rotulada, null);
                this.rotulada.setImage(image2);
                this.rotuladaTxt.setText("Rotulada");

                this.quantidadeTxt.setText("Quantidade de Regiões: " + this.algoritmo.quantidadeRegioes);
            }
//            BufferedImage bufferedImage = ImageIO.read(selectedFile);
//            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (IOException ex) {
            System.out.println("Error");
        }
    }

}