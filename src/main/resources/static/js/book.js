$(function() {
	$("#book-button").click(function() {
		var name = $("#book-name").val();
		var url = "https://www.googleapis.com/books/v1/volumes?q=" + name;
		$.ajax({
			url : url,
			type : 'GET',
			dataType : 'json',
			async: true // 非同期で処理を行う
		}).done(function(data) {
			let element = document.getElementById('book-thumbnail');
			let element1 = document.getElementById('book-name')
			for(var i = 0; i < data.items.length; i++){
				element.insertAdjacentHTML('beforeend', '<a id="register" href=\"' + data.items[i].volumeInfo.industryIdentifiers[1].identifier + '\"><img src=\"' + data.items[i].volumeInfo.imageLinks.smallThumbnail + '\"></a>');
				var id = $('#register').val();
				console.log(id);
			}
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("エラーが発生しました！");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		});
	});
});