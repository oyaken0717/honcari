(function() {
	'use strict';
	
	// フラッシュメッセージを消す
	setTimeout(() => {
		$('#flash').alert('close');
	}, 3000);
	
	$("#name").keyup(function(){
		$(".error-name").remove();
		if($("#name").val() != ""){
			$("#confirm").prop("disabled",false).css("cursor","pointer");
		}else {
			$("#confirm").prop("disabled",true).css("cursor","default");
			$("#error-name").prepend('<label class="col-form-label error-name" style="color:red">入力は必須です</label>');
		}
	});
	$("#email").keyup(function(){
		$(".error-email").remove();
		if($("#email").val() != ""){
			$("#confirm").prop("disabled",false).css("cursor","pointer");
		}else {
			$("#confirm").prop("disabled",true).css("cursor","default");
			$("#error-email").prepend('<label class="col-form-label error-email" style="color:red">入力は必須です</label>');
		}
	});
	$("#content").keyup(function(){
		$(".error-content").remove();
		if($("#content").val() != ""){
			$("#confirm").prop("disabled",false).css("cursor","pointer");
		}else {
			$("#confirm").prop("disabled",true).css("cursor","default");
			$("#error-content").prepend('<label class="col-form-label error-content" style="color:red">入力は必須です</label>');
		}
	});
	
	$("#confirm").click(function(){
		$("#input-name").text($("#name").val());
		$("#input-email").text($("#email").val());
		$("#input-content").text($("#content").val());
	});

	
})();