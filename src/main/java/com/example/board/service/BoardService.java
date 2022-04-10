package com.example.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.board.domain.entity.BoardEntity;
import com.example.board.domain.repository.BoardRepository;
import com.example.board.dto.BoardDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BoardService {
	private BoardRepository boardRepository;

    @Transactional
    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public List<BoardDto> getBoardlist() {
        // List<BoardEntity> boardEntities = boardRepository.findAll();
    	List<BoardEntity> boardEntities = boardRepository.findAll( Sort.by(Sort.Direction.DESC, "id") );
        List<BoardDto> list = new ArrayList<>();
        for ( BoardEntity boardEntity : boardEntities) {
            BoardDto boardDTO = BoardDto.builder()
                    .id(boardEntity.getId())
                    .title(boardEntity.getTitle())
                    .content(boardEntity.getContent())
                    .writer(boardEntity.getWriter())
                    .createdDate(boardEntity.getCreatedDate())
                    .build();
            list.add(boardDTO);
        }
        return list;
    }

    @Transactional
    public BoardDto getPost(Long id) {
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityWrapper.get();
        BoardDto boardDTO = BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreatedDate())
                .build();
        return boardDTO;
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

}
