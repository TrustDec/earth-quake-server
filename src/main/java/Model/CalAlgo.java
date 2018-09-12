package main.java.Model;

public class CalAlgo {
	public String cal(double height,double weight){
		double bmi = weight/Math.pow(height,2);
		if(bmi<19){
			return "thin";
		}else if(bmi>25){
			return "fat";
		}else{
			return "normal";
		}
	}
}
