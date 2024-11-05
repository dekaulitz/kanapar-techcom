package com.github.dekalitz.kanaparktechcom.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.github.dekalitz.kanaparktechcom.domain.staticvariable.DateConstant;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @NotBlank
    private String id;
    @JsonFormat(pattern = DateConstant.DATE_TIME_FORMAT)
    @JsonSerialize(using = DateSerializer.class)
    private Date createdAt;
    @JsonFormat(pattern = DateConstant.DATE_TIME_FORMAT)
    @JsonSerialize(using = DateSerializer.class)
    private Date updatedAt;
    private String updatedBy;
    private String createdBy;
}
