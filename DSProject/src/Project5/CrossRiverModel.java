package Project5;

import Project4.Graph.version2.UnweightedGraph;

import java.util.ArrayList;
import java.util.List;


public class CrossRiverModel extends UnweightedGraph<Position> {

	private List<List<Position>> result;

	public CrossRiverModel(List<Edge> edges,List<Position> vertices) {
		super(edges, vertices);
	}	
	
	public List<List<Position>> getAllPath(){

		result = new ArrayList<>();

		List<Position> list = new ArrayList<>();

		//Position从“0000”开始过河
		list.add(vertices.get(0));
		crossRiver(list);
		return result;
	}

	//图的搜索算法
	private void crossRiver(List<Position> list){

		Position position = list.get(list.size() - 1);
		int index = position.getNumOfPosition();

		for(int i : neighbors.get(index)){

			//判断是否重复
			if(!list.contains(vertices.get(i))){
				list.add(vertices.get(i));

				//判断是否过河成功
				if(vertices.get(i).toString().equals("1111")){

					result.add(new ArrayList<>(list));
					list.remove(list.size() - 1);
					continue;
				}
				crossRiver(list);
			}
		}
		list.remove(list.size()-1);
	}

	//得到结果集合
	public List<List<Position>> getSolution(){
		if(result == null)
			getAllPath();
		return result;
	}

	//得到所有状态
	public static List<Position> creatVertice(){

		ArrayList<Position> list = new ArrayList<>();
		for(int i = 0;i < 16;i++){
			Position temp = new Position(i);
				list.add(temp);
		}
		return list;
	}

	public static List<Edge> creatEdge(List<Position> vertices){

		List<Edge> edge = new ArrayList<>();

		for(int i = 0;i < vertices.size();i++){
			if(vertices.get(i).checkSafe()){
				for(int j = 0;j < 4;j++){
					Position temp = new Position(vertices.get(i));
					switch(j){
						//人
						case 0:
							temp.changeFramer();
							break;
						//人+狼
						case 1:
							if(temp.isWolfCanbeTrans()){
								temp.changeFramer();
								temp.changeWolf();
							}
							break;
						//人+羊
						case 2:
							if(temp.isSheepCanbeTrans()){
								temp.changeFramer();
								temp.changeSheep();
							}
							break;
						//人+草
						case 3:
							if(temp.isCabbageCanbeTrans()){
								temp.changeFramer();
								temp.changeCabbage();
							}
							break;
					}

					//状态安全且与改变之前状态不同
					if(temp.checkSafe() && !temp.toString().equals(vertices.get(i).toString())){
						for(int k = 0;k < vertices.size();k++){
							if(vertices.get(k).toString().equals(temp.toString())){
								edge.add(new Edge(i,k));
								break;
							}
						}
					}

				}
			}
		}
		return edge;
	}

}
