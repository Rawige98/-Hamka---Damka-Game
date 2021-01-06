package View;

import java.awt.image.BufferedImage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import com.github.sarxos.webcam.Webcam;
import javafx.stage.Stage;


public class WebCamPreviewController implements Initializable {


    @FXML Button btnStartCamera;
    @FXML Button btnProceedCamera;
    @FXML Button btnDisposeCamera,btnResetCamera;
    @FXML AnchorPane bpWebCamPaneHolder;
    @FXML ImageView imgWebCamCapturedImage;
    private BufferedImage grabbedImage;
    private Webcam selWebCam = null;
    private boolean stopCamera = false;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
    Image mainiamge;
    private String userData;
    @FXML
    private ImageView pic;
    @FXML
    public static ImageView profilePic = new ImageView();
    public static ImageView profilePic2 = new ImageView();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try{
            initializeWebCam(0);
        }catch(Exception e){
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            userData = (String) btnStartCamera.getScene().getWindow().getUserData();
        });

    }
    protected void setImageViewSize() {

        double height = bpWebCamPaneHolder.getHeight();
        double width  = bpWebCamPaneHolder.getWidth();
        imgWebCamCapturedImage.setFitHeight(height);
        imgWebCamCapturedImage.setFitWidth(width);
        imgWebCamCapturedImage.prefHeight(height);
        imgWebCamCapturedImage.prefWidth(width);
        imgWebCamCapturedImage.setPreserveRatio(true);

    }
    protected void initializeWebCam(final int webCamIndex) {

        Task<Void> webCamIntilizer = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                if(selWebCam == null)
                {
                    selWebCam = Webcam.getWebcams().get(webCamIndex);
                    selWebCam.open();
                }else
                {
                    closeCamera();
                    selWebCam = Webcam.getWebcams().get(webCamIndex);
                    selWebCam.open();

                }
                startWebCamStream();
                return null;
            }

        };

        new Thread(webCamIntilizer).start();
        btnProceedCamera.setDisable(true);
        btnResetCamera.setDisable(true);
    }

    protected void startWebCamStream() {

        stopCamera  = false;
        Task<Void> task = new Task<Void>() {


            @Override
            protected Void call() throws Exception {

                while (!stopCamera) {
                    try {
                        if ((grabbedImage = selWebCam.getImage()) != null) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                        mainiamge = SwingFXUtils
                                            .toFXImage(grabbedImage, null);
                                    imageProperty.set(mainiamge);
                                }
                            });

                           grabbedImage.flush();

                        }
                    } catch (Exception e) {
                    } finally {

                    }

                }

                return null;

            }

        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        imgWebCamCapturedImage.imageProperty().bind(imageProperty);

    }

    private void closeStage() {
        ((Stage) btnStartCamera.getScene().getWindow()).close();
    }

    private void closeCamera()
    {
        if(selWebCam != null)
        {
            selWebCam.close();
        }
    }

    public void proceed(){
        profilePic.setImage(imgWebCamCapturedImage.getImage());
        closeStage();
    }
    
    public void proceed2(){
        profilePic2.setImage(imgWebCamCapturedImage.getImage());
        closeStage();
    }

    public void proceedToAddPartner(){

    }

    public void stopCamera(ActionEvent event)
    {
        stopCamera = true;
        btnStartCamera.setDisable(true);
        btnResetCamera.setDisable(false);
        btnProceedCamera.setDisable(false);
    }

    public void startCamera(ActionEvent event)
    {
        stopCamera = false;
        startWebCamStream();
        btnStartCamera.setDisable(false);
        btnResetCamera.setDisable(true);
        btnProceedCamera.setDisable(true);
    }

    public void disposeCamera(ActionEvent event)
    {
        //stopCamera = true;
        //closeCamera();
        //Webcam.shutdown();
        //btnStopCamera.setDisable(true);
        //btnStartCamera.setDisable(true);
        closeStage();
    }


}