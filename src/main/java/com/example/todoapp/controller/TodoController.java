package com.example.todoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.todoapp.model.TodoItem;
import com.example.todoapp.repository.TodoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

//    @GetMapping("/")
//    public String welcome() {
//        return "welcome to todo app";
//    }
    
    
    // GET all to-do items
    @GetMapping("/todos")
    public List<TodoItem> getAllTodos() {
        return todoRepository.findAll();
    }

    // POST create a new to-do item
    @PostMapping("/todos")
    public TodoItem createTodoItem(@RequestBody TodoItem todoItem) {
        return todoRepository.save(todoItem);
    }

    // PUT update an existing to-do item
    @PutMapping("/todos/{id}")
    public ResponseEntity<TodoItem> updateTodoItem(@PathVariable Long id, @RequestBody TodoItem todoItemDetails) {
        Optional<TodoItem> optionalTodoItem = todoRepository.findById(id);
        if (optionalTodoItem.isPresent()) {
            TodoItem todoItem = optionalTodoItem.get();
            todoItem.setDescription(todoItemDetails.getDescription());
            todoItem.setCompleted(todoItemDetails.isCompleted());
            final TodoItem updatedTodoItem = todoRepository.save(todoItem);
            return ResponseEntity.ok(updatedTodoItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE delete an existing to-do item
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable Long id) {
        Optional<TodoItem> optionalTodoItem = todoRepository.findById(id);
        if (optionalTodoItem.isPresent()) {
            todoRepository.delete(optionalTodoItem.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
