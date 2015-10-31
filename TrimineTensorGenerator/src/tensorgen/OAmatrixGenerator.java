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
	public StringBuilder status_sb = new StringBuilder();
	public boolean processing = false;
	private ProgressFrame progressFrame;
	
	
	OAmatrixGenerator(String outputFileName, long time_n, List<String> object,
			List<String> actor, DataDAO dbCon, ProgressFrame progressFrame) {
		
		this.outputFileName = outputFileName;
		this.time_n = time_n;
		this.object = object;
		this.actor = actor;
		this.dbCon = dbCon;
		this.progressFrame = progressFrame;
	}

	
	@Override
	public void run() {
		// TODO 自動生成されたメソッド・スタブ
		processing = true;
		int nowprogres = 0;
		progressFrame.addList(status_sb);
		setStatus_sb(nowprogres);
		
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
						ressb.append(j+1 + ":" + k + " ");
					}					
				}
				ressb.insert(0, elenum);
				ressb.append("1 1\n");
				filewriter.write(ressb.toString());
				nowprogres++;
				setStatus_sb(nowprogres);
				
			}
			
			filewriter.close();
			progressFrame.progvalIncrement();
			progressFrame.removeList(status_sb);

			
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		processing=false;
	}


	/**
	 * @param nowprogres
	 */
	private void setStatus_sb(int nowprogres) {
		status_sb.delete(0, status_sb.length());
		status_sb.append("TensorGen outputFileName = ");
		status_sb.append(outputFileName);
		status_sb.append(" time_n = ");
		status_sb.append(time_n);
		status_sb.append(" ");
		status_sb.append(nowprogres);
		status_sb.append(" / ");
		status_sb.append(object.size());
		//System.out.println(status_sb);
	}

}
