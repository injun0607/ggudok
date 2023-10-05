package com.alham.ggudok.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GgudokUtil {
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@(.+)$";

    private static final String AGE_TAG_FORMAT = "\\d+대";

    public static final String MAN = "남성";

    public static final String WOMAN = "여성";

    public static final String EMAIL_FAIL = "FAIL";

    // 이메일 유효성 검사 메서드
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String certEmail(String checkEmail) {

        // SMTP 서버 및 계정 정보 설정
        String smtpHost = "smtp.naver.com"; // SMTP 호스트 주소
        String username = ""; // 보내는 이메일 주소
        String password = ""; // 이메일 비밀번호

        // 이메일 수신자 정보 설정
        String toAddress = checkEmail; // 수신자 이메일 주소

        // 이메일 속성 설정
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", "587"); // SMTP 포트 (일반적으로 587 사용)

        // 세션 생성
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // 이메일 메시지 작성
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject("인증 이메일");
            String randomNum = GgudokUtil.randomNum();
            message.setText("인증 코드: "+ randomNum); // 본문 내용
//            message.setText("인증 코드: 꽃등심,갈비,돈까스,엽떡...먹고싶다"); // 본문 내용

            // 이메일 보내기
            Transport.send(message);
            System.out.println("이메일을 성공적으로 보냈습니다.");

            return randomNum;
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("이메일을 보내는 중 오류가 발생했습니다.");
            return EMAIL_FAIL;
        }

    }

    private static String randomNum() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();

    }

    public static boolean isAgeFormat(String tagName) {
        return tagName.matches(AGE_TAG_FORMAT);
    }
}
