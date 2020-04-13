$(function() {
	$('#inputPassword').on("keyup", function() {
		check_password();
	});
	
	$('#inputConfirmationPassword').on("keyup", function() {
		check_password();
	});
	
	function check_password(){
		var hostUrl = 'http://localhost:8080/check_password_api/passwordcheck';
		var inputPassword = $("#inputPassword").val();
		var inputConfirmationPassword = $("#inputConfirmationPassword").val();
		$.ajax({
			url : hostUrl,
			type : 'POST',
			dataType : 'json',
			data : {
				password : inputPassword,
				confirmationPassword : inputConfirmationPassword
			},
			async: true // 非同期で処理を行う
		}).done(function(data) {
			// コンソールに取得データを表示
			console.log(data.robustnessMessage);
			$("#robustnessMessage").text(data.robustnessMessage);
			$("#disagreementMessage").text(data.disagreementMessage);
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("エラーが発生しました！");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		});
	}
});