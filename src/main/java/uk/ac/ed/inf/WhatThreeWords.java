package uk.ac.ed.inf;
import com.google.gson.Gson;
import com.what3words.javawrapper.response.Coordinates;

/**
 * class handles conversion from what3Words to LongLat
 */
public class WhatThreeWords {
        private static final String DETAILS_NAME = "details.json";
        private static final String WORDS_LOCATION = "words";
        Coordinates coordinates;

        /**
         * takes a what3Words address and creates a string which is the path for finding the coordinates described by the what3Words on the webserver
         * @param w3wString what 3 words string
         * @param machineName machine name
         * @param port port of web server
         * @return the path to the coordinates on the server
         */
        private static String w3wToAddress(String w3wString, String machineName, String port){
                String[] words = w3wString.split("\\.");
                String urlString = ("http://" + machineName + ":" + port + "/" + WORDS_LOCATION + "/" +
                        words[0] + "/" + words[1] + "/" + words[2] + "/" + DETAILS_NAME);
                return urlString;
        }

        /**
         * converts a what3Words address to a pair of doubles which represent the longitude and latitude coordinates respectively
         * @param w3wString what3Words address
         * @param machineName machine name
         * @param port port of webserver
         * @return pair of doubles which represent the longitude and latitude coordinates respectively
         */
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
