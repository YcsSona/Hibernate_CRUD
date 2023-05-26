package tester;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import dao.IUserDao;
import dao.UserDaoImpl;
import entity.Role;
import entity.User;
import utils.HibernateUtils;

public class TestSessionAPISave {

	public static void main(String[] args) {
		try (SessionFactory factory = HibernateUtils.getFactory(); Scanner sc = new Scanner(System.in)) {

			// create dao instance
			IUserDao userDao = new UserDaoImpl();

			System.out.println(
					"Enter user details : name, email, password, confirmPassword, userRole, regAmount, regDate(yr-mon-day)");

			User u = new User(sc.next(), sc.next(), sc.next(), sc.next(), Role.valueOf(sc.next().toUpperCase()),
					sc.nextDouble(), LocalDate.parse(sc.next()));

			System.out.println("User id: " + u.getUserId()); // null

//			u.setUserId(1); // ignored by save()
//			u.setUserId(1234); // ignored by save()

			System.out.println(userDao.registerUserWithGetCurrentSession(u));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
