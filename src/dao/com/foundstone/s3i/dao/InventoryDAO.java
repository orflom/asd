/*
 * Created on Feb 7, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.foundstone.s3i.dao;

import java.util.List;

import com.foundstone.s3i.model.InventoryEntry;

/**
 * @author davidraphael
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface InventoryDAO {
	public List getInventoryItems();
	public InventoryEntry getInventoryEntry(int id);
}
