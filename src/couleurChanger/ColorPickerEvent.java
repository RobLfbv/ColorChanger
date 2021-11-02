package couleurChanger;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ColorPickerEvent implements EventHandler<Event>{

	@Override
	public void handle(Event event) {
		System.out.println("Change de Couleur");
		ColorPicker cp = (ColorPicker) event.getSource();
		HBox parent = (HBox) cp.getParent();
		//------------Maj-TextField-------------------------//
		TextField tf = (TextField) parent.getChildren().get(1);
		tf.setText("#" + cp.getValue().toString().substring(2, 8)  );
		//------------Maj-RectangleGris-------------------------//
		Rectangle rc = (Rectangle) parent.getChildren().get(3);
		rc.setFill(cp.getValue().grayscale());
		
		//Maj de RGB/TSL si le menu est d�rouler
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
	}
}
