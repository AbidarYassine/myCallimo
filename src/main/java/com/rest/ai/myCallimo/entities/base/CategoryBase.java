package com.rest.ai.myCallimo.entities.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryBase implements Serializable {
    private String offer_category;
}
