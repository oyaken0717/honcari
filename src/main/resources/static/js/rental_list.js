(function() {
	'use strict';
	$('.cancel-btn').click(function() {
		if (!confirm("キャンセルしてよろしいでしょうか?")) {
			return false;
		}
	});
	
	$('.approval-btn').click(function() {
		if (!confirm("承認してよろしいでしょうか?")) {
			return false;
		}
	});
	
	setTimeout(() => {
		$('#flash').alert('close');
	}, 1000);
	
	
})();