package tensorgen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.map.MultiKeyMap;

class OAmatrixGenerator implements Runnable {
	
	private static final String SLASH = " / ";
	private static final String TIME_N2 = " time_n = ";
	private static final String TENSOR_GEN_OUTPUT_FILE_NAME = "TensorGen outputFileName = ";
	String outputFileName;
	long time_n;
	List<String> object;
	List<String> actor;
	DataDAO dbCon;
	public StringBuilder status_sb = new StringBuilder();
	public boolean processing = false;
	private ProgressFrame progressFrame;
	int objectsize = 0;
	private static final String terminalStr = "1 1\n";;
	private static final String colone = ":";
	private static final String space = " ";
	private boolean taskdone = false;
	
	
	OAmatrixGenerator(String outputFileName, long time_n, List<String> object,
			List<String> actor, DataDAO dbCon, ProgressFrame progressFrame) {
		
		this.outputFileName = outputFileName;
		this.time_n = time_n;
		this.object = object;
		this.actor = actor;
		this.dbCon = dbCon;
		this.progressFrame = progressFrame;
		this.objectsize = object.size();
		
		
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
			
			
			
			for (int i = 0; i < object.size()  && !Thread.currentThread().isInterrupted()/* && !TensorGenerator.exec.isShutdown()*/; i++) {
				oneLineGenerate(filewriter, res, i);
				nowprogres++;
				setStatus_sb(nowprogres);
				//System.out.println(Thread.currentThread().isInterrupted());
				
			}
			
			filewriter.close();
			res=null;
			progressFrame.progvalIncrement();
			progressFrame.removeList(status_sb);

			
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		processing=false;
		setTaskdone(true);
	}


	private void oneLineGenerate(FileWriter filewriter, MultiKeyMap<String, Integer> res, int i) throws IOException {
		int elenum = 0;
	    StringBuilder ressb = new StringBuilder(space);
		for (int j = 0; j < actor.size(); j++) {
			
			Integer k = res.get(object.get(i), actor.get(j));
			if (k != null) {
				elenum++;
				ressb.append(j+1 + colone + k + space);
			}					
		}
		ressb.insert(0, elenum);
		ressb.append(terminalStr);
		filewriter.write(ressb.toString());
	}


	/**
	 * @param nowprogres
	 */
	private void setStatus_sb(int nowprogres) {
		status_sb.delete(0, status_sb.length());
		status_sb.append(TENSOR_GEN_OUTPUT_FILE_NAME/* + outputFileName + TIME_N2 + time_n + space + nowprogres + SLASH + objectsize*/);
		status_sb.append(outputFileName);
		status_sb.append(TIME_N2);
		status_sb.append(time_n);
		status_sb.append(space);
		status_sb.append(nowprogres);
		status_sb.append(SLASH);
		status_sb.append(objectsize);
		//System.out.println(status_sb);
	}


	/**
	 * @return taskdone
	 */
	boolean isTaskdone() {
		return taskdone;
	}


	/**
	 * @param taskdone セットする taskdone
	 */
	void setTaskdone(boolean taskdone) {
		this.taskdone = taskdone;
	}

}
