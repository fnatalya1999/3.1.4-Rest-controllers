package web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dto.UserDto;
import web.model.Role;
import web.model.User;
import web.service.AdminService;
import web.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/data")
    @ResponseBody
    public List<UserDto> getAllUsers() {
        return adminService.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @GetMapping("/new")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "new";
    }

    @GetMapping("/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = adminService.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        return "edit";
    }

    @GetMapping("/data/{id}")
    @ResponseBody
    public UserDto getUserById(@PathVariable Long id) {
        User user = adminService.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return new UserDto(user);
    }


    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        adminService.save(user);
        return "redirect:/admin";
    }

    @PostMapping(consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> addUserJson(@RequestBody User user) {
        adminService.save(user);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> updateUserJson(@RequestBody User user, @PathVariable Long id) {
        user.setId(id);
        adminService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteUserJson(@PathVariable Long id) {
        adminService.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable Long id) {
        adminService.updateUser(user);
        return "redirect:/admin";
    }



    @GetMapping("/roles")
    @ResponseBody
    public List<Role> getAllRoles() {
        return roleService.findAll();
    }
}
