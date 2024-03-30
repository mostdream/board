[개발환경]

        -Intellij IDEA Community 
        -Spring framework 5.3.20
        -JDK 11
	    -Apache Tomcat/9.0.87
        -MySQL Workbench 8.0.36 Community
	    -Git 2.44.0


[디렉토리 설명]

        *spring_framework_01 : 게시판

        *repository에 있는 내용들은 로컬에서 테스트 완료 후 업데이트했습니다.
        *현재까지는 JAVA 언어의 활용성을 보여드리기 위함이 목적이라 가장 기본적인 로직 위주로 구성되어져 있습니다.
         이 점 너른 양해 부탁드리며 추가로 업데이트 예정이니 참고 부탁드립니다. 


[DB Schema]

    create table board_table(
        id bigint primary key auto_increment,
        boardWriter varchar(50),
        boardPass varchar(20),
        boardTitle varchar(50),
        boardContents varchar(500),
        boardCreatedTime datetime default now(),
        boardHits int default 0,
        fileAttached int default 0
    );
    
    create table board_file_table
    (
        id	bigint auto_increment primary key,
        originalFileName varchar(100),
        storedFileName varchar(100),
        boardId bigint,
        constraint fk_board_file foreign key(boardId) references board_table(id) on delete cascade
    );
