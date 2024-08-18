package com.example.accessingdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RequestMapping(path = "/todo")
public class TodoController {
    @Autowired
    private TodoRepository todoRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addTodo(@RequestParam String name, @RequestParam boolean isCompleted) {
        Todo newTodo = new Todo();
        newTodo.setName(name);
        newTodo.setCompleted(isCompleted);
        todoRepository.save(newTodo);
        return "Saved";
    }

    @PostMapping(path = "/delete")
    public @ResponseBody String deleteTodo(@RequestParam Integer id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return "Deleted";
        } else {
            return "Id is not present";
        }
    }

    @PostMapping(path="/edit")
    public @ResponseBody String editTodo(@RequestParam Integer id,@RequestParam String name){
        if(todoRepository.findById(id).isPresent()){
            Todo newTodo = todoRepository.findById(id).get();
            newTodo.setName(name);
            todoRepository.save(newTodo);
            return "edited";
        }else{
            return "Id is not present";
        }
    }

    @PostMapping(path="/changeComplete")
    public @ResponseBody String changeCompleteTodo(@RequestParam Integer id){
        if(todoRepository.findById(id).isPresent()){
            Todo newTodo = todoRepository.findById(id).get();
            Boolean oldComplete = newTodo.isCompleted();
            newTodo.setCompleted(!oldComplete);
            todoRepository.save(newTodo);
            return "changed";
        }else{
            return "Id is not present";
        }
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
}
