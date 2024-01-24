package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDto;
import com.nnk.springboot.services.RuleNameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

/**
 * Controller class for the RuleName folder.
 * Page to see the list of all RuleName.
 * Page to update RuleName.
 * Page to delete RuleName.
 */
@Controller
public class RuleNameController {
    /**
     * Call the RuleNameService to apply business treatments before interact with the RuleNameRepository.
     */
    private final RuleNameService ruleNameService;
    
    /**
     * The class constructor.
     *
     * @param ruleNameService to apply business treatments and interact with the RuleNameRepository.
     */
    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }
    
    /**
     * Model to get data from add and update form.
     *
     * @return a new RuleName.
     */
    @ModelAttribute("ruleNameDto")
    public RuleNameDto ruleNameDto() {
        return new RuleNameDto();
    }
    
    /**
     * To get and display the RuleName list.
     *
     * @param model to parse data to the view.
     * @return the list.html of the ruleName template folder.
     */
    @RequestMapping("/ruleName/list")
    public String ruleNameList(Model model) {
        List<RuleName> ruleNames = ruleNameService.getAll();
        model.addAttribute("ruleNames", ruleNames);
        return "ruleName/list";
    }
    
    /**
     * To display the add page.
     *
     * @return the add.html of the ruleName template folder.
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm() {
        return "ruleName/add";
    }
    
    /**
     * To get data from the validate form into the add page.
     *
     * @param ruleNameDto the model to get data form the form inputs.
     * @param result      the validation of the form.
     * @param model       to get data from the view.
     * @return redirect on the ruleName/list page or stay on the add page if result has an error.
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleNameDto ruleNameDto, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            ruleNameService.save(ruleNameDto);
            model.addAttribute("ruleNameDto", ruleNameDto);
            return "redirect:/ruleName/list";
        }
        return "/ruleName/add";
    }
    
    /**
     * To display the update page.
     *
     * @param id    the id of the RuleName to update parsed.
     * @param model the RuleName's attributes to parse for the view.
     * @return the update.html of the ruleName template folder.
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(
            @PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.getById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }
    
    /**
     * To perform the update form of the page.
     *
     * @param id          the id of the RuleName parsed into the url of the page with showUpdateForm method.
     * @param ruleNameDto the Dto create after submit of the form to update the RuleName.
     * @param result      to catch any errors and return the related error message.
     * @param model       to get data from the form.
     * @return the list.html of the ruleName template folder or stay on the update page if result has an error.
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(
            @PathVariable("id") Integer id, @Valid RuleNameDto ruleNameDto, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            ruleNameDto.setId(id);
            ruleNameService.update(ruleNameDto);
            model.addAttribute("ruleNameDto", ruleNameDto);
            return "redirect:/ruleName/list";
        }
        return showUpdateForm(id, model);
    }
    
    /**
     * Manage the delete button.
     * To delete the RuleName.
     * Call the deleteById of the RuleNameService to delete the RuleName linked.
     *
     * @param id the id of the RuleName linked.
     * @return the list.html of the RuleName template folder.
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(
            @PathVariable("id") Integer id) {
        ruleNameService.deleteById(id);
        return "redirect:/ruleName/list";
    }
}
