package com.example.insightlogfe.utilities;

import android.util.Log;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import retrofit2.Response;

public class Utilities {


    public static void logError(String err) {
        Log.i("[ERROR]", "-->" + err);
    }

    public static void logMessage(String msg) {

        Log.i("[INFO]", "-->" + msg);
    }

    public static void logApiError(Response response) {

        if (response != null) {

            logMessage(
                    "--------------------" +
                            "Error Response:\n"
                            + response
                            + "\nError Response Body:\n"
                            + response.body() +
                            "\nError Code:\n"
                            + response.code() +
                            "-----------------");
        } else {
            logMessage("Api response is null");
        }
    }

    public static void logApiResponse(Response response) {

        if (response != null) {
            logMessage("Response:\n"
                    + response
                    + "\n"
                    + response.body() +
                    "\n Response Code:"
                    + response.code());
        } else {
            logMessage("Api response is null");
        }
    }

}
