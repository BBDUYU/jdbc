package days05.board.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.util.DBConn;

import days05.board.domain.BoardDTO;
import days05.board.service.BoardService;

public class BoardController {

	private BoardService service=null;
	private Scanner scanner=null;
	private int selectedNumber; // 메뉴 선택후 저장할 변수

	public BoardController() {
		super();
		this.scanner = new Scanner(System.in);
	}

	// 생성자 DI
	public BoardController(BoardService service) {
		this();
		this.service = service;
	}

	public void boardStart() {
		while (true) {  
			메뉴출력();
			메뉴선택();
			메뉴처리();
		} // while
	}

	public enum Board {
		NEW,        // 새글
		LIST,       // 목록
		VIEW,       // 보기
		EDIT,       // 수정
		DELETE,     // 삭제
		SEARCH,     // 검색
		EXIT        // 종료
	}

	private void 메뉴출력() {
		String [] menu = {"새글","목록","보기","수정","삭제","검색","종료"};
		System.out.println("[ 메뉴 ]");
		for (int i = 0; i < menu.length; i++) {
			System.out.printf("%d. %s\t", i+1, menu[i]);
		}
		System.out.println();
	}

	private void 메뉴선택() {
		System.out.print("> 메뉴 선택하세요 ? ");
		this.selectedNumber = this.scanner.nextInt();      
		this.scanner.nextLine(); //   \r\n 제거
	}


	private void 메뉴처리() {
		Board selectedMenu = Board.values()[selectedNumber - 1];

		switch (selectedMenu) {
		case NEW:// 새글   O
			새글쓰기();
			break;
		case LIST:// 목록  O
			목록보기();
			break;
		case VIEW:// 보기  O
			상세보기();
			break;
		case EDIT:// 수정  O
			수정하기();
			break;
		case DELETE:// 삭제  O
			삭제하기();
			break;
		case SEARCH:// 검색
			검색하기();
			break;
		case EXIT:// 종료   
			exit();
			break; 
		} // switch
		일시정지();
	}

	private void 일시정지() {

		System.out.println(" \t\t 계속하려면 엔터치세요.");
		try {
			System.in.read();
			System.in.skip(System.in.available()); // 13, 10
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}


	private void exit() {
		DBConn.close();
		System.out.println("\t\t\t  프로그램 종료!!!");
		System.exit(-1);
	}

	private void 검색하기() {
	    System.out.println("> 검색조건을 입력 (t: 제목, c: 내용, w: 작성자, tc: 제목+내용)");
	    String searchCondition = this.scanner.nextLine();

	    System.out.print("> 검색어를 입력 ? ");
	    String searchWord = this.scanner.nextLine();

	    // 서비스 호출
	    List<BoardDTO> list = this.service.searchService(searchCondition, searchWord);

	    // 결과 출력
	    System.out.println("\t\t\t\t[ 검색 결과 ]");
	    System.out.println("-------------------------------------------------------------------------");
	    System.out.printf("%s\t%s\t%s\t%s\t%s\n", 
	            "글번호","글제목","글쓴이","작성일","조회수");
	    System.out.println("-------------------------------------------------------------------------");

	    if (list == null || list.isEmpty()) {
	        System.out.println("\t\t> 검색 결과가 없습니다.");
	    } else {
	        list.forEach(dto -> {
	            System.out.printf("%d\t%s  %s\t%s\t%d\n",
	                    dto.getSeq(),
	                    dto.getTitle(),
	                    dto.getWriter(),
	                    dto.getWritedate(),
	                    dto.getReaded());
	        });
	    }
	}


	private void 삭제하기() {
		System.out.println("> 삭제할 게시글 번호를 입력 ?");
		int seq=this.scanner.nextInt();

		int rowCount=this.service.deleteService(seq);
		if(rowCount==1) {
			System.out.println("> 게시글 삭제 성공");
			목록보기();
		}else {
			System.out.println("> 게시글 삭제 실패");			
		}
	}

	private void 수정하기() {
		// 글 상세보기 페이지 - [수정]버튼을 클릭
		// 글 수정 페이지
		//  수정할 컬럼 값을 입력
		//  예) 제목, 내용 등
		//  	ㄴ	[저장]버튼을 클릭
		// 글 상세보기 페이지
		System.out.println("> 수정할 게시글 번호를 입력 ?");
		int seq=this.scanner.nextInt();

		//문제점2) 조회수증가필요 X
		BoardDTO dto=this.service.viewService(seq);

		// 출력 View X 컨트롤러가 바로 출력
		System.out.println("\tㄱ. 글번호 : " + seq );
		System.out.println("\tㄴ. 작성자 : " + dto.getWriter() );
		System.out.println("\tㄷ. 조회수 : " + dto.getReaded() );
		System.out.println("\tㄹ. 글제목 : " + dto.getTitle() );
		System.out.println("\tㅁ. 글내용 : " + dto.getContent() );
		System.out.println("\tㅂ. 작성일 : " + dto.getWritedate() );

		if("문종범".equals(dto.getWriter())) {
			System.out.print("\t\n [수정] [삭제]");			
		}
		System.out.println(" [답글] [목록]");
		scanner.nextLine();
		//이메일, 제목, 내용 정보수정
		System.out.print("> 1. 이메일 입력 ? ");
		String email = scanner.nextLine();  
		System.out.print("> 2. 제목 입력 ? ");
		String title = scanner.nextLine();
		System.out.print("> 3. 내용 입력 ? ");
		String content = scanner.nextLine();
		//그냥 엔터친경우 이전값으로 update
		if(email.trim().equals(""))email=dto.getEmail();
		if(title.trim().equals(""))title=dto.getTitle();
		if(content.trim().equals(""))content=dto.getContent();
		
		//수정할 내용을 갖고있음
		dto = BoardDTO.builder()
				.seq(seq)
				.email(email)
				.title(title)
				.content(content)
				.build();
		
		int rowCount=this.service.updateService(dto);
		if(rowCount==1) {
			System.out.println("> 게시글 수정 성공");
			상세보기(); //조회수 증가 X 처리 필요
		}
		

	}

	//BoardController.상세보기
	// -> BoardService 상세보기관련된 메서드
	// -> BoardDAO 	   상세보기관련된 메서드

	private void 상세보기() {
		//글 목록에서 보고자하는 게시글의 제목을 클릭하면 출력
		System.out.println("> 보고자 하는 게시글 번호를 입력 ?");
		int seq=this.scanner.nextInt();

		BoardDTO dto = this.service.viewService(seq);
		if(dto==null) {
			System.out.println("> 보고자 하는 게시글이 존재하지 않습니다");
			return ;
		}
		// 출력 View X 컨트롤러가 바로 출력
		System.out.println("\tㄱ. 글번호 : " + seq );
		System.out.println("\tㄴ. 작성자 : " + dto.getWriter() );
		System.out.println("\tㄷ. 조회수 : " + dto.getReaded() );
		System.out.println("\tㄹ. 글제목 : " + dto.getTitle() );
		System.out.println("\tㅁ. 글내용 : " + dto.getContent() );
		System.out.println("\tㅂ. 작성일 : " + dto.getWritedate() );

		if("문종범".equals(dto.getWriter())) {
			System.out.print("\t\n [수정] [삭제]");			
		}
		System.out.println(" [답글] [목록]");
	}
	//페이징 처리 X
	private void 목록보기() {
		List<BoardDTO> list= this.service.selectService();
		//MVC
		System.out.println("\t\t\t  게시판");
		System.out.println("-------------------------------------------------------------------------");
		System.out.printf("%s\t%-40s\t%s\t%-10s\t%s\n", 
				"글번호","글제목","글쓴이","작성일","조회수");
		System.out.println("-------------------------------------------------------------------------");

		if (list == null) {  // 게시글 존재 X
			System.out.println("\t\t> 게시글 존재 X");   
		} else {
			list.forEach(dto->{
				System.out.printf("%d\t%-30s  %s\t%-10s\t%d\n",
						dto.getSeq(), 
						dto.getTitle(),
						dto.getWriter(),
						dto.getWritedate(),
						dto.getReaded());
			});
			/*
			 *    Iterator<BoardDTO> ir = list.iterator();
         while (ir.hasNext()) {
            BoardDTO dto =  ir.next();            
            System.out.printf("%d\t%-30s  %s\t%-10s\t%d\n",
                  dto.getSeq(), 
                  dto.getTitle(),
                  dto.getWriter(),
                  dto.getWritedate(),
                  dto.getReaded());
         } // while
			 * */
		}

	}

	private void 새글쓰기() {
		System.out.print("> writer, pwd, email, title, tag, content 입력 ? ");
		String datas[] = this.scanner.nextLine().split("\\s*,\\s*");
		String writer=datas[0]; 
		String pwd=datas[1];
		String email=datas[2];
		String title=datas[3];
		int tag=Integer.parseInt(datas[4]);
		String content=datas[5];

		BoardDTO dto=new BoardDTO().builder()
				.writer(writer)
				.pwd(pwd)
				.email(email)
				.title(title)
				.tag(tag)
				.content(content)
				.build();

		int rowCount=this.service.insertService(dto);
		if(rowCount==1) {
			System.out.println("> 새 글쓰기 성공");
			목록보기();
		} 
	}
}
