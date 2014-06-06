package com.google.demo;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONException;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@SuppressWarnings("serial")
public class SendGridServlet extends HttpServlet {

	private static final String SENDGRID_USERNAME = "PLEASE REPLACE";
	private static final String SENDGRID_PASSWORD = "PLEASE REPLACE";

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String responseMessage = "";
		try {
			// Get parameters
			String[] tos = req.getParameter("to").split(",");
			Address from = new InternetAddress(req.getParameter("from").trim());
			String subject = req.getParameter("subject");
			String text = req.getParameter("text");
			// Send an email
			googleSendgridJava.Sendgrid sendgrid = new googleSendgridJava.Sendgrid(
					SENDGRID_USERNAME, SENDGRID_PASSWORD);
			for (String to : tos) {
				Address toa = new InternetAddress(to.trim());
				sendgrid.addTo(toa.toString(), "");
			}
			sendgrid.setFrom(from.toString());
			sendgrid.setSubject(subject);
			sendgrid.setText(text);
			sendgrid.send();
			responseMessage = sendgrid.getServerResponse();
		} catch (AddressException ae) {
			ae.printStackTrace();
			responseMessage = ae.getMessage();
		} catch (JSONException e) {
			e.printStackTrace();
			responseMessage = e.getMessage();
		}
		resp.setContentType("text/plain");
		resp.getWriter().println(responseMessage);
	}
}
