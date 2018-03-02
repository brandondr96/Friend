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
	
	public void start(Stage stage) {
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
		
		
		TextArea outputField = new TextArea();
		outputField.setEditable(false);
		outputField.setWrapText(true);
		outputField.setPrefSize(300, 450);
		
		TextField inputField = new TextField();
		inputField.setPromptText("Type here...");
		inputField.setPrefSize(300,40);
		inputField.setOnAction(click->{
			outputField.appendText(inputField.getText()+"\n");
			inputField.setEditable(false);
			String next = friend.respond(inputField.getText());
			outputField.appendText("\t"+next+"\n\n");
			inputField.setEditable(true);
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