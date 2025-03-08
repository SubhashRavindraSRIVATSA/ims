package com.subhash.ims.service;

import com.subhash.ims.dto.LoginRequest;
import com.subhash.ims.dto.RegisterRequest;
import com.subhash.ims.dto.Response;
import com.subhash.ims.dto.UserDTO;
import com.subhash.ims.entity.User;

public interface UserService {
    Response registerUser(RegisterRequest registerRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUsers();
    User getCurrentLoggedInUser();
    Response updateUser(Long id, UserDTO userDTO);
    Response deleteUser(Long id);
    Response getUserTransactions(Long id);
}
