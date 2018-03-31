package tester;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import constants.LuceneConstants;


public class TextFileWriter {
	
	

	List<List<String>> inputToFiles = null;
	File file;
	
	public TextFileWriter(List<List<String>> inputToFiles) {
		this.inputToFiles  = inputToFiles;
		
	}

	public void writeFile(String document , String model, String parentDir){
		
		System.out.println("Writing Started..........");
		
		BufferedWriter writer = null;
		int count = 0;
		 try {
			 if(document.equals("result")) {
			     file = new File(Paths.get(parentDir,model.concat(".txt")).toString());
			 }else {
				 file = new File(Paths.get(parentDir,LuceneConstants.REFERENCE.concat(".txt")).toString());
			 }
			 System.out.println("file..........."+file.toString());
			 writer = new BufferedWriter(new FileWriter(file));
			 for(List<String> a:inputToFiles) {
				 count = count+1;
				 if(document.equals("result")) {
					 writer.write(a.get(0)+" "+ a.get(1) + " " +a.get(2) + " "+count+ " "+ a.get(3)+ " INFO_RET"+ "\n");
				 }else {
					 if(a.size()==4) {
						 writer.write(a.get(0)+" "+ a.get(1) + " " +a.get(2) + " "+ a.get(3)+"\n");
					 }
					 
				 }
				 
				 
			 }

		 }catch (IOException e) {

				e.printStackTrace();

			}finally {

				try {

					if (writer != null) {
						writer.close();
					}
				} catch (IOException ex) {

					ex.printStackTrace();

				}
			}
			  
	}

}
