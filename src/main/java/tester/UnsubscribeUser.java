package tester;

import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.IUserDao;
import dao.UserDaoImpl;
import utils.HibernateUtils;

public class UnsubscribeUser {

	public static void main(String[] args) {
		try (SessionFactory factory = HibernateUtils.getFactory(); Scanner sc = new Scanner(System.in)) {

			// create dao instance
			IUserDao userDao = new UserDaoImpl();

			System.out.println("Enter user id");

			System.out.println(userDao.unsubscribeUser(sc.nextInt()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
