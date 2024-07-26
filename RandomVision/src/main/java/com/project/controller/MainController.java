package com.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.DAO.UserDao;
import com.project.EmailSend.EmailSending;
import com.project.user.Notes;
import com.project.user.SavedNotes;
import com.project.user.User;
import com.sun.tools.javac.util.JCDiagnostic.Note;

@Controller
public class MainController {
	@Autowired
	private UserDao userdao;
	private EmailSending sendmail;
//	private int min=1;
//	private int max=22;

	@PostMapping(path = "/Note")
	public String generate(Model model, HttpSession session) {
//	int randomNumber = (int) (Math.random() * (max - min + 1)) + min;
//	System.out.println(randomNumber);
		try {
			int randomNumber = (Integer) session.getAttribute("randomNumber");
			// fetch the note from the userDao object from the database RandomVision and
			// table notes

			Notes notes = userdao.getNotes(randomNumber);
			session.setAttribute("userNote", notes.getNote());
			model.addAttribute("YourNote", notes);

		} catch (NumberFormatException e) {
			// Handle the case where conversion fails
			System.out.println("Error converting value to integer: " + e.getMessage());
		}
		return "forward:/";
	}

	@PostMapping("/SaveTheNote")
	public String saveNoteoperationBegins(HttpSession session) {
		int keyToBeStored = (Integer) session.getAttribute("randomNumber");
		System.out.println("Key to Be stored " + keyToBeStored);
		// store the notes for the user in the database RandomVision in table History of
		// notes.
		return "SaveTheNote";

	}

	@GetMapping("/Register")
	public String registerForAccount() {
		return "Register";
	}

	@GetMapping("/Save")
	public String saveNoteToDB(HttpSession session) {
		User user = (User) session.getAttribute("user");
		int note_id = (Integer) session.getAttribute("randomNumber");
		String note = (String) session.getAttribute("userNote");
		String ifalreadySavedOrNot = userdao.addNoteToSavedNotes(user.getUserid(), note_id, note);

		// transfer this data to showAllNotes so that mailIsSent will print instead of
		// anchor tag to send mail

		Integer stateOfMailSent = (Integer) session.getAttribute("stateOfMailSent");
		System.out.println(stateOfMailSent);
		String mailSentOrNot = (String) session.getAttribute("MailSentOrNot");
		// Redirect with query parameter

		// check if mailSendOrNot is null or not(if not null then can be encode else
		// pass null String)
		String encodedMailSentOrNot = mailSentOrNot != null ? URLEncoder.encode(mailSentOrNot, StandardCharsets.UTF_8)
				: "";

		return "redirect:/showAllNotes?statementForSaving="
				+ URLEncoder.encode(ifalreadySavedOrNot, StandardCharsets.UTF_8);
//				+ URLEncoder.encode(ifalreadySavedOrNot, StandardCharsets.UTF_8) + "&stateOfMailSent=" + stateOfMailSent
//				+ "&mailSentOrNot=" +encodedMailSentOrNot;
	}

	@GetMapping("/showAllNotes")
	public String showAllSavedNotes(Model model,@RequestParam(required = false) String statementForSaving,/*@RequestParam(required = false)Integer stateOfMailSent,@RequestParam(required = false) String mailSentOrNot,*/
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		int userid = user.getUserid();
		List<SavedNotes> notes_info = userdao.retriveAllNotes(userid);
		model.addAttribute("StatementForSaving", statementForSaving);
		model.addAttribute("notes_info", notes_info);

		// adding again to model object that id mail is already sent or not to user so
		// that appropriate message wil print instead of anchor tag
		
		
//		model.addAttribute("MailSentOrNot", stateOfMailSent);
//		model.addAttribute("stateOfMailSent",stateOfMailSent ); // Mail sent
		return "forward:/Home";
	}

	@RequestMapping("/SuccessRegistration")
	public void completeRegistration(@RequestParam("username") String username, @RequestParam("email") String email,
			@RequestParam("password") String password, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		boolean check = userdao.setUser(username, email, password);
		if (check) {
			System.out.println("Done");
			boolean m = sendmail.sendEmail("Welcome " + username
					+ " to Random Notes!! Your registration has been successfully completed.\nFor your reference we are providing you with the password.\nPassword :"
					+ password + "\n\nNote:It is a system generated mail. So, don't reply back.",
					"RandomNotes ::Registraion Successful", email, "ghardalesakshi@gmail.com");
			RequestDispatcher rd = req.getRequestDispatcher("/SaveTheNote");
			rd.forward(req, resp);
		} else
			System.out.println("NotDone");
		PrintWriter out = resp.getWriter();
		out.print("Something went wrong try again!!");
	}

	@GetMapping("/Login")
	public String LoginAccount() {
		return "Login";
	}

	@PostMapping("/Login")
	public String checkLoginCreds(@RequestParam("username1") String username,
			@RequestParam("password1") String password, HttpSession session, Model model)
			throws ServletException, IOException {
		User user = userdao.checkLoginCreds(username, password);
		System.out.println(user);
		if (user != null) {
			System.out.println(
					"Done " + user.getPassword() + user.getUseremail() + user.getUserid() + user.getUsername());
			session.setAttribute("user", user);
			return "redirect:/Home";

		} else {
			System.out.println("notDoneYRRR");
			model.addAttribute("errorLogin", "Invalid username or password.");
			return "Login";
		}
	}

	@RequestMapping("/Home")
	public String goToHome() {

		return "Home";
	}

	@RequestMapping("/RandomVision/")
	public void backToWelcome(HttpSession session, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		session.removeAttribute("randomNumber");
		RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
		rd.forward(req, resp);
		System.out.println("After session removal " + session.getAttribute("randomNumber"));
	}

	@RequestMapping("/Logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/Sendmail")
	public String sendNoteToMail(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		System.out.println(user);
		String note = (String) session.getAttribute("userNote");
		String name = user.getUsername();
		String email = user.getUseremail();
		boolean check = sendmail.sendEmail("Nice note " + name + "!!\nNote :" + note, "RandomNotes ::Note sent.", email,
				"ghardalesakshi@gmail.com");
		if (check) {
			session.setAttribute("MailSentOrNot", "Mail sent successfully.");
			session.setAttribute("stateOfMailSent", 1); // Mail sent

		} else {
			session.setAttribute("MailSentOrNOt", "Something went wrong, Note is not sent to mail.");
			session.setAttribute("stateOfMailSent", 0); // Mail not sent
		}
		return "forward:/Home";
	}
}
