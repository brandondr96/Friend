import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class FriendDriver extends Application{
	private static final Paint BACK_COLOR = Color.LIGHTBLUE;
	private static final int SCENE_WIDTH = 500;
	private static final int SCENE_HEIGHT = 600;
	private int state;
	
	public void start(Stage stage) {
		state = 0;
		Pane pane = setObjects();
		Scene scene = new Scene(pane,SCENE_WIDTH,SCENE_HEIGHT,BACK_COLOR);
		stage.setScene(scene);
		stage.setTitle("Friend");
		stage.setMinHeight(SCENE_HEIGHT);
		stage.setMinWidth(SCENE_WIDTH);
		pane.setBackground(new Background(new BackgroundFill(BACK_COLOR,null,null)));
		stage.show();
	}
	
	private Pane setObjects() {
		HBox center = new HBox(20);
		center.setAlignment(Pos.CENTER);
		VBox order = new VBox(50);
		order.setAlignment(Pos.CENTER);
		center.getChildren().add(order);
		Speech friend = new Speech();
		neuroTester friendtest = new neuroTester();
		
		TextArea outputField = new TextArea();
		outputField.setEditable(false);
		outputField.setWrapText(true);
		outputField.setPrefSize(300, 450);
		outputField.setText("Commands that exist:\n--clear : clears the chat\n--init    : adds words to program memory\n--reset : clears program memory and chat\n--test : Use unfinished\n--simple : Revert to default\n\n\n");
		
		TextField inputField = new TextField();
		inputField.setPromptText("Type here...");
		inputField.setPrefSize(300,40);
		inputField.setOnAction(click->{
			outputField.appendText(inputField.getText()+"\n\n");
			inputField.setEditable(false);
			String next = "";
			if(state==0) {
				next = friend.respond(inputField.getText());
			}
			else if(state==1) {
				next = friendtest.respond(inputField.getText());
			}
			outputField.appendText("\t"+next+"\n\n");
			inputField.setEditable(true);
			if(inputField.getText().equals("--clear")) {
				outputField.setText("");
			}
			if(inputField.getText().equals("--test")) {
				state = 1;
				outputField.setText("");
			}
			if(inputField.getText().equals("--simple")) {
				state = 0;
				outputField.setText("");
			}
			if(inputField.getText().equals("--init")) {
				friend.getPrevData();
				outputField.setText("--init\n\tMy memories have returned\n");
			}
			if(inputField.getText().equals("--reset")) {
				friendtest.clearMemory();
				friend.clearMemory();
				outputField.setText("");
			}
			inputField.setText("");
		});
		order.getChildren().add(outputField);
		order.getChildren().add(inputField);
		return center;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	

}
