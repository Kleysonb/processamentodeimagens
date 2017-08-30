package sample;


import interpolacao.Interpolacao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button abrirArquivo;

    private Interpolacao interpolacao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.interpolacao = new Interpolacao();
    }

    @FXML
    private void seletorArquivo() {
        //System.out.println("oii");
        Stage mainStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir Arquivo");
        boolean b = fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            System.out.println("Arquivo Enviado para Interpolação.");
            this.interpolacao.seletorArquivo(selectedFile);
        }
    }

}
