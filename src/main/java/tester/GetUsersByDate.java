package tester;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.IUserDao;
import dao.UserDaoImpl;
import utils.HibernateUtils;

public class GetUsersByDate {

	public static void main(String[] args) {
		try (SessionFactory factory = HibernateUtils.getFactory(); Scanner sc = new Scanner(System.in)) {

			// create dao instance
			IUserDao userDao = new UserDaoImpl();

			System.out.println("Enter a date (yyyy-MM-dd): ");

			userDao.getUsersByDate(LocalDate.parse(sc.next())).forEach(System.out::println);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
