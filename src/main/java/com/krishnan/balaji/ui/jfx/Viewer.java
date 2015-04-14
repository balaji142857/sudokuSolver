package com.krishnan.balaji.ui.jfx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.krishnan.balaji.Sudoku;

public class Viewer extends Application{
	
	 public static void main(String[] args) {
	        launch(args);
	    }
	    
    @Override
    public void start(Stage primaryStage) {
	        primaryStage.setTitle("Sudoku Solver v0.1");
	        Button solve,reset,stop;
	        final TextField[] inputs = new TextField[81];
	        for(int i=0;i<9;i++){
	        	for(int j=0;j<9; j++){
	        	inputs[i*9+j] = new TextField();
	        	final TextField dummy = inputs[i*9+j];
	        	if(i<3)
	        		if(j<3)
	        			dummy.setStyle("-fx-background-color:rgb(244, 211, 211)");
	        		else if(j<6)
	        			dummy.setStyle("-fx-background-color:rgb(109,246,79)");
	        		else
	        			dummy.setStyle("-fx-background-color:#00F0F0");
	        	else if(i<6)
	        		if(j<3)
	        			dummy.setStyle("-fx-background-color:rgb(241,246,79)");
	        		else if(j<6)
	        			dummy.setStyle("-fx-background-color:rgb(140,156,226)");
	        		else
	        			dummy.setStyle("-fx-background-color:#F0F0F0");
	        	else
	        		if(j<3)
	        			dummy.setStyle("-fx-background-color:white");
	        		else if(j<6)
	        			dummy.setStyle("-fx-background-color:pink");
	        		else
	        			dummy.setStyle("-fx-background-color:#E7E2BC");
	        	dummy.getStyleClass().add("someStyle");
	        	//dummy.setPrefWidth(70);
	        	/*dummy.textProperty().addListener(new ChangeListener<String>() {
	                @Override
	                public void changed(ObservableValue<? extends String> observable,
	                        String oldValue, String newValue) {
	                	if (newValue.matches("\\d")) {
	                    } else {
	                    	dummy.setText(oldValue);
	                    }
	                	if (dummy.getText().length() > 1) {
	                		dummy.setText(dummy.getText().substring(0, 1));
	                	}
	                }
	        });*/
	        }
	        }
	        reset = new Button();
	        stop = new Button();
	        solve = new Button();
	        solve.setText("Solve");
	        solve.setPrefSize(100, 20);
	        reset = new Button();
	        reset.setText("Reset");
	        reset.setPrefSize(100, 20);
	        
	        stop = new Button();
	        stop.setText("Stop");
	        stop.setPrefSize(100, 20);
	        
	        BorderPane root = new BorderPane();
	        HBox hbox = new HBox();
	        hbox.setStyle("-fx-background-color: #336699;");
	        hbox.setPadding(new Insets(15, 12, 15, 12));
	        hbox.setSpacing(10);
	        hbox.getChildren().addAll(reset,stop,solve);
	        root.setTop(hbox);
	        root.setBottom(new HBox());
	        GridPane centrePane  = new GridPane();
	        for(int i=0; i<9; i++)
	        	for(int j=0; j<9; j++)
	        		centrePane.add(inputs[9*i+j], i, j);
	        centrePane.getStyleClass().add("centrePane");
	        root.setLeft(new VBox());
	        root.setCenter(centrePane);
	        root.setRight(new VBox());
	        solve.setOnAction(new EventHandler<ActionEvent>() {
	 
	            @Override
	            public void handle(ActionEvent event) {
	            	Integer[][] intArray = new Integer[9][9];
	                System.out.println("Hello World!");
	                for(int i=0;i<9;i++){
	                	for(int j=0;j<9;j++){
	                		System.out.print(inputs[i*9+j].getText());
	                		if(inputs[i*9+j].getText().length()==1)
	                			intArray[j][i]=Integer.parseInt(inputs[i*9+j].getText());
	                	}
	                }
	                Sudoku.initialize(intArray);
	                Integer[][] solved = Sudoku.solveIt();
	                for(int i=0;i<9;i++){
	                	for(int j=0;j<9;j++){
	                		if(solved[i][j]!=null)
	                			inputs[j*9+i].setText(solved[i][j]+"");
	                	}
                	}
	            }
	        });
	        Scene scene = new Scene(root, 300, 250);
	        scene.getStylesheets().add("test.css");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }
}
