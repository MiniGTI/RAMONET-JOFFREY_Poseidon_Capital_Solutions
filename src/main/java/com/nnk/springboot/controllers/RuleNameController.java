package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDto;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

/**
 * Controller class for the RuleName folder.
 * Page to see the list of all RuleName.
 * Page to update RuleName.
 * Page to delete RuleName.
 */
@AllArgsConstructor
@Controller
public class RuleNameController {
    
    private final RuleNameService ruleNameService;
    
    private final UserService userService;

    @ModelAttribute("ruleNameDto")
    public RuleNameDto ruleNameDto() {
        return new RuleNameDto();
    }
    
    /**
     * To get and display the RuleName list.
     *
     * @param principal the user authenticated.
     * @param model     to parse data to the view.
     * @return the list.html of the ruleName template folder.
     */
    @RequestMapping("/ruleName/list")
    public String ruleNameList(Principal principal, Model model) {
        List<RuleName> ruleNames = ruleNameService.getAll();
        String fullname = userService.getUserName(principal);
        model.addAttribute("ruleNames", ruleNames);
        model.addAttribute("fullname", fullname);
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
