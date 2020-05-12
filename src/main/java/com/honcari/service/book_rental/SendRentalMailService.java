package com.honcari.service.book_rental;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.honcari.domain.OwnedBookInfo;
import com.honcari.repository.OwnedBookInfoRepository;

/**
 * メール送信用のサービスクラス.
 * 
 * @author shumpei
 *
 */
@Service
@Transactional
public class SendRentalMailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private OwnedBookInfoRepository ownedBookInfoRepository;

	/**
	 * メールの送信処理を行う.
	 * 
	 * @param doneUserName    動作したユーザー名
	 * @param ownedBookInfoId ユーザーの所有する本情報ID
	 * @param subject         件名
	 */
	public void sendRentalMail(String doneUserName, Integer ownedBookInfoId, String subject) {
		Context context = new Context();
		OwnedBookInfo ownedBookInfo = ownedBookInfoRepository.findByOwnedBookInfoId(ownedBookInfoId);
		context.setVariable("doneUserName", doneUserName);
		context.setVariable("book", ownedBookInfo.getBook());
		String mailSubHeading = "貸出リクエストを送りました";
		context.setVariable("mailHeading", subject);
		context.setVariable("mailSubHeading", mailSubHeading);
		createRentalMail(ownedBookInfo.getUser().getEmail(), subject, context);
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
				helper.setFrom("greatpotato@gmail.com");
				helper.setTo(emailTo);
				helper.setSubject("Hocari :" + subject);
				helper.setText(getMailBody("book_rental_mail", context), true);
			}
		});
		return null;
	}

	/**
	 * メールの本文を作成する.
	 * 
	 * @param htmlPath HTMLパス
	 * @param context  メール内容
	 * @return メール本文
	 */
	private String getMailBody(String htmlPath, Context context) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(mailTemplateResolver());
		return templateEngine.process(htmlPath, context);
	}

	/**
	 * メールテンプレートを作成する.
	 * 
	 * @return メールテンプレート
	 */
	private ClassLoaderTemplateResolver mailTemplateResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setPrefix("templates/mailTemplates/");
		templateResolver.setSuffix(".html");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setCacheable(true);
		return templateResolver;

	}

}
