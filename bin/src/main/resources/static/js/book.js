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
//			let element1 = document.getElementById('book-names')
			$('#book-list').html('');
			for(var i = 0; i < data.items.length; i++){
//				element1.insertAdjacentHTML('afterend', '<a>' + data.items[i].volumeInfo.title + '</a>');
				element.insertAdjacentHTML('beforeend', '<li class="item col-lg-2 col-md-3 col-3"><button class="get-button" type="button"><img class="book-img" src="' + data.items[i].volumeInfo.imageLinks.smallThumbnail + 
						'"></button><span class="mask">Tap to register!</span><input class="isbn-id" type="hidden" value="' + data.items[i].volumeInfo.industryIdentifiers[0].identifier + 
						'"><input type="hidden" class="title" value="' + data.items[i].volumeInfo.title + 
						'"><input type="hidden" class="author" value="' + data.items[i].volumeInfo.authors[0] + 
						'"><input type="hidden" class="published-date" value="' + data.items[i].volumeInfo.publishedDate + 
						'"><input type="hidden" class="description" value="' + data.items[i].volumeInfo.description + 
						'"><input type="hidden" class="page-count" value="'+ data.items[i].volumeInfo.pageCount + 
						'"><input type="hidden" class="thumbnail-path" value="' + data.items[i].volumeInfo.imageLinks.smallThumbnail + '"></li>');
			}
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("エラーが発生しました！");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		});
	});
	
	$(document).on('click', '.item', function(){
		var isbnId = $(this).children('.isbn-id').val();
		var title = $(this).children('.title').val();
		var author = $(this).children('.author').val();
		var publishedDate = $(this).children('.published-date').val();
		var description = $(this).children('.description').val();
		var pageCount = $(this).children('.page-count').val();
		var thumbnailPath = $(this).children('.thumbnail-path').val();
		var url = 'http://localhost:8080/register_book_api/register_book';
		$.ajax({
			url : url,
			type : 'POST',
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
			$('#result').text("登録完了！");
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("エラーが発生しました！");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		});
	});
	
	$(document).on('mouseover', '.item', function(){
		var isbnId = $(this).children('.isbn-id').val();
		var title = $(this).children('.title').val();
		var author = $(this).children('.author').val();
		var publishedDate = $(this).children('.published-date').val();
		var description = $(this).children('.description').val();
		var pageCount = $(this).children('.page-count').val();
		var thumbnailPath = $(this).children('.thumbnail-path').val();
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
			$('#result-1').text("author：" + author);
			$('#result-2').text("title：" + title);
			$('#result-3').text("published date：" + publishedDate);
			});
	});
	
	$(document).on('mouseout', '.item', function(){
		$.ajax({
			async: true
		}).done(function(data){
			$('#result-1').text('');
			$('#result-2').text('');
			$('#result-3').text('');
		});
	});
});