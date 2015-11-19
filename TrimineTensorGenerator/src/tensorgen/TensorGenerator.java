package tensorgen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TensorGenerator {
	static final public int SQL_TYPE_H2DATABASE = 0;
	static final public int SQL_TYPE_POSTGRESQL = 1;

	static final public int UNIT_IS_WEEK = 1;
	static final public int UNIT_IS_DAY = 2;
	static final public int UNIT_IS_HOUR = 3;

	private DataDAO dbCon;

	private int sqlType;
	private String csvfileName;

	private String tblName;
	public ArrayList<OAmatrixGenerator> matgen = new ArrayList<>();
	private CSVLoader csvLoader;
	static private ExecutorService exec = Executors.newFixedThreadPool(8);

	public boolean done = false;

	public TensorGenerator(int sqlType, String uri, String username, String password) {
		// TODO 自動生成されたコンストラクター・スタブ
		switch (sqlType) {
		case SQL_TYPE_H2DATABASE:
			dbCon = new H2dbDAO();
			break;
		case SQL_TYPE_POSTGRESQL:
			dbCon = new PostgresqlDAO();
			break;
		default:
			break;
		}
		dbCon.dbConnect(uri, username, password);
	}

	public TensorGenerator(String csvfileName) {
		csvLoader = new CSVLoader(csvfileName);
	}

	public CSVLoader getCSVLoader() {
		return csvLoader;
	}

	public void setTableName(String tblName) {
		this.tblName = tblName;
		dbCon.setTableName(tblName);
	}

	public String[] getHeader() {
		return dbCon.getHeader();
	}

	public void setfieldName(String objectColName, String actorColName, String timeColName) {
		dbCon.setTimeColumnName(timeColName);
		System.out.println(dbCon.isUseTimeStamp());
		dbCon.setActorColumnName(actorColName);
		dbCon.setObjectColumnName(objectColName);
	}

	public void setTimeUnit(int unit) {
		dbCon.setTimeUnit(unit);
	}

	public void setTimeRange(String start, String end) {
		dbCon.setTimeRange(start, end);
	}

	public void setCombifieldName(String combiColName) {
		dbCon.setCombiColumnName(combiColName);
	}

	public void setCombiObject() {
		dbCon.setCombitoObject();
	}

	public void setCombiActor() {
		dbCon.setCombitoActor();
	}

	public String[] getCombiDistinctValues() {
		return dbCon.getCombiDistinctValues();
	}

	public void setCombiGroupMap(Map<String, String> map) {
		dbCon.setGroupofCombiValue(map);
	}

	public void setCombiTimesMap(Map<String, Integer> combitimesMap) {
		// TODO 自動生成されたメソッド・スタブ
		dbCon.setCombiTimesMap(combitimesMap);
	}

	public void setCombiCancel() {
		dbCon.setCombiCancel();
	}

	public void setDbCon(DataDAO dbCon) {
		this.dbCon = dbCon;
	}

	public ArrayList<OAmatrixGenerator> startGenrerateTensor() {

		String dir = "./output/";
		String baseFileName = dir + "tensor/dat.t";

		exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		delete(new File(dir));

		new File("output/tensor").mkdirs();

		long[] timeList = dbCon.getTimeDistinctValues();

		List<String> objectList = new ArrayList<>();
		List<String> actorList = new ArrayList<>();

		FileWriter fwlist = null;
		try {
			fwlist = new FileWriter(dir + "list", true);
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		StringBuilder sblist = new StringBuilder();

		Map<String, String> combival = dbCon.getGroupofCombiValue();

		if (dbCon.isCombitoObject()) {
			for (String obj : dbCon.getObjectDistinctValues()) {
				combival.values().stream().distinct().sorted().map((s) -> obj + "_" + s)
						.forEach((s) -> objectList.add(s));
			}
			Collections.sort(objectList);
			Arrays.stream(dbCon.getActorDistinctValues()).sorted().forEach((s) -> actorList.add(s));
		} else if (dbCon.isCombitoActor()) {
			Arrays.stream(dbCon.getObjectDistinctValues()).sorted().forEach((s) -> objectList.add(s));
			for (String actor : dbCon.getActorDistinctValues()) {
				combival.values().stream().distinct().sorted().forEach((s) -> actorList.add(actor + "_" + s));
			}
			Collections.sort(actorList);
		} else {
			Arrays.stream(dbCon.getObjectDistinctValues()).sorted().forEach((s) -> objectList.add(s));
			Arrays.stream(dbCon.getActorDistinctValues()).sorted().forEach((s) -> actorList.add(s));
		}

		ProgressFrame progressFrame = new ProgressFrame();
		progressFrame.setVisible(true);

		ArrayList<Future<?>> futures = new ArrayList<>();
		long timemin = timeList[0];
		long timemax = timeList[timeList.length - 1];
		progressFrame.progressBar.setMaximum((int) (timemax - timemin) + 1);
		progressFrame.setTitle("進捗状況ウィンドウ　総実行数：" + ((timemax - timemin) + 1));
		for (long j = timemin; j <= timemax; j++) {
			OAmatrixGenerator tmp = new OAmatrixGenerator(baseFileName + (j - timemin + 1), j, objectList, actorList,
					dbCon, progressFrame);
			sblist.append(baseFileName + (j - timemin + 1) + "\n");
			matgen.add(tmp);
			futures.add(exec.submit(tmp));
		}

		try {
			fwlist.write(sblist.toString());
			sblist = null;
			fwlist.close();
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		File objectcsvList = new File(dir + "objectlist.csv");
		File actorcsvList = new File(dir + "actorlist.csv");

		FileWriter fwobj = null;
		FileWriter fwact = null;
		FileWriter fwcmb = null;
		try {
			fwobj = new FileWriter(objectcsvList, true);
			fwact = new FileWriter(actorcsvList, true);

		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		StringBuilder sbobj = new StringBuilder();
		StringBuilder sbact = new StringBuilder();
		StringBuilder sbcmb = new StringBuilder();
		for (int i = 1; i <= objectList.size(); i++) {
			sbobj.append(i + ",\"" + objectList.get(i - 1).replaceAll("\"", "\"\"") + "\"\n");
		}
		try {
			fwobj.write(sbobj.toString());
			sbobj = null;
			fwobj.close();
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		for (int i = 1; i <= actorList.size(); i++) {
			sbact.append(i + ",\"" + actorList.get(i - 1).replaceAll("\"", "\"\"") + "\"\n");
		}
		try {
			fwact.write(sbact.toString());
			sbact = null;
			fwact.close();
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		File combiList = new File(dir + "combilist.csv");
		if (dbCon.isCombitoActor() == true || dbCon.isCombitoObject() == true) {
			try {
				fwcmb = new FileWriter(combiList, true);

				for (Entry<String, String> men : combival.entrySet()) {
					sbcmb.append("\"" + men.getKey().replaceAll("\"", "\"\"") + "\",\"" + men.getValue() + "\"\n");
				}

				fwcmb.write(sbcmb.toString());
				sbcmb = null;
				fwcmb.close();
			} catch (IOException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
		}

		/*
		 * Thread futurejoin = new Thread( new Runnable() {
		 * 
		 * @Override public void run() { // TODO 自動生成されたメソッド・スタブ for (Future<?>
		 * future : futures) { try { future.get(); } catch (InterruptedException
		 * | ExecutionException e) { // TODO 自動生成された catch ブロック
		 * e.printStackTrace(); } } } });
		 */

		exec.shutdown();
		
		Timer t = new Timer();
		t.schedule(new stateUpdate(matgen), 0, 3000);
		
		return matgen;
	}

	public synchronized void processShutdown() {
		exec.shutdownNow();
	}

	static void delete(File f) {
		/*
		 * ファイルまたはディレクトリが存在しない場合は何もしない
		 */
		if (f.exists() == false) {
			return;
		}

		if (f.isFile()) {
			/*
			 * ファイルの場合は削除する
			 */
			f.delete();

		} else if (f.isDirectory()) {

			/*
			 * ディレクトリの場合は、すべてのファイルを削除する
			 */

			/*
			 * 対象ディレクトリ内のファイルおよびディレクトリの一覧を取得
			 */
			File[] files = f.listFiles();

			/*
			 * ファイルおよびディレクトリをすべて削除
			 */
			for (int i = 0; i < files.length; i++) {
				/*
				 * 自身をコールし、再帰的に削除する
				 */
				delete(files[i]);
			}
			/*
			 * 自ディレクトリを削除する
			 */
			f.delete();
		}
	}

	private class stateUpdate extends TimerTask {
		private List<OAmatrixGenerator> matgen;

		public stateUpdate(List<OAmatrixGenerator> matgen) {
			// TODO 自動生成されたコンストラクター・スタブ
			this.matgen = matgen;
		}

		public void run() {
			if (matgen.stream().allMatch(s -> s.isTaskdone() == true)) {
				done = true;
			}
		}
	}
}
