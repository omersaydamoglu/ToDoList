package com.nazlim.test2todolist.mapper;

import com.nazlim.test2todolist.dto.TodoRequest;
import com.nazlim.test2todolist.dto.TodoResponse;
import com.nazlim.test2todolist.entity.Todo;

public class TodoMapper {

    public static Todo toEntity(TodoRequest req) {
        Todo t = new Todo();
        t.setTitle(req.getTitle());
        t.setDescription(req.getDescription());
        t.setCompleted(req.isCompleted());
        return t;
    }

    public static TodoResponse toResponse(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isCompleted()
        );
    }
}