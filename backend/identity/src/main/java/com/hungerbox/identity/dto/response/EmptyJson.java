package com.hungerbox.identity.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize // Ensures it serializes to "{}" instead of null
public class EmptyJson {
    // This class is intentionally empty
}