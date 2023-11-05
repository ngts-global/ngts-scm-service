package com.ngts.auth.service;

import com.ngts.auth.entity.Roles;
import com.ngts.auth.entity.UserRoles;
import com.ngts.auth.entity.Users;
import com.ngts.auth.entity.dto.UsersDTO;
import com.ngts.auth.payload.response.UserInfoResponse;
import com.ngts.auth.repository.RolesRepository;
import com.ngts.auth.repository.UserRolesRepository;
import com.ngts.auth.repository.UsersRepository;
import com.ngts.auth.vo.UsersQueryVO;
import com.ngts.auth.vo.UsersUpdateVO;
import com.ngts.auth.vo.UsersVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UsersService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private RolesRepository rolesRepository;

    public Integer save(UsersVO vO) {
        Users bean = new Users();
        BeanUtils.copyProperties(vO, bean);
        bean = usersRepository.save(bean);
        return bean.getId();
    }

    public void delete(Integer id) {
        usersRepository.deleteById(id);
    }

    public void update(Integer id, UsersUpdateVO vO) {
        Users bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        usersRepository.save(bean);
    }

    public UsersDTO getById(Integer id) {
        Users original = requireOne(id);
        return toDTO(original);
    }

    public Page<UsersDTO> query(UsersQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private UsersDTO toDTO(Users original) {
        UsersDTO bean = new UsersDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Users requireOne(Integer id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }


    /**
     *
     * @param email
     * @param password
     * @return
     */
    public UserInfoResponse findAllUsersByEmailAndPassword(String email, String password){
        List<Users> usersList =  usersRepository.findAllUsersByEmailAndPassword(email, password)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + email));
        if(usersList != null & usersList.size() == 1){
            UsersDTO bean = new UsersDTO();
            BeanUtils.copyProperties(usersList.get(0), bean);
            UserRoles userRoles = userRolesRepository.findByUserId(bean.getId()) .orElseThrow(() -> new NoSuchElementException("Role not found for the email  : " + bean.getEmail()));
            Roles roles1 = rolesRepository.findById(userRoles.getRoleId()).orElseThrow(() -> new NoSuchElementException("Role not found for the email  : " + bean.getEmail()));;

            UserInfoResponse userInfoResponse = new UserInfoResponse();
            userInfoResponse.setEmail(bean.getEmail());
            userInfoResponse.setUsername(bean.getUsername());
            userInfoResponse.setRoleName(roles1.getName().name());
            return userInfoResponse;
        }else {
            return null;
        }
        //return null;
    }

    public List<UsersDTO> getAllRegUsers() {
        return usersRepository
                .findAll()
                .stream()
                .map(this::mapToUsersDTO)
                .collect(Collectors.toList());
    }

    private UsersDTO mapToUsersDTO(Users users) {
       // TypeMap<Users, UsersDTO> propertyMapper = this.mapper.createTypeMap(Users.class, UsersDTO.class);
        //propertyMapper.addMappings(mapper -> mapper.skip(UsersDTO::setPassword));
        return mapper.map(users, UsersDTO.class);
    }
}
