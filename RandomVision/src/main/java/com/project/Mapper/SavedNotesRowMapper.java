package com.project.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.user.SavedNotes;

public class SavedNotesRowMapper implements RowMapper<SavedNotes>{

	public SavedNotes mapRow(ResultSet rs, int rowNum) throws SQLException {
		SavedNotes ans=new SavedNotes();
		ans.setNote(rs.getString("note"));
		ans.setNoteid(rs.getInt("noteid"));
		ans.setTime(rs.getTimestamp("time"));
		ans.setUserid(rs.getInt("userid"));
		return ans;
		
	}

}
