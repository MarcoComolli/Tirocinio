package elaborationSystem;

import java.io.IOException;
import java.io.StringReader;

public class CommentsRemover {

	public static boolean inBlockComment = false;
	public static boolean out = true;
	
	public static String removeComments(String code) {
	    StringBuilder newCode = new StringBuilder();
	    try (StringReader sr = new StringReader(code)) {
	        
	    	out = true;
	        boolean inLineComment = false;
	        int redCharPosition = 0;
	        int prev = sr.read();
	        int cur, prevprev = -1;
	        for(cur = sr.read(); cur != -1; cur = sr.read()) {
	        	redCharPosition++;
	            if(inBlockComment) {
	                if (prev == '*' && cur == '/') {
	                    inBlockComment = false;
	                    out = true;
	                }
	            } else if (inLineComment) {
	                if (cur == '\r' || cur == '\n') {
	                    sr.mark(1);
	                    int next = sr.read();
	                    if (next != '\n') {
	                        sr.reset();
	                    }
	                    inLineComment = false;
	                    out = false; 
	                } else if (cur == '\n') {
	                    inLineComment = false;
	                    out = false;
	                }
	            } else {
	            	if (prev == '/' && cur == '*') {
	            		inBlockComment = !checkInString(code, "/*", redCharPosition); 
	            	} else if (prev == '/' && cur == '/') {
	            		inLineComment = !checkInString(code, "//", redCharPosition);
	                } else if (out){
	                	if(prevprev != '*' || prev != '/'){
	                		newCode.append((char)prev);
	                	}
	                } else {
	                   	newCode.append((char)prev);
	                    out = true;
	                }
	            }
	            prevprev = prev;
	            prev = cur;
	        }
	        if(!inLineComment && !inBlockComment){
	        	if(prev == '/'){
	        		out = false;
	        	}
	        }
	        if (prev != -1 && out && !inLineComment && !inBlockComment) {
	            newCode.append((char)prev);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return newCode.toString();
	}
	
	private static boolean checkInString(String line, String key, int beginIndex) {
		if(beginIndex >= line.length()){
			return false;
		}
		String substring = line.substring(beginIndex);
		boolean firstApex = false;
		for (int i = 0; i < line.length(); i++) {
			if(line.charAt(i) == '"'){
				if(i != 0 && line.charAt(i-1) != '\\'){
					if(firstApex){
						if(i < beginIndex){
							firstApex = false;
						}
						else{
							return true;
						}
					}
					else{
						if(i < beginIndex){
							firstApex = true;
						}
					}
				}	
			}

		}
		return false;
	}
}
