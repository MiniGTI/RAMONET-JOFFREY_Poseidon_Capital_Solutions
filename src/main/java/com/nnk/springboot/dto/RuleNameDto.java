package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto object to parse data from forms of RuleName pages.
 * ruleName/add to save a new RuleName, managed by the validate method in the RuleNameController.
 * ruleName/update to update an existing RuleName, managed by the updateRuleName method in the RuleNameController.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleNameDto {
    
    private Integer id;
    private String name;
    private String description;
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;
}
