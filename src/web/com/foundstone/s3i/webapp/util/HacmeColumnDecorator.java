package com.foundstone.s3i.webapp.util;

import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.exception.DecoratorException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/** This class is necessary for formatting numeric columns
 * and dates in the Hacme Books web application.
 * 
 * @author Roman Hustad
 */
public class HacmeColumnDecorator implements ColumnDecorator {
	private SimpleDateFormat dateFmt = null;
	private DecimalFormat moneyFmt = null;

	public HacmeColumnDecorator() {
		super();
		this.dateFmt = new SimpleDateFormat("MM/dd/yy");
		this.moneyFmt = new DecimalFormat("###,###.00");
	}

	public String decorate(Object obj) throws DecoratorException {
		if (obj instanceof Double || obj instanceof Float)
			return moneyFormat(obj);
		else if (obj instanceof Date || obj instanceof java.sql.Date)
			return dateFormat(obj);
		return obj.toString();
	}

	/**
	 * Returns the date as a String in MM/dd/yy format
	 */
	public String dateFormat(Object obj) {
		return this.dateFmt.format(obj);
	}

	/**
	 * Returns the decimal as a String in ###,###.00 format
	 */
	public String moneyFormat(Object obj) {
		return this.moneyFmt.format(obj);
	}
}
