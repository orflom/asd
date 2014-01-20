/*
 * Created on Feb 16, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.foundstone.s3i.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.foundstone.s3i.model.Feedback;

/**
 * @author davidraphael
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ProductFeedbackRowMapper implements RowMapper {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
	 *      int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		Feedback fb = new Feedback();
		fb.setFeedback(rs.getString("FEEDBACK"));
		fb.setId(rs.getInt("ID"));
		return fb;
	}

}