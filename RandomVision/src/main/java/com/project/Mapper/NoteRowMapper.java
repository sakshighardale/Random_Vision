package com.project.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.user.Notes;

public class NoteRowMapper implements RowMapper<Notes>{

	public Notes mapRow(ResultSet rs, int rowNum) throws SQLException {
		Notes n=new Notes();
		System.out.println(rs.getString("note"));

		n.setId(rs.getInt("sr_no"));
		n.setNote(rs.getString("note"));
		return n;
	}

}
