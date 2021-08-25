package com.rest.ai.myCallimo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateCategoryOffreType {
    private Integer offre_id;
    private Integer category;
    private Integer offre_type;
}
