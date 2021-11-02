package couleurChanger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;


public class ColorChanger {
	private static final double DIFF = 0.07;
	static String myPath = System.getProperty("user.dir") + File.separator + "ressources" + File.separator;
	FileWriter fw;
	int nbFiles;
	
	public ColorChanger() {
		try {
			this.nbFiles = this.countFiles(myPath);
		}catch(Exception e) {
			System.out.println(e);
		}
		try {
			this.fw = new FileWriter(myPath+"save"+(this.nbFiles+1)+".txt");
		}catch(IOException e) {
			System.out.println("erreur");
			System.out.println(e);
		}
	}
	
	int countFiles (String parent) throws Exception {
	    File file = new File (parent);
	    
	    if (!file.exists ())
	        throw new FileNotFoundException ();
	    return file.list ().length;
	}
	
	public void inTxt( List<Color> l) {
		String stk = "";
		try {
			for(int i = 0; i<l.size(); i++) {
				stk ="#"+ l.get(i).toString().substring(2,8);
				this.fw.write(""+stk+"\n");
			}
			this.fw.close();
		}catch(IOException e) {
			System.out.println(e);
		}
	}
	
	public static double calculAGrayShade(Color l){
		double res = (0.3*l.getRed()+0.59*l.getGreen()+0.11*l.getBlue());
		return res;
	}
	
	private static List<Color> lightAllColor(List<Color> l) {
		List<Color> res = new ArrayList<Color>();
		Color stock;
		for(int i = 0; i<l.size();i++) {
			stock = l.get(i).brighter();
			while(!stock.equals(stock.brighter())) {
				stock = stock.brighter();
			}
			res.add(stock);
		}
		return res;
	}
	
	public static List<Color> differencierCouleurs(List<Color> l){
		List<Color> res = new ArrayList<Color>();
		List<Color> lColor = lightAllColor(l);
		Color stock = null;
		for(int i = 0; i<lColor.size();i++) {
			stock = lColor.get(i);
			for(int j = 0; j<i; j++) {
				if(res.size()!=0) {
					while(stock.getBrightness()-0.001>0 && Math.abs(ColorChanger.calculAGrayShade(stock)-ColorChanger.calculAGrayShade(res.get(j)))<DIFF) {
						stock = Color.hsb(stock.getHue(),stock.getSaturation(), stock.getBrightness()-0.001);
					}
				}
			}
			res.add(stock);
		}
		return res;
	}
	
	public static List<Color> randomColor(){
		List<Color> res = new ArrayList<Color>();
	    String zeros = "000000";
	    Random rnd = new Random();
	    String s = "";
		for(int i = 0; i<10; i++) {
		    s = Integer.toString(rnd.nextInt(0X1000000), 16);
		    s = zeros.substring(s.length()) + s;
		    res.add(Color.web("0x"+s));
		}
		return res;
	}
}
