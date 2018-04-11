package com.fireim.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fireim.jdbc.JdbcUtil;

public class Test {

	/**
	 * 测试
	 * 
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		Connection con = JdbcUtil.getConnection();
		String sql;
		long startTime = System.currentTimeMillis();
		sql = "SELECT total.goodsno,total.goodsname,total.goodsprice,total.totalamount,pp.colors from (select o.goodsno,o.goodsname,o.goodsprice,sum(o.totalamount) as totalamount from orders o GROUP BY o.goodsno ORDER BY sum(o.totalamount) desc  limit 20) as total join (select  d.goodsno, GROUP_CONCAT(DISTINCT d.colors Separator ',') as colors from (SELECT goodsno,REPLACE(SUBSTRING_INDEX(colors, ',', a.id),CONCAT(SUBSTRING_INDEX(colors, ',', a.id - 1),','),'')AS colors FROM squence a CROSS JOIN(SELECT goodsno,CONCAT(colors, ',')AS colors,LENGTH(colors)- LENGTH(REPLACE(colors, ',', ''))+ 1 AS size FROM `orders`)b ON a.id <= b.size) as d group by d.goodsno) as pp on pp.goodsno=total.goodsno";
		long endTime = System.currentTimeMillis();
		PreparedStatement ps=con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		System.out.println("查询数据库耗时：" + (endTime - startTime) + "ms");
		while (rs.next()) {
			System.out.println("款号:" + rs.getString(1) + "---名称:" + rs.getString(2) + "---价格:" + rs.getString(3)
					+ "---累计销量:" + rs.getString(4) + "---销售过的颜色:" + rs.getString(5));
		}
		JdbcUtil.close(con, ps, rs);
	}
}
