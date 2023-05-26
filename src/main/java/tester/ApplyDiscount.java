package tester;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.IUserDao;
import dao.UserDaoImpl;
import utils.HibernateUtils;

public class ApplyDiscount {

	public static void main(String[] args) {
		try (SessionFactory factory = HibernateUtils.getFactory(); Scanner sc = new Scanner(System.in)) {

			// create dao instance
			IUserDao userDao = new UserDaoImpl();

			System.out.println("Enter date and discount amount : ");

			System.out.println(userDao.applyDiscountBeforeDate(LocalDate.parse(sc.next()), sc.nextDouble()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
