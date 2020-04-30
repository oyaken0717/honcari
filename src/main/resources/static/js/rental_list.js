(function() {
	'use strict';

	// 延長申請時に延長後の貸出期限のデフォルト値を１週間後にセットする
	if(document.getElementById('rental-period') != null){
		var deadline = document.getElementById('rental-period').textContent.split("-");
		var deadlineDate = new Date(deadline[0], deadline[1]-1, deadline[2]);
		deadlineDate.setDate(deadlineDate.getDate()+7);
		var yyyy = deadlineDate.getFullYear();
		var mm = ("0"+(deadlineDate.getMonth()+1)).slice(-2);
		var dd = ("0"+deadlineDate.getDate()).slice(-2);
		document.getElementById("extend-date").value=yyyy+'-'+mm+'-'+dd;
	}
    
	// 延長申請時の入力値チェックでエラーが出た場合はモーダルを表示
	if (document.getElementById("error-message") !== null) {
		$("#extend-request-form").modal();
	}
	
	setTimeout(() => {
		$('#flash').alert('close');
	}, 1000);
	
	$('.cancel-btn').click(function() {
		if (!confirm("キャンセルしてよろしいでしょうか?")) {
			return false;
		}
	});
	
	$('.approval-btn').click(function() {
		if (!confirm("承認してよろしいでしょうか?")) {
			return false;
		}
	});
	
	
	
})();