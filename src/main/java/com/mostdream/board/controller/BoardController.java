package com.mostdream.board.controller;


import com.mostdream.board.dto.BoardDTO;
import com.mostdream.board.dto.CommentDTO;
import com.mostdream.board.dto.PageDTO;
import com.mostdream.board.service.BoardService;
import com.mostdream.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor //의존성 주입을 받아야함
@RequestMapping("/board") // 보드로 주는 내용을 받는다
public class BoardController {
    private final BoardService boardService; //주입을 받는다.
    private final CommentService commentService;

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO){
        int saveResult = boardService.save(boardDTO); //글작성 성공 여부를 이 로직에서 판단
        if(saveResult > 0){
            return "redirect:/board/paging";
        } else {
            return "save";
        }
    }

    @GetMapping("/") //링크를 타고 왔기 때문에 이 어노테이션으로 지정. (위에서 @RequestMapping("/board")  <- 이렇게 지정해줬기 때문에 슬래쉬로 지정)
    public String findAll(Model model){ //DB에서 데이터를 가져가야한다면 Model이라는 객체를 model이라는 매게변수에 포함시켜야함.
        List<BoardDTO> boardDTOList = boardService.findAll(); //전체 목록은 List 객체로 받아옴.List 갹체에는 DTO 객체가 담겨있음.
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    @GetMapping //상세 페이지로 넘길 때는 파라미터만 있기 때문에 주소 링크가 별도로 없어도 됨
    public String findById(@RequestParam("id") Long id,
                           @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model){
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board",boardDTO);
        model.addAttribute("page",page);

        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList",commentDTOList);

        return "detail";
    }

    @GetMapping("/update")
    public String updateForm(@RequestParam("id") Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board",boardDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model){
        boardService.update(boardDTO);
        BoardDTO dto = boardService.findById(boardDTO.getId());
        model.addAttribute("board",dto);
        return "detail"; //수정 트랜잭션 후에 상세 페이지로.
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id){
        boardService.delete(id);
        return "redirect:/board/";
    }

    @GetMapping("/paging")
    public String paging(Model model,@RequestParam(value = "page", required = false, defaultValue = "1") int page){
        //value = "page" : 파라미터 이름 의미.
        // required = false는 필수 아님 의미.

        //해당 페이지에서 보여줄 글 목록을 디비로부터 불러와야함.
        List<BoardDTO> pagingList = boardService.pagingList(page); //목록이기 때문에 글 목록을 불러올  List 객체 필요.
        //System.out.println("pagingList = " + pagingList); //페이징 데이터가 잘 넘어가지는지 콘솔에 찍어보며 테스트.

        PageDTO pageDTO = boardService.pagingParam(page);
        model.addAttribute("boardList",pagingList);
        model.addAttribute("paging",pageDTO);

        return "paging";
    }
}
