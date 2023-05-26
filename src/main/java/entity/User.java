package entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity // mandatory
@Table(name = "users_tbl")
// Is it mandatory as per hibernate/JPA specs to make POJO class Serializable? NO
// BUT the data type of PK (Simple/Composite) MUST BE serializable
public class User {

	@Id // mandatory --> PK : user supplied
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@Column(length = 30) // varchar(30)
	private String name;

	@Column(length = 30, unique = true) // varchar(30), unique constraint
	private String email;

	@Column(length = 20)
	private String password;

	@Transient // skip from persistent
	private String confirmPassword;

	@Enumerated(EnumType.STRING) // varchar: enum constant name, default is ordinal
	@Column(name = "role", length = 20)
	private Role userRole;

	@Column(name = "reg_amount")
	private Double regAmount;

	@Column(name = "reg_date") // default column type : date
	private LocalDate regDate;

	@Lob // column : longblob : 4GB
	private byte[] image;

	public User() {
		System.out.println("In user constr");
	}

	// to test constructor expression
	public User(String name, String email, Double regAmount, LocalDate regDate) {
		super();
		this.name = name;
		this.email = email;
		this.regAmount = regAmount;
		this.regDate = regDate;
	}

	public User(String name, String email, String password, String confirmPassword, Role userRole, Double regAmount,
			LocalDate regDate) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.userRole = userRole;
		this.regAmount = regAmount;
		this.regDate = regDate;
	}

//	setters will be used to construct the object

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Role getUserRole() {
		return userRole;
	}

	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}

	public Double getRegAmount() {
		return regAmount;
	}

	public void setRegAmount(Double regAmount) {
		this.regAmount = regAmount;
	}

	public LocalDate getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDate regDate) {
		this.regDate = regDate;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", userRole=" + userRole + ", regAmount=" + regAmount
				+ ", regDate=" + regDate + "]";
	}

}
