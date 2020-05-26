$(function() {

	// 入力値チェックでエラーが出た場合はモーダルを表示
	if (document.getElementById("error-message") !== null) {
		$("#rental-request-form").modal();
	}

	// 貸出状況に応じてボタンを無効化
	let bookStatus = document.getElementById("book-status")
	let bookStatusNumber = bookStatus.getAttribute("value");
	const requestBtn = document.getElementById("rental-request-button")
	if (bookStatusNumber == 0 || bookStatusNumber == 2 || bookStatusNumber == 3) {
		bookStatus.style.color = "rgb(255, 0, 0)"
		requestBtn.disabled = true;
	} else {
		bookStatus.style.color = "rgb(0, 0, 255)"
	}

	// フォームが送信されたら無効化
	const requestForm = document.getElementById("requestForm");
	let sendRequestBtn = document.getElementById("send-request-btn");
	requestForm.addEventListener("submit", () => {
		sendRequestBtn.disabled = true;
	});

});