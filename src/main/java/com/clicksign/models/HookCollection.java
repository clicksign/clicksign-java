package com.clicksign.models;

import java.util.List;

public class HookCollection {
	List<Hook> hooks;

	public HookCollection(List<Hook> hooks) {
		super();
		this.hooks = hooks;
	}

	public List<Hook> getHooks() {
		return hooks;
	}

	public void setHooks(List<Hook> hooks) {
		this.hooks = hooks;
	}
}
