package com.bowmanidris.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bowmanidris.dao.AppRoleDAO;
import com.bowmanidris.dao.AppUserDAO;
import com.bowmanidris.model.AppUser;

/*
 * UserDetailsService means a central interface in Spring Security. It is a service to search "User account and such user's roles". 
 * It is used by the  Spring Security everytime when users log in the system. Therefore, you need to write a class to implement this interface.
 * 
 *Herein, I create the  UserDetailsServiceImpl class which implements the UserDetailsService interface. 
 *The  UserDetailsServiceImpl class is annotated by   @Service to tell the  Spring "let's manage it as a Spring BEAN".    
 */

@Service 
public class UserDetailsServiceImpl implements UserDetailsService  {

	@Autowired
    private AppUserDAO appUserDAO;
 
    @Autowired
    private AppRoleDAO appRoleDAO;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		//find user in database by the user name
		AppUser appUser = this.appUserDAO.findUserAccount(userName);
		
		if (appUser == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
		
		//log a sucessful find 
		System.out.println("Found User: " + appUser);
		
		/*[ROLE_USER, ROLE_ADMIN,..]
		 * returns a list of role names that corresponds to the AppUsers userId in the user_role table
		 * The appUsers userId can correspond to many roles 
		 */
        List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());
        
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        
        /*
         * if the appUser has roles corresponding to the userId in the database
         * create an authority and add all the roles to the list
         */
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
		
        UserDetails userDetails = (UserDetails) new User(appUser.getUserName(), //
                appUser.getEncrytedPassword(), grantList);
 
        return userDetails;
		
	}

}
