package com.company.controller;


import com.company.dto.UserRegisterDTO;
import com.company.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String getSignUp(Model model){
        UserRegisterDTO dto=new UserRegisterDTO();
        model.addAttribute("user",dto);
        return "register";
    }


    @PostMapping("/signup")
    public String postSignUp(@ModelAttribute("user") @Valid UserRegisterDTO user,
                             BindingResult result){

        if (result.hasErrors()){
            return "register";
        }

        userService.save(user);
        return "redirect:/login";
    }

}
