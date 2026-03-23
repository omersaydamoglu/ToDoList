package com.nazlim.test2todolist.services;

import com.nazlim.test2todolist.auth.UserRepository;
import com.nazlim.test2todolist.dto.TodoRequest;
import com.nazlim.test2todolist.dto.TodoResponse;
import com.nazlim.test2todolist.entity.AppUser;
import com.nazlim.test2todolist.entity.Todo;
import com.nazlim.test2todolist.mapper.TodoMapper;
import com.nazlim.test2todolist.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repo;

    private final UserRepository userRepository;

    public TodoServiceImpl(TodoRepository repo,UserRepository userRepository) {
        this.repo = repo;
        this.userRepository=userRepository;
    }

    @Override
    public TodoResponse create(TodoRequest request) {
        AppUser currentUser = getCurrentUser();

        Todo entity = TodoMapper.toEntity(request);
        entity.setUser(currentUser);

        Todo saved = repo.save(entity);
        return TodoMapper.toResponse(saved);
    }
    @Override
    public List<TodoResponse> getAll() {
        AppUser currentUser = getCurrentUser();

        return repo.findByUser(currentUser)
                .stream()
                .map(TodoMapper::toResponse)
                .toList();
    }

    @Override
    public TodoResponse getById(Long id) {
        AppUser currentUser = getCurrentUser();

        Todo todo = repo.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found: " + id));

        return TodoMapper.toResponse(todo);
    }

    @Override
    public TodoResponse update(Long id, TodoRequest request) {
        AppUser currentUser = getCurrentUser();

        Todo existing = repo.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found: " + id));

        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setCompleted(request.isCompleted());

        Todo saved = repo.save(existing);
        return TodoMapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        AppUser currentUser = getCurrentUser();

        Todo existing = repo.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found: " + id));

        repo.delete(existing);
    }
    @Override
    public List<TodoResponse> getByStatus(boolean completed) {
        AppUser currentUser = getCurrentUser();

        return repo.findByUserAndCompleted(currentUser, completed)
                .stream()
                .map(TodoMapper::toResponse)
                .toList();

    }
    private AppUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }
}