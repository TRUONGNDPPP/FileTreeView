package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import treeview.FileTreeItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            /* Create a new MenuBar. */
            MenuBar menu = new MenuBar();
            /* Create new sub menus. */
            Menu menuFile = new Menu("File");
            MenuItem add = new MenuItem("Add");
            MenuItem delete = new MenuItem("Delete");
            menuFile.getItems().addAll(add, delete);

            Menu menuHelp = new Menu("Help");
            MenuItem about = new MenuItem("About");
            menuHelp.getItems().add(about);

            add.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // add a new item
                }
            });

            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // delete the chosen item
                }
            });

            about.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    TextArea about = new TextArea();
                    about.appendText("Double click on text files or images to see the content of them");
                    about.setWrapText(true);
                    Scene scene = new Scene(about, 300, 300);
                    Stage stage = new Stage();
                    stage.setTitle("About program");
                    stage.setScene(scene);
                    stage.show();
                }
            });

            menu.getMenus().addAll(menuFile, menuHelp);

            /* Adding a TreeView and Webview */
            TreeView<File> treeView = new TreeView<File>(
                    new FileTreeItem(new File("D:\\")));
            /* Text viewer*/
            TextArea textViewer = new TextArea();

            treeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(event.getClickCount() == 2) {
                        File chosenFile = treeView.getSelectionModel().getSelectedItem().getValue();

                        // read text file
                        if(isTextFile(chosenFile.getAbsolutePath())) {
                            String line;
                            try {
                                textViewer.clear();
                                BufferedReader br = new BufferedReader(new FileReader(chosenFile));
                                while ((line = br.readLine()) != null) {
                                    textViewer.appendText(line);
                                }
                                textViewer.setWrapText(true);
                                br.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }

                        //read images
                        if(isImage(chosenFile.getAbsolutePath())) {
                            Image image = new Image(chosenFile.toURI().toString());
                            ImageView imageView = new ImageView();
                            imageView.setImage(image);
                            //Setting the image view parameters
                            imageView.setX(10);
                            imageView.setY(10);
                            imageView.setFitWidth(575);
                            imageView.setPreserveRatio(true);
                            //Setting the Scene object
                            Group root = new Group(imageView);
                            Scene scene = new Scene(root, 595, 370);
                            Stage stage = new Stage();
                            stage.setTitle("Displaying Image");
                            stage.setScene(scene);
                            stage.show();
                        }
                    }
                }
            });

            /* Creating a SplitPane and adding file tree and file viewer */
            SplitPane splitView = new SplitPane();
            splitView.getItems().add(treeView);
            splitView.getItems().add(textViewer);

            /* Create a root node as BorderPane. */
            BorderPane root = new BorderPane();
            root.setTop(menu);
            root.setCenter(splitView);

            /* Adding a scene to the stage. */
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("FileTree");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isTextFile(String fileName) {
        String[] extension = {".txt", ".doc", ".docx"};
        return Arrays.stream(extension).anyMatch(entry -> fileName.endsWith(entry));
    }

    private boolean isImage(String fileName) {
        String[] extension = {".jpeg", ".jpg", ".png", ".svg", ".tiff", ".gif", ".webp"};
        return Arrays.stream(extension).anyMatch(entry -> fileName.endsWith(entry));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
