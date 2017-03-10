package Quantcast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Cell {

	private String input;
	
	public int row;
	public int col;

	private double cellValue;
	private boolean isEvaluated;

	public List<Token> references;
	public List<Token> tokens;
	
	public int indegree;
	
	public Cell(final int row, final int col){
		references = new ArrayList<>();
		tokens = new ArrayList<>();
		this.row = row;
		this.col = col;
	}

	public void setContent(String input) {
		this.input = input.toUpperCase();
		String[] tokenList = input.trim().toUpperCase().split("\\s+");
		
		for(String token : tokenList) {
			
			Token t = new Token(token);
			tokens.add(t);
			if(t.type == 1){
				references.add(t);
			}
			
		}
	}

	public double evaluate() {		
		if (isEvaluated){
			return cellValue;
		}
			
		Stack<Double> stack = new Stack<Double>();
		for (Token token : tokens) {

			//number
			if (token.type == 0) {
				stack.push(token.value);
			} 
			// reference
			else if (token.type == 1) {
				Cell reference = Spreadsheet.cells[token.referenceRow][token.referenceColumn];
				stack.push(reference.evaluate());
			} 
			//+
			else if (token.type == 2) {
				stack.push(stack.pop() + stack.pop());
			} 
			//-
			else if (token.type == 3) {
				stack.push((-1) * stack.pop() + stack.pop());
			} 
			//*
			else if (token.type == 4) {
				stack.push(stack.pop() * stack.pop());
			} 
			// /
			else if (token.type == 5) {
				stack.push((1 / stack.pop()) * stack.pop());
			} 
			//++
			else if (token.type == 6) {
				stack.push(stack.pop() + 1);
			} 
			//--
			else if (token.type == 7) {
				stack.push(stack.pop() - 1);
			} 
			
			
		}
		
		isEvaluated = true;
		cellValue = stack.pop();
		return cellValue;
	}
	
	
	@Override
	public int hashCode() {
		return (String.valueOf(row) + String.valueOf(col)).hashCode();
	}
	
	public void setEvaluated(boolean isEvaluated) {
		this.isEvaluated = isEvaluated;
	}
	
	public boolean isEvaluated() {
		return isEvaluated;
	}

}
