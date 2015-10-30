package tensorgen;

import java.io.File;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.management.RuntimeErrorException;

import org.apache.commons.collections4.map.MultiKeyMap;

public class H2dbDAO extends DataDAO {

	@Override
	void dbConnect(String uri, String username, String password) {
		// TODO 自動生成されたメソッド・スタブ

		try {
			Class.forName("org.h2.Driver");
			this.connection = DriverManager.getConnection("jdbc:h2:" + uri,
					username, password);
			//connection.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Override
	MultiKeyMap<String, Integer> sqlExecute(long time_n) {
		// TODO 自動生成されたメソッド・スタブ
		// ArrayList<OAstruct> result_als = new ArrayList<OAstruct>();
		MultiKeyMap<String, Integer> map = new MultiKeyMap<>();
		PreparedStatement pstmt;
		int useTP = isUseTimeStamp();
		try {
			if (combitoObject == false && combitoActor == false) {

				if (useTP == 1) {

					if (timeUnit == UNIT_IS_WEEK) {
						pstmt = connection
								.prepareStatement("select "+objectColumnName+" , "+actorColumnName+" , count(*) from "+tableName+" where DATEDIFF('day',timestamp '1970-01-01 00:00:00' , "+timeColumnName+")/7 = ? group by 1,2 order by 1,2");
					} else if (timeUnit == UNIT_IS_DAY) {
						pstmt = connection
								.prepareStatement("select "+objectColumnName+" , "+actorColumnName+" , count(*) from "+tableName+" where DATEDIFF('day',timestamp '1970-01-01 00:00:00' , "+timeColumnName+") = ? group by 1,2 order by 1,2");
					} else {
						pstmt = connection
								.prepareStatement("select "+objectColumnName+" , "+actorColumnName+" , count(*) from "+tableName+" where DATEDIFF('hour',timestamp '1970-01-01 00:00:00' ,  "+timeColumnName+") = ? group by 1,2 order by 1,2");
					}
				} else {
					pstmt = connection
							.prepareStatement("select "+objectColumnName+" , "+actorColumnName+" , count(*) from "+tableName+" where "+timeColumnName+" = ? group by 1,2 order by 1,2");
				}
				//pstmt.setString(1, objectColumnName);
				//pstmt.setString(2, actorColumnName);
				//pstmt.setString(3, tableName);
				//pstmt.setString(4, timeColumnName);
				pstmt.setLong(1, time_n);
				/*
				 * pstmt.setString(6, objectColumnName); pstmt.setString(7,
				 * actorColumnName); pstmt.setString(8, objectColumnName);
				 * pstmt.setString(9, actorColumnName);
				 */
				ResultSet rs = pstmt.executeQuery();
				//connection.commit();
				while (rs.next()) {
					// result_als.add(new OAstruct(rs.getString(1), rs
					// .getString(2), rs.getInt(3)));
					map.put(rs.getString(1), rs.getString(2), rs.getInt(3));
				}
			} else {
				if (useTP == 1) {
					if (timeUnit == UNIT_IS_WEEK) {
						pstmt = connection
								.prepareStatement("select "+objectColumnName+" , "+actorColumnName+" , "+combiColumnName+", count(*) from "+tableName+" where DATEDIFF('day',timestamp '1970-01-01 00:00:00' , "+timeColumnName+")/7 = ? group by 1,2,3 order by 1,2,3");
					} else if (timeUnit == UNIT_IS_DAY) {
						pstmt = connection
								.prepareStatement("select "+objectColumnName+" , "+actorColumnName+" , "+combiColumnName+" ,count(*) from "+tableName+" where DATEDIFF('day',timestamp '1970-01-01 00:00:00' , "+timeColumnName+") = ? group by 1,2,3 order by 1,2,3");
					} else {
						pstmt = connection
								.prepareStatement("select "+objectColumnName+" , "+actorColumnName+" , "+combiColumnName+" ,count(*) from "+tableName+" where DATEDIFF('hour',timestamp '1970-01-01 00:00:00' ,  "+timeColumnName+") = ? group by 1,2,3 order by 1,2,3");
					}
				} else {
					pstmt = connection
							.prepareStatement("select "+objectColumnName+" , "+actorColumnName+" , "+combiColumnName+" ,count(*) from "+tableName+" where "+timeColumnName+" = ? group by 1,2,3 order by 1,2,3");
				}
				//pstmt.setString(1, objectColumnName);
				//pstmt.setString(2, actorColumnName);
				//pstmt.setString(3, combiColumnName);
				//pstmt.setString(4, tableName);
				//pstmt.setString(5, timeColumnName);
				pstmt.setLong(1, time_n);
				/*
				 * pstmt.setString(6, objectColumnName); pstmt.setString(7,
				 * actorColumnName); pstmt.setString(8, objectColumnName);
				 * pstmt.setString(9, actorColumnName);
				 */
				ResultSet rs = pstmt.executeQuery();
				//connection.commit();
				if (combitoObject && !combitoActor) {

					while (rs.next()) {
						if (groupofCombiValue.containsKey(rs.getString(3))) {
							// result_als.add(new OAstruct(rs.getString(1) + "_"
							// + groupofCombiValue.get(rs.getString(3)),
							// rs.getString(2), rs.getInt(4)));
							Integer kekka = map.get(rs.getString(1) + "_"
									+ groupofCombiValue.get(rs.getString(3)),
									rs.getString(2), rs.getInt(4));
							if (kekka == null) {
								map.put(rs.getString(1)
										+ "_"
										+ groupofCombiValue
												.get(rs.getString(3)),
										rs.getString(2), rs.getInt(4));
							} else {
								map.put(rs.getString(1)
										+ "_"
										+ groupofCombiValue
												.get(rs.getString(3)),
										rs.getString(2), kekka + rs.getInt(4));
							}
						}
					}
				} else if (!combitoObject && combitoActor) {
					while (rs.next()) {
						if (groupofCombiValue.containsKey(rs.getString(3))) {
							// result_als.add(new OAstruct(rs.getString(1), rs
							// .getString(2)
							// + "_"
							// + groupofCombiValue.get(rs.getString(3)),
							// rs.getInt(4)));
							Integer kekka = map.get(rs.getString(1) + "_"
									+ groupofCombiValue.get(rs.getString(3)),
									rs.getString(2), rs.getInt(4));
							if (kekka == null) {
								map.put(rs.getString(1)
										+ "_"
										+ groupofCombiValue
												.get(rs.getString(3)),
										rs.getString(2), rs.getInt(4));
							} else {
								map.put(rs.getString(1)
										+ "_"
										+ groupofCombiValue
												.get(rs.getString(3)),
										rs.getString(2), kekka + rs.getInt(4));
							}
						}
					}
				} else {
					new RuntimeException("どっちとも組み合わせられるわけ無いじゃろ。出直しじゃ。")
							.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// return (OAstruct[]) result_als.toArray(new OAstruct[0]);
		return map;
	}

	@Override
	long[] getTimeDistinctValues() {
		ArrayList<Long> timeList = new ArrayList<Long>();

		PreparedStatement pstmt = null;

		try {
			int timeTP = isUseTimeStamp();
			if (timeTP == 1) {
				if (timeUnit == UNIT_IS_WEEK) {
					pstmt = connection
							.prepareStatement("select distinct DATEDIFF('day',timestamp '1970-01-01 00:00:00' , "+timeColumnName+")/7 where "+timeColumnName+" between timestamp ? and timestamp ? order by 1");
				} else if (timeUnit == UNIT_IS_DAY) {
					pstmt = connection
							.prepareStatement("select distinct DATEDIFF('day',timestamp '1970-01-01 00:00:00' , "+timeColumnName+") where "+timeColumnName+" between timestamp ? and timestamp ? order by 1");
				} else {
					pstmt = connection
							.prepareStatement("select distinct DATEDIFF('hour',timestamp '1970-01-01 00:00:00' , "+timeColumnName+") where "+timeColumnName+" between timestamp ? and timestamp ? order by 1");
				}
				//pstmt.setString(1, timeColumnName);
				//pstmt.setString(1, timeColumnName);
				pstmt.setString(1, start);
				pstmt.setString(2, end);
			} else if (timeTP == 0) {
				pstmt = connection
						.prepareStatement("select distinct "+timeColumnName+" where "+timeColumnName+" between ? and ? order by 1");
				//pstmt.setString(1, timeColumnName);
				//pstmt.setString(2, timeColumnName);
				pstmt.setLong(1, Long.parseLong(start));
				pstmt.setLong(2, Long.parseLong(end));
			} else {
				new Exception("isUseTimeStampがうまく行ってないよ");
			}

			ResultSet rs = pstmt.executeQuery();
			//connection.commit();

			while (rs.next()) {
				timeList.add(rs.getLong(1));
			}

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		long[] longArray = new long[timeList.size()];
		for (int i = 0; i < timeList.size(); i++) {
			longArray[i] = timeList.get(i); // Long
		}
		return longArray;
	}

	void csvread(String fileName, String[] header, String[] dataType) {
		if (header.length != dataType.length) {
			new IllegalArgumentException("ヘッダーの長さとデータ・タイプの長さが違う");
		} else {

			TensorGenerator.delete(new File("./tempmv.db"));
			
			dbConnect("./temp", "sa", "");
			StringBuilder sql = new StringBuilder();
			sql.append("Create table tmp (");

			int l = header.length;
			String[] str = new String[l];
			for (int i = 0; i < l; i++) {
				str[i] = header[i] + " " + dataType[i];
			}
			sql.append(String.join(",", str));
			sql.append(");");

			int res;
			try {
				res = connection.createStatement()
						.executeUpdate(sql.toString());
				

				Statement stmt = connection.createStatement();

				String sql2 = "insert into tmp select * from csvread( '"
						+ fileName + "' );";

				res = stmt.executeUpdate(sql2);
				//connection.commit();
				stmt.close();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}
}
