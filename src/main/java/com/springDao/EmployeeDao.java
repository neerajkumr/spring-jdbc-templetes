package com.springDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.spring.Employee;

public class EmployeeDao {

	private JdbcTemplate jd;

	public JdbcTemplate getJd() {
		return jd;
	}

	public void setJd(JdbcTemplate jd) {
		this.jd = jd;
	}

	public int insertE(Employee e) {
		String quary = "insert into employees values('" + e.geteName() + "','" + e.geteId() + " ','" + e.getDOB()
				+ "')";
		return jd.update(quary);
	}

	public int updateE(int id, String name) {
		String quary = "update employees set e_name='" + name + "' where e_id='" + id + "'";
		return jd.update(quary);
	}

	public int deleteE(String name) {
		String quary = "delete from employees where e_name='" + name + "'";
		return jd.update(quary);
	}

	public Boolean insertWithPS(final Employee e) {
		String quary = "insert into employees values(?,?,?)";
		return jd.execute(quary, new PreparedStatementCallback<Boolean>() {

			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, e.geteName());
				ps.setInt(2, e.geteId());
				ps.setString(3, e.getDOB());
				return ps.execute();
			}

		});
	}

	public List<Employee> getAll() {

		return jd.query("select * from employees", new ResultSetExtractor<List<Employee>>() {

			public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Employee> list = new LinkedList<Employee>();
				while (rs.next()) {
					Employee e11 = new Employee();
					e11.seteName(rs.getString(1));
					e11.seteId(rs.getInt(2));
					e11.setDOB(rs.getString(3));
					list.add(e11);
				}
				return list;
			}

		});
	}

	public List<Employee> getByID(int id) {
		String quary = "select * from Employees where e_id=?";
		List<Employee> emp = jd.query(quary, new BeanPropertyRowMapper<Employee>(Employee.class), id);
		return emp;
	}
}
