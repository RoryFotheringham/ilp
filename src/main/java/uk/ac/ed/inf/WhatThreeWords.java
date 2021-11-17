package uk.ac.ed.inf;
import com.google.gson.Gson;
import com.what3words.javawrapper.response.Coordinates;

public class WhatThreeWords {
        private static final String DETAILS_NAME = "details.json";
        private static final String WORDS_LOCATION = "words";
        Coordinates coordinates;

        private static String w3wToAddress(String w3wString, String machineName, String port){
                String[] words = w3wString.split("\\.");
                String urlString = ("http://" + machineName + ":" + port + "/" + WORDS_LOCATION + "/" +
                        words[0] + "/" + words[1] + "/" + words[2] + "/" + DETAILS_NAME);
                return urlString;
        }

        public static double[] convertToCoordinates(String w3wString, String machineName, String port){
                Client client = new Client();
                String urlString = w3wToAddress(w3wString, machineName, port);
                String w3wJson = client.getResponse(urlString);
                WhatThreeWords w3w = new Gson().fromJson(String.valueOf(w3wJson), WhatThreeWords.class);

                double[] coords = new double[2];
                coords[0] = w3w.coordinates.getLng();
                coords[1] = w3w.coordinates.getLat();
                return coords;
        }
}
