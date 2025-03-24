/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.npd.starspost.utils;

/**
 *
 * @author midas
 */
public class EscapeHTMLTags {

// private static final ArrayList<Byte> symbolToEscape = new ArrayList<>();
//
// static {
// symbolToEscape.add(new Byte("\"\"".getBytes()[0])); // double quote ""
// symbolToEscape.add(new Byte("\"".getBytes()[0])); // single quote "
// symbolToEscape.add(new Byte("#".getBytes()[0])); // hash mark (no HTML named
// entity) #
// symbolToEscape.add(new Byte("&".getBytes()[0])); // ampersand &
// symbolToEscape.add(new Byte("'".getBytes()[0])); // apostrophe, aka single
// quote '
// symbolToEscape.add(new Byte("<".getBytes()[0])); // less than <
// symbolToEscape.add(new Byte(">".getBytes()[0])); // greater than >
// }
//
// public static String cleanStringFromHTMLSpecialSymbols(String text) {
// byte[] textBytes = text.getBytes();
// byte[] clearedText = new byte[textBytes.length];
// int i=0;
// for(byte b: textBytes) {
// if(!symbolToEscape.contains(b)) {
// clearedText[i++] = b;
// }
// }
// return new String(clearedText);
// }
    /*
    private static Map<String,String[]> SanitazeParameters(HttpServletRequest request, Model model) {
        Map<String, String[]> sanitazedProps = new HashMap<>();
        request.getParameterMap().forEach((String key, String[]value) -> {
            key = cleanStringFromHTMLSpecialSymbols(key);
            List<String> values = new ArrayList<>();
           Arrays.stream(value).forEach(val -> {
               values.add(cleanStringFromHTMLSpecialSymbols(val));
            });
            sanitazedProps.put(key,values.toArray(new String[0]));
        });
        return sanitazedProps;
    }
     */
}


