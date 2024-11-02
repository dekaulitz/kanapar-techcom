package com.github.dekalitz.kanaparktechcom.handler.controller;

import com.github.dekalitz.kanaparktechcom.application.dto.BaseResponse;
import com.github.dekalitz.kanaparktechcom.application.dto.UserRegistrationResultDto;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.usecase.users.GetDetailRegistration;
import com.github.dekalitz.kanaparktechcom.domain.model.UserModel;
import com.github.dekalitz.kanaparktechcom.domain.service.userservice.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController extends BaseApiController {
    private final UserService userService;

    private final GetDetailRegistration getDetailRegistration;

    @Autowired
    public UserController(UserService userService, GetDetailRegistration getDetailRegistration, HttpServletRequest request) {
        super(request);
        this.userService = userService;
        this.getDetailRegistration = getDetailRegistration;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<List<UserModel>>> getAll() {
        BaseResponse<List<UserModel>> response = new BaseResponse<>("OK", userService.findAll(), Collections.emptyList());
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<UserRegistrationResultDto>> getDetailRegistration(@PathVariable String id) throws ApplicationException {
        UserRegistrationResultDto userRegistrationResultDto = getDetailRegistration.execute(id);
        BaseResponse<UserRegistrationResultDto> response = new BaseResponse<>("OK", userRegistrationResultDto, Collections.emptyList());
        return ResponseEntity.ok(response);
    }


}
