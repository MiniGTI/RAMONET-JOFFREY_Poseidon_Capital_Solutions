package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.services.RatingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for the Rating folder.
 * Page to see the list of all Rating.
 * Page to update Ratings.
 * Page to delete Ratings.
 */
@Controller
public class RatingController {
    
    /**
     * Call the RatingService to apply business treatments before interact with the RatingRepository.
     */
    private final RatingService ratingService;
    
    /**
     * The class constructor.
     *
     * @param ratingService to apply business treatments and interact with the RatingRepository.
     */
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    
    /**
     * Model to get data from add and update form.
     *
     * @return a new Rating.
     */
    @ModelAttribute("ratingDto")
    public RatingDto ratingDto() {
        return new RatingDto();
    }
    
    /**
     * To get and display the Rating list.
     *
     * @param model to parse data to the view.
     * @return the list.html of the rating template folder.
     */
    @RequestMapping("/rating/list")
    public String ratingList(Model model) {
        List<Rating> ratings = ratingService.getAll();
        model.addAttribute("ratings", ratings);
        return "rating/list";
    }
    
    /**
     * To display the add page.
     *
     * @return the add.html of the rating template folder.
     */
    @GetMapping("/rating/add")
    public String addRatingForm() {
        return "rating/add";
    }
    
    /**
     * To get data from the validate form into the add page.
     *
     * @param ratingDto the model to get data form the form inputs.
     * @param result    the validation of the form.
     * @param model     to get data from the view.
     * @return redirect on the rating/list page or stay on the add page if result has an error.
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid RatingDto ratingDto, BindingResult result, Model model) {
        
        if(!result.hasErrors()) {
            ratingService.save(ratingDto);
            model.addAttribute("ratingDto", ratingDto);
            return "redirect:/rating/list";
        }
        return "rating/add";
    }
    
    /**
     * To display the update page.
     *
     * @param id    the id of the Rating to update parsed.
     * @param model the Rating's attributes to parse for the view.
     * @return the update.html of the rating template folder.
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(
            @PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.getById(id);
        
        model.addAttribute("rating", rating);
        return "rating/update";
    }
    
    /**
     * To perform the update form of the page.
     *
     * @param id        the id of the Rating parsed into the url of the page with showUpdateForm method.
     * @param ratingDto the Dto create after submit of the form to update the Rating.
     * @param result    to catch any errors and return the related error message.
     * @param model     to get data from the form.
     * @return the list.html of the Rating template folder or stay on the update page if result has an error.
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(
            @PathVariable("id") Integer id, @Valid RatingDto ratingDto, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            ratingDto.setId(id);
            ratingService.update(ratingDto);
            model.addAttribute("ratingDto", ratingDto);
            return "redirect:/rating/list";
        }
        return showUpdateForm(id, model);
    }
    
    /**
     * Manage the delete button.
     * To delete the Rating.
     * Call the deleteById of the RatingService to delete the Rating linked.
     *
     * @param id the id of the Rating linked.
     * @return the list.html of the rating template folder.
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(
            @PathVariable("id") Integer id) {
        ratingService.deleteById(id);
        return "redirect:/rating/list";
    }
}
