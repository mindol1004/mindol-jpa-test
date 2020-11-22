package kiz.space.service;

import kiz.space.domain.Board;
import kiz.space.repository.BoardRepository;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board findById(long id) {
        return null;
    }

}
