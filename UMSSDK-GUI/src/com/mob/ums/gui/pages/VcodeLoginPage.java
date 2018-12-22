package com.mob.ums.gui.pages;


import com.mob.jimu.gui.Page;
import com.mob.jimu.gui.Theme;

public class VcodeLoginPage extends Page<VcodeLoginPage> {
	private String loginNumber;

	public VcodeLoginPage(Theme theme) {
		super(theme);
	}

	public String getLoginNumber() {
		return loginNumber;
	}

	public void setLoginNumber(String loginNumber) {
		this.loginNumber = loginNumber;
	}
}
