package com.mostdream.board.repository;

import com.mostdream.board.dto.CommentDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {

    @Autowired
    private SqlSessionTemplate sql;

    public void save(CommentDTO commentDTO){
        sql.insert("Comment.save", commentDTO); //mapper 파일로 넘김.
    }

    public List<CommentDTO> findAll(Long boardId){
        return sql.selectList("Comment.findAll", boardId);
    }
}
