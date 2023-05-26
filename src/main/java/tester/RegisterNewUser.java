package tester;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.IUserDao;
import dao.UserDaoImpl;
import entity.Role;
import entity.User;
import utils.HibernateUtils;

public class RegisterNewUser {

	public static void main(String[] args) {
		try (SessionFactory factory = HibernateUtils.getFactory(); Scanner sc = new Scanner(System.in)) {

			// create dao instance
			IUserDao userDao = new UserDaoImpl();

			System.out.println(
					"Enter user details : name, email, password, confirmPassword, userRole, regAmount, regDate(yr-mon-day)");

			System.out.println(userDao.registerUser(new User(sc.next(), sc.next(), sc.next(), sc.next(),
					Role.valueOf(sc.next().toUpperCase()), sc.nextDouble(), LocalDate.parse(sc.next()))));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
