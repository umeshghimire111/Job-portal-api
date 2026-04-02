package com.jobportal.Job.Portal.Backend.API.constant;


public class EmailConstants {

    public static final String NAME = "name";
    public static final String OTP = "otp";
    public static final String EXPIRY_TIME = "expiryTime";
    public static final String CURRENT_YEAR = "currentYear";
    public static final String VERIFICATION_LINK = "verificationLink";
    public static final String RESET_LINK = "resetLink";
    public static final String JOB_TITLE = "jobTitle";
    public static final String COMPANY_NAME = "companyName";


    public static final String OTP_TEMPLATE = "otp-email.ftlh";
    public static final String VERIFICATION_TEMPLATE = "verification-email.ftlh";
    public static final String RESET_PASSWORD_TEMPLATE = "reset-password.ftlh";
    public static final String APPLICATION_CONFIRMATION_TEMPLATE = "application-confirmation.ftlh";


    public static final String OTP_SUBJECT = "Your OTP Code - Job Portal";
    public static final String VERIFICATION_SUBJECT = "Verify Your Email - Job Portal";
    public static final String RESET_PASSWORD_SUBJECT = "Password Reset Request - Job Portal";
    public static final String APPLICATION_SUBJECT = "Application Received - Job Portal";


    public static final int OTP_EXPIRY_MINUTES = 10;
    public static final int LINK_EXPIRY_HOURS = 24;
}