package Project5;


public class Position {

	private int Farmer;
	private int Wolf;
	private int Sheep;
	private int Cabbage;

	public Position(){
		int Farmer = 0;
		int Wolf = 0;
		int Sheep = 0;
		int Cabbage = 0;
	}
	public Position(Position object){
		this.Farmer = object.Farmer;
		this.Cabbage = object.Cabbage;
		this.Sheep = object.Sheep;
		this.Wolf = object.Wolf;
	}
	public Position(int i){
		//二进制数字表示位置状态
		String temp = Integer.toBinaryString(i);

		if(temp.length() == 4){
			Farmer = temp.charAt(0) - '0';
			Wolf = temp.charAt(1) - '0';
			Sheep = temp.charAt(2) - '0';
			Cabbage = temp.charAt(3) - '0';
		}
		if(temp.length() == 3){
			Wolf = temp.charAt(0) - '0';
			Sheep = temp.charAt(1) - '0';
			Cabbage = temp.charAt(2) - '0';
		}
		if(temp.length() == 2){
			Sheep = temp.charAt(0) - '0';
			Cabbage = temp.charAt(1) - '0';
		}
		if(temp.length() == 1)
			Cabbage = temp.charAt(0) - '0';

	}

	public void changeFramer(){
		Farmer = (Farmer == 1 ? 0 : 1);
	}
	
	public void changeWolf(){
		Wolf = (Wolf == 1 ? 0 : 1);
	}
	
	public void changeSheep(){
		Sheep = (Sheep == 1 ? 0 : 1);
	}
	
	public void changeCabbage(){
		Cabbage = (Cabbage == 1 ? 0 : 1);
	}
	
	public boolean checkSafe(){
        if(Farmer != Wolf && Wolf == Sheep)
            return false;
		if(Farmer != Sheep && Sheep == Cabbage)
			return false;
		return true;
	}
	
	public boolean isWolfCanbeTrans(){
		return (Farmer == Wolf ? true : false);
	}
	
	public boolean isSheepCanbeTrans(){
		return (Farmer == Sheep ? true : false);
	}
	
	public boolean isCabbageCanbeTrans(){
		return (Farmer == Cabbage ? true : false);
	}

	public int getNumOfPosition(){
		int result = 0;
		if(Farmer == 1)
			result+= 8;
		if(Wolf == 1)
			result += 4;
		if(Sheep == 1)
			result += 2;
		if(Cabbage == 1)
			result += 1;
		return result;
	}
	
	public String toString(){
		return Farmer + "" + Wolf + "" + Sheep + "" + Cabbage;
	}
	
	public String getNorth(){
		String result = "";
		if(Farmer == 1)
			result += "Farmer ";
		if(Wolf == 1)
			result += "Wolf ";
		if(Sheep == 1)
			result += "Sheep ";
		if(Cabbage == 1)
			result += "Cabbage ";
		for(int i = result.length();i <= 27;i++)
			result += " ";
		return result;
	}
	
	public String getSouth(){
		String result = "";
		if(Farmer == 0)
			result += "Farmer ";
		if(Wolf == 0)
			result += "Wolf ";
		if(Sheep == 0)
			result += "Sheep ";
		if(Cabbage == 0)
			result += "Cabbage ";
		for(int i = result.length();i <= 27;i++)
			result += " ";
		return result;
	}

}