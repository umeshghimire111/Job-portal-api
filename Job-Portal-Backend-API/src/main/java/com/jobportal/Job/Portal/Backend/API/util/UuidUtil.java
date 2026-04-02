package com.jobportal.Job.Portal.Backend.API.util;

import java.util.UUID;

public class UuidUtil {

    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }
}
