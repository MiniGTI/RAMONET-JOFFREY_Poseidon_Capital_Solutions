package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.NewUserDto;
import com.nnk.springboot.dto.UpdateUserDto;
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


/**
 * Controller class for the User folder.
 * Page to see the list of all User.
 * Page to update User.
 * Page to delete User.
 */
@AllArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @ModelAttribute("newUserDto")
    public NewUserDto newUserDto() {
        return new NewUserDto();
    }

    @ModelAttribute("updateUserDto")
    public UpdateUserDto updateUserDto() {
        return new UpdateUserDto();
    }

    /**
     * To get and display the User list.
     *
     * @param model to parse data to the view.
     * @return the list.html of the user template folder.
     */
    @RequestMapping("/user/list")
    public String userList(Model model) {
        model.addAttribute("users", userService.getAll());
        return "user/list";
    }

    /**
     * To display the add page.
     *
     * @return the add.html of the user template folder.
     */
    @GetMapping("/user/add")
    public String addUser() {
        return "user/add";
    }

    /**
     * To get data from the validate form into the add page.
     *
     * @param newUserDto the model to get data form the form inputs.
     * @param result     the validation of the form.
     * @param model      to get data from the view.
     * @return redirect on the user/list page or stay on the add page if result has an error.
     */
    @PostMapping("/user/validate")
    public String validate(@Valid NewUserDto newUserDto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            userService.save(newUserDto);
            model.addAttribute("newUserDto", newUserDto);
            return "redirect:/user/list";
        }
        return "user/add";
    }

    /**
     * To display the update page.
     *
     * @param id    the id of the User to update parsed.
     * @param model the User's attributes to parse for the view.
     * @return the update.html of the user template folder.
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(
            @PathVariable("id") Integer id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * To perform the update form of the page.
     *
     * @param id            the id of the User parsed into the url of the page with showUpdateForm method.
     * @param updateUserDto the Dto create after submit of the form to update the User.
     * @param result        to catch any errors and return the related error message.
     * @param model         to get data from the form.
     * @return the list.html of the user template folder or stay on the update page if result has an error.
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(
            @PathVariable("id") Integer id, @Valid UpdateUserDto updateUserDto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            updateUserDto.setId(id);
            userService.update(updateUserDto);
            model.addAttribute("updateUserDto", updateUserDto);
            return "redirect:/user/list";
        }

        return showUpdateForm(id, model);
    }

    /**
     * Manage the delete button.
     * To delete the User.
     * Call the deleteById of the UserService to delete the User linked.
     *
     * @param id the id of the User linked.
     * @return the list.html of the user template folder.
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(
            @PathVariable("id") Integer id) {
        userService.deleteById(id);
        return "redirect:/user/list";
    }
}
