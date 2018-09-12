package java.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import java.analyse.Statis;
import java.Model.CalAlgo;
import java.crawer.CrawData;
import net.sf.json.JSONArray;
@Controller
public class MainController {
	@RequestMapping("/welcome.do")
	public String input(){
		return "input";
	}
	@RequestMapping("/input.do")
	public String result(java.controller.CalParam cp, ModelMap mm){
		Double height = cp.getHeight();
		Double weight = cp.getWeight();
		if(height!=null&&weight!=null){
			CalAlgo cal = new CalAlgo();
			String res = cal.cal(height, weight);
			System.out.println(res);
			mm.addAttribute("res",res);
			return "result";
		}else{
			return null;
		}
		
	}
	@RequestMapping("/quake.do")
	public String quake(ModelMap mm){
		CrawData cd= new CrawData();
		JSONArray json = cd.craw(1);
	    String jsonString = json.toString();
		mm.addAttribute("json",jsonString);
		return "quake";
	}
	@RequestMapping("statis.do")
	public String sta(ModelMap mm){
		Statis statis = new Statis();
		String res =  statis.generate();
		mm.addAttribute("statis",res);
		return "statis";
	}
	@RequestMapping("/chinaquake.do")
	public String chinaquake(ModelMap mm){
		CrawData cd= new CrawData();
		JSONArray json = cd.crawChina();
	    String jsonString = json.toString();
		mm.addAttribute("chinajson",jsonString);
		return "chinaquake";
	}
	@RequestMapping("/blog.do")
	public void blog(HttpServletResponse response) throws Exception{
		response.sendRedirect("http://scottyi.club:8000");
	}
}
