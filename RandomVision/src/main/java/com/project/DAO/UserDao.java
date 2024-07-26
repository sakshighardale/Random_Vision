package com.project.DAO;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.Mapper.NoteRowMapper;
import com.project.Mapper.SavedNotesRowMapper;
import com.project.Mapper.UserRowMapper;
import com.project.user.Notes;
import com.project.user.SavedNotes;
import com.project.user.User;

@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Notes getNotes(int key) {
		String select_query = "select * from notes where sr_no=?;";
		Notes note = jdbcTemplate.queryForObject(select_query, new NoteRowMapper(), key);
		return note;
	}

	// create table user(userid int auto_increment primary key, username
	// varchar(30),useremail varchar(20), password varchar(10));
	public boolean setUser(String username, String useremail, String password) {
		String insert_query = "insert into user (username, useremail,password) values(?,?,?)";
		int check = jdbcTemplate.update(insert_query, username, useremail, password);
		if (check > 0) {
			System.out.println("Successfully inserted");
			return true;
		} else {
			System.out.println("Failed to insert");
			return false;
		}
	}

	public User checkLoginCreds(String username, String password) {
		String check_query = "select * from user where username=? and password=?";
		try {
			return jdbcTemplate.queryForObject(check_query, new UserRowMapper(), username, password);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

//	 create table savedNotes(sr_no int auto_increment primary key, userid int references user(userid) on delete cascade,noteid int references notes(sr_no) on delete cascade,note text, time DATETIME);
	public String addNoteToSavedNotes(int userid, int noteid, String note) {
		
		String select_query="select * from savedNotes where userid=? and noteid=? and note=?";
	     List<SavedNotes> savedNotes = jdbcTemplate.query(select_query,new SavedNotesRowMapper(),userid,noteid,note);
		System.out.println(savedNotes);
		if(!savedNotes.isEmpty()) {
			System.out.println("Already saved");
			return "Already Saved!!";
		}
		
		else {
		String insert_query = "insert into savedNotes (userid, noteid,note,time) values(?,?,?,?);";
		int check = jdbcTemplate.update(insert_query, userid, noteid,note, new Timestamp(System.currentTimeMillis()));
		if (check > 0)
			System.out.println("Saved Notes successfully");
		else
			System.out.println("Saving notes failed");
		}

		return "Saved Now!!";
	}
	
	public List<SavedNotes> retriveAllNotes(int userid)
	{
		String retrive_query="Select * from savedNotes where userid=? order by time desc;";
		return jdbcTemplate.query(retrive_query, new SavedNotesRowMapper(),userid);
	}
}
