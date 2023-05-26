package tester;

import org.hibernate.SessionFactory;

import dao.IUserDao;
import dao.UserDaoImpl;
import utils.HibernateUtils;

public class GetAllUsers {

	public static void main(String[] args) {
		try (SessionFactory factory = HibernateUtils.getFactory()) {

			// create dao instance
			IUserDao userDao = new UserDaoImpl();

			System.out.println("List of users : ");
			userDao.getAllUsers().forEach(System.out::println);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
