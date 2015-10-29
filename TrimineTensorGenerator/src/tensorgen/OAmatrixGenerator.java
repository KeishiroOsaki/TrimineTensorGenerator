package tensorgen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.map.MultiKeyMap;

class OAmatrixGenerator implements Runnable {
	
	String outputFileName;
	long time_n;
	List<String> object;
	List<String> actor;
	DataDAO dbCon;
	
	
	OAmatrixGenerator(String outputFileName, long time_n, List<String> object,
			List<String> actor, DataDAO dbCon) {
		
		this.outputFileName = outputFileName;
		this.time_n = time_n;
		this.object = object;
		this.actor = actor;
		this.dbCon = dbCon;
	}

	
	@Override
	public void run() {
		// TODO 自動生成されたメソッド・スタブ
		
		
		
		File file = new File(outputFileName);
		try {
			FileWriter filewriter = new FileWriter(file, true);
			filewriter.write(object.size() + " " + actor.size() + " 0\n");
			
			MultiKeyMap<String, Integer> res = dbCon.sqlExecute(time_n);
			
			for (int i = 0; i < object.size(); i++) {
				int elenum = 0;
				StringBuilder ressb = new StringBuilder(" ");
				for (int j = 0; j < actor.size(); j++) {
					
					Integer k = res.get(object.get(i), actor.get(j));
					if (k != null) {
						elenum++;
						ressb.append(j + ":" + k + " ");
					}					
				}
				ressb.insert(0, elenum);
				ressb.append("1 1\n");
				filewriter.write(ressb.toString());
			}
			
			filewriter.close();

			
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
	}

}
