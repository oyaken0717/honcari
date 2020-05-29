$(function(){
	var name1 = $("#name1").val();
	var name2 = $("#name2").val();
	
	$('.custom-file-input').on('change', handleFileSelect);
	function handleFileSelect(evt) {
		var sizeLimit = 1024 * 1024 * 1;
		var file = evt.target.files[0];
		
		//1MBを超えているかチェック
		if(file.size > sizeLimit) {
			alert('1MBより大きいファイルはアップロードできません');
			evt.target.value = ''; // inputの中身をリセット
			return;
		}
			
		var reader = new FileReader();
		reader.onload = (function(theFile) {
			return function(e) {
				if (theFile.type.match('image.*')) {
					$("#current-image").removeClass("d-block").addClass("d-none");
					$("#input-image").remove();
					var $html = ['<img id="input-image" class="rounded-lg mx-auto d-block border border-secondary user-image" src="', e.target.result,'" title="', encodeURI(theFile.name), '" width="100px" height="100px">'].join('');// 画像では画像のプレビューの表示
					$('#image-panel').append($html);
				} else {
					alert('画像でないファイルはアップロードできません');
					evt.target.value = ''; // inputの中身をリセット
					return;
				}
			};
		})(file);
		
		reader.readAsDataURL(file);
		
		$(this).next('.custom-file-label').html(encodeURI(file.name));
	}
	
	//ファイルの取消
	$('.reset').click(function(){
		$(this).parent().prev().children('.custom-file-label').html('ファイル選択...');
		$("#current-image").removeClass("d-none").addClass("d-block");
		$('#input-image').remove();
		$('.custom-file-input').val('');
	});
	
	$('#delete-image').click(function(){
		if(confirm("現在の画像を削除してよろしいですか？")) {
			$('#current-image').removeClass("d-none").addClass("d-block").attr('src','https://'+name2+'.s3-ap-northeast-1.amazonaws.com/'+name1+'/user_default.png');
			$('#image-path').val('https://'+name2+'.s3-ap-northeast-1.amazonaws.com/'+name1+'/user_default.png');
			$('.custom-file-label').html('ファイル選択...');
			$('#input-image').remove();
			$('.custom-file-input').val('');
		}
	});
	
});