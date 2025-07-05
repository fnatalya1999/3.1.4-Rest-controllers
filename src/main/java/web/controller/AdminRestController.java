package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.dto.UserDto;
import web.model.Role;
import web.model.User;
import web.service.AdminService;
import web.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final AdminService adminService;
    private final RoleService roleService;

    @Autowired
    public AdminRestController(AdminService adminService, RoleService roleService) {
        this.adminService = adminService;
        this.roleService = roleService;
    }

    @GetMapping("/data")
    public List<UserDto> getAllUsers() {
        return adminService.findAllUser().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @GetMapping("/data/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        User user = adminService.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return new UserDto(user);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> addUserJson(@RequestBody User user) {
        adminService.save(user);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<?> updateUserJson(@RequestBody User user, @PathVariable Long id) {
        user.setId(id);
        adminService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserJson(@PathVariable Long id) {
        adminService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.findAllRole();
    }
}
