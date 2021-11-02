package couleurChanger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;

public class Graphs {
	static public List<Chart> initPieChart(List<Color> l){
		List<Chart> stock = new ArrayList<Chart>();
		 final PieChart chart1 = new PieChart(); 
	     final PieChart chart2 = new PieChart(); 
		  chart1.setTitle("Couleurs changées");  
		  chart2.setTitle("Couleurs changées en gris");
		     for(int c1 = 0; c1<l.size();c1++) {
		     	chart1.getData().add(new PieChart.Data(""+(c1+1),100/l.size()));
		     	chart2.getData().add(new PieChart.Data(""+(c1+1),100/l.size()));
		     }
		     int i = 0;
		     for (PieChart.Data data : chart1.getData()) {
		         data.getNode().setStyle(
		           "-fx-pie-color: " + "#"+l.get(i).toString().substring(2,10)+ ";"
		         );
		         i++;
		       }
		     i = 0;
		     for (PieChart.Data data : chart2.getData()) {
		         data.getNode().setStyle(
		           "-fx-pie-color: " + "#"+l.get(i).grayscale().toString().substring(2,10)+ ";"
		         );
		         i++;
		       }
		     chart1.setLegendVisible(false);
		     chart2.setLegendVisible(false);
		     stock.add(chart1);
		     stock.add(chart2);
		return stock;
	}
	
	static public List<Chart> initStackedBarChart(List<Color> l){
		List<Chart> stock = new ArrayList<Chart>();
		 final StackedBarChart<String,Number> chart1 = new StackedBarChart<String,Number>(new CategoryAxis(),new NumberAxis()); 
	     final StackedBarChart<String,Number>  chart2 = new StackedBarChart<String,Number>(new CategoryAxis(),new NumberAxis()); 
		  chart1.setTitle("Couleurs chang�es");  
		  chart2.setTitle("Couleurs chang�es en gris");
		  for(int c1 = 0; c1<l.size();c1++) {
			  Random r = new Random();
			  int i = r.nextInt(9)+1;
			  XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
			  dataSeries1.getData().add(new XYChart.Data<String, Number>(""+(c1+1),i));
			  chart1.getData().add(dataSeries1);
			  
			  XYChart.Series<String, Number> dataSeries2 = new XYChart.Series<String, Number>();
			  dataSeries2.getData().add(new XYChart.Data<String, Number>(""+(c1+1),i));
			  chart2.getData().add(dataSeries2);
		  }
		  
		     int i = 0;
		     for (Node n : chart1.lookupAll(".data0.chart-bar")) {
		    	 System.out.println(n);

		         n.setStyle(
		           "-fx-bar-fill:" + "#"+l.get(i).toString().substring(2,10)+ ";"
		         );
		         i++;
		       }
		     i = 0;
		     for (Node n : chart2.lookupAll(".data0.chart-bar")) {
		    	 System.out.println(n);
		         n.setStyle(
		           "-fx-bar-fill:"+ "#"+l.get(i).grayscale().toString().substring(2,10)+ ";"
		         );
		         i++;
		       }
		     chart1.setLegendVisible(false);
		     chart2.setLegendVisible(false);
		     stock.add(chart1);
		     stock.add(chart2);
		return stock;
	}
}
