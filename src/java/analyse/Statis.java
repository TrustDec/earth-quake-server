package java.analyse;

import java.util.ArrayList;
import java.util.Collections;
import net.sf.json.JSONObject;

public class Statis {
	private static ArrayList<Earthquake> data =
			new ArrayList<Earthquake>();
	String[] a = {"Scotia", "Antarctic", "African", "Arabian", "Eurasian", "Indian", "Australian", "Juan de Fuca",
            "Caribbean", "North-American", "Nazca", "Pacific", "Filipino", "South-American"};
    private static ArrayList<String> plate = new ArrayList<String>();
    String[] b = {"1", "2", "3", "4", "5", "6", "7", "8"};
    private static ArrayList<String> magnitudes = new ArrayList<String>();
    private static JSONObject magObject;
    private static JSONObject seasonObject;
    private static JSONObject areaObject;
    private void init() {
        data = Earthquake.getInit();
        Collections.addAll(plate, a);
        Collections.addAll(magnitudes, b);
    }
	private void magPaint() {
        int[] magCounter = new int[8];
        for (int i = 0; i < data.size(); i++) {
            Earthquake eq =data.get(i);
            int magnitude = Integer.parseInt(String.valueOf(eq.getMagnitude().charAt(0)));
            magCounter[magnitude - 1]++;
        }
        magObject = new JSONObject(); 
        for (int i = 0; i < magCounter.length; i++) {
        	magObject.put(magnitudes.get(i),String.valueOf(magCounter[i]));
        }
    }

    /**
     * A method used to paint data<br>
     * on the PieChart in the controller class
     */
    private void seasonPaint() {
        int[] seasonCounter = new int[4];
        String season[] = {"Spring(Month 1-3)", "Summer(Month 4-6)", "Fall(Month 7-9)", "Winter(Month 10-12)"};
        for (int i = 0; i < data.size(); i++) {
            Earthquake eq = data.get(i);
            int month = Integer.parseInt(eq.getUTC_date().substring(5, 7)) - 1;
            seasonCounter[month / 3]++;
        }
        seasonObject = new JSONObject(); 
        for (int i = 0; i < 4; i++) {
        	seasonObject.put(season[i], String.valueOf(seasonCounter[i]));
        }
    }

    /**
     * A method used to paint data<br>
     * on the LineChart in the controller class
     */
    private void areaPaint() {
        int[] areaCounter = new int[plate.size()];
        for (int i = 0; i < data.size(); i++) {
            Earthquake eq = data.get(i);
            ArrayList<String> list = new ArrayList<String>(plate);
            for (int j = 0; j < list.size(); j++) {
                if (eq.getName().equals(list.get(j))) {
                    areaCounter[j]++;
                }
            }
        }
        areaObject = new JSONObject(); 
        for (int i = 0; i < areaCounter.length; i++) {
        	areaObject.put(plate.get(i),
        			String.valueOf(areaCounter[i]));
        }
    }
    public static void main(String[] args) {
    	Statis con = new Statis();
		con.init();
		con.magPaint();
		con.seasonPaint();
		con.areaPaint();
		JSONObject ob = new JSONObject();
		ob.element("magnitude", magObject);
		ob.put("season", seasonObject);
		ob.put("area", areaObject);
		System.out.println(ob.toString());
	}
	public String generate() {
		Statis con = new Statis();
		con.init();
		con.magPaint();
		con.seasonPaint();
		con.areaPaint();
		JSONObject ob = new JSONObject();
		ob.element("magnitude", magObject);
		ob.put("season", seasonObject);
		ob.put("area", areaObject);
		return ob.toString();
	}
}
