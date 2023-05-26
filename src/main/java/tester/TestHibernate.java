package tester;

import org.hibernate.SessionFactory;

import utils.HibernateUtils;

public class TestHibernate {

	public static void main(String[] args) {
		try (SessionFactory factory = HibernateUtils.getFactory()) {
			System.out.println(factory);
			System.out.println("Hibernate bootstraped");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
