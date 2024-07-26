package com.project.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.user.User;

public class UserRowMapper implements RowMapper<User> {

	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		User user=new User();
		user.setUserid(rs.getInt("userid"));
		user.setUseremail(rs.getString("useremail"));
		user.setPassword(rs.getString("password"));
		user.setUsername(rs.getString("username"));
		return user;
	}


}
