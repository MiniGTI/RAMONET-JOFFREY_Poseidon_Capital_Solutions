package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.services.BidListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;


/**
 * Controller class for the BidList folder.
 * Page to see the list of all BidList.
 * Page to update BidLists.
 * Page to delete BidLists.
 */
@Controller
public class BidListController {
    /**
     * Call the BidListService to apply business treatments before interact with the BidListRepository.
     */
    private final BidListService bidListService;
    
    /**
     * The class constructor.
     *
     * @param bidListService to apply business treatments and interact with the BidListRepository.
     */
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }
    
    /**
     * Model to get data from add and update form.
     *
     * @return a new BidList.
     */
    @ModelAttribute("BidListDto")
    public BidListDto bidListDto() {
        return new BidListDto();
    }
    
    /**
     * To get and display the BidList list.
     *
     * @param model to parse data to the view.
     * @return the list.html of the bidList template folder.
     */
    @RequestMapping("/bidList/list")
    public String bidListList(Model model) {
        List<BidList> bidLists = bidListService.getAll();
        model.addAttribute("bidLists", bidLists);
        return "bidList/list";
    }
    
    /**
     * To display the add page.
     *
     * @return the add.html of the bidList template folder.
     */
    @GetMapping("/bidList/add")
    public String addBidForm() {
        return "bidList/add";
    }
    
    /**
     * To get data from the validate form into the add page.
     *
     * @param bidListDto the model to get data form the form inputs.
     * @param result     the validation of the form.
     * @param model      to get data from the view.
     * @return redirect on the bidList/list page or stay on the add page if result has an error.
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid
                           @ModelAttribute("bidListDto")
                           BidListDto bidListDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "redirect:/bidList/add";
        }
        bidListService.save(bidListDto);
        model.addAttribute("bidListDto", bidListDto);
        return "redirect:/bidList/list";
    }
    
    /**
     * To display the update page.
     *
     * @param id    the id of the BidList to update parsed.
     * @param model the BidList's attributes to parse for the view.
     * @return the update.html of the bidList template folder.
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(
            @PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.getById(id);
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }
    
    /**
     * To perform the update form of the page.
     *
     * @param id         the id of the BidList parsed into the url of the page with showUpdateForm method.
     * @param bidListDto the Dto create after submit of the form to update the BidList.
     * @param result     to catch any errors and return the related error message.
     * @param model      to get data from the form.
     * @return the list.html of the bidList template folder or stay on the update page if result has an error.
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(
            @PathVariable("id") Integer id, @Valid
    @ModelAttribute("BidListDto")
    BidListDto bidListDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "redirect:/bidList/update/{id}";
        }
        
        bidListDto.setId(id);
        bidListService.update(bidListDto);
        model.addAttribute("bidListDto", bidListDto);
        return "redirect:/bidList/list";
    }
    
    /**
     * Manage the delete button.
     * To delete the BidList.
     * Call the deleteById of the BidListService to delete the BidList linked.
     *
     * @param id the id of the BidList linked.
     * @return the list.html of the bidList template folder.
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(
            @PathVariable("id") Integer id) {
        bidListService.deleteById(id);
        return "redirect:/bidList/list";
    }
}
