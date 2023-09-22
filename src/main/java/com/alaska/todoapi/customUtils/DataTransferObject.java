package com.alaska.todoapi.customUtils;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataTransferObject {
    private Long id;
    private String title;
    private String description;
    private Date dueDate;
    private Boolean completed;
    private Date createdAt;
    private Date updatedAt;
    private Long user_id;
}
