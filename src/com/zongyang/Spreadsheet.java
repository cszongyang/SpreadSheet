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

	//for topological sort.
	public static Queue<Cell> startNodes = new LinkedList<Cell>();
	
	public static Map<Cell, LinkedList<Cell>> dependancyMap = new HashMap<Cell,LinkedList<Cell>>();


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
			//matrix of all rows*cols cells
			cells = new Cell[rows][cols];
			
			initializeCells(sc, rows, cols);
			
			// construct a hashmap for detecting circular dependency using topological sort. 
			constructDependencyMap(cells, dependancyMap);
			
			if (hasCircularDependecy(rows * cols)){
				throw new Exception("circular dependency detected.");
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
			
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		} finally{
			sc.close();
		}
	}
	
	
	private static boolean hasCircularDependecy(int totalNum){
		
		// do topological sort
		int countEvaluated = 0;
		while (startNodes.size() > 0) {
			Cell cell = startNodes.poll();
			countEvaluated++;

			// get every cell x that refer cell y
			List<Cell> list = dependancyMap.get(cell);
			if (null == list){
				continue;
			}
				
			for (Cell referCell : list) {
				referCell.indegree--;
				if (referCell.indegree == 0) {
					startNodes.add(referCell);
				}
			}
		}
		
		return countEvaluated != totalNum;
	}
	
	
	private static void initializeCells(Scanner sc, int rows, int cols){
		for (int row = 0; row < rows; row++) {			
			for (int col = 0; col< cols; col++) {
				Cell cell = new Cell(row, col);
				cells[row][col] = cell;
				String tokens = sc.nextLine();
				cell.setContent(tokens);
			}
		}
	}
	
	private static void constructDependencyMap(Cell[][] cells, Map<Cell, LinkedList<Cell>> dependancyMap){
		
		for (int row = 0; row < cells.length; row++) {			
			for (int col = 0; col< cells[0].length; col++) {
				Cell cell = cells[row][col];
				cell.indegree = cell.references.size();
				if (cell.indegree == 0) {
					startNodes.add(cell);
				} else {
					List<Token> references = cell.references;
					for (Token token : references) {
						
						addDependancyMap(cells[token.referenceRow][token.referenceColumn], cell);
					}
				}
			}
		}
	}
	

	private static void addDependancyMap(Cell currentCell, Cell referenceCell) {
		LinkedList<Cell> list = dependancyMap.get(currentCell);
		if (null == list) {
			list = new LinkedList<Cell>();
			dependancyMap.put(currentCell,list);
		}
		list.add(referenceCell);
	}

}
