package tensorgen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVLoader {

	String fileName;
	String[] header;
	String[] dataType;
	H2dbDAO db;

	public CSVLoader(String fileName) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.fileName = fileName;
		this. header = getHeader();

	}

	public String[] getHeader() {
		File file = new File(fileName);
		String[] res = {};
		try {
		FileReader filereader = new FileReader(file);
		BufferedReader br = new BufferedReader(filereader);
		String header = br.readLine();
		
			br.close();
			res = header.split(",");
			
			
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			new RuntimeException("CSVのヘッダーが読めませんでした");
		}
		
		for (int i = 0; i < res.length; i++) {
			String string = res[i];
			
			Pattern p = Pattern.compile("^\"(.*)\"$");
			Matcher m = p.matcher(string);
			if (m.find()) {
			res[i] = m.group(1);
			}
		}
		
		return res;
		
	}

	public void setDataType(String[] dataType) {
		this.dataType = dataType;
	}
	
	public H2dbDAO createDB() {
		this.db = new H2dbDAO();
		db.csvread(fileName, header, dataType);
		return db;
	}
}
