# SpreadSheet

A spreadsheet consists of a two-dimensional array of cells, labeled A1, A2, etc. Rows are identified using letters, columns by numbers. Each cell contains either an integer (its value) or an expression. Expressions contain integers, cell references, and the operators '+', '-', '*', '/' with the usual rules of evaluation – note that the input is RPN and should be evaluated in stack order.

The spreadsheet input is defined as follows:
 Line 1: two integers, defining the width and height of the spreadsheet (n, m)
 n*m lines each containing an expression which is the value of the corresponding cell
(cells enumerated in the order A1, A2, A<n>, B1, ...)
Your program must output its data in the same format, but each cell should be reduced to a single floating-point value. For example, we would expect the following expect to produce the indicated output:
 
 
Expected Output
 
32 A2 45* A1
A1 B2 / 2 +
3
39 B1 B2 * /
 
