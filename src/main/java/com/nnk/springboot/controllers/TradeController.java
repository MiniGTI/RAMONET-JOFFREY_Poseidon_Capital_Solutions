package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.services.TradeService;
import com.nnk.springboot.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;

/**
 * Controller class for the Trade folder.
 * Page to see the list of all Trade.
 * Page to update Trades.
 * Page to delete Trades.
 */
@Controller
public class TradeController {
    
    private final TradeService tradeService;
    
    private final UserService userService;
    
    public TradeController(TradeService tradeService, UserService userService) {
        this.tradeService = tradeService;
        this.userService = userService;
    }
    
    @ModelAttribute("tradeDto")
    public TradeDto tradeDto() {
        return new TradeDto();
    }
    
    /**
     * To get and display the Trade list.
     *
     * @param principal the user authenticated.
     * @param model     to parse data to the view.
     * @return the list.html of the trade template folder.
     */
    @RequestMapping("/trade/list")
    public String home(Principal principal, Model model) {
        List<Trade> trades = tradeService.getAll();
        String fullname = userService.getUserName(principal);
        
        model.addAttribute("trades", trades);
        model.addAttribute("fullname", fullname);
        return "trade/list";
    }
    
    /**
     * To display the add page.
     *
     * @return the add.html of the trade template folder.
     */
    @GetMapping("/trade/add")
    public String addUser() {
        return "trade/add";
    }
    
    /**
     * To get data from the validate form into the add page.
     *
     * @param tradeDto the model to get data form the form inputs.
     * @param result   the validation of the form.
     * @param model    to get data from the view.
     * @return redirect on the trade/list page or stay on the add page if result has an error.
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid TradeDto tradeDto, BindingResult result, Model model) {
        
        if(!result.hasErrors()) {
            tradeService.save(tradeDto);
            model.addAttribute("tradeDto", tradeDto);
            return "redirect:/trade/list";
        }
        return "trade/add";
    }
    
    /**
     * To display the update page.
     *
     * @param id    the id of the Trade to update parsed.
     * @param model the Trade's attributes to parse for the view.
     * @return the update.html of the trade template folder.
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(
            @PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.getById(id);
        
        model.addAttribute("trade", trade);
        return "trade/update";
    }
    
    /**
     * To perform the update form of the page.
     *
     * @param id       the id of the Trade parsed into the url of the page with showUpdateForm method.
     * @param tradeDto the Dto create after submit of the form to update the Trade.
     * @param result   to catch any errors and return the related error message.
     * @param model    to get data from the form.
     * @return the list.html of the Trade template folder or stay on the update page if result has an error.
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(
            @PathVariable("id") Integer id, @Valid TradeDto tradeDto, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            
            tradeDto.setId(id);
            tradeService.update(tradeDto);
            model.addAttribute("tradeDto", tradeDto);
            return "redirect:/trade/list";
        }
        return showUpdateForm(id, model);
    }
    
    /**
     * Manage the delete button.
     * To delete the Trade.
     * Call the deleteById of the TradeService to delete the Trade linked.
     *
     * @param id the id of the Trade linked.
     * @return the list.html of the trade template folder.
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(
            @PathVariable("id") Integer id) {
        tradeService.deleteById(id);
        return "redirect:/trade/list";
    }
}
