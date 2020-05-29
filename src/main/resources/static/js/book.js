$(function() {	
	$("#flash").hide();
	
	var hideAlert = function(){
		$("#flash").hide();
	}
	
	$(document).on('click', '#register-btn', function(){
		var isbnId = $(this).nextAll('.isbn-id').val();
		var title = $(this).nextAll('.title').val();
		var author = $(this).nextAll('.author').val();
		var publishedDate = $(this).nextAll('.published-date').val();
		var description = $(this).nextAll('.description').val();
		var pageCount = $(this).nextAll('.page-count').val();
		var thumbnailPath = $(this).nextAll('.thumbnail-path').val();
		let element = document.getElementById('book-request-parameter');
		$('#book-request-parameter').html('');
		element.insertAdjacentHTML('beforeend', '<input type="hidden" name="isbnId" value="' + isbnId + '" id="register-isbn"><input type="hidden" name="title" value="' + 
					title + '" id="register-title"><input type="hidden" name="author" value="' + author + '" id="register-author"><input type="hidden" name="publishedDate" value="' + publishedDate + 
					'" id="register-published-date"><input type="hidden" name="description" value="' + description + '" id="register-description"><input type="hidden" name="pageCount" value="' + pageCount + 
					'" id="register-page-count"><input type="hidden" name="thumbnailPath" value="' + thumbnailPath + '" id="register-thumbnail-path">');
	});
	
	$(document).on('click', '#book-register-button', function(){
		$(this).prop('disabled', true);
		var token = $("meta[name='_csrf']").attr("content");
	    var header = $("meta[name='_csrf_header']").attr("content");
	    $(document).ajaxSend(function(e, xhr, options) {
	      xhr.setRequestHeader(header, token);
	    });
	    var element = document.getElementById("book-data") ;
		var isbnId = $('#register-isbn').val();
		var title = $('#register-title').val();
		var author = $('#register-author').val();
		var publishedDate = $('#register-published-date').val();
		var description = $('#register-description').val();
		var pageCount = $('#register-page-count').val();
		var thumbnailPath = $('#register-thumbnail-path').val();
		var categoryId = $('#register-category-id').val();
		var comment = $('#register-comment').val();
		var bookStatusRadio = element.bookStatus;
		var bookStatus = bookStatusRadio.value;
		var url = location.href
		url = url.replace(/book\/search_books/g, "book_api/register_book");
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
				comment : comment,
				bookStatus : bookStatus
			},
			async: false
		}).done(function(data) {
			$("#check").text(data.check);
			$("#register-comment").val('');
			$('#register-category-id').val(1);
			const xhr = new XMLHttpRequest();
			xhr.withCredentials = true;
			$("#book-register-button").prop('disabled', false);
			$("#flash").show();
			$("#register-success").text('書籍の登録が完了しました');
			setTimeout(hideAlert, 3000);
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
});