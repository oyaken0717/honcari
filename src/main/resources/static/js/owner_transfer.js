$(function() {
	//グループに追加するユーザーの配列
	var groupUser = '';
	
	function serachUser(searchName) {
		var loginUserId = $(".loginUserId").val();
		var url = location.href;
		var groupId = $(".groupId").val();
		if(url.includes('to_edit_group')){
			url = url.replace(/group\/(to_edit_group)/g, "search_user_api_for_owner_transfer")
		}
		if(url.includes('edit_group')){
			url = url.replace(/group\/(edit_group)/g, "search_user_api_for_owner_transfer")
		}
		$.ajax({
			url : url,
			type : 'GET',
			dataType : 'json',
			data : {
				name : searchName,
				userId : loginUserId,
				groupId : groupId
			},
			async : true
		}).done(function(data) {
			//ユーザーリストを初期化
			$(".addUserList").children().remove();
			//あいまい検索にヒットした数だけ表示、表示制限は後でつける
			$.each(data.userList, function(index, user) {
				//すでに追加したユーザーは表示しない
				if(user.name === groupUser) {
					return true;
				}
				//追加するHTML
				var addUserHTML 
					= '<div class="row user ml-1 mt-1">'
						+ '<img class="col-auto" src="/img/user_default.png" height="38">'
						+ '<span class="col-auto select-user">'
							+ '<object><a tabindex="0" data-toggle="popover" data-trigger="hover" data-placement="right" title="プロフィール文" data-content="' + user.profile + '">' 
								+ user.name 
							+ '</a></object>' 
						+ '</span>'
						+ '<a class="col-auto select-user addUser" href="#">'
								+ '<i class="far fa-plus-square add-user-icon"></i>'
							+ '<input class="user-name" type="hidden" value="' + user.name + '">'
							+ '<input class="user-profile" type="hidden" value="' + user.profile + '">'
						+ '</a>'
					+ '</div>';
				
				$(".addUserList").append(addUserHTML);
				$('[data-toggle="popover"]').popover();
			})
			if (data.userList == null) {
				$(".addUserList").append('<div class="row user ml-2 mt-2" style="color:gray">該当ユーザーが存在しません</div>');				
			}
			return false;
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("エラーが発生しました！");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		});
	}
	
	//ユーザー検索
	serachUser('');
	
	$(".searchUser").on("keyup", function() {
		var searchName = $(this).val()
		serachUser(searchName);
	});
	
//	//ユーザー検索
//	$(".searchUser").on("keyup", function() {
//		var searchName = $(this).val();
//		var loginUserId = $(".loginUserId").val();
//		var groupId = $(".groupId").val();
//		console.log(loginUserId);
//		var url = 'http://localhost:8080/search_user_api_for_invite';
//		$.ajax({
//			url : url,
//			type : 'GET',
//			dataType : 'json',
//			data : {
//				name : searchName,
//				userId : loginUserId,
//				groupId : groupId
//			},
//			async : true
//		}).done(function(data) {
//			//ユーザーリストを初期化
//			$(".addUserList").children().remove();
//			//あいまい検索にヒットした数だけ表示、表示制限は後でつける
//			$.each(data.userList, function(index, user) {
//				//すでに追加したユーザーは表示しない
//				if($.inArray(user.name, groupUser) != -1) {
//					return true;
//				}
//				//追加するHTML
//				var addUserHTML 
//					= '<div class="row user mt-1">'
//						+ '<div class="col-md-6 text-left position-relative">'
//							+ '<a class="position-absolute" style="top:50%;transform:translateY(-50%);" data-toggle="popover" data-trigger="hover" data-placement="right" title="プロフィール文" data-content="' + user.profile + '">' 
//								+ user.name 
//							+ '</a>' 
//						+ '</div>'
//						+ '<span class="col-md-6 text-right">'
//							+ '<button type="button" class="btn btn-info addUser">追加</button>'
//							+ '<input type="hidden" value="' + user.name + '">'
//							+ '<input type="hidden" value="' + user.profile + '">'
//						+ '</span>'
//					+ '</div>';
//				
//				
//				$(".addUserList").append(addUserHTML);
//				$('[data-toggle="popover"]').popover();
//			})
//		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
//			alert("エラーが発生しました！");
//			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
//			console.log("textStatus     : " + textStatus);
//			console.log("errorThrown    : " + errorThrown.message);
//		});
//	});
	
	var listCount = 0;
	//追加ボタンを押した時に発火
	$(document).on("click", ".addUser", function() {
		//ユーザー名を取得
		var userName = $(this).find(".user-name").val();
		var profile = $(this).find(".user-profile").val();
		//配列に格納
		groupUser = userName;
		//中身を初期化する
		$(this).parents(".user").remove();
		//グループに追加するユーザーのHTML
		var deleteUserHTML 
			= '<span class="col-auto invite-user">'
				+ '<img class="col-auto pl-0" src="/img/user_default.png" height="38">'
				+ '<span class="mx-2 select-user">'
					+ '<a tabindex="0" data-toggle="popover" data-trigger="hover" data-placement="right" title="プロフィール文" data-content="' + profile + '">' 
						+ userName 
					+ '</a>' 
				+ '</span>'
				+ '<span class="mt-1 select-user">'
					+ '<a class="delete-user" href="#">'
						+ '<i class="far fa-window-close delete-user-icon"></i>'
						+ '<input class="user-name" type="hidden" name="userName" value="' + userName + '">'
					+ '</a>'
				+ '</span>'
			+ '</span>';
		listCount++;
		$(".groupUser").html(deleteUserHTML);
		serachUser($(".searchUser").val());

		$('[data-toggle="popover"]').popover();
		return false;
	});
	
	//削除ボタンを押した時に発火
	$(document).on("click", ".delete-user", function() {
		//ユーザー名を取得
		var userName = $(this).find(".user-name").val();
		//ボタンを押したユーザーのHTMLを削除
		$(this).parents(".invite-user").remove();
		//配列から削除
		groupUser = ''
		
		listCount = 0;
		//リスト番号を1から採番
//		$(".invite-user").each(function() {
//			var inputName = $(this).find("input").attr("name");
//			inputName = inputName.replace(/userNameList\[[0-9]{1,2}/g, "userNameList[" + listCount);
//			$(this).find("input").attr("name", inputName);
//			listCount++;
//		});
		serachUser($(".searchUser").val());
		return false;
	});
});