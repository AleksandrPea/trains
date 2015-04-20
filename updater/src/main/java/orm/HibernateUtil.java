package orm;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	 private static final SessionFactory sessionFactory;
	    
	    static {
	        try {
				//Configuration cfg = new Configuration().configure(new File("C:\\Users\\Admin\\IdeaProjects\\updater\\src\\main\\java\\hibernate.cfg.xml"));
	        	sessionFactory = new Configuration().configure().buildSessionFactory();
	        } catch (Throwable ex) {

	            System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    }
	    
	    public static SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }
}
