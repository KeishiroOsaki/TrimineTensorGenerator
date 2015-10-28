package tensorgen;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.management.RuntimeErrorException;

public class H2dbDAO extends DataDAO {

	@Override
	void dbConnect(String uri, String username, String password) {
		// TODO 自動生成されたメソッド・スタブ

		try {
			Class.forName("org.h2.Driver");
			this.connection = DriverManager.getConnection("jdbc:h2:" + uri,
					username, password);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Override
	OAstruct[] sqlExecute(long time_n) {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<OAstruct> result_als = new ArrayList<OAstruct>();
		PreparedStatement pstmt;
		int useTP = isUseTimeStamp();
		try {
			if (combitoObject = false && combitoActor == false) {

				if (useTP == 1) {

					if (timeUnit == UNIT_IS_WEEK) {
						pstmt = connection
								.prepareStatement("select ? , ? , count(*) from ? where DATEDIFF('day',timestamp '1970-01-01 00:00:00' , ?)/7 = ? group by 1,2 order by 1,2");
					} else if (timeUnit == UNIT_IS_DAY) {
						pstmt = connection
								.prepareStatement("select ? , ? , count(*) from ? where DATEDIFF('day',timestamp '1970-01-01 00:00:00' , ?) = ? group by 1,2 order by 1,2");
					} else {
						pstmt = connection
								.prepareStatement("select ? , ? , count(*) from ? where DATEDIFF('hour',timestamp '1970-01-01 00:00:00' ,  ?) = ? group by 1,2 order by 1,2");
					}
				} else {
					pstmt = connection
							.prepareStatement("select ? , ? , count(*) from ? where ? = ? group by 1,2 order by 1,2");
				}
				pstmt.setString(1, objectColumnName);
				pstmt.setString(2, actorColumnName);
				pstmt.setString(3, tableName);
				pstmt.setString(4, timeColumnName);
				pstmt.setLong(5, time_n);
				/*
				 * pstmt.setString(6, objectColumnName); pstmt.setString(7,
				 * actorColumnName); pstmt.setString(8, objectColumnName);
				 * pstmt.setString(9, actorColumnName);
				 */
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					result_als.add(new OAstruct(rs.getString(1), rs
							.getString(2), rs.getInt(3)));
				}
			} else {
				if (useTP == 1) {
					if (timeUnit == UNIT_IS_WEEK) {
						pstmt = connection
								.prepareStatement("select ? , ? , ?, count(*) from ? where DATEDIFF('day',timestamp '1970-01-01 00:00:00' , ?)/7 = ? group by 1,2,3 order by 1,2,3");
					} else if (timeUnit == UNIT_IS_DAY) {
						pstmt = connection
								.prepareStatement("select ? , ? , ? ,count(*) from ? where DATEDIFF('day',timestamp '1970-01-01 00:00:00' , ?) = ? group by 1,2,3 order by 1,2,3");
					} else {
						pstmt = connection
								.prepareStatement("select ? , ? , ? ,count(*) from ? where DATEDIFF('hour',timestamp '1970-01-01 00:00:00' ,  ?) = ? group by 1,2,3 order by 1,2,3");
					}
				} else {
					pstmt = connection
							.prepareStatement("select ? , ? , ? ,count(*) from ? where ? = ? group by 1,2,3 order by 1,2,3");
				}
				pstmt.setString(1, objectColumnName);
				pstmt.setString(2, actorColumnName);
				pstmt.setString(3, combiColumnName);
				pstmt.setString(4, tableName);
				pstmt.setString(5, timeColumnName);
				pstmt.setLong(6, time_n);
				/*
				 * pstmt.setString(6, objectColumnName); pstmt.setString(7,
				 * actorColumnName); pstmt.setString(8, objectColumnName);
				 * pstmt.setString(9, actorColumnName);
				 */
				ResultSet rs = pstmt.executeQuery();
				if (combitoObject && !combitoActor) {

					while (rs.next()) {
						if (groupofCombiValue.containsKey(rs.getString(3))) {
							result_als.add(new OAstruct(rs.getString(1) + "_"
									+ groupofCombiValue.get(rs.getString(3)),
									rs.getString(2), rs.getInt(4)));
						}
					}
				} else if (!combitoObject && combitoActor) {
					while (rs.next()) {
						if (groupofCombiValue.containsKey(rs.getString(3))) {
							result_als.add(new OAstruct(rs.getString(1), rs
									.getString(2)
									+ "_"
									+ groupofCombiValue.get(rs.getString(3)),
									rs.getInt(4)));
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

		return (OAstruct[]) result_als.toArray(new OAstruct[0]);
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
							.prepareStatement("select distinct DATEDIFF('day',timestamp '1970-01-01 00:00:00' , ?)/7 where ? between timestamp ? and timestamp ? order by 1");
				} else if (timeUnit == UNIT_IS_DAY) {
					pstmt = connection
							.prepareStatement("select distinct DATEDIFF('day',timestamp '1970-01-01 00:00:00' , ?) where ? between timestamp ? and timestamp ? order by 1");
				} else {
					pstmt = connection
							.prepareStatement("select distinct DATEDIFF('hour',timestamp '1970-01-01 00:00:00' , ?) where ? between timestamp ? and timestamp ? order by 1");
				}
				pstmt.setString(1, timeColumnName);
				pstmt.setString(2, timeColumnName);
				pstmt.setString(3, start);
				pstmt.setString(4, end);
			} else if (timeTP == 0) {
				pstmt = connection
						.prepareStatement("select distinct ? where ? between timestamp ? and timestamp ? order by 1");
				pstmt.setString(1, timeColumnName);
				pstmt.setString(2, timeColumnName);
				pstmt.setLong(3, Long.parseLong(start));
				pstmt.setLong(4, Long.parseLong(end));
			} else {
				new Exception("isUseTimeStampがうまく行ってないよ");
			}

			ResultSet rs = pstmt.executeQuery();

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

	void csvread(String[] dataType) {
		
	}
}
