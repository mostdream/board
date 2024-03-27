package com.mostdream.board.repository;

import com.mostdream.board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final SqlSessionTemplate sql; //mybatis에서 제공하는 클래스. 자바 클래스와 wrapper간의 연결을 해주는 역할.
    public int save(BoardDTO boardDTO){
        return sql.insert("Board.save",boardDTO); //boardDTO는 프런트단에서 받아온 값을 전달하기 위한 객체.
        //sql은 private final SqlSessionTemplate sql 문장에서 주입받은 객체.
        //Board.save가 가리키는 것은 BoardMapper.xml에 작성한 <mapper namespace="Board">.
    }

    public List<BoardDTO> findAll(){
        return sql.selectList("Board.findAll");
    }

    public BoardDTO findById(Long id){
        return sql.selectOne("Board.findById", id); //하나일 경우에는 selectOne 메소드를 사용. 여러개일 경우에는 selectList.
    }

    public void updateHits(Long id){
        sql.update("Board.updateHits",id);
    }

    public void update(BoardDTO boardDTO){
        sql.update("Board.update", boardDTO);
    }

    public void delete(Long id){
        sql.delete("Board.delete",id);
    }
}
