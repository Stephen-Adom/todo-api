package com.alaska.todoapi.customUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alaska.todoapi.entity.Todo;
import com.alaska.todoapi.entity.User;

@Service
public class ResponseBody {

    public Map<String, Object> listResponseBody(HttpStatus status, List<User> allUsers) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("status", status);
        body.put("data", allUsers);

        return body;
    }

    public Map<String, Object> responseBody(HttpStatus status, User user) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("status", status);
        body.put("data", user);

        return body;
    }

    public Map<String, Object> todoListResponseBody(HttpStatus status, List<Todo> allUsers) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("status", status);
        body.put("data", allUsers);

        return body;
    }

    public Map<String, Object> todoResponseBody(HttpStatus status, Todo todo) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("status", status);
        body.put("data", todo);

        return body;
    }
}
