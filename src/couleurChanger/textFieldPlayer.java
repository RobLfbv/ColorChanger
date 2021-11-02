package couleurChanger;

import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class textFieldPlayer implements EventHandler<KeyEvent>{

	@Override
	public void handle(KeyEvent event) {		
		String old =  ( (TextField) event.getTarget()).getText() ; 
		
		//Maj si couleur valide
		try {
			TextField tf = (TextField) event.getSource();
			HBox parent = (HBox) tf.getParent();
			ColorPicker cp = (ColorPicker) parent.getChildren().get(2);
			cp.setValue(Color.web("0x" + old.substring(1, old.length())));
			
			//Maj de RGB/TSL si le menu est dérouler			
			try {
				VBox superParent = (VBox) parent.getParent();
				HBox hbScroll = (HBox) superParent.getChildren().get(2);
				VBox vbRGB = (VBox) hbScroll.getChildren().get(0);
				VBox vbTsl = (VBox) hbScroll.getChildren().get(1);

				((TextField) vbRGB.getChildren().get(2)).setText("" + (int) (cp.getValue().getRed()   * 255 ));
				((TextField) vbRGB.getChildren().get(4)).setText("" + (int) (cp.getValue().getGreen() * 255 ));
				((TextField) vbRGB.getChildren().get(6)).setText("" + (int) (cp.getValue().getBlue()  * 255 ));
				
				
				((TextField) vbTsl.getChildren().get(2)).setText("" + cp.getValue().getHue() )  ;
				((TextField) vbTsl.getChildren().get(4)).setText("" + cp.getValue().getSaturation() )  ;
				((TextField) vbTsl.getChildren().get(6)).setText("" + cp.getValue().getBrightness() )  ;

			} catch (Exception e) {
				System.out.println("Bouton non déroulé");
			}
			
		} catch (Exception e) {
			System.out.println("Couleur Pas valide");
		}	
	};

}
