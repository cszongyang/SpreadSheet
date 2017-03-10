package Quantcast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Logger;


public class Spreadsheet {

	private static String input = "/Users/zongyangli/Desktop/quantcast/input.txt";


	public static Cell[][] cells;

	// static List<Cell> topologicalSortL = new ArrayList<Cell>();
	static Queue<Cell> topologicalSortS = new LinkedList<Cell>();
	
	static Map<String,LinkedList<Cell>> dependancyMap = new HashMap<String,LinkedList<Cell>>(10000);

	static void testCode() throws CircularDependancyException {
		Cell testCell = new Cell();
		testCell.setTokens("-100 1 *");
		System.out.println(testCell.evaluate());		
	}
	
	public Spreadsheet(){

	}

	public static void main(String[] args) {		
		Scanner sc = new Scanner(System.in);
		try {
			if (args.length > 0)
				input = args[0];
			sc = new Scanner(new File(input));
		} catch (Exception e) {

		}		
		
		int cols = sc.nextInt();
		int rows = sc.nextInt();
		sc.nextLine();

		try {
		
			cells = new Cell[rows][cols];
			for (int row = 0; row < rows; row++) {			
				for (int col = 0; col< cols; col++) {
					Cell cell = new Cell();
					cells[row][col] = cell;
					String tokens = sc.nextLine();
					cell.setTokens(tokens);
					
					
					cell.setName((char)('A'+row)+String.valueOf(col+1));
					cell.edgeCount = cell.references.size();
					if (cell.edgeCount == 0) {
						topologicalSortS.add(cell);
					} else {
						List<String> references = cell.references;
						for (String string : references) {
							addDependancyMap(string,cell);
						}
					}
				}
			}
			
			// do topological sort
			int countEvaluated = 0;
			while (topologicalSortS.size() > 0) {
				Cell cell = topologicalSortS.poll();
				//cell.evaluate();
				countEvaluated++;
				// topologicalSortL.add(cell);
	
				// get every cell x that refer cell y
				List<Cell> list = dependancyMap.get(cell.name);
				if (null == list){
					continue;
				}
					
				for (Cell cellx : list) {
					cellx.edgeCount--;
					if (cellx.edgeCount == 0) {
						topologicalSortS.add(cellx);
					}
				}
			}
			
			
			if (countEvaluated < rows * cols){
				throw new CircularDependancyException("circular dependency detected: " + countEvaluated + " cells evaluated");
			}
				
			 
			System.out.println(cols +" "+ rows);
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					Cell cell = cells[row][col];
					double d = cell.evaluate();
					String s = String.format("%.5f", d);
					System.out.println(s);
				}
			}
			
		
		} catch (CircularDependancyException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		} finally{
			sc.close();
		}
	}
	
	private static void initializeCells(){
		
	}
	
	private static void constructDependencyMap(){
		
	}

	private static void addDependancyMap(String string, Cell cell) {
		LinkedList<Cell> list = dependancyMap.get(string);
		if (null == list) {
			list = new LinkedList<Cell>();
			dependancyMap.put(string,list);
		}
		list.add(cell);
	}

}
