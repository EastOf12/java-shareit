package ru.practicum.shareit.item.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.request.NewCommentRequest;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommentMapper {

    public static Comment mapToNewComment(Long userId, NewCommentRequest newCommentRequest, Item item, User author) {
        return new Comment(
                userId,
                newCommentRequest.getText(),
                item,
                author,
                LocalDateTime.now()
        );
    }

    public static CommentDto mapToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getItem(),
                comment.getAuthor().getName(),
                comment.getCreated()
        );
    }

}
