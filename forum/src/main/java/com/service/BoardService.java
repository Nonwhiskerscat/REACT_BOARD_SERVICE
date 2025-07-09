package com.service;

import com.entity.BoardVO;
import com.repository.BoardRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardVO> getCommonList() throws Exception {
        return boardRepository.listCommon();
    }

    public List<BoardVO> getBestList(int likeCut, int clickCut) throws Exception {
        return boardRepository.listBest(likeCut, clickCut);
    }

    public List<BoardVO> getUserList(String regCode) throws Exception {
        return boardRepository.listUser(regCode);
    }

    public BoardVO save(BoardVO board) throws Exception {
        return boardRepository.save(board);  // JPA 한 줄이면 INSERT 끝!
    }

    public BoardVO findById(String idx) throws Exception {
        return boardRepository.findById(idx)
                .orElseThrow(() -> new NoSuchElementException("해당 게시글 없음"));
    }

    public void deleteById(String idx) throws Exception {
        boardRepository.deleteById(idx);
    }
}