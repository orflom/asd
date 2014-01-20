package com.foundstone.s3i;


/**
 * Constant values used throughout the application.
 *
 * <p>
 * <a href="Constants.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class Constants {
    //~ Static fields/initializers =============================================

    /**
     * The application scoped attribute for persistence engine and class that
     * implements it
     */
    public static final String DAO_TYPE = "daoType";
    public static final String DAO_TYPE_HIBERNATE = "hibernate";

    /** Application scoped attribute for authentication url */
    public static final String AUTH_URL = "authURL";

    /** Application scoped attributes for SSL Switching */
    public static final String HTTP_PORT = "httpPort";
    public static final String HTTPS_PORT = "httpsPort";

    /** The application scoped attribute for indicating a secure login */
    public static final String SECURE_LOGIN = "secureLogin";

    /** The encryption algorithm key to be used for passwords */
    public static final String ENC_ALGORITHM = "algorithm";

    /** A flag to indicate if passwords should be encrypted */
    public static final String ENCRYPT_PASSWORD = "encryptPassword";

    /** File separator from System properties */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /** User home from System properties */
    public static final String USER_HOME =
        System.getProperty("user.home") + FILE_SEP;

    /**
     * The session scope attribute under which the breadcrumb ArrayStack is
     * stored
     */
    public static final String BREADCRUMB = "breadcrumbs";

    /**
     * The session scope attribute under which the User object for the
     * currently logged in user is stored.
     */
    public static final String USER_KEY = "currentUserForm";

    /**
     * The request scope attribute under which an editable user form is stored
     */
    public static final String USER_EDIT_KEY = "userForm";

    /**
     * The request scope attribute that holds the user list
     */
    public static final String USER_LIST = "userList";
	public static final String CURRENT_PRODUCT = "currentProduct";

    /**
     * The request scope attribute that holds the product list
     */
    public static final String PRODUCT_LIST = "productList";
    
    /**
     * The request scope attribute that holds the users orders list
     */
    public static final String ORDER_LIST = "orderList";
    
    /**
     * The request scope attribute that holds the shopping list
     * This should just be a single order that hasn't been persisted
     */
    public static final String SHOPPING_LIST = "shoppingList";
    
    /**
     * The request scope attribute for indicating a newly-registered user
     */
    public static final String REGISTERED = "registered";

    /**
     * The name of the Administrator role, as specified in web.xml
     */
    public static final String ADMIN_ROLE = "admin";

    /**
     * The name of the User role, as specified in web.xml
     */
    public static final String USER_ROLE = "tomcat";

    /**
     * The name of the user's role list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String USER_ROLES = "userRoles";

    /**
     * The name of the available roles list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String AVAILABLE_ROLES = "availableRoles";

    /**
     * Name of cookie for "Remember Me" functionality.
     */
    public static final String LOGIN_COOKIE = "sessionId";

    /**
     * The name of the configuration hashmap stored in application scope.
     */
    public static final String CONFIG = "appConfig";
    
    /**
     * The name of the user's shopping cart
     * @author davidraphael
     */
    public static final String USER_SHOPPING_CART = "shoppingCart";

    /**
     * This is the ID we will pass to the shopping cart that represents the Product we want to add to the cart.
     */
    public static final String SHOPPING_CART_PRODUCT_ID = "productId";

    /**
     * This is the ID we will pass to the shopping cart that represents the Product we want to add to the cart.
     */
    public static final String PRODUCT_OBJECT = "product";

    /**
     * This is the ID we will pass to the shopping cart that represents the Hotlis we want to add to the main page.
     */
    public static final String PRODUCT_HOTLIST = "hotlist";
    
    public static final String USER_NAME = "username";

    /** 
     * Payment Type options
     */
    public static final String PAYMENT_TYPE_CC = "Credit Card Number";
    public static final String PAYMENT_TYPE_BANK = "Bank Account Number";
}
