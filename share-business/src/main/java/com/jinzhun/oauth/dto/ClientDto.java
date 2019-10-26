package com.jinzhun.oauth.dto;

import java.util.List;
import java.util.Set;

import com.jinzhun.oauth.model.Client;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientDto extends Client {

    private static final long serialVersionUID = 1475637288060027265L;

    private List<Long> permissionIds;

    private Set<Long> serviceIds;
}
