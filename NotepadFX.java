import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NotepadFX extends Application implements EventHandler<ActionEvent> {

   // JavaFX Controls
   private TextField filenameTextField;
   private TextArea fileContentTextArea;
   private Button saveButton;
   private Button toggleButton;
   private VBox filesViewer;
   private List<Button> filesButtons;

   private String toggleButtonFilesAll = "Show txt files ONLY";
   private String toggleButtonFilesTxtOnly = "Show all files";
   private boolean toggleButtonBool_AllFiles = true;

   @Override
   public void start(Stage appWindowStage) {

      // Create Content Grid Panel
      GridPane contentPanel = new GridPane(); // Column, Row || 0,0 Top-Left 4,4 Bottom-Right
      // 0,0 1,0
      // 0,1 1,0

      // Initialize VerticalBox and Toggle & Save Button
      saveButton = new Button("Save");
      saveButton.setOnAction(this);

      toggleButton = new Button(toggleButtonFilesAll);
      toggleButton.setOnAction(this);

      HBox buttonsBox = new HBox();
      buttonsBox.getChildren().add(toggleButton);
      buttonsBox.getChildren().add(saveButton);
      buttonsBox.setMargin(saveButton, new Insets(20, 20, 20, 20));
      buttonsBox.setMargin(toggleButton, new Insets(20, 20, 20, 20));
      contentPanel.add(buttonsBox, 1, 0);

      // Initilize Text label and textbox
      Label filenameLabel = new Label("Filename:");
      contentPanel.add(filenameLabel, 0, 1);

      filenameTextField = new TextField();
      contentPanel.add(filenameTextField, 1, 1);

      // Initialize content label and content textbox
      Label contentLabel = new Label("Content:");
      contentPanel.add(contentLabel, 0, 2);

      fileContentTextArea = new TextArea();
      fileContentTextArea.setWrapText(true);
      contentPanel.add(fileContentTextArea, 1, 2);

      // Create File Viewer
      filesViewer = new VBox();
      filesButtons = new ArrayList<>();

      // Create Split Pane
      SplitPane splitPane = new SplitPane();
      splitPane.getItems().addAll(filesViewer, contentPanel);
      splitPane.setDividerPositions(0.2);

      // Create Scene
      Scene scene = new Scene(splitPane, 1000, 500); // Horizontal || Vertical

      // Populate File Viewer
      redrawFileButtons(toggleButtonBool_AllFiles);
      // toggleTxtFiles();

      // Set Stage
      appWindowStage.setTitle("NotePro");
      appWindowStage.setScene(scene);
      appWindowStage.show();
   }

   @Override
   public void handle(ActionEvent event) {

      // Execute if 'saveButton' clicked
      if (event.getSource() == saveButton) {
         String filename = filenameTextField.getText();
         String content = fileContentTextArea.getText();

         // Try to create new file / save file contents only if filename not empty
         if (!filename.isEmpty()) {

            // Write file if file ends with .txt
            if (filename.endsWith(".txt")) {
               try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                  writer.println(content);
                  writer.flush();
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setContentText("File saved successfully!");
                  alert.showAndWait();
               } catch (IOException ex) {
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                  alert.setContentText("Error saving file: " + ex.getMessage());
                  alert.showAndWait();
               }
               redrawFileButtons(toggleButtonBool_AllFiles);
            } else {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("Filename doesn't end in .txt");
               alert.showAndWait();
            }

         } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a filename!");
            alert.showAndWait();
         }
      }

      // Execute if 'toggleButton' clicked
      else if (event.getSource() == toggleButton) {
         toggleTxtFiles();
      }
   }

   // Adds a button to the file viewer for each file in folder
   private void addButtonToViewer(String filename, boolean isTxtFile) {
      Button filesButton = new Button(filename);

      // Execute function if Button is Pressed
      filesButton.setOnAction(e -> {
         filenameTextField.setText(filename);

         // Read file if textfile
         if (isTxtFile) {
            try {
               BufferedReader reader = new BufferedReader(new FileReader(filename));
               StringBuilder text = new StringBuilder();
               String line;
               while ((line = reader.readLine()) != null) {
                  text.append(line).append("\n");
               }
               reader.close();
               fileContentTextArea.setText(text.toString());
            } catch (IOException ex) {
               ex.printStackTrace();
            }
         } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
               StringBuilder text = new StringBuilder();
               String line;
               while ((line = reader.readLine()) != null) {
                  text.append(line).append("\n");
               }
               reader.close();
               fileContentTextArea.setText(text.toString());
            } catch (IOException ex) {
               ex.printStackTrace();
            }
         }
      });

      // Sets FileButton
      if (isTxtFile) {
         filesButton.setStyle("-fx-text-fill: green;");
      } else {
         filesButton.setStyle("-fx-text-fill: red;");
      }
      filesViewer.getChildren().add(filesButton);
      filesButtons.add(filesButton);
   }

   // Removes all buttons from the file viewer
   private void clearFileViewer() {
      filesViewer.getChildren().clear();
      filesButtons.clear();
   }

   // Refresh file viewer buttons
   private void redrawFileButtons(boolean allFiles) {
      clearFileViewer();
      File[] files = new File(".").listFiles();
      if (files != null) {
         for (File file : files) {

            if (file.isFile() && allFiles) {
               addButtonToViewer(file.getName(), file.getName().endsWith(".txt"));
            } else if (file.isFile() && file.getName().endsWith(".txt")) {
               addButtonToViewer(file.getName(), true);
            }

         }
      }

   }

   // Toggles showing only .txt files in the file viewer
   private void toggleTxtFiles() {

      if (toggleButtonBool_AllFiles) {
         toggleButtonBool_AllFiles = false;
         toggleButton.setText(toggleButtonFilesTxtOnly);

      } else {
         toggleButtonBool_AllFiles = true;
         toggleButton.setText(toggleButtonFilesAll);
      }
      redrawFileButtons(toggleButtonBool_AllFiles);
   }

   public static void main(String[] args) {
      launch(args);
   }
}
