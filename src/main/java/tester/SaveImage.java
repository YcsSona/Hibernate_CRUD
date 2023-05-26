package tester;

import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.IUserDao;
import dao.UserDaoImpl;
import utils.HibernateUtils;

public class SaveImage {

	public static void main(String[] args) {
		try (SessionFactory factory = HibernateUtils.getFactory(); Scanner sc = new Scanner(System.in)) {

			// create dao instance
			IUserDao userDao = new UserDaoImpl();

			System.out.println("Enter email : ");
			String email = sc.nextLine();
			
			System.out.println("Enter file name along with path: ");
			String fileName = sc.nextLine();
			
			System.out.println(userDao.saveImage(email, fileName));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
