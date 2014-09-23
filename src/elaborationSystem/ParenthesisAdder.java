package elaborationSystem;

import java.io.File;
import java.io.IOException;



import java.util.LinkedList;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

import elaborationSystem.Interface.ProcessTask;


public class ParenthesisAdder {
	
	private LinkedList<File> dirList = new LinkedList<File>();
	private TreeMap<String, Integer> methodMap;
	private String rootPath, writePath, midFilesPath;
	private ProcessTask processTask;
	private int filesCount;
	private double increment = 50;
	private int progress = 50;
	
	
	public ParenthesisAdder(TreeMap<String, Integer> methodMap, String rootPath, String writePath, String midFilesPath, ProcessTask processTask, int filesCount){
		this.methodMap = methodMap;
		this.rootPath = rootPath;
		this.writePath = writePath;
		this.midFilesPath = midFilesPath;
		this.processTask = processTask;
		this.filesCount = filesCount;
		processTask.setTheProgress(50);
	}
	public void parseFilesInDir(String dirPath){

		// String dirPath = dirs.getCanonicalPath() +
		// File.separator+"src"+File.separator;
		//dirPath = "C:/Users/Marco/Desktop/pmd-src-5.1.1/src/main/java/net/sourceforge/pmd";


		File root = new File(dirPath);
		System.out.println("rootpath: " + root.getAbsolutePath());
		File[] files = root.listFiles();
		
		if(files == null){
			if (root.isFile()) {
				if(getExtension(root).equals("java")){
					FileParser fp = new FileParser(root.getAbsolutePath(),methodMap, writePath  +"\\" +getRelativePath(root.getAbsolutePath(), rootPath), rootPath, midFilesPath);
					fp.a();
				}
			}
			return;
		}
		
		String filePath = null;

		for (File f : files) {
			filePath = f.getAbsolutePath();

			
			if (f.isFile()) {
				if(getExtension(f).equals("java")){
					FileParser fp = new FileParser(f.getAbsolutePath(),methodMap, writePath +"\\" + getRelativePath(filePath, rootPath), rootPath, midFilesPath);
				 	increment += 100.0/filesCount/2.00;
				 	System.out.println(increment + " " + filesCount);
			    	progress = (int)increment;
			    	processTask.setTheProgress(progress);
					fp.a();
				}

			}
			else if(f.isDirectory()){ //Sennò aggiungilo alla lista e poi ci entrerai a vedere
				dirList.add(f);
			}
		}

		if(!dirList.isEmpty()){ //se la directory non è vuota
			parseFilesInDir(dirList.poll().getAbsolutePath());
		}
	}
	
	private String getRelativePath(String absolutePath, String root) {
		String s = absolutePath.replace(root, "");
		return s;
	}
	public static void main(String[] args) throws IOException {
		
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
		//F:/Documenti - Marco/JavaPrg/Workspace/Tirocinio/src/originalFiles/ --> path test base
//		String rootPath = "C:/Users/Marco/Desktop/pmd-src-5.1.1/src/main/java";
//		String writePath = "C:/Users/Marco/Desktop/nn/";
//		String txtFile = "C:/Users/Marco/Desktop/MetodiTirocinio.txt";
//		MethodSignatureExtractor mse = new MethodSignatureExtractor(txtFile,rootPath);
//		TreeMap<String, Integer> m = mse.parseFilesInDir("C:/Users/Marco/Desktop/pmd-src-5.1.1/src/main/java/");
//		ParenthesisAdder p = new ParenthesisAdder(m, rootPath, writePath, "C:/Users/Marco/Desktop");
//		p.parseFilesInDir("C:/Users/Marco/Desktop/pmd-src-5.1.1/src/main/java");
//		p.paintMap(m);

		
		
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
