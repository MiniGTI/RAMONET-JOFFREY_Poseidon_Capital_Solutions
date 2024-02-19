package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.services.CurvePointService;
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
 * Controller class for the CurvePoint folder.
 * Page to see the list of all CurvePoint.
 * Page to update CurvePoints.
 * Page to delete CurvePoints.
 */
@AllArgsConstructor
@Controller
public class CurvePointController {
    
    private final CurvePointService curvePointService;
    
    private final UserService userService;

    @ModelAttribute("curvePointDto")
    public CurvePointDto curvePointDto() {
        return new CurvePointDto();
    }
    
    /**
     * To get and display the CurvePoint list.
     *
     * @param principal the user authenticated.
     * @param model     to parse data to the view.
     * @return the list.html of the curvePoint template folder.
     */
    @RequestMapping("/curvePoint/list")
    public String curvePointList(Principal principal, Model model) {
        List<CurvePoint> curvePoints = curvePointService.getAll();
        String fullname = userService.getUserName(principal);
        model.addAttribute("curvePoints", curvePoints);
        model.addAttribute("fullname", fullname);
        return "curvePoint/list";
    }
    
    /**
     * To display the add page.
     *
     * @return the add.html of the curvePoint template folder.
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm() {
        return "curvePoint/add";
    }
    
    /**
     * To get data from the validate form into the add page.
     *
     * @param curvePointDto the model to get data form the form inputs.
     * @param result        the validation of the form.
     * @param model         to get data from the view.
     * @return redirect on the curvePoint/list page or stay on the add page if result has an error.
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePointDto curvePointDto, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            curvePointService.save(curvePointDto);
            model.addAttribute("curvePointDto", curvePointDto);
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }
    
    /**
     * To display the update page.
     *
     * @param id    the id of the CurvePoint to update parsed.
     * @param model the CurvePoint's attributes to parse for the view.
     * @return the update.html of the curvePoint template folder.
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(
            @PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curvePointService.getById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }
    
    /**
     * To perform the update form of the page.
     *
     * @param id            the id of the CurvePoint parsed into the url of the page with showUpdateForm method.
     * @param curvePointDto the Dto create after submit of the form to update the CurvePoint.
     * @param result        to catch any errors and return the related error message.
     * @param model         to get data from the form.
     * @return the list.html of the curvePoint template folder or stay on the update page if result has an error.
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(
            @PathVariable("id") Integer id, @Valid CurvePointDto curvePointDto, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            curvePointDto.setId(id);
            curvePointService.update(curvePointDto);
            model.addAttribute("curvePointDto", curvePointDto);
            return "redirect:/curvePoint/list";
        }
        return showUpdateForm(id, model);
    }
    
    /**
     * Manage the delete button.
     * To delete the CurvePoint.
     * Call the deleteById of the curvePointService to delete the CurvePoint linked.
     *
     * @param id the id of the CurvePoint linked.
     * @return the list.html of the curvePoint template folder.
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(
            @PathVariable("id") Integer id) {
        curvePointService.deleteById(id);
        
        return "redirect:/curvePoint/list";
    }
}
