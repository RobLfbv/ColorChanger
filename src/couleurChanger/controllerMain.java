package couleurChanger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Limiter les decimals dans rgb/tsl et limiter le nombre de caractÃ¨re en hexa
//Mise a jour de rgb/tsl vers colorpicker et tfHexa
//Mise a jour de tfHexa vers tgb/tsl
//Mise a jour de l'apercu en gris


public class controllerMain {
	Integer[] nb = {1,2,3,4,5,6,7,8,9,10};
	@FXML
	VBox VBoxAllColor, vboxResult;
	@FXML
	ChoiceBox<Integer> choiceBoxNbColor;
	@FXML
	Button myButtonValidateColor, buttonGenerate, generateFile;


	public void initialize() {
		System.out.println("Initialisation...");
		choiceBoxNbColor.getItems().addAll(nb);
		choiceBoxNbColor.getSelectionModel().selectedItemProperty().addListener( new UpdateNbChoiceBox());
		choiceBoxNbColor.setValue(3);
	} 

	class UpdateNbChoiceBox implements ChangeListener<Integer>{
		@Override
		public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
			//On Ajoute que ce qu'on a besoin
			if (choiceBoxNbColor.getValue() > VBoxAllColor.getChildren().size()) {
				//Partie de Gauche
				for (int i = VBoxAllColor.getChildren().size() ; i < choiceBoxNbColor.getValue() ; i++) {		
					VBox vbMain = new VBox();
					HBox hbHaut = new HBox();
					HBox hbMid = new HBox();

					vbMain.getChildren().add(hbHaut);
					vbMain.getChildren().add(hbMid);
					//-------------Label--------------------------//
					Label lb = new Label("Couleur " + (i+1) );
					hbMid.getChildren().add(lb);	
					hbHaut.getChildren().add(lb);
					//-------------TextField----------------------//
					TextField tf = new TextField();
					tf.setPromptText("#123456");
					hbHaut.getChildren().add(tf);
					tf.addEventHandler(KeyEvent.KEY_TYPED, new textFieldPlayer()); 
					//-------------ColorPicker-------------- -----//
					ColorPicker cp = new ColorPicker();
					cp.setPrefSize(50, 25);
					hbHaut.getChildren().add(cp);
					cp.addEventHandler(ActionEvent.ACTION, new ColorPickerEvent());
					//----------------Maj-Du-TextField---------------//
					tf.setText("#" + cp.getValue().toString().substring(2,8));
					//-------------Rectangle Gray-------------------//
					Rectangle r = new Rectangle(50, 25);
					r.setFill(Color.WHITE);
					hbHaut.getChildren().add(r);
					//-------------Button-------------------//
					Button scroll = new  Button("⋀");
					hbMid.getChildren().add(scroll);
					scroll.addEventHandler(ActionEvent.ACTION, new ButtonScroll());
					
					VBoxAllColor.getChildren().add(vbMain);
				}
				//Partie de Droite
				for (int i = vboxResult.getChildren().size() ; i < choiceBoxNbColor.getValue() ; i++) {	
					VBox vbMain = new VBox();
					vbMain.setPadding(new Insets(5, 15, 20, 0));
					HBox hb = new HBox();
					vbMain.getChildren().add(hb);

					//-------------Label--------------------------//
					Label lb = new Label("Couleur " + (i+1) );
					lb.setMinSize(80, 20);
					hb.getChildren().add(lb);
					//-------------TextField----------------------//
					TextField tf = new TextField();
					hb.getChildren().add(tf);
					tf.addEventHandler(KeyEvent.KEY_TYPED, new textFieldPlayer()); 
					//-------------Rectangle----------------------//
					Rectangle rc = new Rectangle(50, 25);
					rc.setFill(Color.WHITE);
					hb.getChildren().add(rc);
					Rectangle r = new Rectangle(50, 25);
					r.setFill(Color.WHITE);
					hb.getChildren().add(r);

					vboxResult.getChildren().add(vbMain);
				}
			//On retire ce qu'il y a en trop
			}else {
				List<Node> allColorChoice = new ArrayList<Node>();
				List<Node> allColorAsk = new ArrayList<Node>();

				allColorChoice.addAll(VBoxAllColor.getChildren().subList(0, choiceBoxNbColor.getValue())  );
				allColorAsk.addAll(   vboxResult.getChildren().subList(0, choiceBoxNbColor.getValue())    );
				
				VBoxAllColor.getChildren().clear();
				VBoxAllColor.getChildren().addAll(allColorChoice);

				vboxResult.getChildren().clear(); 
				vboxResult.getChildren().addAll(allColorAsk);
			}
		}
	}

	public void validateColor(ActionEvent event) {
		
		List<Color> color = new ArrayList<Color>();

		for(int i = 0; i < VBoxAllColor.getChildren().size(); i++) {
			VBox vb = (VBox) VBoxAllColor.getChildren().get(i) ;
			HBox hbColor = ((HBox) vb.getChildren().get(0));
			ColorPicker cp = (ColorPicker) hbColor.getChildren().get(2) ;
			color.add(cp.getValue());
		}


		color = ColorChanger.differencierCouleurs(color);

		for(int i = 0; i < VBoxAllColor.getChildren().size(); i++) {

			VBox result = (VBox) vboxResult.getChildren().get(i);
			HBox hbResult = (HBox)result.getChildren().get(0);
			TextField tf = (TextField) hbResult.getChildren().get(1);	
			Rectangle rc = (Rectangle) hbResult.getChildren().get(2);

			tf.setText("#" +  color.get(i).toString().substring(2, 8))  ; 
			rc.setFill(color.get(i));

			HBox hb = (HBox)result.getChildren().get(0);
			Rectangle rcGray = (Rectangle) hb.getChildren().get(3);

			rcGray.setFill(color.get(i).grayscale());
		}
		 
	}

	public void generateRandom(ActionEvent event) {
		List<Color> color = ColorChanger.randomColor();

		for(int i = 0; i < VBoxAllColor.getChildren().size(); i++) {
			VBox vb = (VBox) VBoxAllColor.getChildren().get(i) ;
			HBox hbColor = ((HBox) vb.getChildren().get(0));

			ColorPicker cp = (ColorPicker) hbColor.getChildren().get(2) ;
			cp.setValue(   Color.web(  color.get(i).toString().substring(2,8) ));

			TextField tf = (TextField) hbColor.getChildren().get(1) ;
			tf.setText("#" + cp.getValue().toString().substring(2, 8));


			Rectangle r = (Rectangle) hbColor.getChildren().get(3);

			r.setFill(cp.getValue().grayscale());
		}
		
		color = ColorChanger.differencierCouleurs(color);
		
		for(int i = 0; i < vboxResult.getChildren().size() ; i++) {

			VBox result = (VBox) vboxResult.getChildren().get(i);
			HBox hbResult = (HBox)result.getChildren().get(0);
			TextField tf = (TextField) hbResult.getChildren().get(1);	

			Rectangle rc = (Rectangle) hbResult.getChildren().get(2);

			tf.setText(  "#" +  color.get(i).toString().substring(2,8) ); 
			rc.setFill(color.get(i));

			HBox hb = (HBox)result.getChildren().get(0);
			Rectangle rcGray = (Rectangle) hb.getChildren().get(3);

			rcGray.setFill(color.get(i).grayscale());


		}
	}
	public void ouvertureApercu(ActionEvent event) throws IOException {
		List<Color> color = new ArrayList<Color>();
		for(int i = 0; i < VBoxAllColor.getChildren().size(); i++) {
			VBox vb = (VBox) VBoxAllColor.getChildren().get(i) ;
			HBox hbColor = ((HBox) vb.getChildren().get(0));
			ColorPicker cp = (ColorPicker) hbColor.getChildren().get(2) ;
			color.add(cp.getValue());
		}
		
		color = ColorChanger.differencierCouleurs(color);
		
        VBox principal = new VBox();
        principal.setPrefSize(600,389);
        principal.setMinSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);
        principal.setMaxSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);
        VBox.setVgrow(principal, Priority.ALWAYS);
        HBox secondaryLayout = new HBox();
        secondaryLayout.setPrefSize(600,356);
        secondaryLayout.setMinSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);

        secondaryLayout.setMaxSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);
        HBox.setHgrow(secondaryLayout, Priority.ALWAYS);

        HBox buttonBox = new HBox();
        
        buttonBox.setPrefSize(600,36);
        HBox.setHgrow(buttonBox, Priority.ALWAYS);
        buttonBox.setMinSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);

        buttonBox.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

        Button retour = new Button("RETOUR");
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.getChildren().add(retour);
        secondaryLayout.getChildren().addAll(Graphs.initPieChart(color));
        principal.getChildren().add(secondaryLayout);
        principal.getChildren().add(buttonBox);
        
        Scene secondScene = new Scene(principal, 600, 389);
        Stage newWindow = new Stage();
        newWindow.setTitle("Aperçu graphique");
        newWindow.setScene(secondScene);
        Stage primaryStage = (Stage) myButtonValidateColor.getScene().getWindow();
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(primaryStage);
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);
        newWindow.setResizable(false);
        newWindow.show();
        retour.addEventHandler(ActionEvent.ACTION, e -> {
        	newWindow.close();
        	});
    }
	
	public void generFile() {
		List<Color> color = new ArrayList<Color>();
		for(int i = 0; i < VBoxAllColor.getChildren().size(); i++) {
			VBox vb = (VBox) VBoxAllColor.getChildren().get(i) ;
			HBox hbColor = ((HBox) vb.getChildren().get(0));
			ColorPicker cp = (ColorPicker) hbColor.getChildren().get(2) ;
			color.add(cp.getValue());
		}
		ColorChanger c = new ColorChanger();
		c.inTxt(color);
	}
}
