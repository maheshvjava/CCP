package com.ccp.service.impl;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.ccp.dao.UsertokenDAO;
import com.ccp.model.Usertoken;
import com.ccp.service.UsertokenService;

public class UsertokenServiceImpl implements UsertokenService {
	
	private UsertokenDAO usertokenDAO;
	
	public void setUsertokenDAO(UsertokenDAO usertokenDAO) {
        this.usertokenDAO = usertokenDAO;
    }
	
	@Override
	@Transactional
	public Usertoken isValidToken(int userid, String token) {
		return this.usertokenDAO.isValidToken(userid, token);
	}
	
	@Override
	@Transactional
	public void deleteToken(int userid, String token) {
		this.usertokenDAO.deleteToken(userid, token);
	}
	
	@Override
	@Transactional
	public Usertoken save(int userid, String token, Date lastaccesstime) {
		return this.usertokenDAO.save(userid, token, lastaccesstime);
	}
	
	@Override
	@Transactional
	public void deleteTokens(int userid) {
		this.usertokenDAO.deleteTokens(userid);
	}
}
