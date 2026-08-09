package com.subhash.ims.service;

import com.subhash.ims.dto.JwtResponse;
import com.subhash.ims.dto.LoginRequest;
import com.subhash.ims.dto.RegisterRequest;
import com.subhash.ims.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    JwtResponse login(LoginRequest request);
}
