package com.lizaveta.contactsapp.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public final class Contact {
    private String name;
    private String phone;
    private String photoUri;
}