package admin.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import admin.bean.AdminBoardPaging;
import admin.bean.AdminMembersDTO;
import admin.bean.AdminProductDTO;
import admin.bean.QnaDTO;
import admin.bean.WithdrawDTO;
import admin.dao.AdminDAO;
import board.bean.CommentDTO;
import member.bean.ComplainDTO;
import member.bean.MemberDTO;
import product.bean.ProductDTO;
import store.bean.ReviewDTO;
import store.bean.StoreDTO;
//담당 : 이건탁(회원관리/상점관리/상품관리)
//	       김명경(탈퇴사유분석/신고내역관리/1:1문의 관리)
@Service
public class AdminServiceImpl implements AdminService {
   @Autowired
   private AdminDAO adminDAO;   
   @Autowired
   private AdminBoardPaging adminBoardPaging;
   @Override
   public List<MemberDTO> getMemberList(String pg, String viewNum) {
      int endNum = Integer.parseInt(pg)*Integer.parseInt(viewNum);
      int startNum = endNum-(Integer.parseInt(viewNum)-1);
      
      Map <String, Integer> map = new HashedMap<String, Integer>();
      map.put("startNum", startNum);
      map.put("endNum", endNum);
      List<MemberDTO> list = adminDAO.getMemberList(map);
      return list;
   }
   @Override
   public AdminBoardPaging boardPaging(String pg, String viewNum) {
      int totalA = adminDAO.getTotalA();
      
      adminBoardPaging.setCurrentPage(Integer.parseInt(pg));
      adminBoardPaging.setPageBlock(10);
      adminBoardPaging.setPageSize(Integer.parseInt(viewNum));//위에endNum,startNum과 맞아야함
      adminBoardPaging.setTotalA(totalA);
      
      adminBoardPaging.makePagingHTML();
      
      return adminBoardPaging;
   }
   
   //전체회원수
   @Override
   public int totalMember() {
      int totalMember = adminDAO.getTotalA();
      return totalMember;
   }
   //판매하는물건 총개수
   @Override
   public int totalSellProduct(String id) {
      int totalSellProduct = adminDAO.totalSellProduct(id);
      return totalSellProduct;
   }
         
      
   //검색
   @Override
   public List<MemberDTO> getSearchMember(Map<String, String> map) {
      int viewNum = Integer.parseInt(map.get("viewNum"));
      
      int endNum = Integer.parseInt(map.get("pg"))*viewNum;
      int startNum = endNum-(viewNum-1);
      
      map.put("startNum", startNum+"");
      map.put("endNum", endNum+"");
      return adminDAO.getSearchMember(map);
   }
   @Override
   public AdminBoardPaging searchBoardPaging(Map<String, String> map) {
      int viewNum = Integer.parseInt(map.get("viewNum"));
      
      int totalB = adminDAO.getTotalB(map);
      
      adminBoardPaging.setCurrentPage(Integer.parseInt(map.get("pg")));
      adminBoardPaging.setPageBlock(10);
      adminBoardPaging.setPageSize(viewNum);
      adminBoardPaging.setTotalA(totalB);
      
      adminBoardPaging.makePagingHTML();
      
      return adminBoardPaging;
   }
   @Override
   public AdminMembersDTO getMemberView(String id) {
      AdminMembersDTO adminMembersDTO = adminDAO.getMemberView(id);
      return adminMembersDTO;
   }
   
   
   
   
   //상점리스트출력
   @Override
   public List<StoreDTO> getStoreList(String pg, String viewNum) {
      int endNum = Integer.parseInt(pg)*Integer.parseInt(viewNum);
      int startNum = endNum-(Integer.parseInt(viewNum)-1);
      
      Map <String, Integer> map = new HashedMap<String, Integer>();
      map.put("startNum", startNum);
      map.put("endNum", endNum);
      List<StoreDTO> storeList = adminDAO.getStoreList(map);
      return storeList;
   }
   @Override
   public AdminBoardPaging StoreBP(String pg, String viewNum) {
      int totalC = adminDAO.getTotalC();
      
      adminBoardPaging.setCurrentPage(Integer.parseInt(pg));
      adminBoardPaging.setPageBlock(10);
      adminBoardPaging.setPageSize(Integer.parseInt(viewNum));//위에endNum,startNum과 맞아야함
      adminBoardPaging.setTotalA(totalC);
      
      adminBoardPaging.makePagingHTML();
      return adminBoardPaging;
   }
   @Override
   public AdminMembersDTO getStoreView(String id) {
      AdminMembersDTO adminMembersDTO = adminDAO.getStoreView(id);
      return adminMembersDTO;
   }
   @Override
   public List<StoreDTO> getSearchStoreList(Map<String, String> map) {
      int viewNum = Integer.parseInt(map.get("viewNum"));
      
      int endNum = Integer.parseInt(map.get("pg"))*viewNum;
      int startNum = endNum-(viewNum-1);
      
      map.put("startNum", startNum+"");
      map.put("endNum", endNum+"");
      return adminDAO.getSearchStoreList(map);
   }
   //상점조건검색 페이징
   @Override
   public AdminBoardPaging getSearchStoreBP(Map<String, String> map) {
      int viewNum = Integer.parseInt(map.get("viewNum"));
      
      int totalD = adminDAO.getTotalD(map);
      
      adminBoardPaging.setCurrentPage(Integer.parseInt(map.get("pg")));
      adminBoardPaging.setPageBlock(10);
      adminBoardPaging.setPageSize(viewNum);
      adminBoardPaging.setTotalA(totalD);
      
      adminBoardPaging.makePagingHTML();
      
      return adminBoardPaging;
   }
   //상점상세보기_물건출력
   @Override
   public List<ProductDTO> getStore_ProductList(String id) {
      List<ProductDTO> productList = adminDAO.getStore_ProductList(id);
      return productList;
   }
   //상점목록_물품리스트에서 삭제
   @Override
   public void store_productDelete(String[] check) {
      Map<String, String[]> map = new HashMap<String, String[]>();
      map.put("array", check);
      adminDAO.store_productDelete(map);
   }
   @Override
   public List<ProductDTO> getProductAllList(String pg, String viewNum) {
      int endNum = Integer.parseInt(pg)*Integer.parseInt(viewNum);
      int startNum = endNum-(Integer.parseInt(viewNum)-1);
      
      Map <String, Integer> map = new HashedMap<String, Integer>();
      map.put("startNum", startNum);
      map.put("endNum", endNum);
      List<ProductDTO> productList = adminDAO.getProductAllList(map);
      return productList;
   }
   @Override
   public AdminBoardPaging ProductBP(String pg, String viewNum) {
      int totalE = adminDAO.getTotalE();
      
      adminBoardPaging.setCurrentPage(Integer.parseInt(pg));
      adminBoardPaging.setPageBlock(10);
      adminBoardPaging.setPageSize(Integer.parseInt(viewNum));//위에endNum,startNum과 맞아야함
      adminBoardPaging.setTotalA(totalE);
      
      adminBoardPaging.makePagingHTML();
      
      return adminBoardPaging;
   }
   
   //물품관련 상세정보, 위에 getStoreView()랑 겹침
   @Override
   public AdminMembersDTO getProductView(String seq) {
      AdminMembersDTO adminMembersDTO = adminDAO.getProductView(seq);
      return adminMembersDTO;
   }
   //물품 조건검색
   @Override
   public List<ProductDTO> getSearchProductList(Map<String, String> map) {
      int viewNum = Integer.parseInt(map.get("viewNum"));
      
      int endNum = Integer.parseInt(map.get("pg"))*viewNum;
      int startNum = endNum-(viewNum-1);
      
      map.put("startNum", startNum+"");
      map.put("endNum", endNum+"");
      return adminDAO.getSearchProductList(map);
   }
   //물품 조건검색_페이징
   @Override
   public AdminBoardPaging getSearchProductBP(Map<String, String> map) {
      int viewNum = Integer.parseInt(map.get("viewNum"));
      
      int totalF = adminDAO.getTotalF(map);
      
      adminBoardPaging.setCurrentPage(Integer.parseInt(map.get("pg")));
      adminBoardPaging.setPageBlock(10);
      adminBoardPaging.setPageSize(viewNum);
      adminBoardPaging.setTotalA(totalF);
      
      adminBoardPaging.makePagingHTML();
      
      return adminBoardPaging;
   }
   //상점_클릭후_후기 총 개수
   @Override
   public int storeReviewTotalA(String id) {
      return adminDAO.storeReviewTotalA(id);
   }
   //상점_정보출력 후 물품 정렬
   @Override
   public List<ProductDTO> getStoreViewOrderby(Map<String, String> map) {
      return adminDAO.getStoreViewOrderby(map);
   }


    
//  [명경-신고]=========================================================================
  //A.신고 전체 리스트 출력
  @Override
  public List<StoreDTO> getComplainList(String pg, String viewNum) {
     int endNum = Integer.parseInt(pg)*Integer.parseInt(viewNum);
     int startNum = endNum-(Integer.parseInt(viewNum)-1);
     
     Map <String, Integer> map = new HashMap<String, Integer>();
     map.put("startNum", startNum);
     map.put("endNum", endNum); 
     List<StoreDTO> complainList= adminDAO.getComplainList(map );
     return complainList;
  }
  //A.신고 전체 리스트 페이징
  @Override
  public AdminBoardPaging adminComplainBP(String pg, String viewNum) {

	 //페이징 처리를 위해 전체 개수를 가져온다.
     int complainTotal = adminDAO.getComplainTotal();
     
     adminBoardPaging.setCurrentPage(Integer.parseInt(pg));
     adminBoardPaging.setPageBlock(10);
     adminBoardPaging.setPageSize(Integer.parseInt(viewNum));//위에endNum,startNum과 맞아야함
     adminBoardPaging.setTotalA(complainTotal);
     
     adminBoardPaging.makePagingHTML();
     return adminBoardPaging;
  }
  
  //B.신고 검색 리스트 출력
  @Override
  public List<ComplainDTO> searchReportedMember(Map<String, String> map) {
     int viewNum = Integer.parseInt(map.get("viewNum"));
     
     int endNum = Integer.parseInt(map.get("pg"))*viewNum;
     int startNum = endNum-(viewNum-1);
     
     map.put("startNum", startNum+"");
     map.put("endNum", endNum+"");
     return adminDAO.searchReportedMember(map);
  }
  //B.신고 검색 리스트 페이징
  @Override
  public AdminBoardPaging getSearchReportedBP(Map<String, String> map) {
	  //map: keyword, searchType, pg,viewNum
     
     int viewNum = Integer.parseInt(map.get("viewNum"));
     System.out.println("view:" +viewNum);
     int total = adminDAO.getTotalReportedMember(map);
     
     adminBoardPaging.setCurrentPage(Integer.parseInt(map.get("pg")));
     adminBoardPaging.setPageBlock(10);
     adminBoardPaging.setPageSize(viewNum);
     adminBoardPaging.setTotalA(total);
     
     adminBoardPaging.makePagingHTML();
     
     return adminBoardPaging;
  }
  
  //C. 신고 카테고리(게시글/댓글/상점/상품/리뷰) 검색 출력
  @Override
  public List<ComplainDTO> findWithdrawCate(Map<String, String> map) {
	 int viewNum = Integer.parseInt(map.get("viewNum"));
	     
     int endNum = Integer.parseInt(map.get("pg"))*viewNum;
     int startNum = endNum-(viewNum-1);
     
     map.put("startNum", startNum+"");
     map.put("endNum", endNum+"");
     return adminDAO.findWithdrawCate(map);
  }
  
  
  //C. 신고 카테고리(게시글/댓글/상점/상품/리뷰) 검색 페이징 처리
  @Override
  public AdminBoardPaging getCateBP(Map<String, String> map) {
	 //map:  withdrawCate, pg,viewNum
	  
	 int viewNum = Integer.parseInt(map.get("viewNum"));
     System.out.println("view:" +viewNum);
     int total = adminDAO.getCateBP(map);
     
     adminBoardPaging.setCurrentPage(Integer.parseInt(map.get("pg")));
     adminBoardPaging.setPageBlock(10);
     adminBoardPaging.setPageSize(viewNum);
     adminBoardPaging.setTotalA(total);
     
     adminBoardPaging.makePagingHTML();
     
     return adminBoardPaging;
  }
  //댓글, 리뷰 : 오른쪽 창에 정보 띄우기(페이지 
  //상품 신고, 상점 신고 : 해당 페이지 윈도우 팝업 창
  //게시글 신고 : 오른쪽 창에 정보 띄우기 + 윈도우 창 팝업
  //신고 댓글 내용(글번호, 댓글 작성자, 신고자, 댓글 내용) 가져오기
  @Override
  public CommentDTO getCommentContent(String comment_seq) {
     return adminDAO.getCommentContent(comment_seq) ;
  }
  //신고 상품 후기 내용(리뷰 번호, 리뷰 작성자, 신고자, 리뷰 내용)가져오기
  @Override
  public ReviewDTO getReviewContent(String review_seq) {
     return adminDAO.getReviewContent(review_seq);
  }
  
  //신고내용 처리상태 변경하기
  @Override
  public void solveComplain(Map<String, Integer> map) {
     adminDAO.solveComplain( map);
  }
  //신고 내역 블라인트처리(게시글/댓글/리뷰에 한해 가능)
  @Override
  public void blindComplain(String board_seq, String comment_seq, String review_seq, String thisIs) {
     adminDAO.blindComplain(board_seq, comment_seq, review_seq, thisIs);
  }

//  [명경-1:1문의]=========================================================================
    //A.1:1문의 전체 리스트 출력
    @Override
    public List<QnaDTO> getQnaList(String pg, String viewNum) {
       int endNum = Integer.parseInt(pg)*Integer.parseInt(viewNum);
       int startNum = endNum-(Integer.parseInt(viewNum)-1);
       
       Map <String, Integer> map = new HashMap<String, Integer>();
       map.put("startNum", startNum);
       map.put("endNum", endNum); 
       
       return adminDAO.getQnaList(map );
    }
    @Override
    public AdminBoardPaging qnaBP(String pg, String viewNum) {
       int qnaTotal = adminDAO.getQnaTotal();
       
       adminBoardPaging.setCurrentPage(Integer.parseInt(pg));
       adminBoardPaging.setPageBlock(10);
       adminBoardPaging.setPageSize(Integer.parseInt(viewNum));//위에endNum,startNum과 맞아야함
       adminBoardPaging.setTotalA(qnaTotal);
       
       adminBoardPaging.makePagingHTML();
       return adminBoardPaging;
    }
    //
    @Override
    public QnaDTO getQnaContent(int qna_seq) {
       return adminDAO.getQnaContent(qna_seq) ;
    }
    //
    @Override
    public void writeAnswer(Map<String, Object> map) {
       System.out.println(map);
       adminDAO.writeAnswer( map);
    }
    
    //조건검색 후 1:1 문의 내역 출력
    @Override
    public List<QnaDTO> getSearchQnaList(Map<String, String> map) {
       int viewNum = Integer.parseInt(map.get("viewNum"));
       
       int endNum =  Integer.parseInt(map.get("pg"))*viewNum;
       int startNum = endNum-(viewNum-1);
       
       map.put("startNum", startNum+"");
       map.put("endNum", endNum+"");
       return adminDAO.getSearchQnaList(map);
    }
    //조건검색 후 문의 내역 출력 페이징
    @Override
    public AdminBoardPaging getSearchqnaBP(Map<String, String> map) {
       int viewNum = Integer.parseInt(map.get("viewNum"));
       
       int totalG = adminDAO.totalG(map);
       
       adminBoardPaging.setCurrentPage(Integer.parseInt(map.get("pg")));
       adminBoardPaging.setPageBlock(10);
       adminBoardPaging.setPageSize(viewNum);
       adminBoardPaging.setTotalA(totalG);
       
       adminBoardPaging.makePagingHTML();
       
       return adminBoardPaging;
    }
    
//    [명경-탈퇴회원 관리]=========================================================================
    //탈퇴회원 리스트 출력
    @Override
    public List<WithdrawDTO> getWithdrawList(String pg, String viewNum) {
       int endNum = Integer.parseInt(pg)*Integer.parseInt(viewNum);
       int startNum = endNum-(Integer.parseInt(viewNum)-1);
       
       Map <String, Integer> map = new HashMap<String, Integer>();
       map.put("startNum", startNum);
       map.put("endNum", endNum); 
       
       return adminDAO.getWithdrawList(map );
    }
    //탈퇴회원 페이징처리
    @Override
    public AdminBoardPaging withdrawBP(String pg, String viewNum) {
       int withdrawTotal = adminDAO.getWithdrawTotal();
       
       adminBoardPaging.setCurrentPage(Integer.parseInt(pg));
       adminBoardPaging.setPageBlock(10);
       adminBoardPaging.setPageSize(Integer.parseInt(viewNum));//위에endNum,startNum과 맞아야함
       adminBoardPaging.setTotalA(withdrawTotal);
       
       adminBoardPaging.makePagingHTML();
       return adminBoardPaging;
    }
    //탈퇴회원 전체 수(for 페이징처리)
    @Override
    public Map <String, Integer> getWithdrawTotal() {
       int withdrawTotal =adminDAO.getWithdrawTotal();
       int lowFrequencyTotal =adminDAO.getWithdraw_lowFrequencyTotal();
       int rejoinTotal =adminDAO.getWithdraw_rejoinTotal();
       int lowContentsTotal =adminDAO.getWithdraw_lowContentsTotal();
       int protectInfoTotal =adminDAO.getWithdraw_protectInfoTotal();
       int lowBenefitTotal =adminDAO.getWithdraw_lowBenefitTotal();
       int othersTotal =adminDAO.getWithdraw_othersTotal();
       
       Map <String, Integer> map = new HashMap<String, Integer>();
       map.put("withdrawTotal", withdrawTotal);
       map.put("lowFrequencyTotal", lowFrequencyTotal);
       map.put("rejoinTotal", rejoinTotal);
       map.put("lowContentsTotal", lowContentsTotal);
       map.put("protectInfoTotal", protectInfoTotal);
       map.put("lowBenefitTotal", lowBenefitTotal);
       map.put("othersTotal", othersTotal); 
       
       return map;
    }
    
    //탈퇴회원 조건검색 리스트 출력
    @Override
    public List<WithdrawDTO> getSearchWithdrawList(Map<String, String> map) {
 	   //map:pg, viewNum,  keyword
       int viewNum = Integer.parseInt(map.get("viewNum"));
       int endNum =  Integer.parseInt(map.get("pg"))*viewNum;
       int startNum = endNum-(viewNum-1);
       
       map.put("startNum", startNum+"");
       map.put("endNum", endNum+"");
       return adminDAO.getSearchWithdrawList(map);
    }
    
    //탈퇴회원 조건검색 리스트 출력_페이징
    @Override
    public AdminBoardPaging getSearchWithdrawBP(Map<String, String> map) {
       int viewNum = Integer.parseInt(map.get("viewNum"));
       
       int totalH = adminDAO.totalH(map);
       
       adminBoardPaging.setCurrentPage(Integer.parseInt(map.get("pg")));
       adminBoardPaging.setPageBlock(10);
       adminBoardPaging.setPageSize(viewNum);
       adminBoardPaging.setTotalA(totalH);
       
       adminBoardPaging.makePagingHTML();
       
       return adminBoardPaging;
    }
    
//==============================================================================================
    

    //회원 영구정지
    @Override
    public void memberBlock(String id) {
       adminDAO.memberBlock(id);
    
    }
    //회원_영구정지 복구
    @Override
    public void memberReleaseBtn(String id) {
       adminDAO.memberReleaseBtn(id);
    }
    //물품_카테고리
    @Override
    public AdminProductDTO getCatagory(String seq) {
       AdminProductDTO adminProductDTO = adminDAO.getCatagory(seq);
       return adminProductDTO;      
    }
    
    //물품_상세보기 대분류
    @Override
    public String getCate_code(String cate_code) {
       String product_cate_code = adminDAO.getCate_code(cate_code);
       return product_cate_code;
    }
    
   
   

   
   //상점_상세조회_상품정보_판매중
   @Override
   public int sale_productSpan(String id) {
      return adminDAO.sale_productSpan(id);
   }
   //상점_상세조회_상품정보_예약중
   @Override
   public int reservation_productSpan(String id) {
      return adminDAO.reservation_productSpan(id);
   }
   //상점_상세조회_상품정보_판매완료
   @Override
   public int sold_productSpan(String id) {
      return adminDAO.sold_productSpan(id);
   }
   
   //총 구매한 물건 개수
   @Override
   public int totalBuyProduct(String id) {
      int totalBuyProduct = adminDAO.totalBuyProduct(id);
      return totalBuyProduct;
   }
   //신고당한 수
   @Override
   public int totalReported(String id) {
      int totalReported = adminDAO.totalReported(id);
      return totalReported;
   }



}
