package tensorgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.collections4.map.MultiKeyMap;

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

	public TensorGenerator(int sqlType) {
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

	public void setCombiCancel() {
		dbCon.setCombiCancel();
	}

	public void startGenrerateTensor() {
		String dir = "./out/";
		String baseFileName = dir + "dat.t";

		long[] timeList = dbCon.getTimeDistinctValues();

		ArrayList<String> objectList = new ArrayList<>();
		ArrayList<String> actorList = new ArrayList<>();

		Map<String, String> combival = dbCon.getGroupofCombiValue();

		if (dbCon.isCombitoObject()) {
			for (String obj : dbCon.getObjectDistinctValues()) {
				combival.values().stream().distinct().sorted().forEach((s) -> objectList.add(obj + "_" + s));
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

		ExecutorService exec = Executors.newFixedThreadPool(6);
		ArrayList<Future<?>> futures = new ArrayList<>();
		for (long time_n : timeList) {
			OAmatrixGenerator tmp = new OAmatrixGenerator(baseFileName + (time_n - timeList[0] + 1), time_n, objectList,
					actorList, dbCon);
			matgen.add(tmp);
			futures.add(exec.submit(tmp));
		}
		
		for (Future<?> future : futures) {
			try {
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		exec.shutdown();
	}
}
