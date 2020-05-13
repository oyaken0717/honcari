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

import com.honcari.common.RentalStatusEnum;
import com.honcari.domain.BookRental;
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
	 * @param rentalStatus    貸出状況
	 */
	public void sendRentalMail(BookRental bookRental) {
		Context context = new Context();
		OwnedBookInfo ownedBookInfo = ownedBookInfoRepository.findByOwnedBookInfoId(bookRental.getOwnedBookInfoId());
		Integer rentalStatus = bookRental.getRentalStatus();
		String doneUserName = null;
		String mailHeading = null;
		String mailSubHeading = null;
		String emailTo = null;

		if (rentalStatus == RentalStatusEnum.WAIT_APPROVAL.getValue()) {
			doneUserName = bookRental.getCreationUserName();
			mailHeading = "貸出申請送信のお知らせ";
			mailSubHeading = "貸出申請を送信しました";
			emailTo = ownedBookInfo.getUser().getEmail();
		} else if (rentalStatus == RentalStatusEnum.CANCELED.getValue()) {
			doneUserName = bookRental.getBorrowUser().getName();
			mailHeading = "貸出申請キャンセルのお知らせ";
			mailSubHeading = "貸出申請をキャンセルしました";
			emailTo = bookRental.getOwnedBookInfo().getUser().getEmail();
		} else if (rentalStatus == RentalStatusEnum.WAIT_EXTEND.getValue()) {
			doneUserName = bookRental.getBorrowUser().getName();
			mailHeading = "貸出延長申請送信のお知らせ";
			mailSubHeading = "貸出延長申請を送信しました";
			emailTo = bookRental.getOwnedBookInfo().getUser().getEmail();
		} else if (rentalStatus == RentalStatusEnum.APPROVED.getValue()) {
			doneUserName = bookRental.getOwnedBookInfo().getUser().getName();
			mailHeading = "貸出申請承認のお知らせ";
			mailSubHeading = "貸出申請を承認しました";
			emailTo = bookRental.getBorrowUser().getEmail();
		} else if (rentalStatus == RentalStatusEnum.REJECTED.getValue()) {
			doneUserName = bookRental.getOwnedBookInfo().getUser().getName();
			mailHeading = "貸出申請否認のお知らせ";
			mailSubHeading = "貸出申請を否認しました";
			emailTo = bookRental.getBorrowUser().getEmail();
		} else if (rentalStatus == RentalStatusEnum.RETURNED.getValue()) {
			doneUserName = bookRental.getOwnedBookInfo().getUser().getName();
			mailHeading = "返却完了のお知らせ";
			mailSubHeading = "本の返却を確認しました";
			emailTo = bookRental.getBorrowUser().getEmail();
		}

		context.setVariable("doneUserName", doneUserName);
		context.setVariable("mailHeading", mailHeading);
		context.setVariable("mailSubHeading", mailSubHeading);
		context.setVariable("book", ownedBookInfo.getBook());
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
