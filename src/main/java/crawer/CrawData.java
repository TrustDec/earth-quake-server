package main.java.crawer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import net.sf.json.JSONArray;
import main.java.entity.Quake;
public class CrawData {
	static List<Quake> earthquakes;
	static List<Quake> chinaQuake;
	public static void updateChina() {
		chinaQuake = new ArrayList<Quake>();
		long prev = System.currentTimeMillis();
		try {
			Document doc;
			doc = Jsoup.connect("http://news.ceic.ac.cn/index.html").get();
			Element table = doc.getElementById("news");
			Elements quakes = table.select("tr");
			for (Element quake : quakes) {
				if (quake.getAllElements().get(0).childNodes().get(1).toString().contains("��(M)"))
					continue;
				Elements dirs = quake.select("td");
				String id = quake.attr("id");
				id = "null";
				String magnitude = dirs.get(0).text();
				String UTC_date = dirs.get(1).text();
				String latitude = dirs.get(2).text();
				String longitude = dirs.get(3).text();
				String depth = dirs.get(4).text();
				String region = dirs.get(5).text();
				Quake earthquake = new Quake();
				earthquake.setId(id);;
				earthquake.setDate(UTC_date);;
				earthquake.setLatitude(latitude);
				earthquake.setLongitude(longitude);
				earthquake.setDepth(depth);
				earthquake.setMagnitude(magnitude);
				earthquake.setRegion(region);
				chinaQuake.add(earthquake);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			long curr = System.currentTimeMillis();
			System.err.println((curr - prev) + "ms");
		}
	}
	static void update(int page) {
		earthquakes = new ArrayList<Quake>();
		long prev = System.currentTimeMillis();
		try {
			Document doc;
			doc = Jsoup.connect("https://www.emsc-csem.org/Earthquake/?view=" + page).get();
			Element table = doc.getElementById("tbody");
			Elements quakes = table.select("tr");
			for (Element quake : quakes) {
				if (quake.attr("id").startsWith("t"))
					continue;
				Elements dirs = quake.getElementsByClass("tabev2");
				Elements infos = quake.getElementsByClass("tabev1");
				Element depthElement = quake.getElementsByClass("tabev3").first();
				Element area = quake.getElementsByClass("tb_region").first();
				Element utc_date = quake.select("td.tabev6 > b > a").first();
				String id = quake.attr("id");
				String UTC_date = utc_date.text();
				String latitude;
				if ((dirs.first().text().equals("N"))) {
					latitude = infos.first().text();
				} else {
					latitude = "-" + infos.first().text();
				}
				String longitude;
				if (dirs.get(1).text().equals("E")) {
					longitude = infos.get(1).text();
				} else {
					longitude = "-" + infos.get(1).text();
				}
				String depth = depthElement.text();
				String magnitude = dirs.last().text();
				String region = area.text();
				Quake earthquake = new Quake();
				earthquake.setId(id);;
				earthquake.setDate(UTC_date);;
				earthquake.setLatitude(latitude);
				earthquake.setLongitude(longitude);
				earthquake.setDepth(depth);
				earthquake.setMagnitude(magnitude);
				earthquake.setRegion(region);
				earthquakes.add(earthquake);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			long curr = System.currentTimeMillis();
			System.err.println((curr - prev) + "ms");
		}
	}
	public static void main(String[] args) {
		CrawData.updateChina();
	}
	public JSONArray craw(int x) {
		for(int i=0;i<x;i++){
			update(i);
		}
		if(earthquakes!=null){
			return JSONArray.fromObject(earthquakes);
		}
		return null;
	}
	public JSONArray crawChina() {
		updateChina();
		if(chinaQuake!=null){
			return JSONArray.fromObject(chinaQuake);
		}
		return null;
	}
}
