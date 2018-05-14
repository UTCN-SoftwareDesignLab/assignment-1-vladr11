import application.Constants;
import application.model.Role;
import application.repository.RoleRepository;
import application.services.RoleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class RoleServiceTest {
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Before
    public void before() {
        roleService = new RoleService(roleRepository);

        Role role1 = new Role();
        role1.setId(new Long(1));
        role1.setName(Constants.Roles.ADMINISTRATOR);

        Role role2 = new Role();
        role2.setId(new Long(2));
        role2.setName(Constants.Roles.EMPLOYEE);

        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role1);
        roles.add(role2);

        Mockito.when(roleRepository.findAll()).thenReturn(roles);
        Optional<Role> optional = Optional.of(role1);
        Mockito.when(roleRepository.findById(new Long(1))).thenReturn(optional);
        Mockito.when(roleRepository.findByName(Constants.Roles.ADMINISTRATOR)).thenReturn(role1);
    }

    @Test
    public void testFindAll() {
        Iterable<Role> roles = roleService.findAll();
        ArrayList<Role> genreArrayList = new ArrayList<>();
        roles.forEach(genreArrayList::add);
        Assert.assertEquals(genreArrayList.size(), 2);
    }

    @Test
    public void testFindById() {
        Role role = roleService.findById(new Long(1));
        Assert.assertEquals(role.getId(), new Long(1));
    }

    @Test
    public void testFindByType() {
        Role role = roleService.findByName(Constants.Roles.ADMINISTRATOR);
        Assert.assertEquals(role.getName(), Constants.Roles.ADMINISTRATOR);
    }
}
