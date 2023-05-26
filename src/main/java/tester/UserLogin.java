package tester;

import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.IUserDao;
import dao.UserDaoImpl;
import entity.User;
import utils.HibernateUtils;

public class UserLogin {

	public static void main(String[] args) {
		try (SessionFactory factory = HibernateUtils.getFactory(); Scanner sc = new Scanner(System.in)) {

			// create dao instance
			IUserDao userDao = new UserDaoImpl();

			System.out.println("Enter email and password : ");

			User user = userDao.userLogin(sc.next(), sc.next());
			if (user == null)
				System.out.println("Invalid login credentials.");
			else
				System.out.println("Login successful :	 " + user);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
