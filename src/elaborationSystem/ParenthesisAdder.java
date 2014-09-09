package elaborationSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.sound.midi.Patch;

public class ParenthesisAdder {
	
	private LinkedList<File> dirList = new LinkedList<File>();
	private TreeMap<String, Integer> methodMap;
	
	
	public ParenthesisAdder(TreeMap<String, Integer> methodMap){
		this.methodMap = methodMap;
	}
	public void parseFilesInDir(String dirPath){

		File dirs = new File(".");
		// String dirPath = dirs.getCanonicalPath() +
		// File.separator+"src"+File.separator;
		//dirPath = "C:/Users/Marco/Desktop/pmd-src-5.1.1/src/main/java/net/sourceforge/pmd";


		File root = new File(dirPath);
		System.out.println("rootpath: " + root.getAbsolutePath());
		File[] files = root.listFiles();
		
		if(files == null){
			if (root.isFile()) {
				if(getExtension(root).equals("java")){
					FileParser fp = new FileParser(root.getAbsolutePath(),methodMap, "C:/Users/Marco/Desktop/nn/" + getRelative(root.getAbsolutePath()));
					fp.a();
				}
			}
			return;
		}
		
		for (int i = 0; i < files.length; i++) {
			System.out.println("file: " + files[i].getAbsolutePath());
		}
		String filePath = null;

		for (File f : files) {
			filePath = f.getAbsolutePath();
			System.out.println(filePath);

			
			if (f.isFile()) {
				if(getExtension(f).equals("java")){
					FileParser fp = new FileParser(f.getAbsolutePath(),methodMap, "C:/Users/Marco/Desktop/nn/" + getRelative(filePath));
					fp.a();
				}

			}
			else if(f.isDirectory()){ //Sennò aggiungilo alla lista e poi ci entrerai a vedere
				dirList.add(f);
			}
		}

		if(!dirList.isEmpty()){ //se la directory non è vuota
			System.out.println("Cerco in:" + dirList.getFirst().getAbsolutePath());
			parseFilesInDir(dirList.poll().getAbsolutePath());
		}
	}
	
	private String getRelative(String absolutePath) {
		int index = absolutePath.indexOf("\\src\\");
		if(index != -1){
			System.err.println(absolutePath.substring(index));
			return absolutePath.substring(index+1);
		}
		else{
			return absolutePath;
		}
	}
	public static void main(String[] args) {
		TreeMap<String, Integer> m;
//		Path source = new File("C:/Users/Marco/Desktop/pmd-src-5.1.1/").toPath();
//		new File("C:/Users/Marco/Desktop/nn/").mkdirs();
//		Path target = new File("C:/Users/Marco/Desktop/nn/").toPath();
//		try {
//			Files.walkFileTree(source, new CopyFileVisitor(target));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		

		
		//C:/Users/Marco/Desktop/pmd-src-5.1.1/src/main/java --> path per pmd
		MethodSignatureExtractor mse = new MethodSignatureExtractor("C:/Users/Marco/Desktop/MetodiTirocinio.txt");
		m = mse.parseFilesInDir("F:/Documenti - Marco/JavaPrg/Workspace/Tirocinio/src/originalFiles/");
		ParenthesisAdder p = new ParenthesisAdder(m);
		p.parseFilesInDir("F:/Documenti - Marco/JavaPrg/Workspace/Tirocinio/src/originalFiles/");
		p.paintMap(m);

		
		
	}
	
	//TODO
	//Cancellare una volta finito tutto
	private void paintMap(SortedMap<String, Integer> m ){
		System.out.println("=== MAPPA ===");
		for (Map.Entry<String, Integer> e : m.entrySet()) {
			System.out.println(e.getKey() +  " --> " + e.getValue());
		}
	}
	
	private SortedMap<String, Integer> getByPreffix(NavigableMap<String, Integer> myMap, String preffix ) {
	    return myMap.subMap( preffix, preffix + Character.MAX_VALUE );
	}
	
	private String getExtension(File f){
		String name = f.getName();

		int i = name.lastIndexOf('.');
		if (i > 0) {
		    return name.substring(i+1);
		}
		else{
			return "";
		}
	}
	
}
