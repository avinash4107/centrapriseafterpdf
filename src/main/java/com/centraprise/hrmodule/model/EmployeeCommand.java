package com.centraprise.hrmodule.model;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


import com.centraprise.hrmodule.validators.AdharValidator;
import com.centraprise.hrmodule.validators.PanValidator;
import com.centraprise.hrmodule.validators.PhoneValidator;
import com.centraprise.hrmodule.validators.PinValidaor;

public class EmployeeCommand {

	private int empId;

	private int employeenumber;

	@NotNull(message = "Employee Name must not null")
	private String name;

	@NotNull(message = "Gender must not null")
	private String gender;

	@NotNull(message = "Birth day  must not null")
	private String bday;

	@PanValidator
	private String pan;

	@NotNull(message = "please provide Marital Status ")
	private String maritalstatus;

	@Email(message = "Employee Name must have not null")
	private String email;

	@PhoneValidator
	private String phone;

	@NotNull(message = "Please provide password")
	private String password;

	@NotNull(message = "Please provide doornumber")
	private String doornum;

	@NotNull(message = "Please provide village")

	private String vlg;

	@NotNull(message = "Please provide Mandal")
	private String mandal;

	@NotNull(message = "Please select Country")
	private String country;

	@NotNull(message = "Please select State")
	private String state;

	@NotNull(message = "Please provide District")
	private String district;

	@PinValidaor
	private String pin;

	@NotNull(message = "Please provide startdate")
	private String startdate;

	@NotNull(message = "Please provide enddate")
	private String endDate;

	@NotNull
	private String job;

	@NotNull(message = "Please provide joindate")
	private String joindate;

	@NotNull
	private float service;

	@NotNull(message = "Please provide Manager")
	private String manager;

	@NotNull(message = "Please provide Bankname")
	private String bankname;

	@NotNull(message = "Please provide AccountNumber")
	private String accountnumber;

	@NotNull
	private String ifsc;

	@NotNull
	private String uan;

	@AdharValidator
	private String adhar;

	@NotNull
	private String previousemp;

	@NotNull(message = "please provide Previous Employee end date ")
	private String previousempleavingdate;

	@NotNull(message = "please provide Previous Employee start date ")
	private String previousempstartdate;

	@NotNull(message = "please provide Previous Employee start date ")
	private String previousempenddate;

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getEmployeenumber() {
		return employeenumber;
	}

	public void setEmployeenumber(int employeenumber) {
		this.employeenumber = employeenumber;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBday() {
		return bday;
	}

	public void setBday(String bday) {
		this.bday = bday;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getMaritalstatus() {
		return maritalstatus;
	}

	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDoornum() {
		return doornum;
	}

	public void setDoornum(String doornum) {
		this.doornum = doornum;
	}

	public String getVlg() {
		return vlg;
	}

	public void setVlg(String vlg) {
		this.vlg = vlg;
	}

	public String getMandal() {
		return mandal;
	}

	public void setMandal(String mandal) {
		this.mandal = mandal;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getJoindate() {
		return joindate;
	}

	public void setJoindate(String joindate) {
		this.joindate = joindate;
	}

	public float getService() {
		return service;
	}

	public void setService(float service) {
		this.service = service;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getUan() {
		return uan;
	}

	public void setUan(String uan) {
		this.uan = uan;
	}

	public String getAdhar() {
		return adhar;
	}

	public void setAdhar(String adhar) {
		this.adhar = adhar;
	}

	public String getPreviousemp() {
		return previousemp;
	}

	public void setPreviousemp(String previousemp) {
		this.previousemp = previousemp;
	}

	public String getPreviousempleavingdate() {
		return previousempleavingdate;
	}

	public void setPreviousempleavingdate(String previousempleavingdate) {
		this.previousempleavingdate = previousempleavingdate;
	}

	public String getPreviousempstartdate() {
		return previousempstartdate;
	}

	public void setPreviousempstartdate(String previousempstartdate) {
		this.previousempstartdate = previousempstartdate;
	}

	public String getPreviousempenddate() {
		return previousempenddate;
	}

	public void setPreviousempenddate(String previousempenddate) {
		this.previousempenddate = previousempenddate;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "EmployeeCommand [empId=" + empId + ", employeenumber=" + employeenumber + ", name=" + name + ", gender="
				+ gender + ", bday=" + bday + ", pan=" + pan + ", maritalstatus=" + maritalstatus + ", email=" + email
				+ ", phone=" + phone + ", password=" + password + ", doornum=" + doornum + ", vlg=" + vlg + ", mandal="
				+ mandal + ", country=" + country + ", state=" + state + ", district=" + district + ", pin=" + pin
				+ ", startdate=" + startdate + ", endDate=" + endDate + ", job=" + job + ", joindate=" + joindate
				+ ", service=" + service + ", manager=" + manager + ", bankname=" + bankname + ", accountnumber="
				+ accountnumber + ", ifsc=" + ifsc + ", uan=" + uan + ", adhar=" + adhar + ", previousemp="
				+ previousemp + ", previousempleavingdate=" + previousempleavingdate + ", previousempstartdate="
				+ previousempstartdate + ", previousempenddate=" + previousempenddate + "]";
	}

}
