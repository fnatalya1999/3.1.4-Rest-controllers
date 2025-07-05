package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.AdminService;
import web.service.RoleService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final RoleService roleService;

    @Autowired
    public AdminController(AdminService adminService, RoleService roleService) {
        this.adminService = adminService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String allUsers() {
        return "users";
    }

    @GetMapping("/new")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAllRole());
        return "new";
    }

    @GetMapping("/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = adminService.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAllRole());
        return "edit";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        adminService.save(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable Long id) {
        adminService.updateUser(user);
        return "redirect:/admin";
    }
}
