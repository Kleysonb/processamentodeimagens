package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import histograma.Algoritmo;
//import rotulacao.Algoritmo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button abrirArquivo;

    private Algoritmo algoritmo;

    @FXML
    private BarChart<?, ?> histograma;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void seletorArquivo() throws IOException {
        Stage mainStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir Arquivo");
        boolean b = fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagem", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            System.out.println("Arquivo Enviado para Interpolação.");
            this.algoritmo = new Algoritmo(selectedFile);
            this.algoritmo.vale();

            histograma.getData().clear();

            int [] valoresHistograma = this.algoritmo.histogramaImagem;
            XYChart.Series series1 = new XYChart.Series();
            for(int i=0; i<valoresHistograma.length; i++)
                series1.getData().add(new XYChart.Data(i+"", valoresHistograma[i]));

            histograma.getData().addAll(series1);
        }
    }

}