package couleurChanger;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class ButtonScroll implements EventHandler<Event> {

	@Override
	public void handle(Event event) {
		Button btn = (Button) event.getSource() ;
		HBox parent = (HBox) btn.getParent();
		VBox superParent = (VBox) parent.getParent();
		HBox hb = (HBox) superParent.getChildren().get(0);
		ColorPicker cp = (ColorPicker) hb.getChildren().get(2);
		HBox hbBas = new HBox();


		
		if (  btn.getText().equals("⋀")) {
			btn.setText("⋁");
			
			TextField tfRed = new TextField( "" + (int) (cp.getValue().getRed()*255));
			tfRed.setId("rgbRed");
			tfRed.addEventHandler(KeyEvent.KEY_TYPED, new TextFieldRgbTsl() );
			TextField tfBlue = new TextField( "" + (int) (cp.getValue().getBlue()*255));
			tfBlue.setId("rgbBlu");
			tfBlue.addEventHandler(KeyEvent.KEY_TYPED, new TextFieldRgbTsl() );
			TextField tfGreen = new TextField( "" + (int) (cp.getValue().getGreen()*255));
			tfGreen.setId("rgbGre");
			tfGreen.addEventHandler(KeyEvent.KEY_TYPED, new TextFieldRgbTsl() );

			TextField tfHue = new TextField( "" + cp.getValue().getHue());
			tfHue.setId("tslHue");
			tfHue.addEventHandler(KeyEvent.KEY_TYPED, new TextFieldRgbTsl() );
			TextField tfSaturation = new TextField( "" + cp.getValue().getSaturation());
			tfSaturation.setId("tslSat");
			tfSaturation.addEventHandler(KeyEvent.KEY_TYPED, new TextFieldRgbTsl() );
			TextField tfBrightness = new TextField( "" + cp.getValue().getBrightness());
			tfBrightness.setId("tslBri");
			tfBrightness.addEventHandler(KeyEvent.KEY_TYPED, new TextFieldRgbTsl() );
			
			VBox rgb = new VBox();

			rgb.getChildren().add(new Label("RGB"));
			rgb.getChildren().add(new Label("Rouge"));
			rgb.getChildren().add(tfRed);
			rgb.getChildren().add(new Label("Vert"));
			rgb.getChildren().add(tfGreen);
			rgb.getChildren().add(new Label("Bleu"));
			rgb.getChildren().add(tfBlue);

			VBox tsl = new VBox();
			tsl.getChildren().add(new Label("TSL"));
			tsl.getChildren().add(new Label("Teinte"));
			tsl.getChildren().add(tfHue);
			tsl.getChildren().add(new Label("Saturation"));
			tsl.getChildren().add(tfSaturation);
			tsl.getChildren().add(new Label("Luminosité"));
			tsl.getChildren().add(tfBrightness);
	
			hbBas.getChildren().add(rgb);
			hbBas.getChildren().add(tsl);

			superParent.getChildren().add(hbBas);
			
		}else {
			btn.setText("⋀");
				superParent.getChildren().remove(2);
		}
	}
}
