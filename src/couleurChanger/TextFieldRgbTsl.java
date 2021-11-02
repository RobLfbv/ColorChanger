package couleurChanger;

import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TextFieldRgbTsl implements EventHandler<KeyEvent>{
	@Override
	public void handle(KeyEvent event) {
		TextField tf = (TextField) event.getTarget();
		VBox vb = (VBox) tf.getParent();
		HBox hbrgbTsl = (HBox) vb.getParent();
		VBox vbParent = (VBox) hbrgbTsl.getParent();
		HBox top = (HBox) vbParent.getChildren().get(0);
		ColorPicker cp = (ColorPicker) top.getChildren().get(2);
		TextField tfAsk = (TextField) top.getChildren().get(1);
		Rectangle rc = (Rectangle) top.getChildren().get(3);

		VBox vbtsl = (VBox) hbrgbTsl.getChildren().get(1);
		TextField tfHue = (TextField) vbtsl.getChildren().get(2);
		TextField tfSaturation = (TextField) vbtsl.getChildren().get(4);
		TextField tfBrightness = (TextField) vbtsl.getChildren().get(6);
		
		VBox vbrgb = (VBox) hbrgbTsl.getChildren().get(0);
		TextField tfRed = (TextField) vbrgb.getChildren().get(2);
		TextField tfGreen = (TextField) vbrgb.getChildren().get(4);
		TextField tfBlue = (TextField) vbrgb.getChildren().get(6);
		
		if (tf.getId().substring(0, 3).equals("rgb")  ) {
			System.out.println("rgb");
			System.out.println(event.getCharacter());
			

			System.out.println(tf.getText());

			try {
				int r = Integer.parseInt( tfRed.getText());
				int g = Integer.parseInt( tfGreen.getText());
				int b = Integer.parseInt( tfBlue.getText());

				try {
					if (tf.getId().substring(3, 6).equals("Red")) {
						r = Integer.parseInt( tfRed.getText() + event.getCharacter());
					}else if(tf.getId().substring(3, 6).equals("Gre")) {
						g = Integer.parseInt( tfGreen.getText() + event.getCharacter() );
					}else if (tf.getId().substring(3, 6).equals("Blu")) {
						b = Integer.parseInt( tfBlue.getText() + event.getCharacter() );
					}
				} catch (Exception e) {
					System.out.println("Supression");
				}

				cp.setValue( Color.rgb(r,g,b));
				tfAsk.setText(  "#" + cp.getValue().toString().substring(2, 8));
				rc.setFill(cp.getValue().grayscale());

				tfHue.setText(       "" + (int) cp.getValue().getHue());
				tfSaturation.setText("" + cp.getValue().getSaturation());
				tfBrightness.setText("" + cp.getValue().getBrightness());
				
			} catch (Exception e) {

			}
		}else if(tf.getId().substring(0, 3).equals("tsl")) {
			System.out.println("tsl");
			
			try {
				double h = Double.parseDouble( tfHue.getText() );
				double s = Double.parseDouble( tfSaturation.getText());
				double b = Double.parseDouble( tfBrightness.getText());
				
				try {
					if (tf.getId().substring(3, 6).equals("Hue")) {
						h = Integer.parseInt( tfHue.getText() + event.getCharacter());
					}else if(tf.getId().substring(3, 6).equals("Sat")) {
						s = Integer.parseInt( tfSaturation.getText() + event.getCharacter() );
					}else if (tf.getId().substring(3, 6).equals("Bri")) {
						b = Integer.parseInt( tfBrightness.getText() + event.getCharacter() );
					}
				} catch (Exception e) {
					
					System.out.println("Supression");
				}
				System.out.println(b);
				cp.setValue( Color.hsb(h,s,b));
				tfAsk.setText(  "#" + cp.getValue().toString().substring(2, 8));
				rc.setFill(cp.getValue().grayscale());
				
				tfRed.setText("" +   (int) (cp.getValue().getRed()   *  255));
				tfGreen.setText("" + (int) (cp.getValue().getGreen() *  255));
				tfBlue.setText("" +  (int) (cp.getValue().getBlue()  *  255));
				
			} catch (Exception e) {
				System.out.println("TSL Pas valide");
			}
		}


	}
}
