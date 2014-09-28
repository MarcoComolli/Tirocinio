package elaborationSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HTMLRenderer {
	
	
	public static void main(String[] args) {
		HTMLRenderer htmlr = new HTMLRenderer();
		htmlr.createTestCoverageHTML();
	}

	private String filesPathRead = "C:\\Users\\Marco\\Desktop\\files";
	private String filesPathWrite = "C:\\Users\\Marco\\Desktop\\files\\html";
	
	private String htmlEnd = "</body></html>";

	public void createTestCoverageHTML(){

		String pathsFilePathHTML = filesPathWrite + "\\TestCoverage.html";
		String pathsFilePathCSS = filesPathWrite + "\\testCoverage.css";

		String css = "body{background: #e0ffe0;}table{ border: 1px solid black;border-collapse: collapse;}" +
		"th{ height: 50px; border:1px solid black; background: #55bb55;}" +
		"tr{background:#bfffbf;border: 1px solid blue;}" +
		"td{height: 15px;border:1px solid blue;text-align: center;}";
		

		try {
			//write css
			PrintWriter printWriter = new PrintWriter(new FileWriter(pathsFilePathCSS));
			printWriter.println(css);
			printWriter.flush();
			printWriter.close();
			
			//write html
			BufferedReader br = new BufferedReader(new FileReader(filesPathRead + "\\TestCoverage.txt"));
			printWriter = new PrintWriter(new FileWriter(pathsFilePathHTML));
			printWriter.println(createHeader("TestCoverage","testCoverage"));
			printWriter.println("<table>");
			printWriter.println("<tr>");
			printWriter.println("<th rowspan='2'>Test Class</th>");
			printWriter.println("<th rowspan='2'>Total Block Tested</th>");
			printWriter.println("<th rowspan='2'>Tests Case Number</th>");
			printWriter.println("<th rowspan='2'>Failures</th>");
			printWriter.println("<th rowspan='2'>Time Elapsed</th>");
			printWriter.println("<th rowspan='2'>New block tested</th>");
			printWriter.println("<th rowspan='2'>New block tested of total</th>");
			printWriter.println("<th rowspan='2'>Unique block tested</th>");
			printWriter.println("<th rowspan='2'>Unique block tested of total</th>");
			printWriter.println("<th colspan = '3'>Similar test-class needed to reach the % coverage</th>");
			printWriter.println("<th colspan = '3'>Similar test-case needed to reach the % coverage</th>");
			printWriter.println("</tr>");
			printWriter.println("<tr>");
			printWriter.println("<th>50</th>");
			printWriter.println("<th>75</th>");
			printWriter.println("<th>85</th>");
			printWriter.println("<th>50</th>");
			printWriter.println("<th>75</th>");
			printWriter.println("<th>85</th>");
			printWriter.println("</tr>");
			
			String line = br.readLine();
			while(line != null){
				String[] lineParts = line.split("#");
				String[] testClassParts = lineParts[9].split("-");
				String[] testCaseParts = lineParts[10].split("-");
				printWriter.println("<tr>");
				printWriter.println("<td>"+lineParts[0]+"</td>");
				printWriter.println("<td>"+lineParts[1]+"</td>");
				printWriter.println("<td>"+lineParts[2]+"</td>");
				printWriter.println("<td>"+lineParts[3]+"</td>");
				printWriter.println("<td>"+lineParts[4]+"</td>");
				printWriter.println("<td>"+lineParts[5]+"</td>");
				printWriter.println("<td>"+lineParts[6]+"%</td>");
				printWriter.println("<td>"+lineParts[7]+"</td>");
				printWriter.println("<td>"+lineParts[8]+"%</td>");
				printWriter.println("<td>"+testClassParts[0]+"</td>");
				printWriter.println("<td>"+testClassParts[1]+"</td>");
				printWriter.println("<td>"+testClassParts[2]+"</td>");
				printWriter.println("<td>"+testCaseParts[0]+"</td>");
				printWriter.println("<td>"+testCaseParts[1]+"</td>");
				printWriter.println("<td>"+testCaseParts[2]+"</td>");
				printWriter.println("</tr>");
				line = br.readLine();
			}
			
			printWriter.println("</table>");
			printWriter.println(htmlEnd);
			printWriter.flush();
			
			printWriter.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private String createHeader(String title, String cssName){
		return "<!DOCTYPE html><html>" +
				"<head>" +
				"<title>"+title+"</title>" +
				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" +
				"<link rel=\"stylesheet\" href=\""+cssName+".css\" type=\"text/css\" />" +
				"</head><body>";
	}



}
