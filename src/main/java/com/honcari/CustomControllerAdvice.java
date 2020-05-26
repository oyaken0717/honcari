package com.honcari;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.honcari.domain.LoginUser;
import com.honcari.service.book_rental.CountPendingApproval;
import com.honcari.service.group.CountInvitePendingService;
import com.honcari.service.group.SearchRequestedOwnerService;

@ControllerAdvice(annotations = CustomControllerAdvice.CommonAttribute.class)
public class CustomControllerAdvice {
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface CommonAttribute {}
	
	@Autowired
	private CountPendingApproval countPendingApproval;
	
	@Autowired
	private CountInvitePendingService countInvitePendingService;
	
	@Autowired
	private SearchRequestedOwnerService searchRequestedOwnerService;

	
  @ModelAttribute
  public void addOneObject( @AuthenticationPrincipal LoginUser loginUser,Model model) {
	  int  NumOfPendingApproval = countPendingApproval.countPendingApproval(loginUser.getUser().getUserId());
	  model.addAttribute("NumOfPendingApproval", NumOfPendingApproval);
	  int  NumOfGroupPendingApproval = countInvitePendingService.countInvitePending(loginUser.getUser().getUserId(), 0);
	  model.addAttribute("NumOfGroupPendingApproval", NumOfGroupPendingApproval);
	  int NumOfOwnerRequest = searchRequestedOwnerService.countOwnerRequest(loginUser.getUser().getUserId());
	  model.addAttribute("NumOfOwnerRequest",NumOfOwnerRequest);
	  
	  int groupNotice = NumOfGroupPendingApproval + NumOfOwnerRequest;
	  model.addAttribute("groupNotice",groupNotice);
	  model.addAttribute("user", loginUser.getUser());
  }
  
}
