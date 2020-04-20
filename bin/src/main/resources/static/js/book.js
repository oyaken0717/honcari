$(function() {
	$("#book-button").click(function() {
		var name = $("#book-name").val();
		var url = "https://www.googleapis.com/books/v1/volumes?q=" + name;
		$.ajax({
			url : url,
			type : 'GET',
			dataType : 'json',
			async: true
		}).done(function(data) {
			let element = document.getElementById('book-list');
			$('#book-list').html('');
			element.insertAdjacentHTML('beforeend', '<tr><td width="20%">書籍サムネイル</td><td width="20%">著者</td><td width="20%">タイトル</td><td width="20%">出版日</td><td width="20%">登録</td></tr>');
			for(var i = 0; i < data.items.length; i++){
				element.insertAdjacentHTML('beforeend', '<tr><td><button class="item" type="button"><img class="book-img" src="' + data.items[i].volumeInfo.imageLinks.smallThumbnail + 
						'"></button></td><td><span>' + data.items[i].volumeInfo.authors[0] + '</span></td><td><span>' + data.items[i].volumeInfo.title + '</span></td><td><span>' + data.items[i].volumeInfo.publishedDate + '</span></td><td><button id="register-btn" class="btn btn-secondary" data-toggle="modal" data-target="#book-register-form">登録</button>' +
						'<input class="isbn-id" type="hidden" value="' + data.items[i].volumeInfo.industryIdentifiers[0].identifier + 
						'"><input type="hidden" class="title" value="' + data.items[i].volumeInfo.title + 
						'"><input type="hidden" class="author" value="' + data.items[i].volumeInfo.authors[0] + 
						'"><input type="hidden" class="published-date" value="' + data.items[i].volumeInfo.publishedDate + 
						'"><input type="hidden" class="description" value="' + data.items[i].volumeInfo.description + 
						'"><input type="hidden" class="page-count" value="'+ data.items[i].volumeInfo.pageCount + 
						'"><input type="hidden" class="thumbnail-path" value="' + data.items[i].volumeInfo.imageLinks.smallThumbnail + '"></tr>');
			}
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("エラーが発生しました！");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		});
	});
	
	$(document).on('click', '#register-btn', function(){
		var isbnId = $(this).nextAll('.isbn-id').val();
		var title = $(this).nextAll('.title').val();
		var author = $(this).nextAll('.author').val();
		var publishedDate = $(this).nextAll('.published-date').val();
		var description = $(this).nextAll('.description').val();
		var pageCount = $(this).nextAll('.page-count').val();
		var thumbnailPath = $(this).nextAll('.thumbnail-path').val();
		$.ajax({
			data : {
				isbnId : isbnId,
				title : title,
				author : author,
				publishedDate : publishedDate,
				description : description,
				pageCount : pageCount,
				thumbnailPath : thumbnailPath
			},
			async: true
		}).done(function(data) {
			let element = document.getElementById('book-request-parameter');
			$('#book-request-parameter').html('');
			element.insertAdjacentHTML('beforeend', '<input type="hidden" name="isbnId" value="' + isbnId + '"><input type="hidden" name="title" value="' + 
					title + '"><input type="hidden" name="author" value="' + author + '"><input type="hidden" name="publishedDate" value="' + publishedDate + 
					'"><input type="hidden" name="description" value="' + description + '"><input type="hidden" name="pageCount" value="' + pageCount + 
					'"><input type="hidden" name="thumbnailPath" value="' + thumbnailPath + '">');
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("エラーが発生しました！");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		});
	});
});