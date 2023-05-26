package dao;

import java.time.LocalDate;
import java.util.List;

import entity.Role;
import entity.User;

public interface IUserDao {
	// TODO add a method to insert user details using openSession
	// Typically DML has string as a return type

	String registerUser(User user);

	// TODO add a method to insert user details using getCurrentSession
	String registerUserWithGetCurrentSession(User user);

	// TODO add a method to get user details from DB
	User getUserById(int userId);

	// TODO add a method to fetch all users
	List<User> getAllUsers();

	// TODO add a method to fetch selected users based on start & end date and based
	// on role
	List<User> getSelectedUsers(LocalDate beginDate, LocalDate endDate, Role userRole);

	// TODO add a method to test user login
	User userLogin(String email, String password);

	// TODO add a method to change the password
	String changePassword(int userId, String newPass);

	// TODO add a method to show users registered after specified date, ordered as
	// per reg amount
	List<User> getUsersByDate(LocalDate date);

	// TODO add a method to insert user details using getCurrentSession using
	// persist
	String registerUserWithGetCurrentSessionUsingPersist(User user);

	// TODO add a method to insert user details using getCurrentSession using
	// saveOrUpdate
	String registerUserWithGetCurrentSessionSaveOrUpdate(User user);

	// TODO add a method to insert user details using getCurrentSession using merge
	String registerUserWithGetCurrentSessionMerge(User user);

	// TODO add a method to insert user details using getCurrentSession using update
	String registerUserWithGetCurrentSessionUpdate(User user);

	// TODO add a method to display user names for specific roles between the dates
	List<String> getSelectedUserNames(LocalDate beginDate, LocalDate endDate, Role userRole);

	// TODO add a method to display some columns for specific roles between the
	// dates
	List<User> getSomeUserDetails(LocalDate beginDate, LocalDate endDate, Role userRole);

	// TODO add a method to apply discount for all users registered before a
	// specific date
	String applyDiscountBeforeDate(LocalDate date, double discount);

	// TODO add a method to unsubscribe user
	String unsubscribeUser(int userId);

	// TODO add a method to delete users having role as customer and after a date
	String deleteUsersAfterDate(LocalDate date);

	// TODO add a method to save image in the DB
	String saveImage(String email, String fileName) throws Exception;

	// TODO add a method to restore the image from the DB into a folder
	String restoreImage(int userId, String fileName) throws Exception;
}
