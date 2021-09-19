package com.example.portfolist.global.mail;

import org.springframework.stereotype.Component;

@Component
public class HtmlSourceProvider {

    public String makeEmailCertification(String baseurl, String token) {
        return "<div style=\"width: 500px; height: 5px; background-color: #FF7659;\"></div>\n" +
                "    <div style=\"margin-top: 50px; display: flex;\">\n" +
                "        <h1 style=\"margin-top: 0px; color: #FF7659; font-style: normal; font-weight: 600; font-family: Noto Sans KR;\">메일 인증</h1>\n" +
                "        <pre style=\"margin-top: 0px;\"> </pre>\n" +
                "        <h1 style=\"margin-top: 0px; font-weight: normal; font-family: Noto Sans KR; color: #4D4D4D;\">안내입니다</h1>\n" +
                "    </div>\n" +
                "    <pre style=\"margin: 0; font-weight: 400; font-family: Noto Sans KR; color: #4D4D4D;\">안녕하세요</pre>\n" +
                "    <pre style=\"margin: 0; font-weight: 400; font-family: Noto Sans KR; color: #4D4D4D;\">Portfolist를 이용해주셔서 진심으로 감사드립니다</pre>\n" +
                "    <div style=\"margin-top: 0px; display: flex;\">\n" +
                "        <pre style=\"margin: 0; font-weight: 400; font-family: Noto Sans KR; color: #4D4D4D;\">아래 </pre>\n" +
                "        <pre  style=\"margin: 0; font-weight: 400; color: #FF7659; font-style: normal; font-weight: 600; font-family: Noto Sans KR;\"> 메일인증</pre>\n" +
                "        <pre style=\"margin: 0; font-weight: 400; font-family: Noto Sans KR; color: #4D4D4D;\"> 버튼을 클릭하여 이메일을 인증해주세요</pre>\n" +
                "    </div>\n" +
                "    <pre style=\"margin: 0; font-weight: 400; font-family: Noto Sans KR; color: #4D4D4D; margin-bottom: 50px;\">감사합니다</pre>\n" +
                "\n" +
                "    <a type=\"button\" href=\"http://" + baseurl + "/receive?token=" + token + "\" style=\"border-radius: 0px; outline: none; border: none; background-color: #FF7659; color: white; width: 200px; height: 40px; font-size: 15px; font-family: Noto Sans KR; text-decoration: none; padding: 10px 50px\">이메일 인증하기</a>\n" +
                "\n" +
                "    <div style=\"width: 500px; height: 1px; margin: 50px 0px; margin-bottom: 20px; background-color: #d1d1d1;\"></div>\n" +
                "    \n" +
                "    <pre style=\"font-size: 13px; margin: 0px; color: #8b8b8b; font-family: Noto Sans KR;\">5분내로 이메일 인증 후 회원가입을 해야 합니다</pre>\n" +
                "    <pre style=\"font-size: 13px; margin: 0px; color: #8b8b8b; font-family: Noto Sans KR;\">만약 5분이 지났다면 다시 재인증을 요청해주세요</pre>\n" +
                "\n" +
                "    <div style=\"width: 500px; height: 5px; background-color: #FF7659; margin-top: 30px;\"></div>";
    }

}
