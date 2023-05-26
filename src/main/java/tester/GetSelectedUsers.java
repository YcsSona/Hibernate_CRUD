package tester;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.IUserDao;
import dao.UserDaoImpl;
import entity.Role;
import utils.HibernateUtils;

public class GetSelectedUsers {

	public static void main(String[] args) {
		try (SessionFactory factory = HibernateUtils.getFactory(); Scanner sc = new Scanner(System.in)) {

			// create dao instance
			IUserDao userDao = new UserDaoImpl();

			System.out.println("Enter start and end date and role : ");

			userDao.getSelectedUsers(LocalDate.parse(sc.next()), LocalDate.parse(sc.next()),
					Role.valueOf(sc.next().toUpperCase())).forEach(System.out::println);
			System.out.println("List of users : ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
