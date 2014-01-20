/*
 * Created on Feb 7, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.foundstone.s3i.dao.jdbc;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * @author davidraphael
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DbUtils {

	public static String getStringFromClob(Clob clob) {
		try {
			Reader characterStream = clob.getCharacterStream();
			int c = -1;
			StringBuffer sb = new StringBuffer();
			while ((c = characterStream.read()) != -1) {
				sb.append((char) c);
			}
			return sb.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}