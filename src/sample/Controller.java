package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    Button info;
    @FXML
    Button start;
    @FXML
    TextField getT;
    @FXML
    Button startT;
    @FXML
    Button startV;
    @FXML
    Button startSec;
    @FXML
    Button onLamp;
    @FXML
    TextField getV;
    @FXML
    TextField getSec;
    @FXML
    TextField getL;
    @FXML
    ImageView imageView;
    @FXML
    Button finish;

    private int tempR;
    private int randSec;
    private boolean flag;
    private int seconds;
    private boolean isStopped;
    private boolean isLamp;
    private int temp;
    //private Thread thread;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        randSec = 0;
        seconds = 0;
        tempR = 0;
        flag = false;
        isLamp = false;
        isStopped = false;
        temp = 0;
        getT.setDisable(true);
        startT.setDisable(true);
        getL.setDisable(true);
        onLamp.setDisable(true);
        getV.setDisable(true);
        startV.setDisable(true);
        getSec.setDisable(true);
        startSec.setDisable(true);
    }

    public void finished(){
        seconds = 0;
        tempR = 0;
        temp = 0;
        flag = false;
        isLamp = false;
        isStopped = false;
        getSec.setText("0");
        getT.setText("");
        getV.setText("");
        getL.setText("");
        InputStream input = getClass().getResourceAsStream("/sample/img/lamp1.png");
        getT.setDisable(true);
        startT.setDisable(true);
        getL.setDisable(true);
        onLamp.setDisable(true);
        getV.setDisable(true);
        startV.setDisable(true);
        getSec.setDisable(true);
        startSec.setDisable(true);
        onLamp.setText("Включить лампочку");
        startSec.setText("Включить секундомер");
    }

    public boolean getInfo() throws IOException {
        // Загружаем fxml-файл и создаём новую сцену для всплывающего диалогового окна.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("FXML/info.fxml"));
        AnchorPane page = loader.load();
        // Создаём диалоговое окно Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Задание");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
        dialogStage.showAndWait();
        return true;
    }

    public void started(){
        getT.setDisable(false);
        startT.setDisable(false);
        getL.setText("");
    }

    public void startTemp(){
        if(!flag) {
            tempR = 24 + (int) (Math.random() * 4);
            temp = tempR;
            Thread thread = new Thread(runnable);
            thread.start();
            getL.setText(String.valueOf(temp));
            getV.setDisable(false);
            startV.setDisable(false);
        }

    }
    public void startVilag(){
        getL.setDisable(false);
        getSec.setDisable(false);
        startSec.setDisable(false);
        onLamp.setDisable(false);
        switch (tempR){
            case 24: {
                getV.setText("75%");
                break;
            }
            case 25: {
                getV.setText("70%");
                break;
            }
            case 26: {
                getV.setText("65%");
                break;
            }
            case 27: {
                getV.setText("60%");
                break;
            }
            case 28: {
                getV.setText("55%");
                break;
            }
        }

    }
    public void startLamp() {
        randSec = 33 + (int) (Math.random() * 14);
        if(!isLamp) {
            InputStream input = getClass().getResourceAsStream("/sample/img/lamp2.png");
            Image image = new Image(input);
            imageView.setImage(image);
            isLamp = true;
            onLamp.setText("Выключить лампочку");
            Thread thread1 = new Thread(lampas);
            thread1.start();
        }
        else {
            InputStream input = getClass().getResourceAsStream("/sample/img/lamp1.png");
            Image image = new Image(input);
            imageView.setImage(image);
            isLamp = false;
            Thread thread1 = new Thread(lampDown);
            thread1.start();
            onLamp.setText("Включить лампочку");
        }
    }
    public void startSecond(){
        if(!isStopped) {
            isStopped = true;
            startSec.setText("Выключить секундомер");
            Thread thread1 = new Thread(secundary);
            thread1.start();
        }
        else {
            isStopped = false;
            startSec.setText("Включить секундомер");
        }
    }


    private Runnable secundary = new Runnable() {
        @Override
        public synchronized void run() {
            while (true){
                try {
                    getSec.setText(String.valueOf(seconds));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isStopped) {
                    seconds++;
                }
            }
        }
    };

    private Runnable runnable =  new Runnable() {
        @Override
        public synchronized void run() {
            int i = 1;
            while (i<=tempR){
                try {
                    getT.setText(String.valueOf(i));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
    };



    private Runnable lampas = new Runnable() {
        @Override
        public synchronized void run() {
            temp = tempR;
            while (true){
                try {
                    getL.setText(String.valueOf(temp));
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isLamp)
                    temp++;
            }
        }
    };

    private Runnable lampDown = new Runnable() {
        @Override
        public void run() {
            while (temp>tempR){
                try {
                    getL.setText(String.valueOf(temp));
                    Thread.sleep(randSec/3*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!isLamp)
                    temp--;
            }
        }
    };
}
