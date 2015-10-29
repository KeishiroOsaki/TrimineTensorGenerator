package tensorgen;

import java.util.List;

class OAmatrixGenerator implements Runnable {
	
	String outputFileName;
	long time_n;
	List object;
	List actor;
	DataDAO dbCon;
	
	
	OAmatrixGenerator(String outputFileName, long time_n, List object,
			List actor, DataDAO dbCon) {
		
		this.outputFileName = outputFileName;
		this.time_n = time_n;
		this.object = object;
		this.actor = actor;
		this.dbCon = dbCon;
	}

	
	@Override
	public void run() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
