package tester;

import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.IUserDao;
import dao.UserDaoImpl;
import utils.HibernateUtils;

public class RestoreImage {

	public static void main(String[] args) {
		try (SessionFactory factory = HibernateUtils.getFactory(); Scanner sc = new Scanner(System.in)) {

			// create dao instance
			IUserDao userDao = new UserDaoImpl();

			System.out.println("Enter the user id : ");
			int userId = sc.nextInt();

			sc.nextLine(); // to read off pending new line from scanner's buffer

			System.out.println("Enter file name along with path: ");
			String fileName = sc.nextLine();

			System.out.println(userDao.restoreImage(userId, fileName));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
