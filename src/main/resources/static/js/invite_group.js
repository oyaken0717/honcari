$(function() {
	//グループに追加するユーザーの配列
	var groupUser = [];
	
	//ユーザー検索
	$(".searchUser").on("keyup", function() {
		var searchName = $(this).val();
		var loginUserId = $(".loginUserId").val();
		var groupId = $(".groupId").val();
		console.log(loginUserId);
		var url = 'http://localhost:8080/search_user_api_for_invite';
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
				if($.inArray(user.name, groupUser) != -1) {
					return true;
				}
				//追加するHTML
				var addUserHTML 
					= '<div class="row user mt-1">'
						+ '<div class="col-md-6 text-left position-relative">'
							+ '<a class="position-absolute" style="top:50%;transform:translateY(-50%);" data-toggle="popover" data-trigger="hover" data-placement="right" title="プロフィール文" data-content="' + user.profile + '">' 
								+ user.name 
							+ '</a>' 
						+ '</div>'
						+ '<span class="col-md-6 text-right">'
							+ '<button type="button" class="btn btn-info addUser">追加</button>'
							+ '<input type="hidden" value="' + user.name + '">'
							+ '<input type="hidden" value="' + user.profile + '">'
						+ '</span>'
					+ '</div>';
				
				
				$(".addUserList").append(addUserHTML);
				$('[data-toggle="popover"]').popover();
			})
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("エラーが発生しました！");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		});
	});
	
	var listCount = 0;
	//追加ボタンを押した時に発火
	$(document).on("click", ".addUser", function() {
		//ユーザー名を取得
		var userName = $(this).next().val();
		var profile = $(this).next().next().val();
		//配列に格納
		groupUser.push(userName);
		//中身を初期化する
		$(this).parents(".user").remove();
		//グループに追加するユーザーのHTML
		var deleteUserHTML 
			= '<div class="row group-user mt-1">'
				+ '<div class="col-md-6 text-left position-relative">'
				+ '<a class="position-absolute" style="top:50%;transform:translateY(-50%);" data-toggle="popover" data-trigger="hover" data-placement="right" title="プロフィール文" data-content="' + profile + '">' 
				+ userName 
			+ '</a>' 
		+ '</div>'
				+ '<span class="col text-right">'
					+ '<button type="button" class="btn btn-danger delete-user">削除</button>'
					+ '<input type="hidden" name="userNameList[' + listCount + ']" value="' + userName + '">'
				+ '</span>'
			+ '</div>';
		$(".groupUserList").append(deleteUserHTML);
		$('[data-toggle="popover"]').popover();
		listCount++;
	});
	
	//削除ボタンを押した時に発火
	$(document).on("click", ".delete-user", function() {
		//ユーザー名を取得
		var userName = $(this).next().val();
		//ボタンを押したユーザーのHTMLを削除
		$(this).parents(".group-user").remove();
		//配列から削除
		groupUser = groupUser.filter(function(user) {
			return user !== userName;
		});
		
		listCount = 1;
		//リスト番号を1から採番
		$(".group-user").each(function() {
			var inputName = $(this).find("input").attr("name");
			inputName = inputName.replace(/userNameList\[[0-9]{1,2}/g, "userNameList[" + listCount);
			$(this).find("input").attr("name", inputName);
			listCount++;
		});
	});
});