$(function() {
	// 入力値チェックでエラーが出た場合はモーダルを表示
	if (document.getElementById("error-message") !== null) {
		$("#rental-request-form").modal();
	}

	// 貸出状況に応じてボタンを無効化
	let bookStatus = document.getElementById("book-status")
	let bookStatusNumber = bookStatus.getAttribute("value");
	const button = document.getElementById("rental-request-button")
	if(bookStatusNumber == 0 || bookStatusNumber == 2 || bookStatusNumber == 3){
		bookStatus.style.color = "rgb(255, 0, 0)"
		button.disabled = true;
	}else{
		bookStatus.style.color = "rgb(0, 0, 255)"
	}

});