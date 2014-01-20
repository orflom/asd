/*
 * Created on Jan 30, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.foundstone.s3i.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.foundstone.s3i.dao.UserDAO;
import com.foundstone.s3i.model.User;

/**
 * @author davidraphael
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class UserDAOJdbc extends JdbcDaoSupport implements UserDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.dao.UserDAO#getUser(java.lang.String)
	 */
	public User getUser(String username) {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		String queryString = "select * from users where username = '"
				+ username + "'";
		System.out.println(queryString);
		List list = jt.query(queryString, new UserRowMapper());

		if (list.size() != 0) {
			User user = (User) list.get(0);
			return user;
		} else {
			throw new ObjectRetrievalFailureException(User.class, username);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.dao.UserDAO#getUsers(com.foundstone.s3i.model.User)
	 */
	public List getUsers() {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		List list = jt.query("select * from users", new UserRowMapper());
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.dao.UserDAO#saveUser(com.foundstone.s3i.model.User)
	 */
	public void saveUser(User user) {
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		String updateString = "";
		updateString = "insert into users "
				+ "(username, password, ssn, first_name, last_name, email, phone, password_hint) "
				+ " values ('" + user.getUsername() + "', " + "'"
				+ user.getPassword() + "', " + "'" + user.getSsn() + "', "
				+ "'" + user.getFirstName() + "', " + "'" + user.getLastName()
				+ "', " + "'" + user.getEmail() + "', " + "'"
				+ user.getPhoneNumber() + "', " + "'" + user.getPasswordHint()
				+ "') ";

		//TODO: this needs to be more robust
		String roleString = "insert into users_roles values ('tomcat', '"
				+ user.getUsername() + "')";
		System.out.println("inserting: " + updateString);
		jt.execute(updateString);
		jt.execute(roleString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.dao.UserDAO#removeUser(java.lang.String)
	 */
	public void removeUser(String username) {
		String update = "delete from users where username='" + username + "'";
		JdbcTemplate jt = new JdbcTemplate(getDataSource());
		jt.execute(update);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.dao.UserDAO#removeUserCookies(java.lang.String)
	 */
	public void removeUserCookies(String username) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.foundstone.s3i.dao.UserDAO#updateUser(com.foundstone.s3i.model.User)
	 */
	public void updateUser(User user) {
		//just checking that it exists
		try {
			getUser(user.getUsername());

			JdbcTemplate jt = new JdbcTemplate(getDataSource());

			String updateString = "update users " + "set username = '"
					+ user.getUsername() + "' " + ", password = '"
					+ user.getPassword() + "' " + ", ssn = '" + user.getSsn()
					+ "' " + ", first_name = '" + user.getFirstName() + "' "
					+ ", last_name= '" + user.getLastName() + "' "
					+ ", email= '" + user.getEmail() + "' " + ", phone= '"
					+ user.getPhoneNumber() + "' " + ", password_hint= '"
					+ user.getPasswordHint() + "' " + "where id = "
					+ user.getId();
			System.out.println("updating: " + updateString);
			jt.execute(updateString);
		} catch (DataAccessException e) {
			throw new DataIntegrityViolationException(
					"This user doesn't seem to exist in the DataBase");
		}
	}

	class UserQuery extends MappingSqlQuery {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.springframework.jdbc.object.MappingSqlQuery#mapRow(java.sql.ResultSet,
		 *      int)
		 */

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("ID"));
			user.setUsername(rs.getString("USERNAME"));
			user.setPassword(rs.getString("PASSWORD"));
			user.setSsn(rs.getString("SSN"));
			user.setFirstName(rs.getString("FIRST_NAME"));
			user.setLastName(rs.getString("LAST_NAME"));
			user.setEmail(rs.getString("EMAIL"));
			user.setPhoneNumber(rs.getString("PHONE"));
			user.setPasswordHint(DbUtils.getStringFromClob(rs
					.getClob("PASSWORD_HINT")));
			return user;
		}
	}

	class UsersQuery extends MappingSqlQuery {
		public UsersQuery(DataSource ds) {
			super(ds, "select * from users");
			compile();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.springframework.jdbc.object.MappingSqlQuery#mapRow(java.sql.ResultSet,
		 *      int)
		 */
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("ID"));
			user.setUsername(rs.getString("USERNAME"));
			user.setPassword(rs.getString("PASSWORD"));
			user.setSsn(rs.getString("SSN"));
			user.setFirstName(rs.getString("FIRST_NAME"));
			user.setLastName(rs.getString("LAST_NAME"));
			user.setEmail(rs.getString("EMAIL"));
			user.setPhoneNumber(rs.getString("PHONE"));
			user.setPasswordHint(DbUtils.getStringFromClob(rs
					.getClob("PASSWORD_HINT")));
			return user;
		}

	}

}