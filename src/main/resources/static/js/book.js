$(function() {	
	$("#book-register-alert").hide();
//	$("#book-button").click(function() {
//		var name = $("#book-name").val();
//		name += "　";
//		var url = "https://www.googleapis.com/books/v1/volumes?q=" + name;
//		$.ajax({
//			url : url,
//			type : 'GET',
//			dataType : 'json',
//			async: false
//		}).done(function(data) {
//			if(data.totalItems == 0){
//				$('#book-list').html('該当の書籍がありません');
//			}else{
//			let element = document.getElementById('book-list');
//			$('#book-list').html('');
//			element.insertAdjacentHTML('beforeend', '<tr><td width="20%">書籍サムネイル</td><td width="20%">著者</td><td width="20%">タイトル</td><td width="20%">出版日</td><td width="20%">登録</td></tr>');
//			//取得した値を一つ一つ存在するか確認してから結合する
//			for(var i = 0; i < data.items.length; i++){
//				//開始タグ
//				var searchHTML = '<tr>';
//				//volumeInfoの中にimageLinksがあるかどうかで判定
//				//これ以降の条件式はほぼ同じなのでコメント割愛
//				//実際に表示されるhtml
//				if ("imageLinks" in data.items[i].volumeInfo) {
//					searchHTML += '<td><button class="item" type="button"><img class="book-img" src="' + data.items[i].volumeInfo.imageLinks.smallThumbnail + '"></button></td>';
//				} else {
//					searchHTML += '<td><span></span></td>';
//				}
//				
//				if ("authors" in data.items[i].volumeInfo) {
//					searchHTML += '<td><span>' + data.items[i].volumeInfo.authors[0] + '</span></td>';
//				} else {
//					searchHTML += '<td><span></span></td>';
//				}
//				
//				if ("title" in data.items[i].volumeInfo) {
//					searchHTML += '<td><span>' + data.items[i].volumeInfo.title + '</span></td>';
//				} else {
//					searchHTML += '<td><span></span></td>';
//				}
//				
//				if ("publishedDate" in data.items[i].volumeInfo) {
//					searchHTML += '<td><span>' + data.items[i].volumeInfo.publishedDate + '</span></td>';
//				} else {
//					searchHTML += '<td><span></span></td>';
//				}
//				
//				//登録ボタンのhtml
//				searchHTML += '<td><button id="register-btn" class="btn btn-secondary" data-toggle="modal" data-target="#book-register-form">登録</button>'; 
//				
//				//formで送るパラメータのhtml
//				if ("industryIdentifiers" in data.items[i].volumeInfo) {
//					searchHTML += '<input class="isbn-id" type="hidden" value="' + data.items[i].volumeInfo.industryIdentifiers[0].identifier + '">';
//				}
//				
//				if ("title" in data.items[i].volumeInfo) {
//					searchHTML += '<input type="hidden" class="title" value="' + data.items[i].volumeInfo.title + '">';
//				}
//				
//				if ("authors" in data.items[i].volumeInfo) {
//					searchHTML += '<input type="hidden" class="author" value="' + data.items[i].volumeInfo.authors[0] + '">'; 
//				}
//				
//				if ("publishedDate" in data.items[i].volumeInfo) {
//					searchHTML += '<input type="hidden" class="published-date" value="' + data.items[i].volumeInfo.publishedDate + '">';
//				}
//				
//				if ("description" in data.items[i].volumeInfo) {
//					searchHTML += '<input type="hidden" class="description" value="' + data.items[i].volumeInfo.description + '">';
//				}
//				
//				if ("pageCount" in data.items[i].volumeInfo) {
//					searchHTML += '<input type="hidden" class="page-count" value="'+ data.items[i].volumeInfo.pageCount + '">';
//				}
//				
//				if ("imageLinks" in data.items[i].volumeInfo) {
//					searchHTML += '<input type="hidden" class="thumbnail-path" value="' + data.items[i].volumeInfo.imageLinks.smallThumbnail + '">';
//				}
//				//終了タグ
//				searchHTML += '</tr>';
//				//htmlを追加
//				element.insertAdjacentHTML('beforeend', searchHTML);
//			}
//			}
//			const xhr = new XMLHttpRequest();
//			xhr.withCredentials = true;
//		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
//			alert("エラーが発生しました！");
//			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
//			console.log("textStatus     : " + textStatus);
//			console.log("errorThrown    : " + errorThrown.message);
//		});
//	});
//	
	$(document).on('click', '#register-btn', function(){
		var isbnId = $(this).nextAll('.isbn-id').val();
		var title = $(this).nextAll('.title').val();
		var author = $(this).nextAll('.author').val();
		var publishedDate = $(this).nextAll('.published-date').val();
		var description = $(this).nextAll('.description').val();
		var pageCount = $(this).nextAll('.page-count').val();
		var thumbnailPath = $(this).nextAll('.thumbnail-path').val();
		console.log(isbnId);
		console.log(title);
		console.log(author);
		console.log(publishedDate);
		console.log(description);
		console.log(pageCount);
		console.log(thumbnailPath);
//		$.ajax({
//			data : {
//				isbnId : isbnId,
//				title : title,
//				author : author,
//				publishedDate : publishedDate,
//				description : description,
//				pageCount : pageCount,
//				thumbnailPath : thumbnailPath
//			},
//			async: true
//		}).done(function(data) {
			let element = document.getElementById('book-request-parameter');
			console.log(element)
			$('#book-request-parameter').html('');
			element.insertAdjacentHTML('beforeend', '<input type="hidden" name="isbnId" value="' + isbnId + '" id="register-isbn"><input type="hidden" name="title" value="' + 
					title + '" id="register-title"><input type="hidden" name="author" value="' + author + '" id="register-author"><input type="hidden" name="publishedDate" value="' + publishedDate + 
					'" id="register-published-date"><input type="hidden" name="description" value="' + description + '" id="register-description"><input type="hidden" name="pageCount" value="' + pageCount + 
					'" id="register-page-count"><input type="hidden" name="thumbnailPath" value="' + thumbnailPath + '" id="register-thumbnail-path">');
//		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
//			alert("エラーが発生しました！");
//			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
//			console.log("textStatus     : " + textStatus);
//			console.log("errorThrown    : " + errorThrown.message);
//		});
	});
	
	$(document).on('click', '#book-register-button', function(){
		var token = $("meta[name='_csrf']").attr("content");
	    var header = $("meta[name='_csrf_header']").attr("content");
	    $(document).ajaxSend(function(e, xhr, options) {
	      xhr.setRequestHeader(header, token);
	    });
		var isbnId = $('#register-isbn').val();
		var title = $('#register-title').val();
		var author = $('#register-author').val();
		var publishedDate = $('#register-published-date').val();
		var description = $('#register-description').val();
		var pageCount = $('#register-page-count').val();
		var thumbnailPath = $('#register-thumbnail-path').val();
		var categoryId = $('#register-category-id').val();
		var comment = $('#register-comment').val();
		var url = 'http://localhost:8080/book_api/register_book';
		$.ajax({
			url : url,
			type : 'POST',
			dataType : 'json',
			data : {
				isbnId : isbnId,
				title : title,
				author : author,
				publishedDate : publishedDate,
				description : description,
				pageCount : pageCount,
				thumbnailPath : thumbnailPath,
				categoryId : categoryId,
				comment : comment
			},
			async: false
		}).done(function(data) {
			$("#check").text(data.check);
			$("#register-comment").val('');
			$('#register-category-id').val(1);
			const xhr = new XMLHttpRequest();
			xhr.withCredentials = true;
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("エラーが発生しました！");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		});
	});
	$(function(){
		  $(".message:not(:animated)").fadeIn("slow",function(){
		    $(this).delay(5000).fadeOut("slow");
		  });
		});
		</script>
});