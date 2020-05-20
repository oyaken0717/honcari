package com.honcari.service;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import com.honcari.form.ContactForm;
import com.honcari.service.book_rental.SendRentalMailService;

/**
 * お問い合わせメールを送信するサービス.
 * 
 * @author katsuya.fujishima
 *
 */
//@Async
@Service
@Transactional
public class SendContactMailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private SendRentalMailService sendRentalMailService;
	
	/**
	 * メールコンテンツ作成と送信処理をするメソッド.
	 * 
	 * @param contactForm
	 */
	public void sendContactMailToCustomer(ContactForm contactForm) {
		Context context = new Context();
		String doneUserName = contactForm.getName();
		String mailHeading = "お問い合わせを受け付けました";
		String emailTo = contactForm.getEmail();
		
		String url = request.getRequestURL().toString();
		String applicationPath = url.replace(request.getRequestURI(), "/");
		context.setVariable("doneUserName", doneUserName);
		context.setVariable("mailHeading", mailHeading);
		context.setVariable("content", contactForm.getContent());
		context.setVariable("applicationPath", applicationPath);
		createRentalMail(emailTo, mailHeading, context);
	}

	/**
	 * メールの作成・送信をする.
	 * 
	 * @param emailTo メールの送信先
	 * @param subject 件名
	 * @param context メール内容
	 * @return null（エラーチェック用）
	 */
	private String createRentalMail(String emailTo, String subject, Context context) {
		javaMailSender.send(new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
				helper.setTo(emailTo);
				helper.setBcc("honcari.web@gmail.com");
				helper.setSubject("【Hocari】" + subject);
				helper.setText(sendRentalMailService.getMailBody("contact_mail", context), true);
			}
		});
		return null;
	}

}
