package in.co.sunrays.proj3.util;
public class HibDataSource {

	
	 private static SessionFactory sessionFactory = null;

	    /**
	     * Get the instance of Session Factory
	     *
	     * @return sessionFactory
	     */
	    public static SessionFactory getSessionFactory() {
	        if (sessionFactory == null) {
	            sessionFactory = new Configuration().configure()
	                    .buildSessionFactory();
	        }
	        return sessionFactory;
	    }

	    /**
	     * Get Session by SessionFactory
	     *
	     * @return session
	     */
	    public static Session getSession() {
	        Session session = getSessionFactory().openSession();
	        return session;
	    }

	    /**
	     * Get Session by SessionFactory
	     *
	     * @return session
	     */
	    public static void closeSession(Session session) {
	        if (session != null) {
	            session.close();
	        }
	    }

	}
}
