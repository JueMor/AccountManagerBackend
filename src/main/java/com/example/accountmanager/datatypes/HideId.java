package com.example.accountmanager.datatypes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"id", "password"})
public interface HideId {
}
