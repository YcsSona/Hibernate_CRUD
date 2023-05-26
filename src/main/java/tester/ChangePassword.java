package tester;

import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.IUserDao;
import dao.UserDaoImpl;
import utils.HibernateUtils;

public class ChangePassword {

	public static void main(String[] args) {
		try (SessionFactory factory = HibernateUtils.getFactory(); Scanner sc = new Scanner(System.in)) {

			// create dao instance
			IUserDao userDao = new UserDaoImpl();

			System.out.println("Enter user id and new password : ");

			System.out.println(userDao.changePassword(sc.nextInt(), sc.next()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
