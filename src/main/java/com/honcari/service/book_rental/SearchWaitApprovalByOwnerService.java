package com.honcari.service.book_rental;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honcari.common.RentalStatusEnum;
import com.honcari.domain.BookRental;
import com.honcari.repository.BookRentalRepository;

/**
 * 所有者ユーザーIDから未承認の貸出情報一覧を取得する. 
 * 
 * @author shumpei
 *
 */
@Service
public class SearchWaitApprovalByOwnerService {
	
	@Autowired
	private BookRentalRepository bookRentalRepository;
	
	/**
	 * 所有者ユーザーIDから未承認の貸出情報一覧を取得する. 
	 * 
	 * @param userId ユーザーID
	 * @return 貸出情報一覧
	 */
	public List<BookRental> searchWaitApprovalListByOwner(Integer ownerUserId) {
		return bookRentalRepository.findByOwnerUserIdAndRentalStatus(ownerUserId, RentalStatusEnum.WAIT_APPROVAL.getValue());
	}

}
