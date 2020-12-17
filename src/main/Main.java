package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import treeview.FileTreeItem;

import java.io.File;
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
                    // show helping info
                }
            });

            menu.getMenus().addAll(menuFile, menuHelp);

            /* Adding a TreeView to the left of the center section */
            TreeView<File> treeView = new TreeView<File>(
                    new FileTreeItem(new File("D:\\")));
            /* Text viewer */
            TextArea textViewer = new TextArea();

            treeView.setOnMouseClicked(event -> {
                File chosenFile = treeView.getSelectionModel().getSelectedItem().getValue();
                if(chosenFile.getAbsolutePath().endsWith(".txt")) {
                    FileReader reader = new FileReader();
                    List<String> lines = reader.read(chosenFile);
                    lines.forEach(textViewer::appendText);
                }
            });

            /* Creating a SplitPane and adding file tree and file view. */
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

    public static void main(String[] args) {
        launch(args);
    }
}
