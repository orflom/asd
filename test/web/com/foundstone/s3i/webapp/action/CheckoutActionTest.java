/*
 * Created on Feb 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.foundstone.s3i.webapp.action;

import junit.framework.TestCase;

/**
 * @author davidraphael
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CheckoutActionTest extends TestCase {

	public void testRetrieveCoupon() {
		CheckoutAction action = new CheckoutAction();
		float f = action.retrieveCoupon("AAOABOBOOE");
		System.out.println(f);
		f = action.retrieveCoupon("AAOBBOBOOE");
		System.out.println(f);
		f = action.retrieveCoupon("AAOBBOBOOD");
		System.out.println(f);
	}

}
