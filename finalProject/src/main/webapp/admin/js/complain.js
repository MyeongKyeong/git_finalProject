$(document).ready(function() {
	$.ajax({
		type: 'post',
		url: '/market/admin/getComplainList',
		data: {'pg': $('#pg').val(), 
				'viewNum': $('#viewNum').val()},
		dataType: 'json',
		success: function(data){
			$("#complainTbody tr:gt(0)").remove();
			
			complainList(data);
			findComplainContent();
		},error: function(err){
			console.log(err);
		}
	});//ajax
});	
	
//20, 50개 출력
$('#selectPrint').change(function(){
	var viewNum = $(this).val();
	$('#viewNum').val(viewNum);
	
	$.ajax({
		type: 'post',
		url: '/market/admin/getComplainList',
		data: {'pg': $('#pg').val(),
			'viewNum': $('#viewNum').val()},
		dataType: 'json',
		success: function(data){
			$("#complainTbody tr:gt(0)").remove();
			complainList(data)
			findComplainContent();
		},error: function(err){
			console.log(err);
		}
	});//ajax
});
	
//출력 개수/키워드 넣고 검색	
$('#memberSearchBtn').click(function(){
	console.log($('#searchType').val() + "/" +$('#keyword').val() +"/"+$('#selectPrint').val());
	$.ajax({
		type: 'post',
		url: '/market/admin/searchReportedMember',
		data: {'searchType': $('#searchType').val(), 
				'keyword': $('#keyword').val(),
				'pg': $('#pg').val(),
				'viewNum': $('#selectPrint').val()},
		dataType: 'json',
		success: function(data){
			$("#complainTbody tr:gt(0)").remove();
			complainList(data);
			findComplainContent();
		}
	});
});


//출력
function complainList(data){
	let eachPart_seq;
	$.each(data.list, function(index, items){
		
		if(items.product_seq != 0)
			eachPart_seq = items.product_seq
		else if(items.review_seq != 0)
			eachPart_seq = items.review_seq
		else if(items.board_seq != 0)
			eachPart_seq = items.board_seq
		else if(items.comment_seq != 0)
			eachPart_seq = items.comment_seq
		else if(items.store_seq != 0)
			eachPart_seq = items.store_seq
//		else if(items.talk_seq != 0)
//			items.eachPart_seq = items.talk_seq
			
		$('<tr/>').append($('<td/>',{
			text: items.complain_seq
		})).append($('<td/>',{
			text: items.complain_category
		})).append($('<td/>',{
			}).append($('<a/>',{
				href: '#',
				text: eachPart_seq,
				id: 'subjectA',
				class: eachPart_seq+""
		}))).append($('<td/>',{
			text: items.complain_content
		})).append($('<td/>',{
			text: items.mem_id
		})).append($('<td/>',{
			text: items.reporter_id,
		})).append($('<td/>',{
			text: items.complain_logtime,
		})).appendTo($('#complainTbody'));
	});//each
	//페이징처리
	$('#boardPagingDiv').html(data.adminComplainBP.pagingHTML);
}


//신고 내용 확인
function findComplainContent(){
	$('#complainTbody').on('click', '#subjectA', function(){
		if($(this).parent().prev().text()=='댓글 신고'){// 댓글 내용 우측에 띄우기
			$.ajax({
				type: 'post',
				url: '/market/admin/getCommentContent',
				data: {'comment_seq': $(this).parent().text() },
				dataType: 'json',
				success: function(data){
					$('#reported_id').text(data.commentDTO.mem_id);
					$('#reported_logtime').text(data.commentDTO.comment_logtime);
					$('#reported_content').text(data.commentDTO.comment_content);
				}
			});//ajax
		}else if($(this).parent().prev().text()=='게시글 신고'){//게시글 페이지 업
			let seq = $(this).parent().text();
			window.open("/market/board/articleForm?seq="+seq+"&pg=1","PopupWin","width=800,height=800");
		}else if($(this).parent().prev().text()=='상품 신고'){//상품 페이지 업
			let seq = $(this).parent().text();
			window.open("/market/product/productDetail?seq="+seq,"PopupWin","width=800,height=800");
		}else if($(this).parent().prev().text()=='리뷰 신고'){// 리뷰 내용 우측에 띄우기
			$.ajax({
				type: 'post',
				url: '/market/admin/getCommentContent',
				data: {'comment_seq': $(this).parent().text() },
				dataType: 'json',
				success: function(data){
					$('#reported_id').text(data.commentDTO.mem_id);
					$('#reported_logtime').text(data.commentDTO.comment_logtime);
					$('#reported_content').text(data.commentDTO.comment_content);
				}
			});//ajax
		}else if($(this).parent().prev().text()=='상점 신고'){//상점 페이지 업
			let id = $(this).parent().text();
			window.open("/market/store/store?id"+id,"PopupWin","width=800,height=800");
		}else if($(this).parent().prev().text()=='바다톡 신고'){//talk
			
			
		}
	});
}


