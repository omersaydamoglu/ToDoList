package com.nazlim.test2todolist.services;

import com.nazlim.test2todolist.auth.UserRepository;
import com.nazlim.test2todolist.dto.TodoRequest;
import com.nazlim.test2todolist.dto.TodoResponse;
import com.nazlim.test2todolist.entity.AppUser;
import com.nazlim.test2todolist.entity.Todo;
import com.nazlim.test2todolist.repository.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    private static final String TEST_USERNAME = "nazlim";

    @Mock
    private TodoRepository repo;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TodoServiceImpl service;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(TEST_USERNAME, null, List.of())
        );
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private AppUser testUser() {
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername(TEST_USERNAME);
        return user;
    }

    @Test
    void create_savesTodoForCurrentUser() {
        AppUser user = testUser();
        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(user));

        TodoRequest request = new TodoRequest();
        request.setTitle("Alışveriş");
        request.setDescription("Süt al");
        request.setDueDate(LocalDate.of(2026, 12, 31));
        request.setPriority("high");

        Todo saved = new Todo();
        saved.setId(1L);
        saved.setTitle("Alışveriş");
        saved.setDescription("Süt al");
        saved.setDueDate(LocalDate.of(2026, 12, 31));
        saved.setPriority("high");
        saved.setUser(user);

        when(repo.save(any(Todo.class))).thenReturn(saved);

        TodoResponse response = service.create(request);

        assertEquals("Alışveriş", response.getTitle());
        assertEquals("high", response.getPriority());
        assertEquals(LocalDate.of(2026, 12, 31), response.getDueDate());
        verify(repo).save(any(Todo.class));
    }

    @Test
    void getAll_returnsOnlyCurrentUsersTodos() {
        AppUser user = testUser();
        Todo todo = new Todo();
        todo.setId(2L);
        todo.setTitle("Rapor yaz");
        todo.setUser(user);

        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(user));
        when(repo.findByUser(user)).thenReturn(List.of(todo));

        List<TodoResponse> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("Rapor yaz", result.get(0).getTitle());
    }

    @Test
    void getAll_throws401_whenNotAuthenticated() {
        SecurityContextHolder.clearContext();

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.getAll());

        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
    }

    @Test
    void getById_returnsTodo_whenOwnedByCurrentUser() {
        AppUser user = testUser();
        Todo todo = new Todo();
        todo.setId(5L);
        todo.setTitle("Ders çalış");
        todo.setUser(user);

        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(user));
        when(repo.findByIdAndUser(5L, user)).thenReturn(Optional.of(todo));

        TodoResponse response = service.getById(5L);

        assertEquals(5L, response.getId());
        assertEquals("Ders çalış", response.getTitle());
    }

    @Test
    void getById_throws404_whenNotFoundOrNotOwned() {
        AppUser user = testUser();
        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(user));
        when(repo.findByIdAndUser(99L, user)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.getById(99L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void update_updatesAllFieldsIncludingDueDateAndPriority() {
        AppUser user = testUser();
        Todo existing = new Todo();
        existing.setId(3L);
        existing.setTitle("Eski başlık");
        existing.setUser(user);

        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(user));
        when(repo.findByIdAndUser(3L, user)).thenReturn(Optional.of(existing));
        when(repo.save(existing)).thenReturn(existing);

        TodoRequest request = new TodoRequest();
        request.setTitle("Yeni başlık");
        request.setDescription("Yeni açıklama");
        request.setCompleted(true);
        request.setDueDate(LocalDate.of(2027, 1, 1));
        request.setPriority("medium");

        TodoResponse response = service.update(3L, request);

        assertEquals("Yeni başlık", response.getTitle());
        assertEquals("Yeni açıklama", response.getDescription());
        assertTrue(response.isCompleted());
        assertEquals(LocalDate.of(2027, 1, 1), response.getDueDate());
        assertEquals("medium", response.getPriority());
    }

    @Test
    void update_throws404_whenNotOwnedByCurrentUser() {
        AppUser user = testUser();
        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(user));
        when(repo.findByIdAndUser(42L, user)).thenReturn(Optional.empty());

        TodoRequest request = new TodoRequest();
        request.setTitle("Fark etmez");

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.update(42L, request));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void delete_deletesTodo_whenOwnedByCurrentUser() {
        AppUser user = testUser();
        Todo todo = new Todo();
        todo.setId(7L);
        todo.setUser(user);

        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(user));
        when(repo.findByIdAndUser(7L, user)).thenReturn(Optional.of(todo));

        service.delete(7L);

        verify(repo).delete(todo);
    }

    @Test
    void delete_throws404_whenNotOwnedByCurrentUser() {
        AppUser user = testUser();
        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(user));
        when(repo.findByIdAndUser(13L, user)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.delete(13L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void getByStatus_returnsOnlyMatchingTodos() {
        AppUser user = testUser();
        Todo completedTodo = new Todo();
        completedTodo.setId(9L);
        completedTodo.setCompleted(true);
        completedTodo.setUser(user);

        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(user));
        when(repo.findByUserAndCompleted(user, true)).thenReturn(List.of(completedTodo));

        List<TodoResponse> result = service.getByStatus(true);

        assertEquals(1, result.size());
        assertTrue(result.get(0).isCompleted());
    }
}
