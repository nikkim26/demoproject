package com.example.demoproject;


import com.example.demoproject.Controller.UserController;
import com.example.demoproject.Entity.User;
import com.example.demoproject.Service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest({UserController.class})
public class UserTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @InjectMocks
    private UserController userController;

    public UserTest() {
    }

    @Test
    public void testGetAllUsers() throws Exception {
        Long userId1 = 1L;
        String userName1 = "User 1";
        String userEmail1 = "user1@example.com";
        User user1 = new User();
        user1.setUserId(userId1);
        user1.setUsername(userName1);
        user1.setEmail(userEmail1);
        Long userId2 = 2L;
        String userName2 = "User 2";
        String userEmail2 = "user2@example.com";
        User user2 = new User();
        user2.setUserId(userId2);
        user2.setUsername(userName2);
        user2.setEmail(userEmail2);
        List<User> sampleUsers = Arrays.asList(user1, user2);
        Mockito.when(this.userService.getAllUsers()).thenReturn(sampleUsers);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users", new Object[0]).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> returnedUsers = (List)objectMapper.readValue(responseContent, new TypeReference<List<User>>() {
        });
        Assertions.assertEquals(2, returnedUsers.size());
        Assertions.assertEquals(userId1, ((User)returnedUsers.get(0)).getUserId());
        Assertions.assertEquals(userName1, ((User)returnedUsers.get(0)).getUsername());
        Assertions.assertEquals(userEmail1, ((User)returnedUsers.get(0)).getEmail());
        Assertions.assertEquals(userId2, ((User)returnedUsers.get(1)).getUserId());
        Assertions.assertEquals(userName2, ((User)returnedUsers.get(1)).getUsername());
        Assertions.assertEquals(userEmail2, ((User)returnedUsers.get(1)).getEmail());
        ((UserService)Mockito.verify(this.userService)).getAllUsers();
    }

    @Test
    public void testGetUserById() throws Exception {
        User sampleUser = new User();
        sampleUser.setUserId(1L);
        sampleUser.setUsername("User 1");
        sampleUser.setEmail("user1@example.com");
        Mockito.when(this.userService.getUserById(1L)).thenReturn(ResponseEntity.ok(sampleUser));
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1", new Object[0]).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        User returnedUser = (User)objectMapper.readValue(responseContent, User.class);
        Assertions.assertEquals(1L, returnedUser.getUserId());
        Assertions.assertEquals("User 1", returnedUser.getUsername());
        Assertions.assertEquals("user1@example.com", returnedUser.getEmail());
        ((UserService)Mockito.verify(this.userService)).getUserById(1L);
    }

    @Test
    public void testUpdateUser() throws Exception {
        Long userId = 1L;
        String updatedUserName = "Updated User";
        String updatedUserEmail = "updateduser@example.com";
        User updatedUser = new User();
        updatedUser.setUserId(userId);
        updatedUser.setUsername(updatedUserName);
        updatedUser.setEmail(updatedUserEmail);
        Mockito.when(this.userService.updateUser(ArgumentMatchers.anyLong(), (User)ArgumentMatchers.any(User.class))).thenReturn(ResponseEntity.ok(updatedUser));
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.put("/api/users/" + userId, new Object[0]).contentType(MediaType.APPLICATION_JSON).content((new ObjectMapper()).writeValueAsString(updatedUser))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        User returnedUser = (User)objectMapper.readValue(responseContent, User.class);
        Assertions.assertEquals(userId, returnedUser.getUserId());
        Assertions.assertEquals(updatedUserName, returnedUser.getUsername());
        Assertions.assertEquals(updatedUserEmail, returnedUser.getEmail());
        ((UserService)Mockito.verify(this.userService)).updateUser((Long)ArgumentMatchers.eq(userId), (User)ArgumentMatchers.any(User.class));
    }
}
