package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import operacoes.Aritmetica;
import operacoes.Geometrica;

import javax.imageio.ImageIO;
import javax.swing.*;
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
    private ImageView vizinhoReducao;

    @FXML
    private ImageView vizinhoAmpliacao;

    @FXML
    private ImageView bilinearReducao;

    @FXML
    private ImageView bilinearAmpliacao;

    private Aritmetica aritmetica;
    private Geometrica geometrica;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //this.interpolacao = new Interpolacao();
    }

    @FXML
    private void seletorArquivo() {
        //System.out.println("oii");
        Stage mainStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir Arquivo");
        try{
            boolean b = fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Imagem", "*.png", "*.jpg", "*.jpeg","*.TIF"));
            // File selectedFile = fileChooser.showOpenDialog(mainStage);
            int x = JOptionPane.showConfirmDialog(null,"Deseja realizar operação Aritmética ?");
            System.out.println(x);
            if(x == 0){
                File selectedFile = fileChooser.showOpenDialog(mainStage);
                File selectedFile2 = fileChooser.showOpenDialog(mainStage);
                if (selectedFile != null && selectedFile2 != null) {
                    System.out.println("Arquivo Enviado para Operação Artitmética.");
                    this.aritmetica = new Aritmetica(selectedFile,selectedFile2);
                    this.aritmetica.adicao();
                    this.aritmetica.subtração();
                    this.aritmetica.multiplicação();
                    this.aritmetica.divisão();
                }
                // BufferedImage bufferedImage = ImageIO.read(selectedFile);
                // Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            }else if(x == 1){
                File selectedFile = fileChooser.showOpenDialog(mainStage);
                if (selectedFile != null) {
                    System.out.println("Arquivo Enviado para Interpolação.");
                    this.geometrica = new Geometrica(selectedFile);
                    this.geometrica.Shear(0.2);
                }
                // BufferedImage bufferedImage = ImageIO.read(selectedFile);
                // Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            }
        } catch (IOException ex) {
            //Logger.getLogger(JavaFXPixel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error");
        }

    }

}
