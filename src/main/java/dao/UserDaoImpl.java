package dao;

import entity.Role;
import entity.User;
import utils.HibernateUtils;

import static utils.HibernateUtils.getFactory;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoImpl implements IUserDao {

	@Override
	public String registerUser(User user) {
		// TODO Get hibernate session from SF
		Session session = getFactory().openSession();

		// Begin Transaction
		Transaction tx = session.beginTransaction();
		System.out.println("Is open ?" + session.isOpen() + " is connected with db cn ?" + session.isConnected()); // t
																													// t
		try {
			session.save(user); // added in L1 cache: transient -> persistence

			tx.commit(); // changes in the object layer will be sync with DB layer
			System.out.println("Is open ?" + session.isOpen() + " is connected with db cn ?" + session.isConnected()); // t
																														// t
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e; // re-throw the same exception to the caller, so that caller know something went
						// wrong
		} finally {
			if (session != null)
				session.close(); // pooled out connection will be returned to the pool
			System.out.println("Is open ?" + session.isOpen() + " is connected with db cn ?" + session.isConnected()); // f
																														// f
		}
		// ID will be generated upon commit
		return "User registered with ID : " + user.getUserId();
	}

	@Override
	public String registerUserWithGetCurrentSession(User user) {
		Session session = HibernateUtils.getFactory().getCurrentSession();

		Transaction tx = session.beginTransaction();
		System.out.println("Is open ?" + session.isOpen() + " is connected with db cn ?" + session.isConnected()); // t
																													// t
		try {
			session.save(user);

			tx.commit();
			// session is auto closed resulting in L1 cache destroyed, DB cn returns to the
			// pool
			System.out.println("Is open ?" + session.isOpen() + " is connected with db cn ?" + session.isConnected()); // f
																														// f
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();

			throw e;
		}

		return "User registered with ID : " + user.getUserId();
	}

	@Override
	public User getUserById(int userId) {
		User user = null; // null

		Session session = HibernateUtils.getFactory().getCurrentSession();

		Transaction tx = session.beginTransaction();

		try {
			user = session.get(User.class, userId); // int --> Integer(auto boxing) --> Serializable
			// user != null --> PERSISTENT
			user = session.get(User.class, userId); // int --> Integer(auto boxing) --> Serializable
			// user != null --> PERSISTENT
			user = session.get(User.class, userId); // int --> Integer(auto boxing) --> Serializable
			// user != null --> PERSISTENT

			// For the very first get request it fires the query as the object is not
			// present in the L1 cache
			// for the rest of the requests it fetches it from the L1 cache.
			// In case it doesn't find the object for very first request then it fires 3
			// request as it is not present in the L1 cache.
			tx.commit();
			// auto dirty checking, no change in L1 & DB, no DMLs, L1 cache destroyed,
			// session.close()
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return user; // DETACHED
	}

	@Override
	public List<User> getAllUsers() {

		List<User> users = null;

		Session session = HibernateUtils.getFactory().getCurrentSession();

		Transaction tx = session.beginTransaction();

		try {

			// list of PERSISTENT objects
			users = session.createQuery("select u from User u", User.class).getResultList();

			// state of PERSISTENT entities is getting modified
			users.forEach(u -> u.setRegAmount(u.getRegAmount() - 50));

			// state of entities: DETACHED : cancels inserts/updates/deletes
			// session.clear();

			// Hibernate performs auto dirty checking: state is dirty
			// update query is fired to sync the state of L1 cache with that of DB
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}

		// updating the state of DETACHED entities
		// Since so session, no L1 cache --> DOES not propagate the changes to DB
		users.forEach(u -> u.setRegAmount(u.getRegAmount() - 50));
		// list of DETACHED objects
		return users;
	}

	@Override
	public List<User> getSelectedUsers(LocalDate beginDate, LocalDate endDate, Role userRole) {
		List<User> users = null;

		String jpql = "select u from User u where u.regDate between :start_date and :end_date and u.userRole=:role";
		Session session = HibernateUtils.getFactory().getCurrentSession();

		Transaction tx = session.beginTransaction();

		try {

			users = session.createQuery(jpql, User.class).setParameter("start_date", beginDate)
					.setParameter("end_date", endDate).setParameter("role", userRole).getResultList();

			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}

		return users;
	}

	@Override
	public User userLogin(String email, String password) {
		User user = null;

		Session session = HibernateUtils.getFactory().getCurrentSession();

		Transaction tx = session.beginTransaction();

		try {
			user = session.createQuery("select u from User u where u.email=:email and u.password=:password", User.class)
					.setParameter("email", email).setParameter("password", password).getSingleResult();

			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}

		return user;
	}

	@Override
	public String changePassword(int userId, String newPass) {

		String mesg = "Could not change the password";

		Session session = HibernateUtils.getFactory().getCurrentSession();

		Transaction tx = session.beginTransaction();

		try {

			User user = session.get(User.class, userId); // PERSISTENT entity
			if (user != null) {
				user.setPassword(newPass); // changes made to the PERSISTENT entity
				mesg = "Password changes successfully";
			}

			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return mesg;
	}

	@Override
	public List<User> getUsersByDate(LocalDate date) {

		List<User> users = null;

		String jpql = "select u from User u where u.regDate > :date and u.userRole=:role order by u.regAmount";

		Session session = HibernateUtils.getFactory().getCurrentSession();

		Transaction tx = session.beginTransaction();

		try {

			users = session.createQuery(jpql, User.class).setParameter("date", date).setParameter("role", Role.CUSTOMER)
					.getResultList();

			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return users;
	}

	@Override
	public String registerUserWithGetCurrentSessionUsingPersist(User user) {
		Session session = HibernateUtils.getFactory().getCurrentSession();

		Transaction tx = session.beginTransaction();
		System.out.println("Is open ?" + session.isOpen() + " is connected with db cn ?" + session.isConnected()); // t
																													// t
		try {
			session.persist(user);

			tx.commit();
			// session is auto closed resulting in L1 cache destroyed, DB cn returns to the
			// pool
			System.out.println("Is open ?" + session.isOpen() + " is connected with db cn ?" + session.isConnected()); // f
																														// f
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();

			throw e;
		}

		return "User registered with ID : " + user.getUserId();
	}

	@Override
	public String registerUserWithGetCurrentSessionSaveOrUpdate(User user) {
		Session session = HibernateUtils.getFactory().getCurrentSession();

		Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(user);

			tx.commit();
			// session is auto closed resulting in L1 cache destroyed, DB cn returns to the
			// pool
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();

			throw e;
		}

		return "User registered with ID : " + user.getUserId();
	}

	@Override
	public String registerUserWithGetCurrentSessionMerge(User user) {

		User userOne = null;

		Session session = HibernateUtils.getFactory().getCurrentSession();

		Transaction tx = session.beginTransaction();
		try {
			userOne = (User) session.merge(user); // user: TRANSIENT, userOne: PERSISTENT

			tx.commit();
			// session is auto closed resulting in L1 cache destroyed, DB cn returns to the
			// pool

		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();

			throw e;
		}
//		return "User registered with ID : " + user.getUserId(); // returns null ID as user is TRANSIENT
		return "User registered with ID : " + userOne.getUserId();
	}

	@Override
	public String registerUserWithGetCurrentSessionUpdate(User user) {

		// user : DETACHED: a pojo ref having not null id, having a matching record in
		// the DB
		Session session = HibernateUtils.getFactory().getCurrentSession();

		Transaction tx = session.beginTransaction();

		try {
			session.update(user); // returns the same POJO as PERSISTENT
			// pojo ref added in L1 cache but not updated in the DB yet

			tx.commit();
			// session is auto closed resulting in L1 cache destroyed, DB cn returns to the
			// pool updating the record in the DB

		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();

			throw e;
		}
		return "User registered with ID : " + user.getUserId();
	}

	@Override
	public List<String> getSelectedUserNames(LocalDate beginDate, LocalDate endDate, Role userRole) {

		List<String> names = null;

		String jpql = "select u.name from User u where u.regDate between :start_date and :end_date and u.userRole=:role";

		Session session = HibernateUtils.getFactory().getCurrentSession();

		Transaction tx = session.beginTransaction();

		try {

			names = session.createQuery(jpql, String.class).setParameter("start_date", beginDate)
					.setParameter("end_date", endDate).setParameter("role", userRole).getResultList();

			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}

		return names;
	}

	@Override
	public List<User> getSomeUserDetails(LocalDate beginDate, LocalDate endDate, Role userRole) {
		List<User> users = null;

		String jpql = "select new entity.User(name, email, regAmount, regDate) from User u where u.regDate between :start_date and :end_date and u.userRole=:role";

		Session session = HibernateUtils.getFactory().getCurrentSession();

		Transaction tx = session.beginTransaction();

		try {

			users = session.createQuery(jpql, User.class).setParameter("start_date", beginDate)
					.setParameter("end_date", endDate).setParameter("role", userRole).getResultList();

			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}

		return users;
	}

	@Override
	public String applyDiscountBeforeDate(LocalDate date, double discount) {

		Integer updateCount = 0;

		String jpql = "update User u set u.regAmount = u.regAmount - :disc where u.regDate < :date";

		Session session = HibernateUtils.getFactory().getCurrentSession();

		Transaction tx = session.beginTransaction();

		try {

			// bulk update: work on standalone object
			// better alternative to: first select the records and the update them for 'n'
			// times

			updateCount = session.createQuery(jpql).setParameter("disc", discount).setParameter("date", date)
					.executeUpdate();

			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return updateCount + " records updated successfully.";
	}

	@Override
	public String unsubscribeUser(int userId) {
		Session session = HibernateUtils.getFactory().getCurrentSession();

		String mesg = "User unsubscription failed: Invalid user ID.";

		Transaction tx = session.beginTransaction();

		try {

			// Get the PERSISTENT user ref
			User user = session.get(User.class, userId);

			if (user != null) {
				// Input: PERSISTENT entity
				session.delete(user); // marked for REMOVAL, user : REMOVED
				mesg = "User unsubscribed successfully.";
			}

			tx.commit(); // L1 destroyed, session closed, user : TRANSIENT
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return mesg;
	} // after method rets : user object is marked for GC:
		// Method local variables are marked for GC if not returned

	@Override
	public String deleteUsersAfterDate(LocalDate date) {
		Session session = HibernateUtils.getFactory().getCurrentSession();

		int deleteCount = 0;

		String jpql = "delete from User u where u.regDate > :date and u.userRole=:role";

		Transaction tx = session.beginTransaction();

		try {

			deleteCount = session.createQuery(jpql).setParameter("date", date).setParameter("role", Role.CUSTOMER)
					.executeUpdate();

			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return deleteCount + " records deleted successfully.";
	}

	@Override
	public String saveImage(String email, String fileName) throws Exception {

		String mesg = "Saving image failed...";

		String jpql = "select u from User u where u.email=:email";

		Session session = HibernateUtils.getFactory().getCurrentSession();

		Transaction tx = session.beginTransaction();

		try {

			User user = session.createQuery(jpql, User.class).setParameter("email", email).getSingleResult();

//			=> VALID user id (Otherwise hibernate throws NoResultException)
//			user: PERSISTENT
//			validate file
			File file = new File(fileName);

			if (file.exists() && file.isFile() && file.canRead()) {
				// valid file, continue to set the image
				user.setImage(FileUtils.readFileToByteArray(file));
				mesg = "Image saved successfully.";
			} else {
				mesg = "Saving image failed: invalid file!!!";
			}

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return mesg;
	}

	@Override
	public String restoreImage(int userId, String fileName) throws Exception {

		String mesg = "Restoring image failed.";

		Session session = HibernateUtils.getFactory().getCurrentSession();

		Transaction tx = session.beginTransaction();

		try {

			User user = session.get(User.class, userId);

			if (user != null) {
				// valid user, user: PERSISTENT
				byte[] imageData = user.getImage();

				FileUtils.writeByteArrayToFile(new File(fileName), imageData);

				mesg = "Image restored successfully!!!";
			}

			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return mesg;
	}

}
