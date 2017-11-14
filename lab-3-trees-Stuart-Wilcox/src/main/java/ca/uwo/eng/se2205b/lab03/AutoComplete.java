package ca.uwo.eng.se2205b.lab03;


import com.sun.javafx.collections.ImmutableObservableList;
import javafx.application.Application;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Simple AutoComplete example to utilize a Trie.
 */
public class AutoComplete extends Application {
    
    @FXML
    private ListView<String> options;

    @FXML
    private TextField input;

    @FXML
    private Spinner<Integer> resultCounter;

    @FXML
    private Label countLabel;

    /**
     * Trie of words
     */
    private Trie prefixTrie;

    /**
     * Load the {@link #prefixTrie} field with the values from the provided dictionary.
     * There is a single word per line.
     */
    private void loadTrie() {
        // TODO SE2205B
        prefixTrie = new LinkedTrie();
        InputStream io = AutoComplete.class.getResourceAsStream("/dictionary.txt");
        StringBuilder sb = new StringBuilder();
        int a;
        char b;
        while(true){
            try{
                a = io.read();
                b = (char)a;
                if(b == '\n'){
                    prefixTrie.put(sb.toString().trim());
                    sb = new StringBuilder();
                }
                else if(a==-1){
                    break;
                }
                else{
                    sb.append(b);
                }
            }
            catch(IOException e){
                break;
            }
        }
    }

    /**
     * Read from the input box, and the spinner and calculate the auto complete and update the "countLabel"
     * with the number of results.
     */
    private void loadAutoComplete() {
        // TODO SE2205B
        Collection<String> results = prefixTrie.getNextN(input.getText(), resultCounter.getValue());
        //update countLabel here
        countLabel.setText(Integer.toString(results.size()));
        //clear the old list
        options.getItems().clear();
        //create the new list
        options.getItems().addAll(results);
    }


    @FXML
    protected void initialize() {

        // TODO SE2205B
        input.setDisable(false);
        resultCounter.setEditable(false);
        input.textProperty().addListener((event) -> loadAutoComplete());


        ////////////////////////////////////////
        // DO NOT CHANGE BELOW
        ////////////////////////////////////////

        resultCounter.valueProperty().addListener((obs, oldValue, newValue) -> {
            loadAutoComplete();
        });

        countLabel.setText("0");

        loadTrie();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AutoComplete.fxml"));
    
        Scene scene = new Scene(root);
    
        stage.setTitle("Auto-complete Example");
        stage.setScene(scene);
        stage.show();
    }
    
    
}
