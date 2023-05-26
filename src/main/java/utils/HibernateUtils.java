package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	private static SessionFactory factory;

	// eager SINGLETON pattern
	// static block get initialized only once while class loading
	static {
		System.out.println("In static init block of HibernateUtils");
		factory = new Configuration().configure().buildSessionFactory();
	}

	public static SessionFactory getFactory() {
		return factory;
	}

}
