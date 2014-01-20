package com.foundstone.s3i.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import com.foundstone.s3i.model.Address;
import com.foundstone.s3i.model.Role;
import com.foundstone.s3i.model.User;

public class UserDAOTest extends BaseDAOTestCase {
	private UserDAO dao = null;

	private User user = null;

	//    private RoleDAO rdao = null;
	private Role role = null;

	protected void setUp() throws Exception {
		super.setUp();
		dao = (UserDAO) ctx.getBean("userDAO");
		//       rdao = (RoleDAO) ctx.getBean("roleDAO");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		dao = null;
	}

	public void testGetUserInvalid() throws Exception {
		try {
			user = dao.getUser("badusername");
			fail("'badusername' found in database, failing test...");
		} catch (DataAccessException d) {
			assertTrue(d != null);
		}
	}

	public void testGetUser() throws Exception {
		user = dao.getUser("draphael");

		assertNotNull(user);
		//assertEquals(1, user.getRoles().size());
	}

	public void testSaveUserDuplicate() throws Exception {
		user = dao.getUser("draphael");
		try {
			dao.saveUser(user);
			fail("saveUser didn't throw DataIntegrityViolationException");
		} catch (DataIntegrityViolationException e) {
			assertNotNull(e);
			log.debug("expected exception: " + e.getMessage());
		}

	}

	public void testUpdateUser() throws Exception {
		user = dao.getUser("draphael");

		System.out.println("user: " + user + " is about to be saved.");

		try {
			dao.updateUser(user);

		} catch (DataAccessException e) {
			log.error(e);
			fail("Looks like their was a problem making the changes persistent.");
		}

	}

	/*
	 * public void testAddUserRole() throws Exception { user = dao.getUser("");
	 * 
	 * assertEquals(1, user.getRoles().size());
	 * 
	 * role = rdao.getRole(Constants.ADMIN_ROLE); user.addRole(role);
	 * dao.saveUser(user);
	 * 
	 * assertEquals(2, user.getRoles().size());
	 * 
	 * //add the same role twice - should result in no additional role
	 * user.addRole(role); dao.saveUser(user);
	 * 
	 * assertEquals("more than 2 roles", 2, user.getRoles().size());
	 * 
	 * user.getRoles().remove(role); dao.saveUser(user);
	 * 
	 * assertEquals(1, user.getRoles().size()); }
	 */

	public void testAddAndRemoveUser() throws Exception {
		user = new User();
		user.setUsername("testuser");
		user.setPassword("testpass");
		user.setSsn("123-12-1234");
		user.setFirstName("Test");
		user.setLastName("Last");
		user.setEmail("testuser@appfuse.org");
		user.setPasswordHint("my hint");

		Address address = new Address();
		address.setCity("Denver");
		address.setProvince("CO");
		address.setCountry("USA");
		address.setPostalCode("80210");

		//TODO: Rewrite to support proper addressing model.
		//user.setAddress(address);

		//       user.addRole(rdao.getRole(Constants.USER_ROLE));

		dao.saveUser(user);

		assertNotNull(user.getUsername());
		assertEquals("testpass", user.getPassword());

		dao.removeUser("testuser");

		try {
			user = dao.getUser("testuser");
			fail("saveUser didn't throw DataAccessException");
		} catch (DataAccessException d) {
			assertNotNull(d);
		}
	}

	public void testSaveAndDeleteUserCookie() throws Exception {
		//        String cookieId = "BA67E786-C031-EA40-2769-863BB30B31EC";
		//        UserCookie cookie = new UserCookie();
		//        cookie.setUsername("draphael");
		//        cookie.setCookieId(cookieId);
		//        dao.saveUserCookie(cookie);
		//        cookie = dao.getUserCookie(cookie);
		//        assertEquals(cookieId, cookie.getCookieId());
		//
		//        dao.removeUserCookies(cookie.getUsername());
		//
		//        cookie = dao.getUserCookie(cookie);
		//
		//        assertNull(cookie);
	}

	public void testGetUsers() {
		List users = dao.getUsers();
		System.out.println(users);

	}
}