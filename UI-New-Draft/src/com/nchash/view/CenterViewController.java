package com.nchash.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;


public class CenterViewController {
    private final static String defaultUploadImage = "file:src/com/nchash/images/drag_and_drop_image.gif";
    // Reference to the main application.
    private Main mainApp;

    @FXML
    private ImageView mainImageView;
    @FXML
    private Label pathToFileLabel;

    public CenterViewController() {
        System.out.println("Hello from CenterViewController");
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    public void initialize(){
        System.out.println("Initializing Center View");
        setDefaultUploadImage(defaultUploadImage);
    }

    /**
     * This function opens the file explorer window. The file selected must be an image.
     * Once the image is selected the default image will be set to the selected image.
     */
    public void handleUploadBtn(){
        String pathToFile = openFileExplorer();
        if(pathToFile != null){
            setDefaultUploadImage(pathToFile);
        }
    }


    /**
     * This function will open the file explorer. The user will pick a file
     * and the path of the file will be returned else it'll returns null.
     * @return path to file String. Null if no file is selected.
     */
    private String openFileExplorer(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File Explorer");
        File imageFile = fileChooser.showOpenDialog(null);
        if(imageFile != null){
            StringBuilder pathToFile = new StringBuilder("file:");
            pathToFile.append(imageFile.getAbsolutePath());
            return pathToFile.toString();
        }
        return null;
    }

    /**
     * This function will handle the drag over file feature.
     * If the file is accepted the handle drop function will be called else
     * the previous image will remain.
     * @param event
     */
    @FXML
    private void handleDragOver(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    /**
     * Once the handleDragOver() is finished this function will
     * set the default image to what was drag and dropped.
     * @param event
     * @return A string containing the path to file.
     * @throws FileNotFoundException
     */
    @FXML
    private void handleDrop(DragEvent event){
        try {
            String pathToFile = getPathToImageDragAndDrop(event);
            setDefaultUploadImage(pathToFile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

//        // Add observable list data to the table
//        personTable.setItems(mainApp.getPersonData());
    }

    /**
     * This function takes in an DragEvent object and returns a String
     * containing the path to the image. As of right now it only returns 1 file.
     * The function can be changed to return multiple files in the DragEvent.
     * @param event
     * @return
     */
    private String getPathToImageDragAndDrop(DragEvent event){
        StringBuilder pathToImage = new StringBuilder("File:");
        pathToImage.append(event.getDragboard().getFiles().get(0));
        event.consume();
        return pathToImage.toString();
    }

    /**
     * Sets the main image in application to any image.
     * @param pathToFile
     */
    private void setDefaultUploadImage(String pathToFile){

        mainImageView.setImage(new Image(pathToFile));
        pathToFileLabel.setText(pathToFile);
    }

}
