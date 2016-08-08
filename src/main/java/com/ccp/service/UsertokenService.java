package com.ccp.service;

import java.util.Date;

import com.ccp.model.Usertoken;

public interface UsertokenService {
	
	public Usertoken isValidToken(int userid, String token);
	public void deleteToken(int userid, String token);
	public Usertoken save(int userid, String token, Date lastaccesstime);
	public void deleteTokens(int userid);
	
}
