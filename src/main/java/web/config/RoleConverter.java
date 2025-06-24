package web.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import web.model.Role;
import web.service.RoleService;

@Component
public class RoleConverter implements Converter<String, Role> {

    private final RoleService roleService;

    public RoleConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Role convert(String source) {
        try {
            Long id = Long.parseLong(source);
            return roleService.findById(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid role id: " + source);
        }
    }
}
