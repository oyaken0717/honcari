$(function() {	

$(document).on('click', '#show-detail-btn', function(){
		var groupName = $(this).nextAll('.group-name').val();
		console.log(groupName)	
		var groupDescription = $(this).nextAll('.group-description').val();
		console.log(groupDescription)	
		let element = document.getElementById('group-detail');
			$('#group-detail').html('');
			element.insertAdjacentHTML('beforeend', '<p class="card-text">' + groupName + '</p>' + 
					'<p class="text-muted" style="overflow: hidden;">' + groupDescription + '</p>');
		});
});