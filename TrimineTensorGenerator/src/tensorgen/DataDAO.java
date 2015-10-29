package tensorgen;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections4.map.MultiKeyMap;

public abstract class DataDAO {

	static final public int UNIT_IS_WEEK = 1;
	static final public int UNIT_IS_DAY = 2;
	static final public int UNIT_IS_HOUR = 3;

	protected Connection connection;
	protected String tableName;
	protected String timeColumnName;
	protected String objectColumnName;
	protected String actorColumnName;
	protected String combiColumnName;
	protected Map<String, String> groupofCombiValue;

	protected boolean combitoObject = false;
	protected boolean combitoActor = false;
	protected int timeUnit = 1;

	protected String start;
	protected String end;

	abstract void dbConnect(String uri, String username, String password);

	abstract MultiKeyMap<String, Integer> sqlExecute(long time_n);

	String[] getHeader() {
		ArrayList<String> colList = new ArrayList<String>();

		try {
			PreparedStatement stmt;
			stmt = connection.prepareStatement("SELECT * from ?;");

			stmt.setString(1, tableName);

			ResultSet rs = stmt.executeQuery();
			rs.getMetaData();

			ResultSetMetaData rmd = rs.getMetaData();
			for (int i = 1; i <= rmd.getColumnCount(); i++) {

				colList.add(rmd.getColumnName(i));
			}
			rs.close();
		} catch (SQLException e2) {
			// TODO 自動生成された catch ブロック
			e2.printStackTrace();
		}
		return (String[]) colList.toArray(new String[0]);
	}

	int[] getDataTypes() {
		ArrayList<Integer> colList = new ArrayList<Integer>();

		try {
			PreparedStatement stmt;
			stmt = connection.prepareStatement("SELECT * from ?;");

			stmt.setString(1, tableName);

			ResultSet rs = stmt.executeQuery();
			rs.getMetaData();

			ResultSetMetaData rmd = rs.getMetaData();
			for (int i = 1; i <= rmd.getColumnCount(); i++) {

				colList.add(rmd.getColumnType(i));
			}
			rs.close();
		} catch (SQLException e2) {
			// TODO 自動生成された catch ブロック
			e2.printStackTrace();
		}

		int[] intArray = new int[colList.size()];
		for (int i = 0; i < colList.size(); i++) {
			intArray[i] = colList.get(i); // Integer
		}
		return intArray;
	}

	void setCombitoObject() {
		this.combitoObject = true;
		this.combitoActor = false;
	}

	void setCombitoActor() {
		this.combitoObject = false;
		this.combitoActor = true;
	}

	void setCombiCancel() {
		this.combitoObject = false;
		this.combitoActor = false;
	}

	/**
	 * @return groupofCombiValue
	 */
	Map<String, String> getGroupofCombiValue() {
		return groupofCombiValue;
	}
	
	void setGroupofCombiValue(Map<String, String> groupofCombiValue) {
		this.groupofCombiValue = groupofCombiValue;
	}

	protected String[] getDistinctValues(String fieldName) {
		ArrayList<String> dList = new ArrayList<String>();

		PreparedStatement pstmt;
		try {
			pstmt = connection.prepareStatement("select DISTINCT ? from ?;");

			pstmt.setString(1, fieldName);
			pstmt.setString(2, tableName);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				dList.add(rs.getString(1));
			}

			rs.close();
			pstmt.close();

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {

		}

		return (String[]) dList.toArray(new String[0]);

	}

	void setTimeUnit(int timeUnit) {
		if (1 <= timeUnit && timeUnit <= 3) {
			this.timeUnit = timeUnit;
		} else {
			new IllegalArgumentException("引数の値が決められた範囲を外れています");
		}
	}

	int isUseTimeStamp() {
		PreparedStatement pstmt;
		try {
			pstmt = connection.prepareStatement("select ? from ?;");

			pstmt.setString(1, timeColumnName);
			pstmt.setString(2, tableName);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rmd = rs.getMetaData();
			if (rmd.getColumnType(1) == java.sql.Types.DATE
					|| rmd.getColumnType(1) == java.sql.Types.TIMESTAMP
					|| rmd.getColumnType(1) == java.sql.Types.TIMESTAMP_WITH_TIMEZONE) {
				return 1;
			} else {
				return 0;
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		new RuntimeException("時間のデータ型検査が正常にできてない");
		return -1;
	}

	void setTimeRange(String start,String end) {
		int tcolType = isUseTimeStamp();
		if (tcolType == 1) {
			try {
			Timestamp.valueOf(start);
			Timestamp.valueOf(end);
			
			this.start = start;
			this.end = end;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		} else if (tcolType == 0) {
			try {
				Long.parseLong(start);
				Long.parseLong(end);
				
				this.start = start;
				this.end = end;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}
	
	void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @param objectColumnName セットする objectColumnName
	 */
	void setObjectColumnName(String objectColumnName) {
		this.objectColumnName = objectColumnName;
	}

	/**
	 * @param actorColumnName セットする actorColumnName
	 */
	void setActorColumnName(String actorColumnName) {
		this.actorColumnName = actorColumnName;
	}

	/**
	 * @param combiColumnName セットする combiColumnName
	 */
	void setCombiColumnName(String combiColumnName) {
		this.combiColumnName = combiColumnName;
	}
	
	/**
	 * @param timeColumnName セットする timeColumnName
	 */
	void setTimeColumnName(String timeColumnName) {
		this.timeColumnName = timeColumnName;
	}

	String[] getCombiDistinctValues() {
		return getDistinctValues(combiColumnName);
	}
	
	String[] getActorDistinctValues() {
		return getDistinctValues(actorColumnName);
		
	}
	
	abstract long[] getTimeDistinctValues(); //時間をタイムスライスに区分するやりかたがSQLサーバーによって違うため抽象化
	
	String[] getObjectDistinctValues() {
		return getDistinctValues(objectColumnName);
	}

	/**
	 * @return combitoObject
	 */
	boolean isCombitoObject() {
		return combitoObject && !combitoActor;
	}

	/**
	 * @return combitoActor
	 */
	boolean isCombitoActor() {
		return combitoActor && !combitoObject;
	}
}
