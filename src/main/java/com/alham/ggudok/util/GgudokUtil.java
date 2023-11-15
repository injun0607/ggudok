package com.alham.ggudok.util;

import org.springframework.beans.factory.annotation.Value;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class GgudokUtil {

    @Value("${emailId}")
    private static String emailId;

    @Value("${password}")
    private static String emailPassword;

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@(.+)$";


    private static final String AGE_TAG_FORMAT = "\\d+대";

    public static final String MAN = "남성";

    public static final String WOMAN = "여성";

    public static final String EMAIL_FAIL = "FAIL";

    public static final String NOT_IMAGE = "NOT_IMAGE";

    // 이메일 유효성 검사 메서드
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String certEmail(String checkEmail) {

        String subject = "인증 이메일";
        String randomNum = GgudokUtil.randomNum();

        String contents = "인증코드" + randomNum;

        if (sendEmail(checkEmail, subject, contents)) {
            return randomNum;

        }else{
            return EMAIL_FAIL;
        }

    }


    public static boolean sendEmail(String recipient,String subject,String contents) {

        // SMTP 서버 및 계정 정보 설정
        String smtpHost = "smtp.naver.com"; // SMTP 호스트 주소
        String username = emailId; // 보내는 이메일 주소
        String password = emailPassword; // 이메일 비밀번호

        // 이메일 수신자 정보 설정
        String toAddress = recipient; // 수신자 이메일 주소

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
            message.setSubject(subject);

            String randomNum = GgudokUtil.randomNum();
            message.setText(contents); // 본문 내용
//            message.setText("인증 코드: 꽃등심,갈비,돈까스,엽떡...먹고싶다"); // 본문 내용

            // 이메일 보내기
            Transport.send(message);
            System.out.println("이메일을 성공적으로 보냈습니다.");

            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("이메일을 보내는 중 오류가 발생했습니다.");
            return false;
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


    public static <K, V extends Comparable<? super V>> Map<K, V> mapSortByValueDescending(Map<K, V> map) {
        List<Map.Entry<K, V>> sortedEntry = map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toList());
        LinkedHashMap<K, V> sortedMap = new LinkedHashMap<>();

        int sort = 0;

        for (Map.Entry<K, V> entry : sortedEntry) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;

    }

    public static String transferDateTime(LocalDateTime localDateTime) {
        StringBuilder sb = new StringBuilder();
        sb.append(localDateTime.getYear());
        sb.append(".");
        sb.append(localDateTime.getMonthValue());
        sb.append(".");
        sb.append(localDateTime.getDayOfMonth());

        return sb.toString();
    }

    public static String checkImageType(String fileContentsType) {
        String contentType = "";

        if (fileContentsType.contains("image/png")) {
            contentType = ".png";
        } else if (fileContentsType.contains("image/jpeg")) {
            contentType = ".jpg";
        } else if (fileContentsType.contains("image/gif")) {
            contentType = ".gif";
        }else if (fileContentsType.contains("image/svg")) {
            contentType = ".svg";
        } else {
            contentType = "not-image";
        }
        return contentType;
    }
}
