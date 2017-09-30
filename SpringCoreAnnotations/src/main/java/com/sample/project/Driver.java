package com.sample.project;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("driver")
public class Driver implements DriverInterface {
	
	@Autowired
	@Resource(name="license")
	private License license;
	private String driverName = "Salman";

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	@Override
	public String toString() {
		return "Driver [license=" + license + ", driverName=" + driverName + "]";
	}

	@Override
	public void getDriverInfo() {
		System.out.println("Driver info: "+driverName);
	}

	public Driver(License license, String driverName) {
		super();
		this.license = license;
		this.driverName = driverName;
	}

	public Driver() {
		// TODO Auto-generated constructor stub
	}
	

}
