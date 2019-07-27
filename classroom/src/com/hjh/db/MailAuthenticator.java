package com.hjh.db;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends   Authenticator{

    public static String USERNAME = "hjh2614@163.com";
    public static String PASSWORD = "hjh134625";

    public MailAuthenticator() {
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(USERNAME, PASSWORD);
    }
}
