package com.example.java17restdocsdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveDto {

    @NotBlank(message = "'name' must not be blank.")
    @Size(max = 100, message = "'name' length must be 100 or less.")
    private String name;

    @NotNull(message = "'age' must not be null.")
    private Integer age;
}
