package com.nnk.springboot.dto;

import jakarta.validation.constraints.Size;
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
    
    private int id;
    @Size(
            max = 125,
            message = "Name can not exceed 125 characters.")
    private String name;
    @Size(
            max = 125,
            message = "Description can not exceed 125 characters.")
    private String description;
    @Size(
            max = 125,
            message = "Json can not exceed 125 characters.")
    private String json;
    @Size(
            max = 512,
            message = "Template can not exceed 512 characters.")
    private String template;
    @Size(
            max = 125,
            message = "Sql Str can not exceed 125 characters.")
    private String sqlStr;
    @Size(
            max = 125,
            message = "Sql Part can not exceed 125 characters.")
    private String sqlPart;
}
