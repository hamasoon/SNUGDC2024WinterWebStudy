package next.dao;

import core.jdbc.ConnectionManager;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConnector {
	private Logger log;

	public DBConnector() {
		log = LoggerFactory.getLogger("DBLogger");
	}

	private void dbClosing(PreparedStatement pstmt, Connection con) throws SQLException {
		try {
			if (pstmt != null) {
				pstmt.close();
			}

			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void dbClosing(PreparedStatement pstmt, Connection con, ResultSet rs) throws SQLException {
		try {
			dbClosing(pstmt, con);

			if (rs == null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private PreparedStatement sqlSetter(Connection con, String sql, String... var) throws SQLException {
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(sql);
			for (int i=0; i<var.length; i++)
				pstmt.setString(i+1, var[i]);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return pstmt;
	}

	public void run(String sql, String... var) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ConnectionManager.getConnection();
			pstmt = sqlSetter(con, sql, var);

			pstmt.executeUpdate();
			dbClosing(pstmt, con);
		} catch (Exception e) {
			e.printStackTrace();
			dbClosing(pstmt, con);
			throw new RuntimeException(e);
		}
	}

	public <T> List<T> run(Class<T> resultTypeClass, String sql, String... var) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsm = null;
		List<T> ret = new ArrayList<T>();

		try {
			con = ConnectionManager.getConnection();
			pstmt = sqlSetter(con, sql, var);

			rs = pstmt.executeQuery();
			rsm = rs.getMetaData();
			int colCount = rsm.getColumnCount();

			if (resultTypeClass != null) {
				while (rs.next()) {
					T instance = null;

					try {
						instance = resultTypeClass.newInstance();
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
						dbClosing(pstmt, con, rs);
						throw new RuntimeException(e);
					}

					if (instance != null) {
						Field[] Fields = resultTypeClass.getDeclaredFields();
						for (int i = 1; i <= colCount; i++) {
							String columnLabel = rsm.getColumnLabel(i);
							for (Field field : Fields) {
								if (field.getName().toLowerCase().equals(columnLabel.toLowerCase())) {
									field.setAccessible(true);
									try {
										field.set(instance, rs.getObject(i));
									} catch (IllegalArgumentException | IllegalAccessException e) {
										e.printStackTrace();
										dbClosing(pstmt, con, rs);
										throw new RuntimeException(e);
									}
								}
							}
						}

						ret.add(instance);
					}
				}
			}

			dbClosing(pstmt, con, rs);

			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
			dbClosing(pstmt, con, rs);
			throw new RuntimeException(e);
		}
	}
}
